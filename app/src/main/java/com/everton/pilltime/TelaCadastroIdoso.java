package com.everton.pilltime;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;
import com.everton.pilltime.api.ApiUser;
import com.everton.pilltime.api.Retrofit;
import com.everton.pilltime.databinding.ActivityTelaCadastroIdosoBinding;
import com.everton.pilltime.dto.EnderecoDTO;
import com.everton.pilltime.dto.IdosoDTO;
import com.everton.pilltime.models.TipoUsuario;
import com.everton.pilltime.user.UserRole;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaCadastroIdoso extends AppCompatActivity {

    private ActivityTelaCadastroIdosoBinding binding;
    private DateFormat dateFormatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTelaCadastroIdosoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.btSalvarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validarCampos()) {
                    Toast.makeText(TelaCadastroIdoso.this, "Erro na validação dos campos. Verifique as informações e tente novamente.", Toast.LENGTH_LONG).show();
                    return;
                }


            String nome = binding.edNomeCadastroIdoso.getText().toString().trim();
            String email = binding.edEmailCadastroIdoso.getText().toString().trim();
            String senha = binding.edSenhaCadastroIdoso.getText().toString().trim();
            String cpf = binding.edCPFCadastroIdoso.getText().toString().trim();
            String telefone = binding.edTelefoneCadastroIdoso.getText().toString().trim();
            String dataNascimentoString = binding.edDataNascCadastroIdoso.getText().toString().trim();
            String estado = binding.edEstadoCadastroCuidador.getText().toString().trim();
            String cidade = binding.edCidadeCadastroIdoso.getText().toString().trim();
            String bairro = binding.edBairroCadastroIdoso.getText().toString().trim();
            String rua = binding.edRuaCadastroIdoso.getText().toString().trim();
            String numero = binding.edNumeroCadastroCuidador.getText().toString().trim();
            String complemento = binding.edComplementoCadastroIdoso.getText().toString().trim();
            String cpfCuidador = binding.edCPFCuidadorCadIdoso.getText().toString().trim();
            String obsIdoso = binding.edObsIdoso.getText().toString().trim();

                Date dataNascimento = formataData(dataNascimentoString);
                if (dataNascimento == null) {
                    Toast.makeText(TelaCadastroIdoso.this, "Data de nascimento inválida.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (nome.isEmpty() || senha.isEmpty() || email.isEmpty() || cpf.isEmpty() || telefone.isEmpty() || estado.isEmpty() || cidade.isEmpty() || bairro.isEmpty() || rua.isEmpty() || numero.isEmpty()) {
                    Toast.makeText(TelaCadastroIdoso.this, "Todos os campos são obrigatórios.", Toast.LENGTH_SHORT).show();
                    return;
                }

                EnderecoDTO enderecoDTO = new EnderecoDTO(estado, cidade, bairro, rua, Integer.parseInt(numero), complemento);

                IdosoDTO idoso = new IdosoDTO();
                idoso.setNome(nome);
                idoso.setLogin(email);
                idoso.setSenha(senha);
                idoso.setEmail(email);
                idoso.setCpf(cpf);
                idoso.setObservacao(obsIdoso);
                idoso.setTelefone(telefone);
                idoso.setDataNascimento(dataNascimento);
                idoso.setEndereco(enderecoDTO);
                idoso.setTipoUsuario(TipoUsuario.I);
                idoso.setRole(UserRole.USER);
                idoso.setCpfCuidador(cpfCuidador);

                ApiUser apiUser = Retrofit.REGISTER_IDOSO();

                SharedPreferences sharedPreferences = getSharedPreferences("MyToken", MODE_PRIVATE);
                String token = sharedPreferences.getString("token", "");

                Call<ResponseBody> call = apiUser.REGISTER_IDOSO(idoso);


                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()) {
                            Log.d("Cadastro Idoso", "Idoso cadastrado com sucesso");
                            showDialog("Cadastro Realizado", "Idoso cadastrado com sucesso!");
                            limparDadosUsuario();
                            limparDadosEndereco();
                        } else {
                            int statusCode = response.code();
                            Log.e("Cadastro Idoso", "Erro ao registrar, código de status HTTP: " + statusCode);
                        }
                        if (response.errorBody() != null) {
                            String errorString;
                            try {
                                errorString = response.errorBody().string();
                                Log.e("Cadastro Idoso", "Erro ao registrar: " + errorString);
                            } catch (IOException e) {
                                Log.e("Cadastro Idoso", "Erro ao ler o corpo de erro", e);
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        // Este código é executado quando ocorre uma falha na requisição
                        Toast.makeText(TelaCadastroIdoso.this, "Falha na comunicação: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


            }
        });

        binding.btLimparDadosUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limparDadosUsuario();
            }
        });

        binding.btLimparDadosEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limparDadosEndereco();
            }
        });
    }


    private boolean validarCampos() {
        Log.d("CadastroIdoso", "Iniciando validação dos campos");

        String nome = binding.edNomeCadastroIdoso.getText().toString().trim();
        String senha = binding.edSenhaCadastroIdoso.getText().toString().trim();
        String cpf = binding.edCPFCadastroIdoso.getText().toString().trim();
        String dataNascimento = binding.edDataNascCadastroIdoso.getText().toString().trim();
        String estado = binding.edEstadoCadastroCuidador.getText().toString().trim();
        String cidade = binding.edCidadeCadastroIdoso.getText().toString().trim();

        // Validação do nome
        if (nome.length() < 3 || nome.length() > 50) {
            Log.e("CadastroIdoso", "Erro na validação do nome");
            binding.edNomeCadastroIdoso.setError("O nome deve ter entre 2 e 50 caracteres.");
            return false;
        }

        // Validação da senha
        if (senha.length() < 6) {
            Log.e("CadastroIdoso", "Erro na validação da senha");
            binding.edSenhaCadastroIdoso.setError("A senha deve ter mais de 6 caracteres.");
            return false;
        }

        // Validação do CPF
        if (cpf.length() != 11) {
            Log.e("CadastroIdoso", "Erro na validação do CPF");
            binding.edCPFCadastroIdoso.setError("O CPF deve ter 11 caracteres.");
            return false;
        }

        // Validação da cidade
        if (cidade.isEmpty()) {
            Log.e("CadastroIdoso", "Erro na validação da cidade");
            binding.edCidadeCadastroIdoso.setError("Cidade não pode estar vazia.");
            return false;
        }

        // Validação do estado
        if (estado.length() != 2 || !estado.matches("[A-Z]{2}")) {
            Log.e("CadastroIdoso", "Erro na validação do estado");
            binding.edEstadoCadastroCuidador.setError("Estado deve ser uma sigla válida (ex: PR)!");
            return false;
        }

        // Validação da data de nascimento
        if (!validarDataNascimento(dataNascimento)) {
            Log.e("CadastroIdoso", "Erro na validação da data de nascimento");
            return false;
        }

        Log.d("CadastroIdoso", "Validação dos campos concluída com sucesso");
        return true;
    }
    private boolean validarDataNascimento(String dataNascimento) {
        SimpleDateFormat sdf;
        if (dataNascimento.contains("/")) {
            sdf = new SimpleDateFormat("dd/MM/yyyy");
        } else {
            sdf = new SimpleDateFormat("ddMMyyyy");
        }
        sdf.setLenient(false);
        try {
            Date dataNasc = sdf.parse(dataNascimento);

        } catch (ParseException e) {
            binding.edDataNascCadastroIdoso.setError("Formato de data inválido. Use dd/MM/yyyy ou ddmmyyyy.");
            Toast.makeText(this, "Formato de data inválido. Use dd/MM/yyyy ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private Date formataData(String data) {

        String dateString = "dd/MM/yyyy"; // Defina o formato da data aqui

        SimpleDateFormat formatter = new SimpleDateFormat(dateString, Locale.getDefault());
        Date dataFormatada = null;

        try {
            dataFormatada = formatter.parse(data);
        } catch (ParseException e) {

            e.printStackTrace();
        }

        return dataFormatada;

    }

    private void showDatePickerDialog() {
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                TelaCadastroIdoso.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(year, monthOfYear, dayOfMonth);
                        String selectedDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedCalendar.getTime());
                        binding.edDataNascCadastroIdoso.setText(selectedDate);

                    }
                },

                newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH)
        );



        datePickerDialog.show();

    }


    private void showDialog(String title, String message) {
        new AlertDialog.Builder(TelaCadastroIdoso.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }





    private void limparDadosUsuario() {
        binding.edNomeCadastroIdoso.setText("");
        binding.edEmailCadastroIdoso.setText("");
        binding.edSenhaCadastroIdoso.setText("");
        binding.edCPFCadastroIdoso.setText("");
        binding.edTelefoneCadastroIdoso.setText("");
        binding.edDataNascCadastroIdoso.setText("");
    }

    private void limparDadosEndereco() {
        binding.edCidadeCadastroIdoso.setText("");
        binding.edEstadoCadastroCuidador.setText("");
        binding.edBairroCadastroIdoso.setText("");
        binding.edRuaCadastroIdoso.setText("");
        binding.edNumeroCadastroCuidador.setText("");
    }

}

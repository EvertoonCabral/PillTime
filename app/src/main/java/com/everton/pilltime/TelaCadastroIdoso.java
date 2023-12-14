package com.everton.pilltime;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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


        binding.edDataNascCadastroIdoso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        binding.btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });


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

    private boolean validarCPF(String cpf) {
        if (cpf.isEmpty()) {
            Toast.makeText(TelaCadastroIdoso.this, "CPF é obrigatório.", Toast.LENGTH_SHORT).show();
            return false;
        }

        cpf = cpf.replaceAll("\\D+", "");

        if (cpf.length() != 11) {
            Toast.makeText(TelaCadastroIdoso.this, "CPF deve ter 11 dígitos.", Toast.LENGTH_SHORT).show();
            return false;
        }

        int[] pesoCPF = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int soma = 0;

        for (int i = 0; i < 9; i++) {
            soma += Integer.parseInt(cpf.substring(i, i + 1)) * pesoCPF[i];
        }

        int numero = 11 - (soma % 11);
        String digitoVerificador1 = (numero > 9) ? "0" : String.valueOf(numero);

        soma = 0;
        pesoCPF = new int[]{11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        for (int i = 0; i < 10; i++) {
            soma += Integer.parseInt(cpf.substring(i, i + 1)) * pesoCPF[i];
        }

        numero = 11 - (soma % 11);
        String digitoVerificador2 = (numero > 9) ? "0" : String.valueOf(numero);

        return cpf.equals(cpf.substring(0, 9) + digitoVerificador1 + digitoVerificador2);
    }

    private boolean validarNome(String nome) {
        if (nome.isEmpty()) {
            Toast.makeText(TelaCadastroIdoso.this, "O nome é obrigatório.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (nome.length() < 2 || nome.length() > 50) {
            Toast.makeText(TelaCadastroIdoso.this, "O nome deve ter entre 2 e 50 caracteres.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!nome.matches("[a-zA-Z\\s]+")) { // Regex para permitir apenas letras e espaços
            Toast.makeText(TelaCadastroIdoso.this, "O nome não deve conter caracteres especiais.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private boolean validarEmail(String email) {
        if (email.isEmpty()) {
            Toast.makeText(TelaCadastroIdoso.this, "Email é obrigatório.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(TelaCadastroIdoso.this, "Email inválido.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean validarSenha(String senha) {
        if (senha.isEmpty()) {
            Toast.makeText(TelaCadastroIdoso.this, "A senha é obrigatória.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (senha.length() < 8) {
            Toast.makeText(TelaCadastroIdoso.this, "A senha deve ter pelo menos 8 caracteres.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!senha.matches(".*[A-Z].*")) {
            Toast.makeText(TelaCadastroIdoso.this, "A senha deve conter pelo menos uma letra maiúscula.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!senha.matches(".*[a-z].*")) {
            Toast.makeText(TelaCadastroIdoso.this, "A senha deve conter pelo menos uma letra minúscula.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!senha.matches(".*\\d.*")) {
            Toast.makeText(TelaCadastroIdoso.this, "A senha deve conter pelo menos um número.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!senha.matches(".*[!@#$%^&*+=?-].*")) {
            Toast.makeText(TelaCadastroIdoso.this, "A senha deve conter pelo menos um caractere especial (!@#$%^&*+=?-).", Toast.LENGTH_SHORT).show();
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
                        limparDadosUsuario();
                        limparDadosEndereco();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }


    private boolean validarCampos() {
        String nome = binding.edNomeCadastroIdoso.getText().toString().trim();
        String email = binding.edEmailCadastroIdoso.getText().toString().trim();
        String senha = binding.edSenhaCadastroIdoso.getText().toString().trim();
        String cpf = binding.edCPFCadastroIdoso.getText().toString().trim();
        String dataNascimento = binding.edDataNascCadastroIdoso.getText().toString().trim();

        return validarNome(nome) && validarEmail(email) && validarSenha(senha) && validarCPF(cpf) && validarDataNascimento(dataNascimento);
    }



    private void limparDadosUsuario() {
        binding.edNomeCadastroIdoso.setText("");
        binding.edEmailCadastroIdoso.setText("");
        binding.edSenhaCadastroIdoso.setText("");
        binding.edCPFCadastroIdoso.setText("");
        binding.edTelefoneCadastroIdoso.setText("");
        binding.edDataNascCadastroIdoso.setText("");
        binding.edObsIdoso.setText("");
        binding.edCPFCuidadorCadIdoso.setText("");
    }

    private void limparDadosEndereco() {
        binding.edCidadeCadastroIdoso.setText("");
        binding.edEstadoCadastroCuidador.setText("");
        binding.edBairroCadastroIdoso.setText("");
        binding.edRuaCadastroIdoso.setText("");
        binding.edNumeroCadastroCuidador.setText("");
        binding.edComplementoCadastroIdoso.setText("");
    }

}

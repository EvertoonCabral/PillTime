package com.everton.pilltime;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.everton.pilltime.api.ApiCep;
import com.everton.pilltime.api.ApiUser;
import com.everton.pilltime.api.Retrofit;
import com.everton.pilltime.databinding.ActivityTelaCadastroIdosoBinding;
import com.everton.pilltime.dto.EnderecoDTO;
import com.everton.pilltime.dto.IdosoDTO;
import com.everton.pilltime.models.CEP;
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

        setupDateMask();

        EditText edSenhaCadastroIdoso = findViewById(R.id.edSenhaCadastroIdoso);

        edSenhaCadastroIdoso.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (edSenhaCadastroIdoso.getRight() - edSenhaCadastroIdoso.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if(edSenhaCadastroIdoso.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                            edSenhaCadastroIdoso.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            edSenhaCadastroIdoso.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_blue_closed, 0);
                        } else {
                            edSenhaCadastroIdoso.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            edSenhaCadastroIdoso.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_blue, 0);
                        }
                        return true;
                    }
                }
                return false;
            }
        });


        binding.edCepIdoso.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {



            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String cep = editable.toString();

                buscarCep(cep);


            }
        });


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

                        Intent intent = new Intent(TelaCadastroIdoso.this, TelaPrincipal.class); // Substitua TelaPrincipal pela sua tela principal
                        startActivity(intent);
                        finish();

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
        binding.edCepIdoso.setText("");
        binding.edCidadeCadastroIdoso.setText("");
        binding.edEstadoCadastroCuidador.setText("");
        binding.edBairroCadastroIdoso.setText("");
        binding.edRuaCadastroIdoso.setText("");
        binding.edNumeroCadastroCuidador.setText("");
        binding.edComplementoCadastroIdoso.setText("");
    }

    private void buscarCep(String cep){


        ApiCep apiCep = Retrofit.API_CEP();

        Call <CEP> call = apiCep.consultarCEP(cep);

        call.enqueue(new Callback<CEP>() {
            @Override
            public void onResponse(Call<CEP> call, Response<CEP> response) {

                if (response.isSuccessful()) {


                    CEP cep = response.body();


                    binding.edEstadoCadastroCuidador.setText(cep.getUf());
                    binding.edCidadeCadastroIdoso.setText(cep.getLocalidade());
                    binding.edBairroCadastroIdoso.setText(cep.getBairro());
                    binding.edRuaCadastroIdoso.setText(cep.getLogradouro());
                    binding.edComplementoCadastroIdoso.setText(cep.getComplemento());


                    Log.d("Consulta CEP"," " + cep.getBairro()+cep.getLogradouro());

                }

            }

            @Override
            public void onFailure(Call<CEP> call, Throwable t) {

            }
        });


    }

    private void setupDateMask() {
        binding.edDataNascCadastroIdoso.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        if(mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon-1);

                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);

                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    binding.edDataNascCadastroIdoso.setText(current);
                    binding.edDataNascCadastroIdoso.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }



}

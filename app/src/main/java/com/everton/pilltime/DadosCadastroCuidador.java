package com.everton.pilltime;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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

import com.everton.pilltime.alarme.AlarmeActivity;
import com.everton.pilltime.api.ApiCep;
import com.everton.pilltime.api.ApiUser;
import com.everton.pilltime.api.Retrofit;
import com.everton.pilltime.databinding.ActivityDadosCadastroCuidadorBinding;
import com.everton.pilltime.dto.EnderecoDTO;
import com.everton.pilltime.models.CEP;
import com.everton.pilltime.models.Cuidador;
import com.everton.pilltime.models.Endereco;
import com.everton.pilltime.models.TipoUsuario;
import com.everton.pilltime.user.RegisterDTO;
import com.everton.pilltime.user.UserRole;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DadosCadastroCuidador extends AppCompatActivity {

    private ActivityDadosCadastroCuidadorBinding binding;
    private DateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDadosCadastroCuidadorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setupDateMask();


        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());



        binding.edCepCuidador.addTextChangedListener(new TextWatcher() {
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


        binding.btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDatePickerDialog();

            }
        });

        binding.edDataNascCadastroCuidador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDatePickerDialog();

            }
        });


        EditText edSenhaCadastroCuidador = findViewById(R.id.edSenhaCadastroCuidador);

        edSenhaCadastroCuidador.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (edSenhaCadastroCuidador.getRight() - edSenhaCadastroCuidador.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if(edSenhaCadastroCuidador.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                            edSenhaCadastroCuidador.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            edSenhaCadastroCuidador.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_blue_closed, 0);
                        } else {
                            edSenhaCadastroCuidador.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            edSenhaCadastroCuidador.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_blue, 0);
                        }
                        return true;
                    }
                }
                return false;
            }
        });


        binding.btnRegistrarCuidador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = binding.edNomeCadastroCuidador.getText().toString().trim();
                String senha = binding.edSenhaCadastroCuidador.getText().toString().trim();
                String email = binding.edEmailCadastroCuidador.getText().toString().trim();
                String cpf = binding.edCPFCadastroCuidador.getText().toString().trim();
                String telefone = binding.edTelefone.getText().toString();
                String dataNascimentoString = binding.edDataNascCadastroCuidador.getText().toString().trim();
                String estado = binding.edEstadoCadastroCuidador.getText().toString().trim();
                String cidade = binding.edCidadeCadastroCuidador.getText().toString().trim();
                String bairro = binding.edBairroCadastroCuidador.getText().toString().trim();
                String rua = binding.edRuaCadastroCuidador.getText().toString().trim();
                String numero = binding.edNumeroCadastroCuidador.getText().toString().trim();
                String complemento = binding.edObservacaoCadastroCuidador.getText().toString().trim();

                Date dataNascimento = formataData(dataNascimentoString);

                if (!validarDataNascimento(dataNascimentoString) ||
                        !validarEmail(email) ||
                        !validarCPF(cpf) ||
                        !validarSenha(senha)) {
                    return; // Se alguma validação falhar, retorna e não continua com o registro
                }

                // Verifica se os campos obrigatórios estão preenchidos
                if (nome.isEmpty() || telefone.isEmpty() || estado.isEmpty() || cidade.isEmpty() || bairro.isEmpty() || rua.isEmpty() || numero.isEmpty()) {
                    Toast.makeText(DadosCadastroCuidador.this, "Todos os campos são obrigatórios.", Toast.LENGTH_SHORT).show();
                    return;
                }


                EnderecoDTO enderecoDTO = new EnderecoDTO(estado, cidade, bairro, rua, Integer.parseInt(numero), complemento);
                Endereco endereco = EnderecoDTO.fromDTO(enderecoDTO);

                // Criar o objeto Pessoa
                Cuidador pessoa = new Cuidador();
                pessoa.setNome(nome);
                pessoa.setEmail(email);
                pessoa.setCpf(cpf);
                pessoa.setTelefone(telefone);
                pessoa.setDataNascimento(dataNascimento);
                System.out.println("Aqui: "+telefone);
                pessoa.setEndereco(endereco);
                pessoa.setTipoUsuario(TipoUsuario.C);

                // Criar o objeto RegisterDTO
                RegisterDTO registerDTO = new RegisterDTO();
                registerDTO.setLogin(email);
                registerDTO.setSenha(senha);
                registerDTO.setRole(UserRole.ADMIN);
                registerDTO.setPessoa(pessoa);


                ApiUser apiUser = Retrofit.REGISTER_USER();

                SharedPreferences sharedPreferences = getSharedPreferences("MyToken", MODE_PRIVATE);
                String token = sharedPreferences.getString("token", "");


                Call<String> call = apiUser.registerCuidador(registerDTO);



                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        if(response.isSuccessful()) {
                            Toast.makeText(DadosCadastroCuidador.this, "Cadastrado com Sucesso!", Toast.LENGTH_SHORT).show();

                        } else {
                            int statusCode = response.code();
                            Log.e("CadastroCuidador", "Erro ao registrar, código de status HTTP: " + statusCode + response.body());
                        }
                        if (response.errorBody() != null) {
                            String errorString;
                            try {
                                errorString = response.errorBody().string();
                                Log.e("CadastroCuidador", "Erro ao registrar: " + errorString);
                            } catch (IOException e) {
                                Log.e("CadastroCuidador", "Erro ao ler o corpo de erro", e);
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                       exibirMensagemSucesso();

                    }
                });




            }
        });


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
                DadosCadastroCuidador.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(year, monthOfYear, dayOfMonth);
                        String selectedDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedCalendar.getTime());
                        binding.edDataNascCadastroCuidador.setText(selectedDate);

                    }
                },

                newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH)
        );



        datePickerDialog.show();

    }

    private boolean validarDataNascimento(String dataNascimentoString) {
        if (dataNascimentoString.isEmpty()) {
            Toast.makeText(DadosCadastroCuidador.this, "Data de nascimento é obrigatória.", Toast.LENGTH_SHORT).show();
            return false;
        }

        Date dataNascimento;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            dataNascimento = formatter.parse(dataNascimentoString);
            if (dataNascimento == null) {
                Toast.makeText(DadosCadastroCuidador.this, "Formato de data inválido.", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (ParseException e) {
            Toast.makeText(DadosCadastroCuidador.this, "Formato de data inválido.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Verifica se a data de nascimento é futura
        if (dataNascimento.after(new Date())) {
            Toast.makeText(DadosCadastroCuidador.this, "A data de nascimento não pode ser no futuro.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Verificar se a pessoa tem pelo menos 18 anos
        Calendar dataLimite = Calendar.getInstance();
        dataLimite.add(Calendar.YEAR, -18);
        if (dataNascimento.after(dataLimite.getTime())) {
            Toast.makeText(DadosCadastroCuidador.this, "A idade deve ser pelo menos 18 anos.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private boolean validarEmail(String email) {
        if (email.isEmpty()) {
            Toast.makeText(DadosCadastroCuidador.this, "Email é obrigatório.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(DadosCadastroCuidador.this, "Email inválido.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean validarCPF(String cpf) {
        if (cpf.isEmpty()) {
            Toast.makeText(DadosCadastroCuidador.this, "CPF é obrigatório.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("\\D+", "");

        if (cpf.length() != 11 || cpf.matches(cpf.charAt(0) + "{11}")) {
            Toast.makeText(DadosCadastroCuidador.this, "CPF inválido.", Toast.LENGTH_SHORT).show();
            return false;
        }

        int[] pesosCPF = {10, 9, 8, 7, 6, 5, 4, 3, 2};

        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (cpf.charAt(i) - '0') * pesosCPF[i];
        }

        int primeiroDigito = soma % 11 < 2 ? 0 : 11 - soma % 11;
        soma = 0;

        int[] pesosCPF2 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
        for (int i = 0; i < 10; i++) {
            soma += (cpf.charAt(i) - '0') * pesosCPF2[i];
        }

        int segundoDigito = soma % 11 < 2 ? 0 : 11 - soma % 11;

        return cpf.equals(cpf.substring(0, 9) + primeiroDigito + segundoDigito);
    }

    private boolean validarSenha(String senha) {
        if (senha.isEmpty()) {
            Toast.makeText(DadosCadastroCuidador.this, "A senha é obrigatória.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (senha.length() < 8) {
            Toast.makeText(DadosCadastroCuidador.this, "A senha deve ter pelo menos 8 caracteres.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!senha.matches(".*[A-Z].*")) {
            Toast.makeText(DadosCadastroCuidador.this, "A senha deve conter pelo menos uma letra maiúscula.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!senha.matches(".*[a-z].*")) {
            Toast.makeText(DadosCadastroCuidador.this, "A senha deve conter pelo menos uma letra minúscula.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!senha.matches(".*\\d.*")) {
            Toast.makeText(DadosCadastroCuidador.this, "A senha deve conter pelo menos um número.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!senha.matches(".*[!@#$%^&*+=?-].*")) {
            Toast.makeText(DadosCadastroCuidador.this, "A senha deve conter pelo menos um caractere especial (!@#$%^&*+=?-).", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void limparCampos() {
        binding.edCepCuidador.setText("");
        binding.edNomeCadastroCuidador.setText("");
        binding.edSenhaCadastroCuidador.setText("");
        binding.edEmailCadastroCuidador.setText("");
        binding.edCPFCadastroCuidador.setText("");
        binding.edTelefone.setText("");
        binding.edDataNascCadastroCuidador.setText("");
        binding.edEstadoCadastroCuidador.setText("");
        binding.edCidadeCadastroCuidador.setText("");
        binding.edBairroCadastroCuidador.setText("");
        binding.edRuaCadastroCuidador.setText("");
        binding.edNumeroCadastroCuidador.setText("");
        binding.edObservacaoCadastroCuidador.setText("");
    }



    private void exibirMensagemSucesso() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DadosCadastroCuidador.this);
        builder.setTitle("Sucesso");
        builder.setMessage("Você foi cadastrado com sucesso!");
        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss();
            limparCampos();

            Intent intent = new Intent(DadosCadastroCuidador.this, MainActivity.class); // Substitua TelaPrincipal pela sua tela principal
            startActivity(intent);
            finish();


        });
        AlertDialog dialog = builder.create();
        dialog.show();
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
                    binding.edCidadeCadastroCuidador.setText(cep.getLocalidade());
                    binding.edBairroCadastroCuidador.setText(cep.getBairro());
                    binding.edRuaCadastroCuidador.setText(cep.getLogradouro());
                    binding.edObservacaoCadastroCuidador.setText(cep.getComplemento());


                    Log.d("Consulta CEP"," " + cep.getBairro()+cep.getLogradouro());

                }

            }

            @Override
            public void onFailure(Call<CEP> call, Throwable t) {

            }
        });


    }


    private void setupDateMask() {
        binding.edDataNascCadastroCuidador.addTextChangedListener(new TextWatcher() {
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
                    binding.edDataNascCadastroCuidador.setText(current);
                    binding.edDataNascCadastroCuidador.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }



}
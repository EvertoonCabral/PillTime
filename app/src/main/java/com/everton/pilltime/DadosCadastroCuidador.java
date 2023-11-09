package com.everton.pilltime;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.DatePicker;
import android.widget.Toast;

import com.everton.pilltime.api.ApiUser;
import com.everton.pilltime.api.Retrofit;
import com.everton.pilltime.databinding.ActivityDadosCadastroCuidadorBinding;
import com.everton.pilltime.dto.EnderecoDTO;
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

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

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



        binding.btnRegistrarCuidador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String nome = binding.edNomeCadastroCuidador.getText().toString().trim();
                String senha = binding.edSenhaCadastroCuidador.getText().toString().trim();
                String email = binding.edEmailCadastroCuidador.getText().toString().trim();
                String cpf = binding.edCPFCadastroCuidador.getText().toString().trim();
                String telefone = binding.edTelefoneCadastroCuidador.getText().toString().trim();
                String dataNascimentoString = binding.edDataNascCadastroCuidador.getText().toString().trim();
                String estado = binding.edEstadoCadastroCuidador.getText().toString().trim();
                String cidade = binding.edCidadeCadastroCuidador.getText().toString().trim();
                String bairro = binding.edBairroCadastroCuidador.getText().toString().trim();
                String rua = binding.edRuaCadastroCuidador.getText().toString().trim();
                String numero = binding.edNumeroCadastroCuidador.getText().toString().trim();
                String complemento = binding.edObservacaoCadastroCuidador.getText().toString().trim();

                Date dataNascimento = formataData(dataNascimentoString);
                if (dataNascimento == null) {
                    Toast.makeText(DadosCadastroCuidador.this, "Data de nascimento inválida.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (nome.isEmpty() || senha.isEmpty() || email.isEmpty() || cpf.isEmpty() || telefone.isEmpty() || estado.isEmpty() || cidade.isEmpty() || bairro.isEmpty() || rua.isEmpty() || numero.isEmpty()) {
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

                            Toast.makeText(DadosCadastroCuidador.this, response.body(), Toast.LENGTH_LONG).show();
                        } else {
                            int statusCode = response.code();
                            Log.e("CadastroCuidador", "Erro ao registrar, código de status HTTP: " + statusCode);
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
                        // Este código é executado quando ocorre uma falha na requisição
                        Toast.makeText(DadosCadastroCuidador.this, "Falha na comunicação: " + t.getMessage(), Toast.LENGTH_LONG).show();
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



}
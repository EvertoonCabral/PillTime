package com.everton.pilltime;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.everton.pilltime.api.ApiCuidador;
import com.everton.pilltime.api.ApiRemedio;
import com.everton.pilltime.api.Retrofit;
import com.everton.pilltime.databinding.ActivityTelaCadastroRemedioBinding;
import com.everton.pilltime.dto.RemedioDTO;
import com.everton.pilltime.models.Remedio;

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

public class TelaCadastroRemedio extends AppCompatActivity {


    private DateFormat dateFormatter;

    private ActivityTelaCadastroRemedioBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTelaCadastroRemedioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());


        SharedPreferences sharedPreferences = getSharedPreferences("MyToken", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        Long id = sharedPreferences.getLong("id", 0);



        binding.btnCadastroRemedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nome = binding.edNomeRemedio.getText().toString().trim();
                String marca = binding.edMarcaRemedio.getText().toString().trim();
                String dosagem = binding.edDosagemRemedio.getText().toString().trim();
                String formaFarmaceutico = binding.edFormaFarmaceutica.getText().toString().trim();
                String dataValidadeRemedioString = binding.edDataValidadeRemedio.getText().toString().trim();

                Date dataValidade = formataData(dataValidadeRemedioString);
                if (dataValidade == null) {
                    Toast.makeText(TelaCadastroRemedio.this, "Data de nascimento inválida.", Toast.LENGTH_SHORT).show();
                    return;
                }
                String observacoes = binding.edObservacaoRemedio.getText().toString();


                RemedioDTO remedio = new RemedioDTO();
                remedio.setNome(nome);
                remedio.setMarcaRemedio(marca);
                remedio.setDosagem(dosagem);
                remedio.setFormaFarmaceutico(formaFarmaceutico);
                remedio.setDataValidade(dataValidade);
                remedio.setObservacoes(observacoes);

                ApiRemedio apiRemedio = Retrofit.REGISTER_REMEDIO();
                ApiCuidador apiCuidador = Retrofit.POST_REMEDIO_TO_CUIDADOR();

/*
                Call<RemedioDTO> call = apiRemedio.REGISTER_REMEDIO(token, remedio);


                call.enqueue(new Callback<RemedioDTO>() {
                    @Override
                    public void onResponse(Call<RemedioDTO> call, Response<RemedioDTO> response) {

                        if (response.isSuccessful()){
                        }else{
                            int statusCode = response.code();
                            Log.e("Cadastro Remedio", "Erro ao registrar, código de status HTTP: " + statusCode);
                        }
                        if (response.errorBody() != null) {
                            String errorString;
                            try {
                                errorString = response.errorBody().string();
                                Log.e("Cadastro Remedio", "Erro ao registrar: " + errorString);
                            } catch (IOException e) {
                                Log.e("Cadastro Remedio", "Erro ao ler o corpo de erro", e);
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<RemedioDTO> call, Throwable t) {

                        Toast.makeText(TelaCadastroRemedio.this, "ERRO na API meu chapa!: " + t.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
*/
                //adiciona a lista do cuidador

                Call<String> call1 = apiCuidador.POST_REMEDIO_TO_CUIDADOR(token, id, remedio);

                call1.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        if (response.isSuccessful()){
                            Toast.makeText(TelaCadastroRemedio.this, "Remedio Cadastrado com Sucesso!", Toast.LENGTH_LONG).show();
                        }else{
                            int statusCode = response.code();
                            Log.e("Cadastro Remedio", "Erro ao registrar, código de status HTTP: " + statusCode);
                        }
                        if (response.errorBody() != null) {
                            String errorString;
                            try {
                                errorString = response.errorBody().string();
                                Log.e("Cadastro Remedio", "Erro ao registrar: " + errorString);
                            } catch (IOException e) {
                                Log.e("Cadastro Remedio", "Erro ao ler o corpo de erro", e);
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                        Toast.makeText(TelaCadastroRemedio.this, "Remedio Cadastrado com Sucesso!", Toast.LENGTH_LONG).show();


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
                TelaCadastroRemedio.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(year, monthOfYear, dayOfMonth);
                        String selectedDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedCalendar.getTime());
                        binding.edDataValidadeRemedio.setText(selectedDate);

                    }
                },

                newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH)
        );



        datePickerDialog.show();

    }


}
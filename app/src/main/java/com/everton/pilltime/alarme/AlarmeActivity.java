package com.everton.pilltime.alarme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.everton.pilltime.TelaRelatorios;
import com.everton.pilltime.api.ApiCuidador;
import com.everton.pilltime.api.Retrofit;
import com.everton.pilltime.databinding.ActivityAlarmeBinding; // Importe o binding gerado
import com.everton.pilltime.dto.RemedioDTO;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlarmeActivity extends AppCompatActivity {

    private ActivityAlarmeBinding binding;
    private MaterialTimePicker picker;
    private Calendar calendar;
    private String token;
    private Long idCuidador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlarmeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        calendar = Calendar.getInstance();

        binding.buttonTimePicker.setOnClickListener(view -> showTimePicker());



        SharedPreferences sharedPreferences = AlarmeActivity.this.getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        Long idCuidador = sharedPreferences.getLong("id", 0);
        String token = sharedPreferences.getString("token", " ");

        // Carregar remédios
        loadRemedios();
    }

    private void loadRemedios() {
        ApiCuidador apiCuidador = Retrofit.GET_ALL_REMEDIO_CUIDADOR();
        Call<List<RemedioDTO>> call = apiCuidador.GET_ALL_REMEDIO_CUIDADOR("Bearer " + token, idCuidador);

        call.enqueue(new Callback<List<RemedioDTO>>() {
            @Override
            public void onResponse(Call<List<RemedioDTO>> call, Response<List<RemedioDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    updateRemedioSpinner(response.body());
                } else {
                    // Tratar falha
                }
            }

            @Override
            public void onFailure(Call<List<RemedioDTO>> call, Throwable t) {
            }
        });





    }

    private void showTimePicker() {
        picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(calendar.get(Calendar.HOUR_OF_DAY))
                .setMinute(calendar.get(Calendar.MINUTE))
                .setTitleText("Selecionar Hora do Alarme")
                .build();

        picker.addOnPositiveButtonClickListener(v -> {
            calendar.set(Calendar.HOUR_OF_DAY, picker.getHour());
            calendar.set(Calendar.MINUTE, picker.getMinute());
            String selectedTime = String.format("%02d:%02d", picker.getHour(), picker.getMinute());

            binding.textViewSelectedTime.setText(selectedTime);
        });

        picker.show(getSupportFragmentManager(), "MaterialTimePicker");
    }


    private void updateRemedioSpinner(List<RemedioDTO> remedioList) {
        ArrayAdapter<RemedioDTO> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, remedioList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerRemedios.setAdapter(adapter);

        binding.spinnerRemedios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RemedioDTO selectedRemedio = (RemedioDTO) parent.getItemAtPosition(position);
                String descricao = selectedRemedio.getNome() + " - " + selectedRemedio.getFormaFarmaceutico() + " - " + selectedRemedio.getDosagem();
                binding.editTextDescription.setText(descricao);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                binding.editTextDescription.setText("");
            }
        });

    }



}

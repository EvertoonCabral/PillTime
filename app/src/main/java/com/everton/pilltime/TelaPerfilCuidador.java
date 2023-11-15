package com.everton.pilltime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.everton.pilltime.api.ApiCuidador;
import com.everton.pilltime.api.ApiPessoa;
import com.everton.pilltime.api.Retrofit;
import com.everton.pilltime.databinding.ActivityTelaPerfilCuidadorBinding; // Importação do binding
import com.everton.pilltime.dto.CuidadorDTO;
import com.everton.pilltime.models.Pessoa;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TelaPerfilCuidador extends AppCompatActivity {

    private ActivityTelaPerfilCuidadorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTelaPerfilCuidadorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        SharedPreferences  sharedPreferences = TelaPerfilCuidador.this.getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        Long id = sharedPreferences.getLong("id", 0);


        ApiCuidador apiCuidador = Retrofit.GET_CUIDADOR();

        Call<CuidadorDTO> call = apiCuidador.GET_CUIDADOR("Bearer" + token, id);


        call.enqueue(new Callback<CuidadorDTO>() {
            @Override
            public void onResponse(Call<CuidadorDTO> call, Response<CuidadorDTO> response) {
                


            }

            @Override
            public void onFailure(Call<CuidadorDTO> call, Throwable t) {

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}

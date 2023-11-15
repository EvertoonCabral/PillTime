package com.everton.pilltime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.everton.pilltime.api.ApiCuidador;
import com.everton.pilltime.api.ApiError;
import com.everton.pilltime.api.ApiErrorParser;
import com.everton.pilltime.api.ApiPessoa;
import com.everton.pilltime.api.Retrofit;
import com.everton.pilltime.databinding.ActivityTelaPerfilCuidadorBinding;
import com.everton.pilltime.dto.CuidadorDTO;
import com.everton.pilltime.dto.PessoaDTOGet;
import com.everton.pilltime.models.Pessoa;

import java.util.List;

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

        SharedPreferences sharedPreferences = TelaPerfilCuidador.this.getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        Long id = sharedPreferences.getLong("id", 0);

        Log.e("", "ID usuario logado: "+id);


        ApiPessoa apiPessoa = Retrofit.GET_PESSOA();

        Call<PessoaDTOGet> call = apiPessoa.GET_PESSOA("Bearer" + token, id);


        call.enqueue(new Callback<PessoaDTOGet>() {
            @Override
            public void onResponse(Call<PessoaDTOGet> call, Response<PessoaDTOGet> response) {


                if (response.isSuccessful()) {

                    PessoaDTOGet pessoa = response.body();

                    binding.edNome.setText(pessoa.getNome());
                    binding.editTextCPF.setText(pessoa.getCpf());
                    binding.edDataNascimento.setText((CharSequence) pessoa.getDataNascimento());
                    binding.editTextTelefoneCuidador.setText(pessoa.getTelefone());
                    binding.edEmailCuidador.setText(pessoa.getEmail());

                    Log.d("DEBUG", "Código de resposta da API: " + response.code());
                } else {
                    try {
                        ApiError apiError = ApiErrorParser.parseError(response.errorBody().string());
                        if (apiError != null) {
                            List<String> errorMessages = apiError.getErrorMessages();
                            String errorMessage = errorMessages.get(0);
                            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("DEBUG", "Resposta da API: " + response.body().toString());
                            Toast.makeText(getApplicationContext(), "Atenção! Contate o suporte.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        Log.e("ERROR", ex.getMessage());
                    }
                }


            }

            @Override
            public void onFailure(Call<PessoaDTOGet> call, Throwable t) {

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}

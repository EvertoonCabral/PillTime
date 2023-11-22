package com.everton.pilltime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.everton.pilltime.api.ApiError;
import com.everton.pilltime.api.ApiErrorParser;
import com.everton.pilltime.api.ApiPessoa;
import com.everton.pilltime.api.Retrofit;
import com.everton.pilltime.databinding.ActivityTelaPerfilCuidadorBinding;
import com.everton.pilltime.dto.PessoaDTOGet;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

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

        Log.d("DEBUG", "ID do usuário logado: " + id);

        ApiPessoa apiPessoa = Retrofit.GET_PESSOA();

        Call<PessoaDTOGet> call = apiPessoa.GET_PESSOA(token, id);

        call.enqueue(new Callback<PessoaDTOGet>() {
            @Override
            public void onResponse(Call<PessoaDTOGet> call, Response<PessoaDTOGet> response) {
                if (response.isSuccessful()) {
                    PessoaDTOGet pessoa = response.body();

                    Log.d("DEBUG", "Resposta da API: " + pessoa);

                    if (pessoa != null) {
                        binding.edNome.setText(pessoa.getNome() != null ? pessoa.getNome() : "");
                        binding.editTextCPF.setText(pessoa.getCpf() != null ? pessoa.getCpf() : "");

                        if (pessoa.getDataNascimento() != null) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                            String formattedDate = dateFormat.format(pessoa.getDataNascimento());
                            Log.d("DEBUG", "Data de Nascimento Formatada: " + formattedDate);
                            binding.edDataNascimento.setText(formattedDate);

                        } else {
                            binding.edDataNascimento.setText("");
                        }

                        binding.editTextTelefoneCuidador.setText(pessoa.getTelefone() != null ? pessoa.getTelefone() : "");
                        binding.edEmailCuidador.setText(pessoa.getEmail() != null ? pessoa.getEmail() : "");
                    } else {
                        Log.e("ERROR", "O objeto pessoa é nulo.");
                        Toast.makeText(getApplicationContext(), "Pessoa não encontrada", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("ERROR", "Resposta não bem-sucedida da API. Código: " + response.code());
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
                        Log.e("ERROR", "Exceção ao processar erro da API: " + ex.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<PessoaDTOGet> call, Throwable t) {
                Log.e("ERROR", "Falha na chamada da API: " + t.getMessage());
            }
        });


        binding.btnVolarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TelaPerfilCuidador.this,TelaPrincipal.class);
                startActivity(intent);
                finish();

            }
        });

    }
}

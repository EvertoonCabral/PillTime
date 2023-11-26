package com.everton.pilltime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.everton.pilltime.api.ApiIdoso;
import com.everton.pilltime.api.ApiPessoa;
import com.everton.pilltime.api.Retrofit;
import com.everton.pilltime.databinding.ActivityTelaPerfilIdosoBinding;
import com.everton.pilltime.dto.IdosoDTO;
import com.everton.pilltime.dto.PessoaDTO;

import java.text.SimpleDateFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class telaPerfilIdoso extends AppCompatActivity {

    private ActivityTelaPerfilIdosoBinding binding;
    private ApiPessoa apiPessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTelaPerfilIdosoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferences = getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        Long id = sharedPreferences.getLong("id", 0);

        apiPessoa = Retrofit.GET_PESSOA();
        ApiIdoso apiIdoso = Retrofit.GET_IDOSO_BY_ID();
        Call<IdosoDTO> callIdoso = apiIdoso.GET_IDOSO_BY_ID(token, id);

        callIdoso.enqueue(new Callback<IdosoDTO>() {
            @Override
            public void onResponse(Call<IdosoDTO> call, Response<IdosoDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    IdosoDTO idosoDTO = response.body();
                    updateUIWithIdosoData(idosoDTO);
                    String cpfCuidador = idosoDTO.getCpfCuidador();
                    if (cpfCuidador != null && !cpfCuidador.isEmpty()) {
                        fetchCuidadorData(cpfCuidador, token);
                    } else {
                        Log.d("telaPerfilIdoso", "Idoso não possui cuidador associado");
                    }
                } else {
                    Log.e("telaPerfilIdoso", "Erro ao buscar dados do idoso");
                }
            }

            @Override
            public void onFailure(Call<IdosoDTO> call, Throwable t) {
                Log.e("telaPerfilIdoso", "Falha na chamada da API: " + t.getMessage());
            }
        });

        binding.btnVoltarTelaPrincipal.setOnClickListener(view -> {
            Intent intent = new Intent(telaPerfilIdoso.this, TelaPrincipalIdoso.class);
            startActivity(intent);
            finish();
        });
    }

    private void updateUIWithIdosoData(IdosoDTO idosoDTO) {
        binding.textViewNome.setText(idosoDTO.getNome());
        binding.editTextCPF.setText(idosoDTO.getCpf());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String dataNascimentoFormatada = idosoDTO.getDataNascimento() != null ? dateFormat.format(idosoDTO.getDataNascimento()) : "";
        binding.editTextIdade.setText(dataNascimentoFormatada);
    }

    private void fetchCuidadorData(String cpfCuidador, String token) {
        Call<PessoaDTO> callCuidador = apiPessoa.getPessoaByCPF(token, cpfCuidador);

        callCuidador.enqueue(new Callback<PessoaDTO>() {
            @Override
            public void onResponse(Call<PessoaDTO> call, Response<PessoaDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PessoaDTO pessoaDTO = response.body();
                    updateUICuidador(pessoaDTO);
                } else {
                    Log.e("telaPerfilIdoso", "Erro ao buscar dados do cuidador");
                }
            }

            @Override
            public void onFailure(Call<PessoaDTO> call, Throwable t) {
                Log.e("telaPerfilIdoso", "Falha ao buscar dados do cuidador: " + t.getMessage());
            }
        });
    }

    private void updateUICuidador(PessoaDTO pessoaDTO) {
        binding.editTextNomeCuidador.setText(pessoaDTO.getNome());
        binding.edTelefoneCuidador.setText(pessoaDTO.getTelefone());
    }
}

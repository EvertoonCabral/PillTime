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
import com.everton.pilltime.api.ApiPessoa; // Certifique-se de que você tem uma API assim
import com.everton.pilltime.api.Retrofit;
import com.everton.pilltime.databinding.ActivityTelaPerfilIdosoBinding;
import com.everton.pilltime.dto.CuidadorDTO;
import com.everton.pilltime.dto.IdosoDTO;
import com.everton.pilltime.dto.PessoaDTO; // Supondo que esta é a classe base

import java.text.SimpleDateFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class telaPerfilIdoso extends AppCompatActivity {

    private ActivityTelaPerfilIdosoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTelaPerfilIdosoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferences = getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        Long id = sharedPreferences.getLong("id", 0);

        ApiIdoso apiIdoso = Retrofit.GET_IDOSO_BY_ID();
        Call<IdosoDTO> call = apiIdoso.GET_IDOSO_BY_ID(token, id);

        call.enqueue(new Callback<IdosoDTO>() {
            @Override
            public void onResponse(Call<IdosoDTO> call, Response<IdosoDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    IdosoDTO idosoDTO = response.body();
                    updateUIWithIdosoData(idosoDTO, token);
                } else {
                    Toast.makeText(telaPerfilIdoso.this, "Erro ao buscar dados do idoso", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<IdosoDTO> call, Throwable t) {
                Log.e("ERROR", "Falha na chamada da API: " + t.getMessage());
                Toast.makeText(telaPerfilIdoso.this, "Falha na comunicação com a API", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnVoltarTelaPrincipal.setOnClickListener(view -> {
            Intent intent = new Intent(telaPerfilIdoso.this, TelaPrincipal.class);
            startActivity(intent);
            finish();
        });
    }

    private void updateUIWithIdosoData(IdosoDTO idosoDTO, String token) {
        // Atualizando o nome do idoso
        binding.textViewNome.setText(idosoDTO.getNome() != null ? idosoDTO.getNome() : "Nome não disponível");

        // Atualizando o CPF do idoso
        binding.editTextCPF.setText(idosoDTO.getCpf() != null ? idosoDTO.getCpf() : "");

        // Formatando e definindo a data de nascimento
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String dataNascimentoFormatada = idosoDTO.getDataNascimento() != null ? dateFormat.format(idosoDTO.getDataNascimento()) : "";
        binding.editTextIdade.setText(dataNascimentoFormatada);

        // Buscando informações do cuidador associado
        fetchPessoaData(idosoDTO.getCpfCuidador(), token);
    }

    private void fetchPessoaData(String cpfCuidador, String token) {
        ApiPessoa apiPessoa = Retrofit.GET_PESSOA();
        Call<PessoaDTO> call = apiPessoa.getPessoaByCPF(token, cpfCuidador);

        call.enqueue(new Callback<PessoaDTO>() {
            @Override
            public void onResponse(Call<PessoaDTO> call, Response<PessoaDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PessoaDTO pessoaDTO = response.body();
                    updateUICuidador(pessoaDTO);
                } else {
                    Log.e("ERROR", "Erro ao buscar dados do cuidador");
                }
            }

            @Override
            public void onFailure(Call<PessoaDTO> call, Throwable t) {
                Log.e("ERROR", "Falha na chamada da API: " + t.getMessage());
            }
        });
    }

    private void updateUICuidador(PessoaDTO pessoaDTO) {
        // Aqui você precisa decidir como tratar PessoaDTO como CuidadorDTO
        // Esta é uma abordagem genérica, supondo que CuidadorDTO estende PessoaDTO
        binding.editTextNomeCuidador.setText(pessoaDTO.getNome() != null ? pessoaDTO.getNome() : "");
        binding.edTelefoneCuidador.setText(pessoaDTO.getTelefone() != null ? pessoaDTO.getTelefone() : "");
        // Atualizar outros campos conforme necessário
    }

}

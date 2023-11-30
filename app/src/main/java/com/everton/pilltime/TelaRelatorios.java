package com.everton.pilltime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.everton.pilltime.adapter.IdosoAdapter;
import com.everton.pilltime.adapter.RemedioAdapter;
import com.everton.pilltime.api.ApiCuidador;
import com.everton.pilltime.api.Retrofit;
import com.everton.pilltime.dto.IdosoDTO;
import com.everton.pilltime.dto.RemedioDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaRelatorios extends AppCompatActivity {


    private Spinner spinnerRelatorio;
    private RecyclerView recyclerView;

    private Button botaoVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_relatorios);

        spinnerRelatorio = findViewById(R.id.spinnerRelatorio);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        botaoVoltar = findViewById(R.id.btnVolarTelaRelatorio);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.opcoes_relatorio, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRelatorio.setAdapter(adapter);

        spinnerRelatorio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedOption = spinnerRelatorio.getSelectedItem().toString();

                SharedPreferences sharedPreferences = TelaRelatorios.this.getSharedPreferences("MyToken", Context.MODE_PRIVATE);
                Long idCuidador = sharedPreferences.getLong("id", 0);
                String token = sharedPreferences.getString("token", " ");

                if (selectedOption.equals("Idoso")) {
                    fetchIdososFromApi(idCuidador, token);
                } else if (selectedOption.equals("Remédio")) {
                    fetchRemediosFromApi(idCuidador, token);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Implementar se necessário
            }
        });


        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TelaRelatorios.this,TelaPrincipal.class);
                startActivity(intent);
                finish();

            }
        });



    }

    private void fetchRemediosFromApi(Long idCuidador, String token) {
        ApiCuidador apiCuidador = Retrofit.GET_ALL_REMEDIO_CUIDADOR();
        Call<List<RemedioDTO>> call = apiCuidador.GET_ALL_REMEDIO_CUIDADOR(token, idCuidador);

        call.enqueue(new Callback<List<RemedioDTO>>() {
            @Override
            public void onResponse(Call<List<RemedioDTO>> call, Response<List<RemedioDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    updateRecyclerView(response.body());
                    Log.d("DEBUG", response.body().toString());
                } else {
                    Log.d("DEBUG", "Deu erro");
                }
            }

            @Override
            public void onFailure(Call<List<RemedioDTO>> call, Throwable t) {
                Log.d("DEBUG", "Falhou " + t.getMessage());
            }
        });
    }

    private void fetchIdososFromApi(Long idCuidador, String token) {
        ApiCuidador apiCuidador = Retrofit.GET_ALL_REMEDIO_CUIDADOR(); // Verifique se este método retorna a instância correta
        Call<List<IdosoDTO>> call = apiCuidador.GET_ALL_IDOSOS_CUIDADOR(token, idCuidador);

        call.enqueue(new Callback<List<IdosoDTO>>() {
            @Override
            public void onResponse(Call<List<IdosoDTO>> call, Response<List<IdosoDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    updateIdososRecyclerView(response.body());
                } else {
                    // Trate o caso de resposta não bem-sucedida
                }
            }

            @Override
            public void onFailure(Call<List<IdosoDTO>> call, Throwable t) {
                // Trate o caso de falha na chamada da API
            }
        });
    }



    private void updateRecyclerView(List<RemedioDTO> remedioDtoList) {
        RemedioAdapter adapter = new RemedioAdapter(remedioDtoList);
        recyclerView.setLayoutManager(new LinearLayoutManager(TelaRelatorios.this));
        recyclerView.setAdapter(adapter);
    }

    private void updateIdososRecyclerView(List<IdosoDTO> idosoDtoList) {
        IdosoAdapter adapter = new IdosoAdapter(idosoDtoList);
        recyclerView.setAdapter(adapter);
    }


}

package com.everton.pilltime;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.everton.pilltime.api.ApiCuidador;
import com.everton.pilltime.api.Retrofit;
import com.everton.pilltime.databinding.ActivityTelaAlterarRemedioBinding;
import com.everton.pilltime.dto.RemedioDTO;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaAlterarRemedio extends AppCompatActivity {

    private ActivityTelaAlterarRemedioBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTelaAlterarRemedioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        carregarRemediosNoSpinner();

        binding.btnSalvarEditRemedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarAlteracoesRemedio();
            }
        });


    }

    private void carregarRemediosNoSpinner() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyToken", MODE_PRIVATE);
        Long idCuidador = sharedPreferences.getLong("id", 0);
        String token = sharedPreferences.getString("token", "");

        ApiCuidador apiCuidador = Retrofit.GET_ALL_REMEDIO_CUIDADOR();
        Call<List<RemedioDTO>> call = apiCuidador.GET_ALL_REMEDIO_CUIDADOR(token, idCuidador);

        call.enqueue(new Callback<List<RemedioDTO>>() {
            @Override
            public void onResponse(Call<List<RemedioDTO>> call, Response<List<RemedioDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Usando um ArrayAdapter customizado
                    ArrayAdapter<RemedioDTO> adapter = new ArrayAdapter(
                            TelaAlterarRemedio.this,
                            android.R.layout.simple_spinner_item,
                            response.body()) {

                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);
                            if (view instanceof TextView) {
                                ((TextView) view).setText(response.body().get(position).getNome());
                            }
                            return view;
                        }
                    };

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.spinnerNomeRemedio.setAdapter(adapter);

                    binding.spinnerNomeRemedio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            RemedioDTO remedioSelecionado = (RemedioDTO) parent.getItemAtPosition(position);
                            preencherCamposRemedio(remedioSelecionado);
                            Log.d("TelaAlterarRemedio", "Remédio selecionado: " + remedioSelecionado.getNome());
                            preencherCamposRemedio(remedioSelecionado);

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }
                }


            @Override
            public void onFailure(Call<List<RemedioDTO>> call, Throwable t) {
                // Trate o erro
            }
        });
    }


    private void salvarAlteracoesRemedio() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyToken", MODE_PRIVATE);
        Long idCuidador = sharedPreferences.getLong("id", 0);
        String token = sharedPreferences.getString("token", "");

        RemedioDTO remedioDTO = new RemedioDTO();
        remedioDTO.setNome(binding.spinnerNomeRemedio.getSelectedItem().toString());

        remedioDTO.setMarcaRemedio(binding.edMarcaRemedioConfig.getText().toString());
        remedioDTO.setDosagem(binding.edDosagemRemedioConfig.getText().toString());
        remedioDTO.setFormaFarmaceutico(binding.edFormaFarmaceuticaConfig.getText().toString());
        // remedioDTO.setDataValidade(/* Sua lógica de conversão de data aqui */);
        remedioDTO.setObservacoes(binding.edObservacaoRemedioConfig.getText().toString());

        ApiCuidador apiCuidador = Retrofit.GET_ALL_REMEDIO_CUIDADOR();
        Call<Void> call = apiCuidador.updateRemedioByNome(token, idCuidador, remedioDTO.getNome(), remedioDTO);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("UpdateRemedio", "Remédio atualizado com sucesso.");

                    // Mostrar diálogo de sucesso
                    AlertDialog.Builder builder = new AlertDialog.Builder(TelaAlterarRemedio.this);
                    builder.setTitle("Sucesso");
                    builder.setMessage("Remédio atualizado com sucesso.");
                    builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("UpdateRemedio", "Falha na atualização do remédio: " + errorBody);
                        Toast.makeText(TelaAlterarRemedio.this, "Erro: " + errorBody, Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("UpdateRemedio", "Erro na chamada da API: " + t.getMessage());
                Toast.makeText(TelaAlterarRemedio.this, "Erro na chamada da API.", Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void preencherCamposRemedio(RemedioDTO remedio) {
        binding.edMarcaRemedioConfig.setText(remedio.getMarcaRemedio());
        binding.edDosagemRemedioConfig.setText(remedio.getDosagem());
        binding.edObservacaoRemedioConfig.setText(remedio.getObservacoes());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String dataValidadeFormatada = sdf.format(remedio.getDataValidade());
        binding.edValidadeRemedioConfig.setText(dataValidadeFormatada);

        binding.edFormaFarmaceuticaConfig.setText(remedio.getFormaFarmaceutico());

        int spinnerPosition = ((ArrayAdapter<RemedioDTO>) binding.spinnerNomeRemedio.getAdapter()).getPosition(remedio);
        binding.spinnerNomeRemedio.setSelection(spinnerPosition);

    }

}

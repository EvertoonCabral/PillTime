package com.everton.pilltime.alarme;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.TimePickerDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.everton.pilltime.api.ApiCuidador;
import com.everton.pilltime.api.ApiIdoso;
import com.everton.pilltime.api.Retrofit;
import com.everton.pilltime.databinding.ActivityAlarmeBinding;
import com.everton.pilltime.dto.AlarmeDTOInsert;
import com.everton.pilltime.dto.IdosoDTO;
import com.everton.pilltime.dto.RemedioDTO;
import com.everton.pilltime.models.Idoso;
import com.everton.pilltime.models.Remedio;
import com.everton.pilltime.utils.ModelConvertUtil;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlarmeActivity extends AppCompatActivity {

    private static final String TAG = "AlarmeActivity";
    private ActivityAlarmeBinding binding;
    private MaterialTimePicker picker;
    private Calendar calendar;
    private String token;
    private Long idCuidador;

    private String  idosoCpfSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlarmeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        calendar = Calendar.getInstance();
        Log.d(TAG, "Activity criada. Inicializando componentes.");

        SharedPreferences sharedPreferences = getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        idCuidador = sharedPreferences.getLong("id", 0);

        binding.btnSelecionarHora.setOnClickListener(view -> {
            Log.d(TAG, "Botão 'Selecionar Hora' pressionado.");
            showTimePicker();
        });

        loadRemedios();
        loadIdosos();

        binding.btnSalvarAlarme.setOnClickListener(v -> {
            Alarme novoAlarme = coletarDadosFormulario();
            getIdosoCpf(novoAlarme.getIdoso().getCpf(), novoAlarme);
        });
    }


    private void salvarAlarme(Alarme alarme) {
        Log.d(TAG, "Iniciando processo para salvar o alarme.");

        ApiCuidador apiCuidador = Retrofit.POST_REMEDIO_TO_CUIDADOR();
        AlarmeDTOInsert alarmeDTO = new AlarmeDTOInsert(alarme.getTitulo(), alarme.getDescricao(), alarme.getHorarioAlarme());

        Call<String> call = apiCuidador.POST_ALARME_TO_IDOSO_LIST("Bearer " + token, idCuidador, alarme.getIdoso().getId(), alarmeDTO);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Alarme salvo com sucesso");
                    exibirMensagemSucesso();
                } else {
                    Log.e(TAG, "Erro ao salvar o alarme: " + response.code());
                    // Tratar erro
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "Falha ao tentar salvar o alarme: " + t.getMessage());
                // Tratar falha
            }
        });
    }


    private void getIdosoCpf(String cpf, Alarme alarme) {
        ApiIdoso apiIdoso = Retrofit.GET_IDOSO_BY_CPF();
        SharedPreferences sharedPreferences = getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        Log.d(TAG, "Enviando requisição para buscar idoso com CPF: " + cpf);

        Call<Idoso> call = apiIdoso.GET_IDOSO_BY_CPF("Bearer " + token, cpf);

        call.enqueue(new Callback<Idoso>() {
            @Override
            public void onResponse(Call<Idoso> call, Response<Idoso> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Idoso idoso = response.body();
                    Long idosoId = idoso.getId();

                    Log.d(TAG, "Idoso encontrado. ID: " + idosoId);

                    // Atualize o ID do idoso no objeto Alarme
                    alarme.getIdoso().setId(idosoId);

                    // Agora, chame o método salvarAlarme
                    salvarAlarme(alarme);
                } else {
                    Log.e(TAG, "Erro na busca do idoso. Código de resposta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Idoso> call, Throwable t) {
                Log.e(TAG, "Falha na busca do idoso. Erro: " + t.getMessage());
            }
        });
    }





    private void loadRemedios() {
        Log.d(TAG, "Carregando lista de remédios...");
        ApiCuidador apiCuidador = Retrofit.GET_ALL_REMEDIO_CUIDADOR();
        Call<List<RemedioDTO>> call = apiCuidador.GET_ALL_REMEDIO_CUIDADOR("Bearer " + token, idCuidador);

        call.enqueue(new Callback<List<RemedioDTO>>() {
            @Override
            public void onResponse(Call<List<RemedioDTO>> call, Response<List<RemedioDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Remédios carregados com sucesso. Total de remédios: " + response.body().size());
                    updateRemedioSpinner(response.body());
                } else {
                    Log.e(TAG, "Erro na resposta da API. Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<RemedioDTO>> call, Throwable t) {
                Log.e(TAG, "Falha na chamada da API. Erro: " + t.getMessage());
            }
        });
    }


    private void loadIdosos() {
        Log.d(TAG, "Carregando idosos...");
        ApiCuidador apiCuidador = Retrofit.GET_ALL_REMEDIO_CUIDADOR();
        Call<List<IdosoDTO>> call = apiCuidador.GET_ALL_IDOSOS_CUIDADOR("Bearer " + token, idCuidador);

        call.enqueue(new Callback<List<IdosoDTO>>() {
            @Override
            public void onResponse(Call<List<IdosoDTO>> call, Response<List<IdosoDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Idosos carregados com sucesso. Total de idosos: " + response.body().size());
                    updateIdosoSpinner(response.body());
                } else {
                    Log.e(TAG, "Erro na resposta da API. Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<IdosoDTO>> call, Throwable t) {
                Log.e(TAG, "Falha na chamada da API. Erro: " + t.getMessage());
            }
        });
    }



    private void showTimePicker() {
        Log.d(TAG, "Exibindo TimePicker.");
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
            Log.d(TAG, "Hora selecionada: " + selectedTime);
            binding.textViewSelectedTime.setText(selectedTime);
        });

        picker.show(getSupportFragmentManager(), "MaterialTimePicker");
    }

    private void updateRemedioSpinner(List<RemedioDTO> remedioList) {
        Log.d(TAG, "Atualizando Spinner de remédios.");

        ArrayAdapter<RemedioDTO> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, remedioList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.spinnerRemedios.setAdapter(adapter);

        binding.spinnerRemedios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RemedioDTO selectedRemedio = (RemedioDTO) parent.getItemAtPosition(position);
                String descricao = selectedRemedio.getNome() + " - " + selectedRemedio.getFormaFarmaceutico() + " - " + selectedRemedio.getDosagem();
                Log.d(TAG, "Remédio selecionado: " + selectedRemedio.getNome() + ", Descrição: " + descricao);
                binding.editTextDescription.setText(descricao);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                binding.editTextDescription.setText("");
            }
        });
    }

    private void updateIdosoSpinner(List<IdosoDTO> idosoList) {
        Log.d(TAG, "Atualizando Spinner de idosos.");
        ArrayAdapter<IdosoDTO> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, idosoList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerIdoso.setAdapter(adapter);

        binding.spinnerIdoso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                IdosoDTO selectedIdoso = (IdosoDTO) parent.getItemAtPosition(position);
                idosoCpfSelecionado = selectedIdoso.getCpf(); }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private Alarme coletarDadosFormulario() {
        Log.d(TAG, "Coletando dados do formulário.");

        String titulo = binding.editTextTitle.getText().toString();
        String descricao = binding.editTextDescription.getText().toString();
        Log.d(TAG, "Título: " + titulo + ", Descrição: " + descricao);

        RemedioDTO remedioSelecionadoDTO = (RemedioDTO) binding.spinnerRemedios.getSelectedItem();
        IdosoDTO idosoSelecionadoDTO = (IdosoDTO) binding.spinnerIdoso.getSelectedItem();
        Log.d(TAG, "Remédio Selecionado DTO: " + remedioSelecionadoDTO.toString());
        Log.d(TAG, "Idoso Selecionado DTO: " + idosoSelecionadoDTO.toString());

        LocalDateTime horarioAlarme = null;
        if (picker != null) {
            horarioAlarme = LocalDateTime.of(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.DAY_OF_MONTH),
                    picker.getHour(),
                    picker.getMinute());
            Log.d(TAG, "Horário do Alarme: " + horarioAlarme.toString());
        } else {
            // Trate o caso em que o picker é null (por exemplo, exibindo uma mensagem ao usuário)
            Log.e(TAG, "Picker é null. Horário do alarme não definido.");
            return null; // Retorne null ou lance uma exceção, dependendo da sua lógica de negócios
        }

        Alarme novoAlarme = new Alarme();
        novoAlarme.setTitulo(titulo);
        novoAlarme.setDescricao(descricao);
        novoAlarme.setHorarioAlarme(horarioAlarme);

        Remedio remedio = ModelConvertUtil.converterParaModeloRemedio(remedioSelecionadoDTO);
        Idoso idoso = ModelConvertUtil.converterParaModeloIdoso(idosoSelecionadoDTO);
        Log.d(TAG, "Remédio Convertido: " + (remedio != null ? remedio.getNome() : "null"));
        Log.d(TAG, "Idoso Convertido: " + (idoso != null ? idoso.getNome() : "null"));

        novoAlarme.setRemedio(remedio);
        novoAlarme.setIdoso(idoso);

        return novoAlarme;
    }


    private void exibirMensagemSucesso() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AlarmeActivity.this);
        builder.setTitle("Sucesso");
        builder.setMessage("Alarme cadastrado com sucesso!");
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }




}



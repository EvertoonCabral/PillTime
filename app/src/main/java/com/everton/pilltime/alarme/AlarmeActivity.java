package com.everton.pilltime.alarme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.TimePickerDialog;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.everton.pilltime.api.ApiCuidador;
import com.everton.pilltime.api.ApiFoto;

import com.everton.pilltime.api.Retrofit;
import com.everton.pilltime.databinding.ActivityAlarmeBinding;
import com.everton.pilltime.dto.AlarmeDTOInsert;
import com.everton.pilltime.dto.IdosoDTO;
import com.everton.pilltime.dto.RemedioDTO;
import com.everton.pilltime.dto.ResponseFotoDTO;
import com.everton.pilltime.models.Foto;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    private Long idFoto;


    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 1;

    private static final int CODIGO_REQUISICAO_ANEXO = 1002;

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
            AlarmeDTOInsert novoAlarme = coletarDadosFormulario();
            salvarAlarme(novoAlarme);
        });

        binding.btnTirarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (ContextCompat.checkSelfPermission(AlarmeActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AlarmeActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                } else {
                    dispatchTakePictureIntent();
                }


            }
        });


    }





    private void dispatchTakePictureIntent() {
        Log.d(TAG, "Iniciando Intent da Câmera");

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(AlarmeActivity.this.getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(AlarmeActivity.this, "Permissão da câmera negada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    if (imageBitmap != null) {
                        Log.d(TAG, "Imagem capturada com sucesso");
                        InputStream inputStream = convertBitmapToInputStream(imageBitmap);
                        enviarAnexoParaAPI(inputStream);
                    }
                }
            }
        } else if (requestCode == CODIGO_REQUISICAO_ANEXO && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri selectedFileUri = data.getData();
            try {
                InputStream inputStream = AlarmeActivity.this.getContentResolver().openInputStream(selectedFileUri);
                enviarAnexoParaAPI(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("DEBUG", "Erro ao ler arquivo", e);
            }
        } else {
            Log.d("DEBUG", "Não passou nas condições esperadas");
        }
    }


    private void enviarAnexoParaAPI(InputStream inputStream) {
        ApiFoto apiFoto = Retrofit.UPLOAD_FOTO();

        Log.d("DEBUG", "Chamou a rota nova");



        try {


            byte[] fileBytes = getBytesFromInputStream(inputStream);

            Log.d(TAG, "Tamanho dos bytes da imagem: " + fileBytes.length);

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), fileBytes);

            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "arquivo_selecionado", requestFile);

            SharedPreferences sharedPreferences = AlarmeActivity.this.getSharedPreferences("MyToken", Context.MODE_PRIVATE);
            token = sharedPreferences.getString("token", "");

            Call <ResponseFotoDTO> call = apiFoto.UPLOAD_FOTO(token, filePart);

            call.enqueue(new Callback<ResponseFotoDTO>() {
                @Override
                public void onResponse(Call<ResponseFotoDTO> call, Response<ResponseFotoDTO> response) {

                    if (response.isSuccessful() && response.body() != null) {
                        idFoto = response.body().getIdFoto();
                        Log.d(TAG, "Upload de foto feito, ID da Foto: " + idFoto);
                        if (response.isSuccessful()) {
                            Log.d(TAG, "Upload de foto bem-sucedido, ID da Foto: " + idFoto);
                        } else {
                            Log.e(TAG, "Upload de foto falhou, código de resposta: " + response.code());
                        }

                    } else {
                        Log.e(TAG, "Resposta não bem-sucedida: " + response.code());
                        if (response.errorBody() != null) {

                            Log.e(TAG, "Erro no corpo da resposta: " + response.errorBody().toString());
                        }
                    }
                }

                @Override
                public void onFailure(Call <ResponseFotoDTO> call, Throwable t) {
                    Log.e("UPLOAD_API", "Falha na chamada à API", t);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("UPLOAD_API", "Erro durante o upload", e);
        }
    }

    private void carregarAnexo(Foto body) {

        byte[] conteudoBytes = Base64.decode(body.getArquivo(), Base64.DEFAULT);
        Log.e("", "vazio" + body);
        Bitmap bitmap = BitmapFactory.decodeByteArray(conteudoBytes, 0, conteudoBytes.length);
        binding.fotoAlarme.setImageBitmap(bitmap);

    }


    private byte[] getBytesFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
    private InputStream convertBitmapToInputStream(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int quality = 80;
        bitmap.compress(Bitmap.CompressFormat.PNG, quality, byteArrayOutputStream);
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }



    private void salvarAlarme(AlarmeDTOInsert alarme) {
        Log.d(TAG, "Iniciando processo para salvar o alarme.");

        ApiCuidador apiCuidador = Retrofit.POST_ALARME_TO_IDOSO_LIST();
        String cpfIdoso = idosoCpfSelecionado;

        Gson gson = new Gson();
        String json = gson.toJson(alarme);
        Log.d(TAG, "JSON enviado: " + json);


        Log.d(TAG, "Id da Foto"+ idFoto);

        alarme.setIdFoto(idFoto);
        Log.d(TAG, "Salvando alarme com ID da Foto: " + idFoto);


        Call<String> call = apiCuidador.POST_ALARME_TO_IDOSO_LIST("Bearer " + token, idCuidador, cpfIdoso, alarme);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {

                    exibirMensagemSucesso();
                    salvarAlarmeEmSharedPreferences(alarme);

                    Log.d(TAG, "Alarme salvo com sucesso. Resposta: " + response.body());
                } else {
                    Log.e(TAG, "Resposta da API com erro. Código: " + response.code());
                    try {
                        Log.e(TAG, "Erro no corpo da resposta: " + response.errorBody().string());
                    } catch (IOException e) {
                        Log.e(TAG, "Erro ao ler o corpo da resposta de erro", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                exibirMensagemSucesso();
                salvarAlarmeEmSharedPreferences(alarme);
                Log.e(TAG, "Falha na chamada da API. Erro: " + t.getMessage());
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

    private AlarmeDTOInsert coletarDadosFormulario() {
        Log.d(TAG, "Coletando dados do formulário.");

        String titulo = binding.editTextTitle.getText().toString();
        String descricao = binding.editTextDescription.getText().toString();

        Log.d(TAG, "Título: " + titulo + ", Descrição: " + descricao);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDateTime = "";
        if (picker != null) {
            LocalDateTime horarioAlarme = LocalDateTime.of(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.DAY_OF_MONTH),
                    picker.getHour(),
                    picker.getMinute());
            formattedDateTime = horarioAlarme.format(formatter);
            Log.d(TAG, "Horário formatado do Alarme: " + formattedDateTime);
        } else {
            Log.e(TAG, "Picker é null. Horário do alarme não definido.");
            return null; // ou lidar com isso de maneira apropriada
        }

        AlarmeDTOInsert novoAlarme = new AlarmeDTOInsert();
        novoAlarme.setTitulo(titulo);
        novoAlarme.setDescricao(descricao);
        novoAlarme.setAlarme(formattedDateTime);
        novoAlarme.setIdFoto(idFoto);


        return novoAlarme;
    }

    private void salvarAlarmeEmSharedPreferences(AlarmeDTOInsert alarme) {
        SharedPreferences sharedPreferences = getSharedPreferences("alarmes", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Convertendo o objeto alarme para JSON
        Gson gson = new Gson();
        String jsonAlarme = gson.toJson(alarme);

        String chaveAlarme = "alarme_" + System.currentTimeMillis();
        editor.putString(chaveAlarme, jsonAlarme);
        editor.apply();

        Log.d(TAG, "Alarme salvo em SharedPreferences com a chave: " + chaveAlarme);
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



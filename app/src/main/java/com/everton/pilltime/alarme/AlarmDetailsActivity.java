package com.everton.pilltime.alarme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import com.everton.pilltime.R;
import com.everton.pilltime.api.ApiFoto;
import com.everton.pilltime.api.Retrofit;
import com.everton.pilltime.databinding.ActivityAlarmDetailsBinding;
import com.everton.pilltime.models.Foto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlarmDetailsActivity extends AppCompatActivity {

    private ActivityAlarmDetailsBinding binding;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAlarmDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Intent intent = getIntent();
        String titulo = intent.getStringExtra("TITULO");
        String descricao = intent.getStringExtra("DESCRICAO");
        Long fotoId = intent.getLongExtra("FOTO_ID", 0L); // Aqui você recebe o ID da foto

        binding.tvAlarmTitle.setText(titulo);
        binding.tvAlarmDescription.setText(descricao);


        if (fotoId != 0) {
                // Aqui você fará a chamada para sua API para buscar a foto com esse ID
            buscarFoto(fotoId);
        }

        binding.btnAccept.setOnClickListener(v -> {

            mediaPlayer.stop();
        });

        binding.btnDeny.setOnClickListener(v -> {
            mediaPlayer.stop();

            //criar requisiçao para negar o alarme.


        });

        mediaPlayer = MediaPlayer.create(this, R.raw.som_alarme_1);
        mediaPlayer.start();

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {0, 1000, 500};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(pattern, 0);
        } else {
            vibrator.vibrate(pattern, -1);
        }

    }

    private String recuperarTokenAutenticacao() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }

    private void buscarFoto(Long fotoId) {
        String token = recuperarTokenAutenticacao(); // Recuperando o token
        if (token.isEmpty()) {
            Log.e("AlarmDetails", "Token de autenticação não encontrado.");
            return;
        }

        Log.d("AlarmDetails", "Buscando foto com ID: " + fotoId);

        ApiFoto apiFoto = Retrofit.getFotoById();
        apiFoto.getFotoById(token, fotoId).enqueue(new Callback<Foto>() {
            @Override
            public void onResponse(Call<Foto> call, Response<Foto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("AlarmDetails", "Resposta da API bem-sucedida e com corpo não nulo.");
                    Foto foto = response.body();

                    // Decodificando a string Base64 para um array de bytes e em seguida para um Bitmap
                    byte[] decodedString = Base64.decode(foto.getArquivo(), Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    binding.imgAlarme.setImageBitmap(bitmap);
                } else {
                    Log.e("AlarmDetails", "Resposta da API não bem-sucedida. Código: " + response.code());
                    if (response.errorBody() != null) {
                        String errorBody = response.errorBody().toString();
                        Log.e("AlarmDetails", "Corpo do erro: " + errorBody);
                    }
                }
            }

            @Override
            public void onFailure(Call<Foto> call, Throwable t) {
                Log.e("AlarmDetails", "Falha na chamada da API.", t);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        if (vibrator != null) {
            vibrator.cancel();
        }
    }


    }

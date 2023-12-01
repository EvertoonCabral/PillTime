package com.everton.pilltime.alarme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.TextView;

import com.everton.pilltime.R;
import com.everton.pilltime.databinding.ActivityAlarmDetailsBinding;

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

        binding.tvAlarmTitle.setText(titulo);
        binding.tvAlarmDescription.setText(descricao);

        binding.btnAccept.setOnClickListener(v -> {

            mediaPlayer.stop();
        });

        binding.btnDeny.setOnClickListener(v -> {
            mediaPlayer.stop();

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

        // Demais configurações da Activity...
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

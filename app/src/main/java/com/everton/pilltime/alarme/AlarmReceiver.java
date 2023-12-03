package com.everton.pilltime.alarme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AlarmReceiver", "Alarme recebido");

        String titulo = intent.getStringExtra("TITULO");
        String descricao = intent.getStringExtra("DESCRICAO");
        Long fotoId = intent.getLongExtra("FOTO_ID", 0L); // Supondo que você já tenha o FOTO_ID

        Intent alarmIntent = new Intent(context, AlarmDetailsActivity.class);
        alarmIntent.putExtra("FOTO_ID", fotoId); // Passe o ID da foto
        alarmIntent.putExtra("TITULO", titulo);
        alarmIntent.putExtra("DESCRICAO", descricao);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(alarmIntent);

    }
}

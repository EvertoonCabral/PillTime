package com.everton.pilltime.alarme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AlarmReceiver", "Alarme recebido");

        // Recebe o título e a descrição do alarme do Intent
        String titulo = intent.getStringExtra("TITULO");
        String descricao = intent.getStringExtra("DESCRICAO");

        // Cria um Intent para abrir a AlarmDetailsActivity
        Intent alarmIntent = new Intent(context, AlarmDetailsActivity.class);

        // Passa o título e a descrição para a AlarmDetailsActivity
        alarmIntent.putExtra("TITULO", titulo);
        alarmIntent.putExtra("DESCRICAO", descricao);

        // Inicia a AlarmDetailsActivity
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(alarmIntent);
    }
}

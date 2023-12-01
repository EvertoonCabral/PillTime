package com.everton.pilltime.alarme;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.everton.pilltime.R;
import com.everton.pilltime.TelaPrincipalIdoso;
import com.everton.pilltime.api.ApiAlarme;
import com.everton.pilltime.api.Retrofit;
import com.everton.pilltime.dto.AlarmeDTOInsert;

import java.util.List;

import retrofit2.Call;

public class AlarmReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AlarmReceiver", "Alarme recebido");




        String titulo = intent.getStringExtra("TITULO");
        String descricao = intent.getStringExtra("DESCRICAO");

        Intent i = new Intent(context, AlarmDetailsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri somNotificacao = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(titulo)
                .setContentText(descricao)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setSound(somNotificacao) // Aqui você utiliza o som personalizado
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());

    }
}


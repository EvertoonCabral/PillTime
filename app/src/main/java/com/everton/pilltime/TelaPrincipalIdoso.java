package com.everton.pilltime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.everton.pilltime.adapter.AlarmeAdapter;
import com.everton.pilltime.adapter.AlarmeAdapterCuidador;
import com.everton.pilltime.alarme.AlarmReceiver;
import com.everton.pilltime.api.ApiAlarme;
import com.everton.pilltime.api.Retrofit;
import com.everton.pilltime.databinding.ActivityTelaPrincipalIdosoBinding;
import com.everton.pilltime.alarme.Alarme;
import com.everton.pilltime.dto.AlarmeDTOInsert;
import com.everton.pilltime.models.Idoso;
import com.everton.pilltime.models.Remedio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaPrincipalIdoso extends AppCompatActivity {

    private ActivityTelaPrincipalIdosoBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTelaPrincipalIdosoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.recyclerViewAlarmes.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sharedPreferences = TelaPrincipalIdoso.this.getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        Long id = sharedPreferences.getLong("id", 0);
        String token = sharedPreferences.getString("token", " ");


        carregarAlarmesDoIdoso(id, token);


        List<Alarme> listaDeAlarmes = gerarAlarmesFicticios();
        AlarmeAdapterCuidador alarmeAdapterCuidador = new AlarmeAdapterCuidador(listaDeAlarmes);
        binding.recyclerViewAlarmes.setAdapter(alarmeAdapterCuidador);


        binding.fabAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(TelaPrincipalIdoso.this, "Feature em Desenvolvimento!", Toast.LENGTH_SHORT).show();


            }
        });


        binding.btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(binding.btnPerfil, R.menu.menu_perfil_idoso);
            }
        });

        binding.btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TelaPrincipalIdoso.this, "Você já esta na tela principal!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnHome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(TelaPrincipalIdoso.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(TelaPrincipalIdoso.this, "Saindo!", Toast.LENGTH_SHORT).show();
                return true;
            }
        });


    }


    private void carregarAlarmesDoIdoso(Long idosoId, String token) {
        ApiAlarme apiAlarme = Retrofit.GET_ALARME_IDOSO();
        Call<List<AlarmeDTOInsert>> call = apiAlarme.GET_ALARME_IDOSO("Bearer " + token, idosoId);
        call.enqueue(new Callback<List<AlarmeDTOInsert>>() {
            @Override
            public void onResponse(Call<List<AlarmeDTOInsert>> call, Response<List<AlarmeDTOInsert>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<AlarmeDTOInsert> alarmesDTO = response.body();
                    AlarmeAdapter alarmeAdapter = new AlarmeAdapter(alarmesDTO);
                    binding.recyclerViewAlarmes.setAdapter(alarmeAdapter);
                    agendarAlarmes(alarmesDTO);
                } else {
                    // Tratar erros de resposta
                }
            }

            @Override
            public void onFailure(Call<List<AlarmeDTOInsert>> call, Throwable t) {
                // Tratar falhas na chamada
            }
        });
    }


    private void showPopupMenu(View view, int menuRes) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(menuRes, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_MeuPerfil:
                        Intent intent = new Intent(TelaPrincipalIdoso.this, telaPerfilIdoso.class);
                        startActivity(intent);
                        return true;
                    case R.id.action_edit_profile:
                        Intent intent2 = new Intent(TelaPrincipalIdoso.this, TelaConfPerfilIdoso.class);
                        startActivity(intent2);
                        return true;
                    case R.id.action_suporte:
                        Intent intent3 = new Intent(TelaPrincipalIdoso.this, TelaSuporteUsuario.class);
                        startActivity(intent3);
                        return true;
                    case R.id.action_perfil_cuidador:
                        Intent intent4 = new Intent(TelaPrincipalIdoso.this, TelaPerfilCuidadorVisaoIdoso.class);
                        startActivity(intent4);
                        return true;
                    case R.id.action_logout:
                        Intent intent5 = new Intent(TelaPrincipalIdoso.this, MainActivity.class);
                        startActivity(intent5);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }


    public void agendarAlarmes(List<AlarmeDTOInsert> listaAlarmes) {
        if (listaAlarmes == null || listaAlarmes.isEmpty()) {
            Log.d("TelaPrincipal", "Nenhum alarme para agendar.");
            return;
        }

        AlarmeDTOInsert ultimoAlarme = listaAlarmes.get(listaAlarmes.size() - 1); // Pega o último alarme

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int requestCode = (int) System.currentTimeMillis();

        try {
            LocalDateTime dateTime = LocalDateTime.parse(ultimoAlarme.getAlarme(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            Calendar calendar = Calendar.getInstance();
            calendar.set(dateTime.getYear(), dateTime.getMonthValue() - 1, dateTime.getDayOfMonth(), dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond());

            Log.d("TelaPrincipal", "Agendando último alarme: " + ultimoAlarme.getTitulo() + " para " + calendar.getTime());

            Intent intent = new Intent(this, AlarmReceiver.class);
            intent.putExtra("TITULO", ultimoAlarme.getTitulo());
            intent.putExtra("DESCRICAO", ultimoAlarme.getDescricao());

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            if (alarmManager != null) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        }
        catch(Exception e){
            Log.e("TelaPrincipal", "Erro ao agendar alarme", e);
        }
    }



    private List<Alarme> gerarAlarmesFicticios() {
        List<Alarme> alarmes = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            Alarme alarme = new Alarme();
            alarme.setTitulo("Alarme " + i);
            alarme.setDescricao("Descrição do alarme " + i);
            //   alarme.setDtCadastrado(new Date());

            Idoso idoso = new Idoso();
            idoso.setNome("Idoso " + i);
            alarme.setIdoso(idoso);

            Remedio remedio = new Remedio();
            remedio.setNome("Remédio " + i);
            List<Remedio> remedios = new ArrayList<>();
            remedios.add(remedio);
            //   alarme.setRemediosIdosos(remedios);
//
            //   alarme.setAlarme(LocalDateTime.now().plusHours(i)); // Adicionando horas para diferenciar

            alarmes.add(alarme);
        }

        return alarmes;
    }


}
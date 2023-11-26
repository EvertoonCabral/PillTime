package com.everton.pilltime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.everton.pilltime.adapter.AlarmeAdapterCuidador;

import com.everton.pilltime.alarme.AlarmReceiver;
import com.everton.pilltime.alarme.AlarmeActivity;
import com.everton.pilltime.api.ApiAlarme;
import com.everton.pilltime.api.ApiIdoso;
import com.everton.pilltime.api.Retrofit;
import com.everton.pilltime.databinding.ActivityTelaPrincipalBinding;
import com.everton.pilltime.alarme.Alarme;
import com.everton.pilltime.dto.AlarmeDTOInsert;
import com.everton.pilltime.models.Idoso;
import com.everton.pilltime.models.Remedio;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaPrincipal extends AppCompatActivity {

    private ActivityTelaPrincipalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TelaPrincipal", "onCreate iniciado");


        binding = ActivityTelaPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.recyclerViewAlarmes.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sharedPreferences = getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        Long idosoId = sharedPreferences.getLong("id_idoso", 0);
        String token = sharedPreferences.getString("token", "");

        Log.d("TelaPrincipal", "SharedPreferences: idosoId = " + idosoId + ", token = " + token);


        ApiAlarme apiAlarme = Retrofit.GET_ALARME_IDOSO();

        Call<List<AlarmeDTOInsert>> call = apiAlarme.GET_ALARME_IDOSO("Bearer " + token, idosoId);
        call.enqueue(new Callback<List<AlarmeDTOInsert>>() {
            @Override
            public void onResponse(Call<List<AlarmeDTOInsert>> call, Response<List<AlarmeDTOInsert>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<AlarmeDTOInsert> listaAlarmesDTO = response.body();
                    Log.d("TelaPrincipal", "Alarmes recebidos: " + listaAlarmesDTO.size());
                    agendarAlarmes(listaAlarmesDTO);
                } else {
                    Log.e("TelaPrincipal", "Falha ao obter alarmes. Código de resposta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<AlarmeDTOInsert>> call, Throwable t) {
                Log.e("TelaPrincipal", "Falha na chamada da API", t);
            }
        });

        List<Alarme> listaDeAlarmes = gerarAlarmesFicticios();
        AlarmeAdapterCuidador alarmeAdapterCuidador = new AlarmeAdapterCuidador(listaDeAlarmes);
        binding.recyclerViewAlarmes.setAdapter(alarmeAdapterCuidador);


        binding.fabAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent intent = new Intent(TelaPrincipal.this, AlarmeActivity.class);
               startActivity(intent);



            }
        });


        binding.bottomNavigation.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.btn_remedio:
                        Intent intent = new Intent(TelaPrincipal.this, TelaCadastroRemedio.class);
                        startActivity(intent);
                        return true;

                    case R.id.btn_relatorio:
                        Intent intent2 = new Intent(TelaPrincipal.this, TelaRelatorios.class);
                        startActivity(intent2);
                        return true;

                    case R.id.btnCadastroIdoso:
                        Intent intent3 = new Intent(TelaPrincipal.this, TelaCadastroIdoso.class);
                        startActivity(intent3);
                        return true;

                    default:
                        return false;
                }
            }
        });

        binding.btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(binding.btnPerfil, R.menu.menu_perfil);
            }
        });

        binding.btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TelaPrincipal.this, "Você já esta na tela principal!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnHome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(TelaPrincipal.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(TelaPrincipal.this, "Saindo!", Toast.LENGTH_SHORT).show();
                return true;
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
                        Intent intent = new Intent(TelaPrincipal.this, TelaPerfilCuidador.class);
                        startActivity(intent);
                        return true;
                    case R.id.action_edit_profile:
                        Intent intent2 = new Intent(TelaPrincipal.this, TelaConfPerfilIdoso.class);
                        startActivity(intent2);
                        return true;
                    case R.id.action_suporte:
                        Intent intent3 = new Intent(TelaPrincipal.this, TelaSuporteUsuario.class);
                        startActivity(intent3);
                        return true;
                    case R.id.action_Alterar_Remedio:
                        Intent intent4 = new Intent(TelaPrincipal.this, TelaAlterarRemedio.class);
                        startActivity(intent4);
                        return true;
                    case R.id.action_logout:
                        Intent intent5 = new Intent(TelaPrincipal.this, MainActivity.class);
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
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int requestCode = 0; // Um identificador único para cada PendingIntent
        try {
            for (AlarmeDTOInsert alarme : listaAlarmes) {
                // Converte a string de horário para Calendar
                LocalDateTime dateTime = LocalDateTime.parse(alarme.getAlarme(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                Calendar calendar = Calendar.getInstance();
                calendar.set(dateTime.getYear(), dateTime.getMonthValue() - 1, dateTime.getDayOfMonth(),
                        dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond());

                Log.d("TelaPrincipal", "Agendando alarme: " + alarme.getTitulo() + " para " + calendar.getTime());

                Intent intent = new Intent(this, AlarmReceiver.class);
                intent.putExtra("TITULO", alarme.getTitulo());
                intent.putExtra("DESCRICAO", alarme.getDescricao());

                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode++, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                if (alarmManager != null) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
            }
        }
            catch(Exception e){
                Log.e("TelaPrincipal", "Erro ao agendar alarme", e);

            }
        }


    private List<Alarme> gerarAlarmesFicticios() {
        List<Alarme> alarmes = new ArrayList<>();

        // Criando alguns alarmes fictícios
        for (int i = 1; i <= 10; i++) {
            Alarme alarme = new Alarme();
            alarme.setTitulo("Alarme " + i);
            alarme.setDescricao("Descrição do alarme " + i);
       //     alarme.setDtCadastrado(new Date());

            Idoso idoso = new Idoso();
            idoso.setNome("Idoso " + i);
            alarme.setIdoso(idoso);

            Remedio remedio = new Remedio();
            remedio.setNome("Remédio " + i);
            List<Remedio> remedios = new ArrayList<>();
            remedios.add(remedio);
         //   alarme.setRemediosIdosos(remedios);

           // alarme.setAlarme(LocalDateTime.now().plusHours(i)); // Adicionando horas para diferenciar

            alarmes.add(alarme);
        }

        return alarmes;
    }


}

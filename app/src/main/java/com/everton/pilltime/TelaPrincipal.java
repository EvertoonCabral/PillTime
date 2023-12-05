package com.everton.pilltime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;

import com.everton.pilltime.adapter.AlarmeDTOAdapter;
import com.everton.pilltime.alarme.AlarmeActivity;
import com.everton.pilltime.databinding.ActivityTelaPrincipalBinding;
import com.everton.pilltime.alarme.Alarme;
import com.everton.pilltime.dto.AlarmeDTOInsert;
import com.everton.pilltime.models.Idoso;
import com.everton.pilltime.models.Remedio;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Override
    protected void onResume() {
        super.onResume();
        List<AlarmeDTOInsert> alarmes = carregarAlarmesDeSharedPreferences();
        AlarmeDTOAdapter alarmeDTOAdapter = new AlarmeDTOAdapter(alarmes);
        binding.recyclerViewAlarmes.setAdapter(alarmeDTOAdapter);
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


    private List<AlarmeDTOInsert> carregarAlarmesDeSharedPreferences() {
        List<AlarmeDTOInsert> alarmes = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences("alarmes", MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();

        Gson gson = new Gson();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String jsonAlarme = entry.getValue().toString();
            AlarmeDTOInsert alarme = gson.fromJson(jsonAlarme, AlarmeDTOInsert.class);
            alarmes.add(alarme);
        }

        return alarmes;
    }





}

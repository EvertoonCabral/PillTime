package com.everton.pilltime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.everton.pilltime.adapter.AlarmeAdapter;
import com.everton.pilltime.databinding.ActivityTelaPrincipalIdosoBinding;
import com.everton.pilltime.alarme.Alarme;
import com.everton.pilltime.models.Idoso;
import com.everton.pilltime.models.Remedio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TelaPrincipalIdoso extends AppCompatActivity {

    private ActivityTelaPrincipalIdosoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTelaPrincipalIdosoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recyclerViewAlarmes.setLayoutManager(new LinearLayoutManager(this));

        List<Alarme> listaDeAlarmes = gerarAlarmesFicticios();
        AlarmeAdapter alarmeAdapter= new AlarmeAdapter(listaDeAlarmes);
        binding.recyclerViewAlarmes.setAdapter(alarmeAdapter);


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
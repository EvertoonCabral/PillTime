package com.everton.pilltime;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import org.checkerframework.checker.nullness.qual.NonNull;

public class TelaPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
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

        ImageButton btnPerfil = findViewById(R.id.btnPerfil);
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPopupMenu(btnPerfil, R.menu.menu_perfil);

            }
        });

        ImageButton btnHome = findViewById(R.id.btnHome);
        // Listener para clique curto
        // Listener para clique curto
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TelaPrincipal.this, "Você já esta na tela principal!", Toast.LENGTH_SHORT).show();
            }
        });

        // Listener para clique longo
        btnHome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Intent intent = new Intent(TelaPrincipal.this, MainActivity.class);
                startActivity(intent);

                Toast.makeText(TelaPrincipal.this, "Saindo!", Toast.LENGTH_SHORT).show();
                return true; // Retorna true para indicar que o evento foi consumido
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


}

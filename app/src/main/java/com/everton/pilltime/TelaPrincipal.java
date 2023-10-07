package com.everton.pilltime;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.os.Bundle;
import android.view.MenuItem;

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

                    // Descomente e atualize o código abaixo quando tiver a atividade correspondente
                    // case R.id.btnCadastroIdoso:
                    //    Intent intent3 = new Intent(TelaPrincipal.this, NomeDaAtividade.class);
                    //    startActivity(intent3);
                    //    return true;

                    default:
                        return false;
                }
            }
        });
    }
}

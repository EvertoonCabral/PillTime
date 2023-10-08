package com.everton.pilltime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class TelaAlterarRemedio extends AppCompatActivity {

    private Button btnSalvar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_alterar_remedio);


        btnSalvar = findViewById(R.id.btnSalvarEditRemedio);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TelaAlterarRemedio.this, TelaPrincipal.class);
                startActivity(intent);

            }
        });




    }
}
package com.everton.pilltime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class TelaCadastroRemedio extends AppCompatActivity {

    String[] formasFarmaceuticas = {
            "Comprimido",
            "Cápsula",
            "Xarope",
            "Pomada",
            "Outro"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro_remedio);

        Spinner spinnerFormaFarmaceutica = findViewById(R.id.spinnerFormaFarmaceutica);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, formasFarmaceuticas);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFormaFarmaceutica.setAdapter(adapter);

    }
}
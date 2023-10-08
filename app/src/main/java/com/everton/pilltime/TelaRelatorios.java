package com.everton.pilltime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.everton.pilltime.adapter.RemedioAdapter;

public class TelaRelatorios extends AppCompatActivity {

    private Spinner spinnerRelatorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_relatorios);


        Spinner spinner = findViewById(R.id.spinnerRelatorio);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (this, R.array.opcoes_relatorio, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


            RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RemedioAdapter adapter2 = new RemedioAdapter(remedioList);
        recyclerView.setAdapter(adapter2);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedOption = spinner.getSelectedItem().toString();

                if (selectedOption.equals("Idoso")) {

                    // Busque os dados dos idosos e atualize o RecyclerView

                } else if (selectedOption.equals("Remédio")) {

                    // Busque os dados dos remédios e atualize o RecyclerView

                }

                // ... e assim por diante para outras opções
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Alguma ação quando nada é selecionado, se necessário
            }
        });



    }



}
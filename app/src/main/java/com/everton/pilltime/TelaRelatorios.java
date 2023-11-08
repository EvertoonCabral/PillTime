    package com.everton.pilltime;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import android.os.Bundle;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.ArrayAdapter;
    import android.widget.Spinner;

    import com.everton.pilltime.adapter.IdosoAdapter;
    import com.everton.pilltime.adapter.RemedioAdapter;
    import com.everton.pilltime.models.Idoso;
    import com.everton.pilltime.models.Remedio;

    import java.text.ParseException;
    import java.text.SimpleDateFormat;
    import java.util.ArrayList;
    import java.util.List;

    public class TelaRelatorios extends AppCompatActivity {

        private Spinner spinnerRelatorio;
        private List    <Remedio> remedioList = new ArrayList<>(); // Declaração da lista de remédios
        private List    <Idoso> idosoList = new ArrayList<>(); // Declaração da lista de iDOSOS.....



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tela_relatorios);

            inicializarListaDeRemediosParaTestes();


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
                    //    inicializarListaDeIdososParaTestes();
                        IdosoAdapter idosoAdapter = new IdosoAdapter(idosoList);
                        recyclerView.setAdapter(idosoAdapter);
                    } else if (selectedOption.equals("Remédio")) {
                        inicializarListaDeRemediosParaTestes();
                        RemedioAdapter remedioAdapter = new RemedioAdapter(remedioList);
                        recyclerView.setAdapter(remedioAdapter);


                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // Alguma ação quando nada é selecionado, se necessário
                }
            });



        }

        private void inicializarListaDeRemediosParaTestes() {

            remedioList.add(new Remedio("Remédio A", "Marca A", "01/01/2023"));
            remedioList.add(new Remedio("Remédio B", "Marca B", "01/02/2023"));


        }





    }
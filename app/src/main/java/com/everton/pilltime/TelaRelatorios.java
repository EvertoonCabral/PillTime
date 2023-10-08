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
                        inicializarListaDeIdososParaTestes();
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

        private void inicializarListaDeIdososParaTestes() {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                idosoList.add(new Idoso("João Silva", sdf.parse("01/01/1945"), "123.456.789-01", "(11) 98765-4321"));
                idosoList.add(new Idoso("Maria Oliveira", sdf.parse("15/05/1948"), "123.456.789-02", "(11) 98765-4322"));
                idosoList.add(new Idoso("Antônio Pereira", sdf.parse("20/08/1950"), "123.456.789-03", "(11) 98765-4323"));
                idosoList.add(new Idoso("Francisca Santos", sdf.parse("28/02/1940"), "123.456.789-04", "(11) 98765-4324"));
                idosoList.add(new Idoso("Carlos Costa", sdf.parse("12/12/1942"), "123.456.789-05", "(11) 98765-4325"));
                idosoList.add(new Idoso("Ana Lima", sdf.parse("03/03/1947"), "123.456.789-06", "(11) 98765-4326"));
                idosoList.add(new Idoso("Paulo Rodrigues", sdf.parse("22/07/1944"), "123.456.789-07", "(11) 98765-4327"));
                idosoList.add(new Idoso("Luciana Alves", sdf.parse("05/10/1946"), "123.456.789-08", "(11) 98765-4328"));
                idosoList.add(new Idoso("Ricardo Gomes", sdf.parse("14/06/1943"), "123.456.789-09", "(11) 98765-4329"));
                idosoList.add(new Idoso("Sandra Moraes", sdf.parse("30/09/1941"), "123.456.789-10", "(11) 98765-4330"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }



    }
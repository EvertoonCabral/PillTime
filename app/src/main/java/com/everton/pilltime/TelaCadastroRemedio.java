package com.everton.pilltime;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.everton.pilltime.api.ApiCuidador;
import com.everton.pilltime.api.ApiRemedio;
import com.everton.pilltime.api.Retrofit;
import com.everton.pilltime.databinding.ActivityTelaCadastroRemedioBinding;
import com.everton.pilltime.dto.RemedioDTO;
import com.everton.pilltime.models.Remedio;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaCadastroRemedio extends AppCompatActivity {


    private DateFormat dateFormatter;

    private ActivityTelaCadastroRemedioBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTelaCadastroRemedioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupDateMask();


        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());


        SharedPreferences sharedPreferences = getSharedPreferences("MyToken", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        Long id = sharedPreferences.getLong("id", 0);



        binding.btnCadastroRemedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validarCampos()) {
                    Toast.makeText(TelaCadastroRemedio.this, "Por favor, preencha todos os campos corretamente.", Toast.LENGTH_SHORT).show();
                    return;
                }


                binding.btnCadastroRemedio.setEnabled(true);

                String nome = binding.edNomeRemedio.getText().toString().trim();
                String marca = binding.edMarcaRemedio.getText().toString().trim();
                String dosagem = binding.edDosagemRemedio.getText().toString().trim();
                String formaFarmaceutico = binding.edFormaFarmaceutica.getText().toString().trim();
                String dataValidadeRemedioString = binding.edDataValidadeRemedio.getText().toString().trim();

                Date dataValidade = formataData(dataValidadeRemedioString);
                if (dataValidade == null) {
                    Toast.makeText(TelaCadastroRemedio.this, "Data de nascimento inválida.", Toast.LENGTH_SHORT).show();
                    return;
                }
                String observacoes = binding.edObservacaoRemedio.getText().toString();


                RemedioDTO remedio = new RemedioDTO();
                remedio.setNome(nome);
                remedio.setMarcaRemedio(marca);
                remedio.setDosagem(dosagem);
                remedio.setFormaFarmaceutico(formaFarmaceutico);
                remedio.setDataValidade(dataValidade);
                remedio.setObservacoes(observacoes);

                ApiRemedio apiRemedio = Retrofit.REGISTER_REMEDIO();
                ApiCuidador apiCuidador = Retrofit.POST_REMEDIO_TO_CUIDADOR();


                Call<String> call1 = apiCuidador.POST_REMEDIO_TO_CUIDADOR(token, id, remedio);

                call1.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()){
                            

                        } else {
                            int statusCode = response.code();
                            Log.e("Cadastro Remedio", "Erro ao registrar, código de status HTTP: " + statusCode);
                        }
                        if (response.errorBody() != null) {
                            String errorString;
                            try {
                                errorString = response.errorBody().string();
                                Log.e("Cadastro Remedio", "Erro ao registrar: " + errorString);
                            } catch (IOException e) {
                                Log.e("Cadastro Remedio", "Erro ao ler o corpo de erro", e);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                        new AlertDialog.Builder(TelaCadastroRemedio.this)
                                .setTitle("Cadastro de Remédio")
                                .setMessage("Remédio cadastrado com sucesso!")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        limparCampos();

                                        Intent intent = new Intent(TelaCadastroRemedio.this, TelaPrincipal.class); // Substitua TelaPrincipal pela sua tela principal
                                        startActivity(intent);
                                        finish();

                                    }
                                })
                                .show();

                        binding.btnCadastroRemedio.setEnabled(true);

                    }



                });



            }

        });





    }

    private boolean validarCampos() {
        String nome = binding.edNomeRemedio.getText().toString().trim();
        String marca = binding.edMarcaRemedio.getText().toString().trim();
        String dosagem = binding.edDosagemRemedio.getText().toString().trim();
        String formaFarmaceutico = binding.edFormaFarmaceutica.getText().toString().trim();
        String dataValidadeRemedioString = binding.edDataValidadeRemedio.getText().toString().trim();

        if (nome.isEmpty() || marca.isEmpty() || dosagem.isEmpty() || formaFarmaceutico.isEmpty() || dataValidadeRemedioString.isEmpty()) {
            Log.e("Validação", "Um ou mais campos estão vazios.");
            return false;
        }

        if (!validarData(dataValidadeRemedioString)) {
            Log.e("Validação", "Data de validade inválida.");
            return false;
        }

        return true;
    }

    private boolean validarData(String dataString) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        formatter.setLenient(false);
        try {
            Date validade = formatter.parse(dataString);
            Date hoje = new Date();

            // Configurar o calendário para 5 anos à frente
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(hoje);
            calendar.add(Calendar.YEAR, 5);
            Date cincoAnosAFrente = calendar.getTime();

            if (validade.before(hoje)) {
                Log.e("Validação", "Data de validade não pode ser retroativa.");
                return false;
            } else if (validade.after(cincoAnosAFrente)) {
                Log.e("Validação", "Data de validade não pode ser mais de 5 anos no futuro.");
                return false;
            }
            return true;
        } catch (ParseException e) {
            Log.e("Validação", "Erro ao formatar a data: " + e.getMessage());
            return false;
        }
    }



    private Date formataData(String data) {

        String dateString = "dd/MM/yyyy"; // Defina o formato da data aqui

        SimpleDateFormat formatter = new SimpleDateFormat(dateString, Locale.getDefault());
        Date dataFormatada = null;

        try {
            dataFormatada = formatter.parse(data);
        } catch (ParseException e) {

            e.printStackTrace();
        }

        return dataFormatada;

    }


    private void showDialog(String title, String message) {
        new AlertDialog.Builder(TelaCadastroRemedio.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }


    private void limparCampos() {
        binding.edNomeRemedio.setText("");
        binding.edMarcaRemedio.setText("");
        binding.edDosagemRemedio.setText("");
        binding.edFormaFarmaceutica.setText("");
        binding.edDataValidadeRemedio.setText("");
        binding.edObservacaoRemedio.setText("");
    }


    private void setupDateMask() {
        binding.edDataValidadeRemedio.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        if(mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon-1);

                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);



                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    binding.edDataValidadeRemedio.setText(current);
                    binding.edDataValidadeRemedio.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }




}
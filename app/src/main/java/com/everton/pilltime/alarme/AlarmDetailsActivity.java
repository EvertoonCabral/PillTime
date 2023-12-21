package com.everton.pilltime.alarme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import com.everton.pilltime.MainActivity;
import com.everton.pilltime.R;
import com.everton.pilltime.TelaPrincipal;
import com.everton.pilltime.TelaPrincipalIdoso;
import com.everton.pilltime.api.ApiFoto;
import com.everton.pilltime.api.Retrofit;
import com.everton.pilltime.databinding.ActivityAlarmDetailsBinding;
import com.everton.pilltime.dto.IdosoComCuidadorDTO;
import com.everton.pilltime.models.Cuidador;
import com.everton.pilltime.models.EmailRequest;
import com.everton.pilltime.models.Foto;
import com.everton.pilltime.models.Idoso;
import com.everton.pilltime.models.Pessoa;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlarmDetailsActivity extends AppCompatActivity {

    private ActivityAlarmDetailsBinding binding;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    private Handler autoCloseHandler = new Handler();
    private Runnable autoCloseRunnable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlarmDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());





        Intent intent = getIntent();
        String titulo = intent.getStringExtra("TITULO");
        String descricao = intent.getStringExtra("DESCRICAO");
        Long fotoId = intent.getLongExtra("FOTO_ID", 0L); // Aqui você recebe o ID da foto
        Long idosoId = getIntent().getLongExtra("IDOSO_ID", 0L); // Recuperando ID do idoso
        Log.d("AlarmDetailsActivity", "Idoso ID: " + idosoId); // Adicionando log para verificar o ID


        binding.tvAlarmTitle.setText(titulo);
        binding.tvAlarmDescription.setText(descricao);


        if (fotoId != 0) {
                // Aqui você fará a chamada para sua API para buscar a foto com esse ID
            buscarFoto(fotoId);
        }

        binding.btnAccept.setOnClickListener(v -> {

            mediaPlayer.stop();
            vibrator.cancel();


            Intent intent1;
            intent1 = new Intent(AlarmDetailsActivity.this, TelaPrincipalIdoso.class);
            startActivity(intent1);


        });

        binding.btnDeny.setOnClickListener(v -> {
            mediaPlayer.stop();
            vibrator.cancel();

            final Long finalIdosoId = getIntent().getLongExtra("IDOSO_ID", 0L);
            if (finalIdosoId != 0) {
                buscarIdCuidadorETelefone(finalIdosoId);
            } else {
                Log.e("AlarmDetailsActivity", "Idoso ID inválido.");
            }
        });



        mediaPlayer = MediaPlayer.create(this, R.raw.som_alarme_1);
        mediaPlayer.start();

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {0, 1000, 500};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(pattern, 0);
            vibrator.vibrate(pattern, -1);

        }

        autoCloseRunnable = new Runnable() {
            @Override
            public void run() {
                final Long finalIdosoId = getIntent().getLongExtra("IDOSO_ID", 0L);
                if (finalIdosoId != 0) {
                    enviarEmailAutomatico(finalIdosoId);
        } else {
                }

                // Fechar a atividade
                finish();
            }
        };

        // Agendar o Runnable para ser executado após 60 segundos
        autoCloseHandler.postDelayed(autoCloseRunnable, 60000);
    }


    private String recuperarTokenAutenticacao() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyToken", Context.MODE_PRIVATE);

        return sharedPreferences.getString("token", "");
    }

    private void buscarFoto(Long fotoId) {
        String token = recuperarTokenAutenticacao(); // Recuperando o token
        if (token.isEmpty()) {
            Log.e("AlarmDetails", "Token de autenticação não encontrado.");
            return;
        }

        Log.d("AlarmDetails", "Buscando foto com ID: " + fotoId);

        ApiFoto apiFoto = Retrofit.getFotoById();
        apiFoto.getFotoById(token, fotoId).enqueue(new Callback<Foto>() {
            @Override
            public void onResponse(Call<Foto> call, Response<Foto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("AlarmDetails", "Resposta da API bem-sucedida e com corpo não nulo.");
                    Foto foto = response.body();

                    // Decodificando a string Base64 para um array de bytes e em seguida para um Bitmap
                    byte[] decodedString = Base64.decode(foto.getArquivo(), Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    binding.imgAlarme.setImageBitmap(bitmap);
                } else {
                    Log.e("AlarmDetails", "Resposta da API não bem-sucedida. Código: " + response.code());
                    if (response.errorBody() != null) {
                        String errorBody = response.errorBody().toString();
                        Log.e("AlarmDetails", "Corpo do erro: " + errorBody);
                    }
                }
            }

            @Override
            public void onFailure(Call<Foto> call, Throwable t) {
                Log.e("AlarmDetails", "Falha na chamada da API.", t);
            }
        });
    }


    private void buscarInformacoesIdosoEEnviarEmail(Long idosoId) {
        String token = recuperarTokenAutenticacao();
        if (token.isEmpty()) {
            Log.e("AlarmDetailsActivity", "Token de autenticação não encontrado.");
            return;
        }

        // Fazendo a chamada à API para buscar informações completas do idoso
        Retrofit.get_Idoso_With_Cuidador().getIdosoWithCuidador(token, idosoId).enqueue(new Callback<IdosoComCuidadorDTO>() {
            @Override
            public void onResponse(Call<IdosoComCuidadorDTO> call, Response<IdosoComCuidadorDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    IdosoComCuidadorDTO idosoDTO = response.body();
                    // Agora você tem o ID do cuidador
                    buscarInformacoesCuidadorEEnviarEmail(idosoDTO.getCuidadorId());
                } else {
                    Log.e("AlarmDetailsActivity", "Falha ao buscar informações do idoso. Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<IdosoComCuidadorDTO> call, Throwable t) {
                Log.e("AlarmDetailsActivity", "Falha na chamada da API.", t);
            }
        });
    }

    private void buscarInformacoesCuidadorEEnviarEmail(Long cuidadorId) {
        String token = recuperarTokenAutenticacao();
        if (token.isEmpty()) {
            Log.e("AlarmDetailsActivity", "Token de autenticação não encontrado.");
            return;
        }

        Retrofit.GET_FULL_CUIDADOR().GET_CUIDADOR_FULL_BY_ID("Bearer " + token, cuidadorId).enqueue(new Callback<Cuidador>() {
            @Override
            public void onResponse(Call<Cuidador> call, Response<Cuidador> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Cuidador cuidador = response.body();
                    enviarEmail(cuidador.getEmail(), "E-MAIL NEGADO!", "Olá " + cuidador.getNome() + " o alarme que você cadastrou, verifique!");
                } else {
                    Log.e("AlarmDetailsActivity", "Falha ao buscar informações do cuidador. Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Cuidador> call, Throwable t) {
                Log.e("AlarmDetailsActivity", "Falha na chamada da API.", t);
            }
        });

    }


    private void buscarIdCuidadorETelefone(Long idosoId) {
        String token = recuperarTokenAutenticacao();
        if (token.isEmpty()) {
            Log.e("AlarmDetailsActivity", "Token de autenticação não encontrado.");
            return;
        }

        Retrofit.get_Idoso_With_Cuidador().getIdosoWithCuidador(token, idosoId).enqueue(new Callback<IdosoComCuidadorDTO>() {
            @Override
            public void onResponse(Call<IdosoComCuidadorDTO> call, Response<IdosoComCuidadorDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Long cuidadorId = response.body().getCuidadorId();
                    buscarTelefoneCuidador(cuidadorId);
                } else {
                    Log.e("AlarmDetailsActivity", "Falha ao buscar informações do idoso. Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<IdosoComCuidadorDTO> call, Throwable t) {
                Log.e("AlarmDetailsActivity", "Falha na chamada da API.", t);
            }
        });
    }

    private void buscarTelefoneCuidador(Long cuidadorId) {
        Log.d("AlarmDetailsActivity", "Iniciando busca pelo telefone do cuidador com ID: " + cuidadorId);
        String token = recuperarTokenAutenticacao();
        if (token.isEmpty()) {
            Log.e("AlarmDetailsActivity", "Token de autenticação não encontrado.");
            return;
        }

        Retrofit.GET_FULL_CUIDADOR().GET_CUIDADOR_FULL_BY_ID("Bearer " + token, cuidadorId).enqueue(new Callback<Cuidador>() {
            @Override
            public void onResponse(Call<Cuidador> call, Response<Cuidador> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Cuidador cuidador = response.body();
                    String numeroTelefoneCuidador = cuidador.getTelefone();
                    Log.d("AlarmDetailsActivity", "Número do telefone do cuidador encontrado: " + numeroTelefoneCuidador);
                    enviarSMS(numeroTelefoneCuidador, "Um alarme foi recusado. Verifique o aplicativo para mais detalhes.");
                } else {
                    Log.e("AlarmDetailsActivity", "Falha ao buscar informações do cuidador. Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Cuidador> call, Throwable t) {
                Log.e("AlarmDetailsActivity", "Falha na chamada da API para buscar informações do cuidador.", t);
            }
        });
    }


    private void enviarSMS(String numeroTelefone, String mensagem) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(numeroTelefone, null, mensagem, null, null);
            Log.d("SMS", "SMS enviado com sucesso para: " + numeroTelefone);
        } catch (Exception e) {
            Log.e("SMS", "Falha ao enviar SMS para: " + numeroTelefone, e);
        }
    }





    private void enviarEmail(String emailDestinatario, String assunto, String corpoEmail) {
        String token = recuperarTokenAutenticacao();


        EmailRequest emailRequest = new EmailRequest(emailDestinatario, assunto, corpoEmail);
        Retrofit.GET_EMAIL_SERVICE().enviarEmail(token, emailRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    Log.d("EmailService", "Email enviado com sucesso");
                } else {
                    Log.e("EmailService", "Falha ao enviar email. Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("EmailService", "Erro na chamada da API.", t);
            }
        });
    }

    private void enviarEmailAutomatico(Long idosoId) {
        // Adicione aqui a lógica para enviar o e-mail
        buscarInformacoesIdosoEEnviarEmail(idosoId);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        autoCloseHandler.removeCallbacks(autoCloseRunnable);

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        if (vibrator != null) {
            vibrator.cancel();
        }
    }


    }

    package com.everton.pilltime;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.app.ActivityCompat;
    import androidx.core.content.ContextCompat;

    import android.app.NotificationChannel;
    import android.app.NotificationManager;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.content.pm.PackageManager;
    import android.graphics.Color;
    import android.os.Build;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.Manifest;


    import com.everton.pilltime.api.ApiUser;
    import com.everton.pilltime.api.Retrofit;
    import com.everton.pilltime.databinding.ActivityFormLoginBinding;
    import com.everton.pilltime.models.TipoUsuario;
    import com.everton.pilltime.user.AuthenticationDTO;
    import com.everton.pilltime.user.LoginResponseDTO;
    import com.google.android.material.snackbar.Snackbar;

    import javax.annotation.Nonnull;

    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;

    public class MainActivity extends AppCompatActivity {

        private ActivityFormLoginBinding binding;
        String[] mensagens = {"Preencha todos os campos", "Login efetuado com sucesso"};

        private static final int PERMISSIONS_REQUEST_CODE = 100;

        private static final String[] PERMISSIONS_REQUIRED = {
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.SET_ALARM,
                Manifest.permission.VIBRATE
        };

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityFormLoginBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            requestPermissionsIfNeeded();




            binding.txtCadastro.setOnClickListener(view -> {

                startActivity(new Intent(this, DadosCadastroCuidador.class));

            });


            binding.btEntrarlogin.setOnClickListener(view -> {

                String email = binding.edLogin.getText().toString().trim();
                String senha = binding.edSenha.getText().toString().trim();

                if (email.isEmpty() || senha.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, mensagens[0], Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                } else {
                    AutenticarUsuario(view);
                }


            });


        }

        private void AutenticarUsuario(View view) {
            String login = binding.edLogin.getText().toString().trim();
            String senha = binding.edSenha.getText().toString().trim();

            // Mostrar a barra de progresso antes de iniciar a chamada de login
            binding.progressBarLogin.setVisibility(View.VISIBLE);

            ApiUser apiUser = Retrofit.LOGIN_CALL();
            AuthenticationDTO authenticationDTO = new AuthenticationDTO(login, senha);

            Call<LoginResponseDTO> call = apiUser.LOGIN_CALL(authenticationDTO);

            call.enqueue(new Callback<LoginResponseDTO>() {
                @Override
                public void onResponse(Call<LoginResponseDTO> call, Response<LoginResponseDTO> response) {
                    // Esconder a barra de progresso após receber a resposta
                    binding.progressBarLogin.setVisibility(View.GONE);

                    if (response.isSuccessful()) {
                        LoginResponseDTO loginResponseDTO = response.body();
                        String token = loginResponseDTO.getToken();
                        Long id = loginResponseDTO.getPessoaId();
                        TipoUsuario tipoUsuario = loginResponseDTO.getTipoUsuario();

                        SharedPreferences sharedPreferences = getSharedPreferences("MyToken", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token", token);
                        editor.putLong("id", id);
                        editor.putString("tipoUsuario", tipoUsuario.name()); // Armazenar o tipo de usuário como String
                        editor.apply();

                        redirecionarParaTelaCorreta(tipoUsuario);
                    } else {
                        // Tratar caso a resposta não seja bem-sucedida
                        Snackbar snackbar = Snackbar.make(view, "Login falhou. Por favor, verifique suas credenciais.", Snackbar.LENGTH_LONG);
                        snackbar.setBackgroundTint(Color.WHITE);
                        snackbar.setTextColor(Color.BLACK);
                        snackbar.show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponseDTO> call, Throwable t) {
                    // Esconder a barra de progresso em caso de falha na chamada
                    binding.progressBarLogin.setVisibility(View.GONE);

                    Snackbar snackbar = Snackbar.make(view, "Erro na conexão: " + t.getMessage(), Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            });
        }


        private void redirecionarParaTelaCorreta(TipoUsuario tipoUsuario) {
            Intent intent;
            if (tipoUsuario == TipoUsuario.I) {
                intent = new Intent(MainActivity.this, TelaPrincipalIdoso.class);
            } else {
                intent = new Intent(MainActivity.this, TelaPrincipal.class);
            }
            startActivity(intent);
            finish();
        }




        private void requestPermissionsIfNeeded() {
            // Verificar se as permissões já foram concedidas
            boolean allPermissionsGranted = true;
            for (String permission : PERMISSIONS_REQUIRED) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }

            // Se as permissões não foram todas concedidas, solicitar ao usuário
            if (!allPermissionsGranted) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_REQUIRED, PERMISSIONS_REQUEST_CODE);
            }
        }



        @Override
        public void onRequestPermissionsResult(int requestCode, @Nonnull String[] permissions, @Nonnull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            if (requestCode == PERMISSIONS_REQUEST_CODE) {
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            Log.i("Permissions", "Permissão concedida: " + permissions[i]);
                        } else {
                            Log.i("Permissions", "Permissão negada: " + permissions[i]);
                        }
                    }
                }
            }
        }




    }
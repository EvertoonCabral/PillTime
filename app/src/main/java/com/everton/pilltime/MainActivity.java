    package com.everton.pilltime;

    import androidx.appcompat.app.AppCompatActivity;

    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.graphics.Color;
    import android.os.Bundle;
    import android.os.Handler;
    import android.view.View;

    import com.everton.pilltime.api.ApiUser;
    import com.everton.pilltime.api.Retrofit;
    import com.everton.pilltime.databinding.ActivityFormLoginBinding;
    import com.everton.pilltime.user.AuthenticationDTO;
    import com.everton.pilltime.user.LoginResponseDTO;
    import com.google.android.material.snackbar.Snackbar;
    import com.google.firebase.auth.FirebaseAuth;

    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;

    public class MainActivity extends AppCompatActivity {

        private ActivityFormLoginBinding binding;
        String [] mensagens = {"Preencha todos os campos","Logind efetuado com sucesso"};

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityFormLoginBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            binding.txtCadastro.setOnClickListener(view -> {

                startActivity(new Intent(   this, DadosCadastroCuidador.class));

            });


            binding.btEntrarlogin.setOnClickListener(view -> {

            String email = binding.edLogin.getText().toString().trim();
            String senha = binding.edSenha.getText().toString().trim();

                if(email.isEmpty() || senha.isEmpty()){
                    Snackbar snackbar = Snackbar.make(view, mensagens[0], Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else{
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

                    if(response.isSuccessful()) {
                        LoginResponseDTO loginResponseDTO = response.body();
                        String token = loginResponseDTO.getToken();

                        SharedPreferences sharedPreferences = getSharedPreferences("MyToken", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token", token);
                        editor.apply();

                        TelaPrincipal();
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



        private void TelaPrincipal(){

            //Metodo para eu ir da Tela de login para a tela principal

            Intent intent = new Intent(MainActivity.this,TelaPrincipal.class);
            startActivity(intent);
            finish();
        }

    }
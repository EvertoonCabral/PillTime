package com.everton.pilltime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.everton.pilltime.databinding.ActivityFormLoginBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private ActivityFormLoginBinding binding;
    String [] mensagens = {"Preencha todos os campos","Logind efetuado com sucesso"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); //usando ViewBinding ao invez de FindView

        binding.txtCadastro.setOnClickListener(view -> {

            startActivity(new Intent(   this, CadastroCuidador.class));

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

        String email = binding.edLogin.getText().toString().trim();
        String senha = binding.edSenha.getText().toString().trim();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,senha).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                binding.progressBarLogin.setVisibility(View.VISIBLE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        TelaPrincipal();
                    }
                }, 3000); //3 segundo de progressBar aparecendo
            } else {
                String erro;
                try {
                    throw task.getException();
                }catch (Exception e){
                    erro = "erro ao logar";
                }
                Snackbar snackbar = Snackbar.make(view, erro, Snackbar.LENGTH_LONG);
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
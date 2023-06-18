package com.everton.pilltime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.everton.pilltime.databinding.ActivityFormLoginBinding;
import com.google.firebase.ktx.Firebase;

public class MainActivity extends AppCompatActivity {

    private ActivityFormLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); //usando ViewBinding ao invez de FindView

        binding.txtCadastro.setOnClickListener(view -> {

            startActivity(new Intent(this, CadastroCuidador.class));

        });


    }

}
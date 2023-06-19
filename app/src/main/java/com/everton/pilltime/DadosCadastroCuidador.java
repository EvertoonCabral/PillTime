package com.everton.pilltime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.everton.pilltime.databinding.ActivityDadosCadastroCuidadorBinding;

public class DadosCadastroCuidador extends AppCompatActivity {

    private ActivityDadosCadastroCuidadorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDadosCadastroCuidadorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }

    private void validarMetodos(){

    }

}
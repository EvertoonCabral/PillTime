package com.everton.pilltime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TelaConfPerfilIdoso extends AppCompatActivity {

    private EditText edNomeUsuario, edSenhaUsuario,edCPFUsuario, edDataNascUsuario, edOBSUsuario;

    private Button btnSalvar, btnEndereco;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_conf_perfil_idoso);


        btnSalvar = findViewById(R.id.btnSalvarEditPerfil);
        btnEndereco = findViewById(R.id.btnAlterarEndereco);
        edNomeUsuario = findViewById(R.id.edNomeUsuario);
        edSenhaUsuario = findViewById(R.id.edSenhaUsuario);
        edCPFUsuario = findViewById(R.id.edCPFUsuario);
        edDataNascUsuario = findViewById(R.id.edDataNascUsuario);
        edOBSUsuario = findViewById(R.id.edOBSUsuario);


        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarCampos();

            }
        });


        btnEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaConfPerfilIdoso.this, TelaAlterarEndereco.class);
                startActivity(intent);
                finish();

            }
        });

    }

    private boolean validarDataNascimento(String data) {
        SimpleDateFormat sdf;
        if (data.contains("/")) {
            sdf = new SimpleDateFormat("dd/MM/yyyy");
        } else if (data.length() == 8) {
            sdf = new SimpleDateFormat("ddMMyyyy");
        } else {
            Toast.makeText(TelaConfPerfilIdoso.this, "Formato de data inválido. Use dd/mm/yyyy ou ddmmyyyy.", Toast.LENGTH_SHORT).show();
            return false;
        }

        sdf.setLenient(false); // Isso faz com que a data seja estritamente validada

        try {
            sdf.parse(data);
            return true;
        } catch (ParseException e) {
            Toast.makeText(TelaConfPerfilIdoso.this, "Data de nascimento inválida.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean validarCampos() {
        String nome = edNomeUsuario.getText().toString().trim();
        String senha = edSenhaUsuario.getText().toString().trim();
        String cpf = edCPFUsuario.getText().toString().trim();
        String dataNascimento = edDataNascUsuario.getText().toString().trim();
        String obs = edOBSUsuario.getText().toString().trim();

        if (nome.length() < 2 || nome.length() > 50) {
            edNomeUsuario.setError("O nome deve ter entre 2 e 50 caracteres.");
            Toast.makeText(this, "O nome deve ter entre 2 e 50 caracteres.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (senha.length() < 6) {
            edSenhaUsuario.setError("A senha deve ter mais de 6 caracteres.");
            Toast.makeText(this, "A senha deve ter mais de 6 caracteres.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (cpf.length() != 11) {
            edCPFUsuario.setError("O CPF deve ter 11 caracteres.");
            Toast.makeText(this, "O CPF deve ter 11 caracteres.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!validarDataNascimento(dataNascimento)) {
            return false;
        }

        if (obs.length() > 100) {
            edOBSUsuario.setError("Observações não deve ter mais de 100 caracteres.");
            Toast.makeText(this, "Observações não deve ter mais de 100 caracteres.", Toast.LENGTH_SHORT).show();
            return false;
        }

        Toast.makeText(this, "Dados validados com sucesso!", Toast.LENGTH_SHORT).show();
        return true;
    }




}
package com.everton.pilltime;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TelaAlterarEndereco extends AppCompatActivity {
    EditText edCepUsuario, edRuaUsuario, edBairrolUsuario, edCidadeUsuario, edEstadoUsuario, edNumLogradouroUsuario, edComplementoUsuario;
    Button btnBuscarCep, btnSalvar, btnLimpar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_alterar_endereco);

            iniciarComponentes();

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarEndereco()) {



                    Toast.makeText(TelaAlterarEndereco.this, "Dados salvos com sucesso!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limparCampos();
            }
        });






    }






    private boolean validarEndereco() {
        String cep = edCepUsuario.getText().toString().trim();
        String rua = edRuaUsuario.getText().toString().trim();
        String bairro = edBairrolUsuario.getText().toString().trim();
        String cidade = edCidadeUsuario.getText().toString().trim();
        String estado = edEstadoUsuario.getText().toString().trim();
        String numero = edNumLogradouroUsuario.getText().toString().trim();

        if (cep.length() != 8 || !cep.matches("[0-9]+")) {
            Toast.makeText(this, "CEP inválido. Deve ter 8 números.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (rua.isEmpty() || rua.length() > 100) {
            Toast.makeText(this, "Rua inválida. Deve ter entre 1 e 100 caracteres.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (bairro.isEmpty() || bairro.length() > 50) {
            Toast.makeText(this, "Bairro inválido. Deve ter entre 1 e 50 caracteres.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (cidade.isEmpty() || cidade.length() > 50) {
            Toast.makeText(this, "Cidade inválida. Deve ter entre 1 e 50 caracteres.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (estado.isEmpty() || (estado.length() != 2 && estado.length() > 20)) {
            Toast.makeText(this, "Estado inválido. Deve ter 2 caracteres (abreviação) ou até 20 caracteres (nome completo).", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (numero.isEmpty() || numero.length() > 10 || !numero.matches("[0-9]+")) {
            Toast.makeText(this, "Número inválido. Deve ter até 10 números.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void iniciarComponentes(){

        edCepUsuario = findViewById(R.id.edCepUsuario);
        edRuaUsuario = findViewById(R.id.edRuaUsuario);
        edBairrolUsuario = findViewById(R.id.edBairrolUsuario);
        edCidadeUsuario = findViewById(R.id.edCidadeUsuario);
        edEstadoUsuario = findViewById(R.id.edEstadoUsuario);
        edNumLogradouroUsuario = findViewById(R.id.edNumLogradouroUsuario);
        edComplementoUsuario = findViewById(R.id.edComplementoUsuario);
        btnBuscarCep = findViewById(R.id.btnBuscarCep);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnLimpar = findViewById(R.id.btnLimpar);

    }

    private void limparCampos() {
        edCepUsuario.setText("");
        edRuaUsuario.setText("");
        edBairrolUsuario.setText("");
        edCidadeUsuario.setText("");
        edEstadoUsuario.setText("");
        edNumLogradouroUsuario.setText("");
        edComplementoUsuario.setText("");
    }




}
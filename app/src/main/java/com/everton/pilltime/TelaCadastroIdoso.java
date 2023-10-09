package com.everton.pilltime;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.everton.pilltime.databinding.ActivityTelaCadastroIdosoBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TelaCadastroIdoso extends AppCompatActivity {

    private ActivityTelaCadastroIdosoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTelaCadastroIdosoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Agora, você pode acessar todos os componentes diretamente através do objeto binding
        // Por exemplo: binding.edNomeCadastroIdoso, binding.btSalvarCadastro, etc.

        binding.btSalvarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validarCampos();

            }
        });

        binding.btLimparDadosUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limparDadosUsuario();
            }
        });

        binding.btLimparDadosEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limparDadosEndereco();
            }
        });
    }


    private boolean validarCampos() {
        String nome = binding.edNomeCadastroIdoso.getText().toString().trim();
        String senha = binding.edSenhaCadastroIdoso.getText().toString().trim();
        String cpf = binding.edCPFCadastroIdoso.getText().toString().trim();
        String dataNascimento = binding.edDataNascCadastroIdoso.getText().toString().trim();
        String estado = binding.edEstadoCadastroCuidador.getText().toString().trim();
        String cidade = binding.edCidadeCadastroIdoso.getText().toString().trim();

        if (nome.length() < 3 || nome.length() > 50) {
            binding.edNomeCadastroIdoso.setError("O nome deve ter entre 2 e 50 caracteres.");
            Toast.makeText(this, "O nome deve ter entre 2 e 50 caracteres.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (senha.length() < 6) {
            binding.edSenhaCadastroIdoso.setError("A senha deve ter mais de 6 caracteres.");
            Toast.makeText(this, "A senha deve ter mais de 6 caracteres.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (cpf.length() != 11) {
            binding.edCPFCadastroIdoso.setError("O CPF deve ter 11 caracteres.");
            Toast.makeText(this, "O CPF deve ter 11 caracteres.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!validarDataNascimento(dataNascimento)) {
            // A mensagem de erro específica será exibida dentro da função validarDataNascimento
            return false;
        }

        if (cidade.isEmpty()) {
            binding.edCidadeCadastroIdoso.setError("Cidade não pode estar vazia.");
            Toast.makeText(this, "Cidade não pode estar vazia.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (estado.length() != 2 || !estado.matches("[A-Z]{2}")) {
            binding.edEstadoCadastroCuidador.setError("Estado deve ser uma sigla válida (ex: PR)!");
            Toast.makeText(this, "Estado deve ser uma sigla válida (ex: PR)!", Toast.LENGTH_SHORT).show();
            return false;
        }

        Toast.makeText(this, "Dados validados com sucesso!", Toast.LENGTH_SHORT).show();
        return true;

    }

    private boolean validarDataNascimento(String dataNascimento) {
        SimpleDateFormat sdf;
        if (dataNascimento.contains("/")) {
            sdf = new SimpleDateFormat("dd/MM/yyyy");
        } else {
            sdf = new SimpleDateFormat("ddMMyyyy");
        }
        sdf.setLenient(false);
        try {
            Date dataNasc = sdf.parse(dataNascimento);

        } catch (ParseException e) {
            binding.edDataNascCadastroIdoso.setError("Formato de data inválido. Use dd/MM/yyyy ou ddmmyyyy.");
            Toast.makeText(this, "Formato de data inválido. Use dd/MM/yyyy ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }



    private void limparDadosUsuario() {
        binding.edNomeCadastroIdoso.setText("");
        binding.edEmailCadastroIdoso.setText("");
        binding.edSenhaCadastroIdoso.setText("");
        binding.edCPFCadastroIdoso.setText("");
        binding.edTelefoneCadastroIdoso.setText("");
        binding.edDataNascCadastroIdoso.setText("");
    }

    private void limparDadosEndereco() {
        binding.edCidadeCadastroIdoso.setText("");
        binding.edEstadoCadastroCuidador.setText("");
        binding.edBairroCadastroIdoso.setText("");
        binding.edRuaCadastroIdoso.setText("");
        binding.edNumeroCadastroCuidador.setText("");
    }

}

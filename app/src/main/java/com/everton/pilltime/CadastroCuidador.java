package com.everton.pilltime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.everton.pilltime.databinding.ActivityCadastroCuidadorBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CadastroCuidador extends AppCompatActivity {

    private ActivityCadastroCuidadorBinding binding;
    private FirebaseAuth mAuth;
    String [] mensagens = {"Preencha todos os campos","Cadatro realizado com sucesso"};

    String usuarioID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroCuidadorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EditText edSenhaCadastroCuidador = findViewById(R.id.edSenhaCadastroCuidador);

        edSenhaCadastroCuidador.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (edSenhaCadastroCuidador.getRight() - edSenhaCadastroCuidador.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if(edSenhaCadastroCuidador.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                            edSenhaCadastroCuidador.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            edSenhaCadastroCuidador.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_blue_closed, 0);
                        } else {
                            edSenhaCadastroCuidador.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            edSenhaCadastroCuidador.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_blue, 0);
                        }
                        return true;
                    }
                }
                return false;
            }
        });


        binding.btSalvarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nome = binding.edNomeCadastroCuidador.getText().toString().trim();
                String email = binding.edEmailCadastroCuidador.getText().toString().trim();
                String senha = binding.edSenhaCadastroCuidador.getText().toString().trim();
                String confirmarSenha = binding.edConfirmarSenha.getText().toString().trim();


                if(nome.isEmpty() || email.isEmpty() || senha.isEmpty()){

                    Snackbar snackbar = Snackbar.make(view, mensagens[0], Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else{
                    CadastrarUsuario(view);
                }


            }
        });






    }

    public void CadastrarUsuario(View view){
        String email = binding.edEmailCadastroCuidador.getText().toString().trim();
        String senha = binding.edSenhaCadastroCuidador.getText().toString().trim();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    //se o cadastro acontecer de maneira correta salvar dados no banco de dados
                    SalvarDadosUsuario();

                    Snackbar snackbar = Snackbar.make(view, mensagens[1], Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();

                }else{
                    String erro;
                    try {
                        throw task.getException();

                    }catch (FirebaseAuthWeakPasswordException e) {

                        erro = "A senha deve ter no minimo 6 caracteres";

                    }catch(FirebaseAuthUserCollisionException e){

                        erro = "Email já cadastrado";

                    }catch(FirebaseAuthInvalidCredentialsException e){

                        erro = "Email invalido";

                    }catch (Exception e){

                        erro = "Erro ao cadastrar usuario";
                    }

                    Snackbar snackbar = Snackbar.make(view, erro, Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();

                }

            }
        });

    }
    private void SalvarDadosUsuario(){

        String nome = binding.edNomeCadastroCuidador.getText().toString().trim();

        //criando o banco de dados
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String,Object> usuario = new HashMap<>();
        usuario.put("nome",nome);

        //é preciso iniciar o FireBaseAuth para acessar o usuario atual, o metod getCurrentUser().getUid pega o usuario e o ID autal para colocar no Banco de dados
        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //criando uma coleção de usuario, onde cada usuario criado no banco de dados tera um documento baseado no usuarioID
        DocumentReference documentReference = db.collection("Usuario").document(usuarioID);
        documentReference.set(usuario).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        //se os dados forem salvos com sucesso:

                        Log.d("db","Sucesso ao salvar os dados");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        //se os dados nao forem salvos imprimir a msg e o erro em forma de String

                        Log.d("db_error","Erro ao salvar os dados"+ e.toString());
                    }
                });
    }



    /* public void ValidarDados(){

        String nome = binding.edNomeCadastroCuidador.getText().toString().trim();
        String email = binding.edEmailCadastroCuidador.getText().toString().trim();
        String senha = binding.edSenhaCadastroCuidador.getText().toString().trim();
        String confirmarSenha = binding.edConfirmarSenha.getText().toString().trim();


        if(!senha.equals(confirmarSenha)) {
            Toast.makeText(this, "As duas senhas informadas devem ser iguais", Toast.LENGTH_LONG).show();
        }

        if(!email.isEmpty()){
            if(!senha.isEmpty()){

                criarContaFireBase(email,senha);

            }else{
                Toast.makeText(this,"Informe uma senha", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Informe seu Email", Toast.LENGTH_SHORT).show();
        }

    }

    private void criarContaFireBase(String email,String senha ){

        mAuth.createUserWithEmailAndPassword(email, senha
                ).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){

                        startActivity(new Intent(this, MainActivity.class));

                    }else{
                        Toast.makeText(this, "Ocorreu um Erro.", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    */
}
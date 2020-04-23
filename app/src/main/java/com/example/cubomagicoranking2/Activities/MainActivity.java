package com.example.cubomagicoranking2.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cubomagicoranking2.Domain.Jogador;
import com.example.cubomagicoranking2.R;
import com.example.cubomagicoranking2.config.AuthConfig;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

    private TextInputEditText email;

    private TextInputEditText senha;

    private Button entrar;

    private TextView textViewCadastro;

    private Jogador jogador;

    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.textEmail);
        senha = findViewById(R.id.textSenha);



        textViewCadastro = findViewById(R.id.textViewCadastro);
        textViewCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CadastroActivity.class);

                startActivity(intent);
            }
        });

        entrar = findViewById(R.id.buttonEntrar);
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String emailString = email.getText().toString();
                String senhaString = senha.getText().toString();

                if(emailString.isEmpty() || senhaString.isEmpty()){
                    mostrarToast("Preencha todos os campos");
                }else {
                    jogador = new Jogador();
                    jogador.setEmail(emailString);
                    jogador.setSenha(senhaString);

                    validarLogin();


                    }
                }
            });
    }

    @Override
    protected void onStart() {
        verificarUsuarioLogado();
        super.onStart();
    }

    public void mostrarToast(String msg){
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    public void validarLogin(){
        autenticacao = AuthConfig.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                jogador.getEmail(),
                jogador.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    mostrarToast("Login efetuado com sucesso");
                    abrirTelaDeRanking();
                }else{

                    try{
                        throw task.getException();
                    }
                    catch(FirebaseAuthInvalidCredentialsException e){
                        mostrarToast("Senha incorreta");
                    }
                    catch(FirebaseAuthInvalidUserException e) {
                        mostrarToast("Usuário não cadastrado");
                    }
                    catch(Exception e ) {
                        mostrarToast("Usuário ou senha inválidos");
                        e.printStackTrace();
                    }
                    }
                }
            });
        }
        public void abrirTelaDeRanking(){
            Intent intent = new Intent(this, Rankings.class);
            startActivity(intent);
        }

        public void verificarUsuarioLogado(){
            autenticacao= AuthConfig.getFirebaseAutenticacao();

            if(autenticacao.getCurrentUser() != null){
                abrirTelaDeRanking();
            }
        }
    }


package com.example.cubomagicoranking2.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cubomagicoranking2.Domain.Jogador;
import com.example.cubomagicoranking2.Helper.JogadorDAO;
import com.example.cubomagicoranking2.R;
import com.example.cubomagicoranking2.config.AuthConfig;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.widget.Toast.LENGTH_SHORT;

public class CadastroActivity extends AppCompatActivity {

    @NonNull
    private EditText textNome;
    @NonNull
    private EditText textSenha;
    @NonNull
    private EditText textEmail;

    private FirebaseAuth autenticacao = FirebaseAuth.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        textNome = findViewById(R.id.editTextNome);
        textSenha = findViewById(R.id.editTextSenha);
        textEmail = findViewById(R.id.editTextEmail);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_tempo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override //cadastrar usuário
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        String textNomeString = textNome.getText().toString();
        String textSenhaString = textSenha.getText().toString();
        String textEmailString = textEmail.getText().toString();

        Jogador jogador = new Jogador();
        jogador.setEmail(textEmailString);
        jogador.setNome(textNomeString);
        jogador.setSenha(textSenhaString);

        switch(item.getItemId()){
            case R.id.itemSalvar:

                if(textNomeString.equals("") || textEmailString.equals("") || textSenhaString.equals("")){

                    Toast.makeText(CadastroActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT ).show();

                }else {
                    autenticacao = AuthConfig.getFirebaseAutenticacao();
                    autenticacao.createUserWithEmailAndPassword(jogador.getEmail(), jogador.getSenha())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Log.i("INFO_DB" , "Usuário cadastrado com sucesso");
                                        Toast.makeText(CadastroActivity.this,"Usuário cadastrado com sucesso", LENGTH_SHORT).show();
                                    }
                                    else{
                                        Log.i("INFO_DB" , "Erro ao cadastrar usuário");
                                        Toast.makeText(CadastroActivity.this , "Erro ao cadastrar usuário", LENGTH_SHORT).show();
                                    }
                                }
                            });

                    break;
                }
        }

        return super.onOptionsItemSelected(item);
    }
}

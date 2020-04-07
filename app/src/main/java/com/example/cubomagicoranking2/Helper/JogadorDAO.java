package com.example.cubomagicoranking2.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObservable;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.cubomagicoranking2.Activities.CadastroActivity;
import com.example.cubomagicoranking2.Domain.Jogador;
import com.example.cubomagicoranking2.config.AuthConfig;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import static android.widget.Toast.LENGTH_SHORT;

public class JogadorDAO implements IJogadorDAO{
    private Context context;

    private FirebaseAuth autenticacao = AuthConfig.getFirebaseAutenticacao();

    private Jogador jogador;

    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

    public JogadorDAO(Context context) {
        this.context = context;
    }

    @Override
    public boolean cadastrarJogador(Jogador jogador) {

        autenticacao.createUserWithEmailAndPassword(jogador.getEmail(), jogador.getSenha())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Log.i("INFO_DB" , "Usuário cadastrado com sucesso");
                                Toast.makeText(context,"Usuário cadastrado com sucesso", LENGTH_SHORT).show();
                            }
                            else{
                                Log.i("INFO_DB" , "Erro ao cadastrar usuário");
                                Toast.makeText(context , "Erro ao cadastrar usuário", LENGTH_SHORT).show();
                            }
                        }
                    });

        return true;
    }

    @Override
    public boolean salvar(Jogador jogador) {

        DatabaseReference usuarios = referencia.child("usuarios");

        usuarios.setValue(jogador);

        return true;
    }

    @Override
    public boolean atualizar(Jogador jogador) {
        return false;
    }

    @Override
    public boolean deletar(Jogador jogador) {
        return false;
    }

    @Override
    public List<Jogador> listar() {
        return null;
    }


}

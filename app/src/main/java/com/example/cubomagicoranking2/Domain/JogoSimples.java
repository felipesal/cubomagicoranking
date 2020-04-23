package com.example.cubomagicoranking2.Domain;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.cubomagicoranking2.Helper.Base64Custom;
import com.example.cubomagicoranking2.config.AuthConfig;
import com.example.cubomagicoranking2.config.FirebaseConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class JogoSimples implements Jogos {

    DatabaseReference firebase = FirebaseConfig.getFirebaseDatabase();
    FirebaseAuth autenticacao = AuthConfig.getFirebaseAutenticacao();

    private Jogador jogador;

    private int tempoSeg;

    private String tempoStr;

    public JogoSimples() {
    }

    public JogoSimples(Tempo tempo, Jogador jogador) {
        this.jogador = jogador;
        this.tempoSeg = tempo.toSeconds();
        this.tempoStr = tempo.toString();
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public int getTempoSeg() {
        return tempoSeg;
    }

    public void setTempoSeg(int tempoSeg) {
        this.tempoSeg = tempoSeg;
    }

    public String getTempoStr() {
        return tempoStr;
    }

    public void setTempoStr(String tempoStr) {
        this.tempoStr = tempoStr;
    }

    @Override
    public Integer resultadoEmSegundos() {

        return tempoSeg;
    }

  /*  @Override
    public String resultadoEmMinutos() {
      //  return tempo.ofSeconds();
    }*/

    @Override
    public void salvar() {

            firebase.child("temposimples")
                    .push()
                    .setValue(this);
    }






}

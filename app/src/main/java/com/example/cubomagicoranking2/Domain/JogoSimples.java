package com.example.cubomagicoranking2.Domain;

import android.widget.Toast;

import com.example.cubomagicoranking2.Helper.Base64Custom;
import com.example.cubomagicoranking2.config.AuthConfig;
import com.example.cubomagicoranking2.config.FirebaseConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JogoSimples implements Jogos {



    private Tempo tempo;

    public JogoSimples(Tempo tempo ) {
        this.tempo = tempo;
    }

    public Tempo getTempo() {
        return tempo;
    }

    public void setTempo(Tempo tempo) {
        this.tempo = tempo;
    }

    @Override
    public Integer resultadoEmSegundos() {

        return tempo.toSeconds();
    }

    @Override
    public String resultadoEmMinutos() {
        return tempo.ofSeconds();
    }

    @Override
    public void salvar() {

            DatabaseReference firebase = FirebaseConfig.getFirebaseDatabase();
            FirebaseAuth autenticacao = AuthConfig.getFirebaseAutenticacao();
            String idUsuario = Base64Custom.codificarBase64(autenticacao.getCurrentUser().getEmail());
            firebase.child("temposimples")
                    .child(idUsuario)
                    .child("descricao")
                    .child("tempo")
                    .setValue(tempo.toString());

            firebase.child("temposimples")
                    .child(idUsuario)
                    .child("descricao")
                    .child("resultadoemsegundos")
                    .setValue(resultadoEmSegundos());


    }


}

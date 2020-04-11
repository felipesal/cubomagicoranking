package com.example.cubomagicoranking2.config;

import com.example.cubomagicoranking2.Domain.Jogador;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseConfig {

    private static DatabaseReference firebase;



    public static DatabaseReference getFirebaseDatabase(){
        if( firebase == null){
            firebase = FirebaseDatabase.getInstance().getReference();
        }

        return firebase;
    }

    public void salvarJogador(DatabaseReference firebase, Jogador jogador) {
        firebase.child("usuarios")
                .child(jogador.getIdUsuario())
                .setValue(jogador);
    }



    }

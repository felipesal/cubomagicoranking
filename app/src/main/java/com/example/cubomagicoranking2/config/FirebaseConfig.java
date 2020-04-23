package com.example.cubomagicoranking2.config;

import androidx.annotation.NonNull;

import com.example.cubomagicoranking2.Domain.Jogador;
import com.example.cubomagicoranking2.Helper.Base64Custom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseConfig {

    private static DatabaseReference firebase;

    private FirebaseAuth autenticacao = AuthConfig.getFirebaseAutenticacao();





    public static DatabaseReference getFirebaseDatabase(){
        if( firebase == null){
            firebase = FirebaseDatabase.getInstance().getReference();
        }

        return firebase;
    }





    }

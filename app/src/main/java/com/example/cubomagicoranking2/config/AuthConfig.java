package com.example.cubomagicoranking2.config;

import com.google.firebase.auth.FirebaseAuth;

public class AuthConfig {

    private static FirebaseAuth autenticacao;

    public static FirebaseAuth getFirebaseAutenticacao(){
        if(autenticacao == null) {
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;
    }



}

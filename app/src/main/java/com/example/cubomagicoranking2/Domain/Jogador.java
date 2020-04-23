package com.example.cubomagicoranking2.Domain;

import com.example.cubomagicoranking2.config.FirebaseConfig;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class Jogador {

    private String id;
    private String email;
    private String nome;
    private String senha;



    public Jogador() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


    public void salvarJogador() {
        DatabaseReference firebase = FirebaseConfig.getFirebaseDatabase();
        DatabaseReference usuariosPath = firebase.child("usuarios");

        usuariosPath.push().setValue(this);

        this.setId(usuariosPath.getKey());

    }

}
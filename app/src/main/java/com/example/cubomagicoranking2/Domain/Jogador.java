package com.example.cubomagicoranking2.Domain;

public class Jogador {

    private String nome;
    private String email;

    private String senha;

    private JogoSimples jogoSimples;

    private MelhorDeTres jogoMelhor;

    private MediaDosCentrais jogoMedia;

    public Jogador() {
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public JogoSimples getJogoSimples() {
        return jogoSimples;
    }

    public void setJogoSimples(JogoSimples jogoSimples) {
        this.jogoSimples = jogoSimples;
    }

    public MelhorDeTres getJogoMelhor() {
        return jogoMelhor;
    }

    public void setJogoMelhor(MelhorDeTres jogoMelhor) {
        this.jogoMelhor = jogoMelhor;
    }

    public MediaDosCentrais getJogoMedia() {
        return jogoMedia;
    }

    public void setJogoMedia(MediaDosCentrais jogoMedia) {
        this.jogoMedia = jogoMedia;
    }
}

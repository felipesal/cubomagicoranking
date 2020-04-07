package com.example.cubomagicoranking2.Domain;

public class JogoSimples implements Jogos {

    private Tempo tempo;

    public JogoSimples(Tempo tempo) {
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
}

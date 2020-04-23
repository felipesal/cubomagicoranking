package com.example.cubomagicoranking2.Domain;

import com.example.cubomagicoranking2.Helper.Base64Custom;
import com.example.cubomagicoranking2.config.AuthConfig;
import com.example.cubomagicoranking2.config.FirebaseConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class MediaDosCentrais implements Jogos {

    private int tempo1Seg, tempo2Seg, tempo3Seg, tempo4Seg, tempo5Seg;

    private String tempo1Str, tempo2Str, tempo3Str, tempo4Str, tempo5Str;

    private Jogador jogador;

    private String resultadoStr;

    private int resultadoSeg;

    public MediaDosCentrais(Jogador jogador, Tempo tempo1, Tempo tempo2, Tempo tempo3, Tempo tempo4, Tempo tempo5) {
        super();
        this.jogador = jogador;
        this.tempo1Seg = tempo1.toSeconds();
        this.tempo1Str = tempo1.toString();
        this.tempo2Seg = tempo2.toSeconds();
        this.tempo2Str = tempo2.toString();
        this.tempo3Seg = tempo3.toSeconds();
        this.tempo3Str = tempo3.toString();
        this.tempo4Seg = tempo4.toSeconds();
        this.tempo4Str = tempo4.toString();
        this.tempo5Seg = tempo5.toSeconds();
        this.tempo5Str = tempo5.toString();

        resultadoEmSegundos();
        resultadoEmMinutos();
    }

    public int getTempo1Seg() {
        return tempo1Seg;
    }

    public void setTempo1Seg(int tempo1Seg) {
        this.tempo1Seg = tempo1Seg;
    }

    public int getTempo2Seg() {
        return tempo2Seg;
    }

    public void setTempo2Seg(int tempo2Seg) {
        this.tempo2Seg = tempo2Seg;
    }

    public int getTempo3Seg() {
        return tempo3Seg;
    }

    public void setTempo3Seg(int tempo3Seg) {
        this.tempo3Seg = tempo3Seg;
    }

    public int getTempo4Seg() {
        return tempo4Seg;
    }

    public void setTempo4Seg(int tempo4Seg) {
        this.tempo4Seg = tempo4Seg;
    }

    public int getTempo5Seg() {
        return tempo5Seg;
    }

    public void setTempo5Seg(int tempo5Seg) {
        this.tempo5Seg = tempo5Seg;
    }

    public String getTempo1Str() {
        return tempo1Str;
    }

    public void setTempo1Str(String tempo1Str) {
        this.tempo1Str = tempo1Str;
    }

    public String getTempo2Str() {
        return tempo2Str;
    }

    public void setTempo2Str(String tempo2Str) {
        this.tempo2Str = tempo2Str;
    }

    public String getTempo3Str() {
        return tempo3Str;
    }

    public void setTempo3Str(String tempo3Str) {
        this.tempo3Str = tempo3Str;
    }

    public String getTempo4Str() {
        return tempo4Str;
    }

    public void setTempo4Str(String tempo4Str) {
        this.tempo4Str = tempo4Str;
    }

    public String getTempo5Str() {
        return tempo5Str;
    }

    public void setTempo5Str(String tempo5Str) {
        this.tempo5Str = tempo5Str;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public String getResultadoStr() {
        return resultadoStr;
    }

    public void setResultadoStr(String resultadoStr) {
        this.resultadoStr = resultadoStr;
    }

    public int getResultadoSeg() {
        return resultadoSeg;
    }

    public void setResultadoSeg(int resultadoSeg) {
        this.resultadoSeg = resultadoSeg;
    }

    @Override
    public Integer resultadoEmSegundos() {
        Integer[] temposEmSegundos = new Integer[5];

        temposEmSegundos[0] = tempo1Seg;
        temposEmSegundos[1] = tempo2Seg;
        temposEmSegundos[2] = tempo3Seg;
        temposEmSegundos[3] = tempo4Seg;
        temposEmSegundos[4] = tempo5Seg;

        int menorValor = 10000000;
        int maiorValor = 0;
        int posicaoMaior = 0;
        int posicaoMenor = 0;
        int soma = 0;

        for (int i=0; i<temposEmSegundos.length; i++) {
            int valorRef = temposEmSegundos[i];

            if(valorRef<menorValor) {
                menorValor=valorRef;
                posicaoMenor = i;
            }
            if(valorRef>maiorValor) {
                maiorValor = valorRef;
                posicaoMaior = i;
            }

        }

        temposEmSegundos[posicaoMenor] = 0;
        temposEmSegundos[posicaoMaior] = 0;

        for(int i = 0; i<temposEmSegundos.length; i++) {
            soma += temposEmSegundos[i];
        }

        resultadoSeg = soma/3;

        return resultadoSeg;
    }


    public String resultadoEmMinutos() {

        Tempo tempoEmMinutos = new Tempo();
        tempoEmMinutos.setSegundos(resultadoSeg);

        resultadoStr = tempoEmMinutos.ofSeconds();
        return resultadoStr;
    }

    @Override
    public void salvar() {
        DatabaseReference firebase = FirebaseConfig.getFirebaseDatabase();


        firebase.child("tempomediadoscentrais")
                .push()
                .setValue(this);


    }
}

package com.example.cubomagicoranking2.Domain;

import com.example.cubomagicoranking2.Helper.Base64Custom;
import com.example.cubomagicoranking2.config.AuthConfig;
import com.example.cubomagicoranking2.config.FirebaseConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class MelhorDeTres implements Jogos {

    private Jogador jogador;

    private int tempo1Seg, tempo2Seg, tempo3Seg, tempoFinalSeg;

    private String tempo1Str, tempo2Str, tempo3Str, tempoFinalStr;

    public MelhorDeTres() {
    }

    public MelhorDeTres(Jogador jogador, Tempo tempo1, Tempo tempo2, Tempo tempo3) {
        this.jogador = jogador;
        this.tempo1Seg = tempo1.toSeconds();
        this.tempo1Str = tempo1.toString();
        this.tempo2Seg = tempo2.toSeconds();
        this.tempo2Str = tempo3.toString();
        this.tempo3Seg = tempo3.toSeconds();
        this.tempo3Str = tempo3.toString();
        resultadoEmSegundos();
        resultadoEmMinutos();
        }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
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

    public int getTempoFinalSeg() {
        return tempoFinalSeg;
    }

    public void setTempoFinalSeg(int tempoFinalSeg) {
        this.tempoFinalSeg = tempoFinalSeg;
    }

    public String getTempoFinalStr() {
        return tempoFinalStr;
    }

    public void setTempoFinalStr(String tempoFinalStr) {
        this.tempoFinalStr = tempoFinalStr;
    }

    @Override
    public Integer resultadoEmSegundos() {

        List<Integer> temposEmSegundos = new ArrayList<>();

        temposEmSegundos.add(tempo1Seg);
        temposEmSegundos.add(tempo2Seg);
        temposEmSegundos.add(tempo3Seg);

        tempoFinalSeg = 1000000000;

        for(int i=0; i< temposEmSegundos.size(); i++) {

            Integer tempoEmSegundoRef = temposEmSegundos.get(i);

            if(tempoEmSegundoRef < tempoFinalSeg) {
                tempoFinalSeg = tempoEmSegundoRef;
            }

        }

        return tempoFinalSeg;
    }



    public String resultadoEmMinutos() {

        Tempo tempoBonito = new Tempo();
        tempoBonito.setSegundos(tempoFinalSeg);

        tempoFinalStr = tempoBonito.ofSeconds();

        return tempoFinalStr;

    }

    @Override
    public void salvar() {
        DatabaseReference firebase = FirebaseConfig.getFirebaseDatabase();
        firebase.child("tempomelhordetres")
                .push()
                .setValue(this);


    }
}

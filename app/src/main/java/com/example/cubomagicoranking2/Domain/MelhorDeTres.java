package com.example.cubomagicoranking2.Domain;

import com.example.cubomagicoranking2.Helper.Base64Custom;
import com.example.cubomagicoranking2.config.AuthConfig;
import com.example.cubomagicoranking2.config.FirebaseConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class MelhorDeTres implements Jogos {

    private Tempo tempo1, tempo2, tempo3;

    public MelhorDeTres(Tempo tempo1, Tempo tempo2, Tempo tempo3) {
        this.tempo1 = tempo1;
        this.tempo2 = tempo2;
        this.tempo3 = tempo3;
    }

    public Tempo getTempo1() {
        return tempo1;
    }

    public void setTempo1(Tempo tempo1) {
        this.tempo1 = tempo1;
    }

    public Tempo getTempo2() {
        return tempo2;
    }

    public void setTempo2(Tempo tempo2) {
        this.tempo2 = tempo2;
    }

    public Tempo getTempo3() {
        return tempo3;
    }

    public void setTempo3(Tempo tempo3) {
        this.tempo3 = tempo3;
    }

    @Override
    public Integer resultadoEmSegundos() {

        List<Integer> temposEmSegundos = new ArrayList<>();

        temposEmSegundos.add(tempo1.toSeconds());
        temposEmSegundos.add(tempo2.toSeconds());
        temposEmSegundos.add(tempo3.toSeconds());

        Integer menorTempo = 1000000000;

        for(int i=0; i< temposEmSegundos.size(); i++) {

            Integer tempoEmSegundoRef = temposEmSegundos.get(i);

            if(tempoEmSegundoRef < menorTempo) {
                menorTempo = tempoEmSegundoRef;
            }

        }

        return menorTempo;
    }


    @Override
    public String resultadoEmMinutos() {
        Integer segundos = resultadoEmSegundos();

        Tempo tempoBonito = new Tempo();
        tempoBonito.setSegundos(segundos);

        return tempoBonito.ofSeconds();
    }

    @Override
    public void salvar() {
        DatabaseReference firebase = FirebaseConfig.getFirebaseDatabase();
        FirebaseAuth autenticacao = AuthConfig.getFirebaseAutenticacao();
        String idUsuario = Base64Custom.codificarBase64(autenticacao.getCurrentUser().getEmail());
        firebase.child("melhordetres")
                .child(idUsuario)
                .child("descricao")
                .child("tempos")
                .child("tempo1")
                .setValue(tempo1.toString());

        firebase.child("melhordetres")
                .child(idUsuario)
                .child("descricao")
                .child("tempos")
                .child("tempo2")
                .setValue(tempo2.toString());

        firebase.child("melhordetres")
                .child(idUsuario)
                .child("descricao")
                .child("tempos")
                .child("tempo3")
                .setValue(tempo3.toString());

        firebase.child("melhordetres")
                .child(idUsuario)
                .child("descricao")
                .child("resultadoemsegundos")
                .setValue(resultadoEmSegundos());

    }
}

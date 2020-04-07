package com.example.cubomagicoranking2.Domain;

public class Tempo {

    private int minutos;

    private int segundos;

    public Tempo() {
    }

    public Integer getMinutos() {
        return minutos;
    }

    public void setMinutos(Integer minutos) {
        this.minutos = minutos;
    }

    public Integer getSegundos() {
        return segundos;
    }

    public void setSegundos(Integer segundos) {
        this.segundos = segundos;
    }

    public Integer toSeconds() {
        return minutos*60 + segundos;
    }

    public String ofSeconds() {
        int minutosFinais = segundos/60;

        int segundosFinais = (segundos%60);

        Tempo tempoFinal = new Tempo();
        tempoFinal.setMinutos(minutosFinais);
        tempoFinal.setSegundos(segundosFinais);

        return tempoFinal.toString();

    }

    public String toString() {
        return minutos + ":" + segundos;
    }



}

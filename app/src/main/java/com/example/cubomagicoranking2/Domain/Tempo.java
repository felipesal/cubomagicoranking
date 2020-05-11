package com.example.cubomagicoranking2.Domain;

public class Tempo {

    private int minutos;

    private int segundos;

    public Tempo() {
    }

    public Tempo(int minutos, int segundos) {
        this.minutos = minutos;
        this.segundos = segundos;
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
        if(minutos == 1000){
            return "DNF";
        }
        if(String.valueOf(segundos).length() == 1 && String.valueOf(minutos).length() == 1) {
            return "0" + minutos + ":0" + segundos;
        }
        if(String.valueOf(minutos).length() == 1 && String.valueOf(segundos).length() == 2 ){
            return "0"+minutos + ":" + segundos;
        }
        if(String.valueOf(minutos).length() == 2 && String.valueOf(segundos).length() == 1){
            return minutos + ":0" + segundos;
        }
        else{
            return minutos + ":" + segundos;
        }

    }



}

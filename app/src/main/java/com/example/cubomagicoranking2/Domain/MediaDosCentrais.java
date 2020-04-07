package com.example.cubomagicoranking2.Domain;

public class MediaDosCentrais implements Jogos {

    private Tempo tempo1, tempo2, tempo3, tempo4, tempo5;

    public MediaDosCentrais(Tempo tempo1, Tempo tempo2, Tempo tempo3, Tempo tempo4, Tempo tempo5) {
        super();
        this.tempo1 = tempo1;
        this.tempo2 = tempo2;
        this.tempo3 = tempo3;
        this.tempo4 = tempo4;
        this.tempo5 = tempo5;
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

    public Tempo getTempo4() {
        return tempo4;
    }

    public void setTempo4(Tempo tempo4) {
        this.tempo4 = tempo4;
    }

    public Tempo getTempo5() {
        return tempo5;
    }

    public void setTempo5(Tempo tempo5) {
        this.tempo5 = tempo5;
    }
    @Override
    public Integer resultadoEmSegundos() {
        Integer[] temposEmSegundos = new Integer[5];

        temposEmSegundos[0] = tempo1.toSeconds();
        temposEmSegundos[1] = tempo2.toSeconds();
        temposEmSegundos[2] = tempo3.toSeconds();
        temposEmSegundos[3] = tempo4.toSeconds();
        temposEmSegundos[4] = tempo5.toSeconds();

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

        return soma/3;
    }

    @Override
    public String resultadoEmMinutos() {
        int segundosFinais = resultadoEmSegundos();

        Tempo tempoEmMinutos = new Tempo();
        tempoEmMinutos.setSegundos(segundosFinais);

        return tempoEmMinutos.ofSeconds();
    }
}

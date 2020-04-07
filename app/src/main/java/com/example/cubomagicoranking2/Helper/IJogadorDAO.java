package com.example.cubomagicoranking2.Helper;

import android.database.Cursor;

import com.example.cubomagicoranking2.Domain.Jogador;

import java.util.List;

public interface IJogadorDAO{

    public boolean cadastrarJogador(Jogador jogador);

    public boolean salvar(Jogador jogador);

    public boolean atualizar(Jogador jogador);

    public boolean deletar(Jogador jogador);

    public List<Jogador> listar();



}

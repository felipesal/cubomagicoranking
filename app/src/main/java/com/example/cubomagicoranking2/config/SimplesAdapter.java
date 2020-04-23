package com.example.cubomagicoranking2.config;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cubomagicoranking2.Domain.JogoSimples;
import com.example.cubomagicoranking2.R;

import java.util.List;

public class SimplesAdapter extends RecyclerView.Adapter<SimplesAdapter.MyViewHolder> {

    private List<JogoSimples> listaJogoSimples;

    private Context context;

    public SimplesAdapter(List<JogoSimples> listaJogoSimples, Context context) {
        this.listaJogoSimples = listaJogoSimples;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.lista_jogosimples_adapter , parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        JogoSimples jogoSimples = listaJogoSimples.get(position);

        holder.posicao.setText(position);
        holder.nome.setText((jogoSimples.getJogador()).getNome());
        holder.tempo.setText((jogoSimples.getTempoStr()));
    }

    @Override
    public int getItemCount() {
        return listaJogoSimples.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView posicao;
        TextView nome;
        TextView tempo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            posicao = itemView.findViewById(R.id.textPosicao);
            nome = itemView.findViewById(R.id.textNome);
            tempo = itemView.findViewById(R.id.textTempo);
        }
    }

}

package com.example.cubomagicoranking2.config;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cubomagicoranking2.Domain.MelhorDeTres;
import com.example.cubomagicoranking2.R;

import java.util.List;

public class MelhorDeTresAdapter extends RecyclerView.Adapter<MelhorDeTresAdapter.MyViewHolder>{

    private List<MelhorDeTres> listaMelhorDeTres;

    private Context context;

    public MelhorDeTresAdapter(List<MelhorDeTres> listaJogoSimples, Context context) {
        this.listaMelhorDeTres = listaJogoSimples;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_melhordetres , parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MelhorDeTres mdt = listaMelhorDeTres.get(position);

        holder.posicao.setText(String.valueOf(position+1));
        holder.nome.setText(mdt.getJogador().getNome());
        holder.tempo.setText(mdt.getTempoFinalStr());
    }

    @Override
    public int getItemCount() {
        return listaMelhorDeTres.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView posicao;
        TextView nome;
        TextView tempo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            posicao = itemView.findViewById(R.id.textViewPosicaoMedia);
            nome = itemView.findViewById(R.id.textViewNomeMedia);
            tempo = itemView.findViewById(R.id.textViewTempoMedia);
        }
    }

}

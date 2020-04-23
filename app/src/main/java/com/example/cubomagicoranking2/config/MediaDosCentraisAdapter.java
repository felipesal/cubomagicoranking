package com.example.cubomagicoranking2.config;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cubomagicoranking2.Domain.JogoSimples;
import com.example.cubomagicoranking2.Domain.MediaDosCentrais;
import com.example.cubomagicoranking2.R;

import java.util.List;

public class MediaDosCentraisAdapter extends RecyclerView.Adapter<MediaDosCentraisAdapter.MyViewHolder> {

    private List<MediaDosCentrais> listaMediaDosCentrais;

    private Context context;

    public MediaDosCentraisAdapter(List<MediaDosCentrais> listaMediaDosCentrais, Context context) {
        this.listaMediaDosCentrais = listaMediaDosCentrais;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_mediadoscentrais , parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MediaDosCentrais mdc = listaMediaDosCentrais.get(position);

        holder.posicao.setText(String.valueOf(position+1));
        holder.nome.setText((mdc.getJogador()).getNome());
        holder.tempo.setText(mdc.getResultadoStr());
    }

    @Override
    public int getItemCount() {
        return listaMediaDosCentrais.size();
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

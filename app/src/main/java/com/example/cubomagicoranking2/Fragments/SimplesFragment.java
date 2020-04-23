package com.example.cubomagicoranking2.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.cubomagicoranking2.Activities.AdicionarTempoSimplesActivity;
import com.example.cubomagicoranking2.Domain.Jogador;
import com.example.cubomagicoranking2.Domain.JogoSimples;
import com.example.cubomagicoranking2.Domain.Tempo;
import com.example.cubomagicoranking2.Helper.Base64Custom;
import com.example.cubomagicoranking2.R;
import com.example.cubomagicoranking2.config.AuthConfig;
import com.example.cubomagicoranking2.config.FirebaseConfig;
import com.example.cubomagicoranking2.config.SimplesAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SimplesFragment extends Fragment {

    private Context context;
    private FloatingActionButton fabSimples;

    private RecyclerView recyclerView;

    private SimplesAdapter simplesAdapter;

    private List<JogoSimples> jogos = new ArrayList<>();

    private DatabaseReference firebase = FirebaseConfig.getFirebaseDatabase();

    private FirebaseAuth autenticacao = AuthConfig.getFirebaseAutenticacao();

    private SimplesAdapter adapterSimples;

    private Jogador jogador;

    private DatabaseReference tempoSimplesRef;

    private ValueEventListener valueEventListenerJogoSimples;

    private View view;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }


    public SimplesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_simples, container, false);
        this.recuperarJogador();

        fabSimples = view.findViewById(R.id.floatingActionButtonSimples);
        fabSimples.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AdicionarTempoSimplesActivity.class);
                intent.putExtra("nome", jogador.getNome());
                intent.putExtra("email", jogador.getEmail());
                intent.putExtra("id", jogador.getId());
                startActivity(intent);

                Log.i("Dados simples fragment", jogador.getId()+", "+ jogador.getNome() + ", " + jogador.getEmail()+ ".");
            }
        });

        ;


        return view;
    }

    @Override
    public void onStart() {

        carregarRecyclerView(view);

        super.onStart();
    }

    @Override
    public void onStop() {

        tempoSimplesRef.removeEventListener(valueEventListenerJogoSimples);

        super.onStop();
    }

    public void recuperarJogador(){
        Query ref = firebase.child("usuarios").orderByChild("email").equalTo(autenticacao.getCurrentUser().getEmail());

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dados : dataSnapshot.getChildren()){
                    jogador = dados.getValue(Jogador.class);
                    jogador.setId(dados.getKey());
                    Log.i("Dados jogador", jogador.getNome());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void carregarRecyclerView(View view){

        this.recuperarJogosSimples();

        adapterSimples = new SimplesAdapter(jogos, getActivity());

        recyclerView = view.findViewById(R.id.recyclerViewSimples);
        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterSimples);
    }

    public void recuperarJogosSimples(){
        tempoSimplesRef = firebase.child("temposimples");

        Query pesq = tempoSimplesRef.orderByChild("tempoSeg");

        valueEventListenerJogoSimples = pesq.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                jogos.clear();

                for(DataSnapshot dados : dataSnapshot.getChildren()){
                    JogoSimples jogoSimples = dados.getValue(JogoSimples.class);
                    jogos.add(jogoSimples);

                }

                 adapterSimples.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

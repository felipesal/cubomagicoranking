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

    private List<Jogador> jogadores = new ArrayList<>();

    private DatabaseReference firebase = FirebaseConfig.getFirebaseDatabase();

    private FirebaseAuth autenticacao = AuthConfig.getFirebaseAutenticacao();

    private Button recuperar;

    private Jogador jogador;

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
        View view = inflater.inflate(R.layout.fragment_simples, container, false);
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

        Log.i("INFO", "Até aqui ok, step 1");

       System.out.println(jogos);

       Log.i("Info", "Até aqui, ok! Step 2");
      //  recyclerView = view.findViewById(R.id.recyclerViewJogoSimples);

     //   simplesAdapter = new SimplesAdapter(jogos, getActivity());

       /* RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity() );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));
        recyclerView.setAdapter( simplesAdapter );

*/



/*    public void carregarRanking(){

        /*String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);

        reference = FirebaseConfig.getFirebaseDatabase();
        jogoSimplesRef = reference.child("temposimples")
                      .child(idUsuario)
                      .child("descricao");



        jogoSimplesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("Info", dataSnapshot.getChildren().toString());
                jogos.clear();*/
/*
                Tempo tempo1 = new Tempo();
                tempo1.setMinutos(1);
                tempo1.setSegundos(58);

                Tempo tempo2 = new Tempo();
                tempo2.setSegundos(02);
                tempo2.setMinutos(2);
                Jogador jogador1 = new Jogador();
                jogador1.setNome("Parepense");
                jogador1.setEmail("parepense@gmail.com");
                Jogador jogador2 = new Jogador();
                jogador2.setNome("Jarrão");
                jogador2.setEmail("jarrao@gmail.com");
                JogoSimples jogo1 = new JogoSimples(tempo1, jogador1);
                JogoSimples jogo2 = new JogoSimples(tempo2, jogador2);
                jogos.add(jogo1);
                jogos.add(jogo2);

               /* for(DataSnapshot dados : dataSnapshot.getChildren()){

                    JogoSimples jogoSimples = dados.getValue(JogoSimples.class);

                    System.out.println(jogoSimples);
                    jogos.add(jogoSimples);

                }

                simplesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Erro ao recuperar jogadores", Toast.LENGTH_SHORT).show();
                databaseError.getMessage();
            }
        });*/
        return view;
    }

    public void carregarJogadores(){
        DatabaseReference firebase = FirebaseConfig.getFirebaseDatabase();

        final DatabaseReference jogadoresRef = firebase.child("usuarios");

        jogadoresRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dados : dataSnapshot.getChildren()){
                    Jogador jogador = dados.getValue(Jogador.class);
                    jogadores.add(jogador);
                }

                for(int i = 0; i<jogadores.size(); i++){
                 //   Log.i("Info", jogadores.get(i).getNome() + ", " + jogadores.get(i).getEmail());
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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

}

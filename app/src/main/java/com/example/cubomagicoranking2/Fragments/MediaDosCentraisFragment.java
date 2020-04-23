package com.example.cubomagicoranking2.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cubomagicoranking2.Activities.AdicionarTempoMediaActivity;
import com.example.cubomagicoranking2.Activities.AdicionarTempoMelhorActivity;
import com.example.cubomagicoranking2.Domain.Jogador;
import com.example.cubomagicoranking2.R;
import com.example.cubomagicoranking2.config.AuthConfig;
import com.example.cubomagicoranking2.config.FirebaseConfig;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class MediaDosCentraisFragment extends Fragment {

    private Context context;
    private FloatingActionButton fabMedia;

    private Jogador jogador;
    private DatabaseReference firebase = FirebaseConfig.getFirebaseDatabase();
    private FirebaseAuth autenticacao= AuthConfig.getFirebaseAutenticacao();


    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    public MediaDosCentraisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_media_dos_centrais, container, false);
        this.recuperarJogador();

        fabMedia = view.findViewById(R.id.floatingActionButtonMedia);
        fabMedia.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AdicionarTempoMediaActivity.class);
                intent.putExtra("nome", jogador.getNome());
                intent.putExtra("email", jogador.getEmail());
                intent.putExtra("id", jogador.getId());
                Log.i("Dados media fragment", jogador.getId()+", "+ jogador.getNome() + ", " + jogador.getEmail()+ ".");
                startActivity(intent);
            }
        });
        return view;
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

package com.example.cubomagicoranking2.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cubomagicoranking2.Activities.AdicionarTempoMelhorActivity;
import com.example.cubomagicoranking2.Activities.AdicionarTempoSimplesActivity;
import com.example.cubomagicoranking2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class MelhorDeTresFragment extends Fragment {

    private Context context;
    private FloatingActionButton fabMelhorDeTres;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    public MelhorDeTresFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_melhor_de_tres, container, false);
        fabMelhorDeTres = view.findViewById(R.id.floatingActionButtonMelhor);
        fabMelhorDeTres.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AdicionarTempoMelhorActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}

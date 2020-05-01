package com.example.cubomagicoranking2.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cubomagicoranking2.Domain.Jogador;
import com.example.cubomagicoranking2.Domain.JogoSimples;
import com.example.cubomagicoranking2.Domain.MediaDosCentrais;
import com.example.cubomagicoranking2.Domain.MelhorDeTres;
import com.example.cubomagicoranking2.Domain.Tempo;
import com.example.cubomagicoranking2.Helper.Base64Custom;
import com.example.cubomagicoranking2.R;
import com.example.cubomagicoranking2.config.AuthConfig;
import com.example.cubomagicoranking2.config.FirebaseConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class AdicionarTempoMelhorActivity extends AppCompatActivity {

    private EditText minuto1, minuto2, minuto3, segundo1, segundo2, segundo3;

    private Tempo tempo1, tempo2, tempo3;

    private MelhorDeTres melhorDeTres, melhorDeTresRecuperado;

    private Jogador jogador = new Jogador();

    private boolean referencia = true;

    private List<MelhorDeTres> jogos = new ArrayList<>();


    DatabaseReference firebase = FirebaseConfig.getFirebaseDatabase();
    FirebaseAuth autenticacao = AuthConfig.getFirebaseAutenticacao();
    DatabaseReference tempoMelhorDeTres = firebase.child("tempomelhordetres");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tempo_melhor);

        Bundle bundle = getIntent().getExtras();
        String nome = bundle.getString("nome");
        String email = bundle.getString("email");
        String id = bundle.getString("id");
        referencia = bundle.getBoolean("referencia");


        jogador.setId(id);
        jogador.setNome(nome);
        jogador.setEmail(email);

        Log.i("Dado jogador", jogador.getId()+", "+ jogador.getNome() + ", " + jogador.getEmail()+ ".");


        minuto1 = findViewById(R.id.editTextMinuto1);
        minuto2 = findViewById(R.id.editTextMinuto2);
        minuto3 = findViewById(R.id.editTextMinuto3);

        segundo1 = findViewById(R.id.editTextSegundo1);
        segundo2 = findViewById(R.id.editTextSegundo2);
        segundo3 = findViewById(R.id.editTextSegundo3);

        recuperar();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_tempo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.itemSalvar:
                String min1String = minuto1.getText().toString();
                String min2String = minuto2.getText().toString();
                String min3String = minuto3.getText().toString();

                String seg1String = segundo1.getText().toString();
                String seg2String = segundo2.getText().toString();
                String seg3String = segundo3.getText().toString();

                int min1Int = Integer.parseInt(min1String);
                int min2Int = Integer.parseInt(min2String);
                int min3Int = Integer.parseInt(min3String);

                int seg1Int = Integer.parseInt(seg1String);
                int seg2Int = Integer.parseInt(seg2String);
                int seg3Int = Integer.parseInt(seg3String);

                tempo1 = new Tempo();
                tempo2 = new Tempo();
                tempo3 = new Tempo();

                tempo1.setMinutos(min1Int);
                tempo1.setSegundos(seg1Int);
                tempo2.setMinutos(min2Int);
                tempo2.setSegundos(seg2Int);
                tempo3.setMinutos(min3Int);
                tempo3.setSegundos(seg3Int);

                melhorDeTres = new MelhorDeTres(jogador, tempo1, tempo2, tempo3);
                try {
                    if(jogos.size()==0) {
                        melhorDeTres.salvar();
                        mostrarToast("Tempo salvo com sucesso");
                        finish();
                    }
                    else {
                        for (int i = 0; i<jogos.size(); i++){
                            String id = jogos.get(i).getId();
                            melhorDeTres.setId(id);
                        }
                        melhorDeTres.atualizar();
                        mostrarToast("Tempo atualizado");
                        finish();
                    }
                }
                catch(Exception e){
                    mostrarToast("Erro ao salvar tempo");
                    e.printStackTrace();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void recuperar(){

        Query pesqTempoSimples = tempoMelhorDeTres.orderByChild("/jogador/email").equalTo(jogador.getEmail());

        pesqTempoSimples.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    melhorDeTresRecuperado = new MelhorDeTres();

                    for(DataSnapshot dados: dataSnapshot.getChildren()) {
                        melhorDeTresRecuperado = dados.getValue(MelhorDeTres.class);
                        melhorDeTresRecuperado.setId(dados.getKey());
                        jogos.add(melhorDeTresRecuperado);
                        Log.i("Nuh", dados.toString());

                    }
                    for(int i= 0; i<jogos.size(); i++){
                        Log.i("Nuh2", jogos.get(i).getJogador().getNome());
                    }

                }
                else{
                    Log.i("JogoSimples", "Não recuperou nada");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void mostrarToast(String msg){
        Toast.makeText(AdicionarTempoMelhorActivity.this, msg, LENGTH_SHORT).show();
    }




}

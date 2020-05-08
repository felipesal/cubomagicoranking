package com.example.cubomagicoranking2.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cubomagicoranking2.Domain.Jogador;
import com.example.cubomagicoranking2.Domain.JogoSimples;
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

public class AdicionarTempoSimplesActivity extends AppCompatActivity {

    private EditText minutos, segundos;

    private Tempo tempo;

    private JogoSimples jogoSimples, jogoSimplesRecuperado;

    private int segundosInt, minutosInt;

    private String segundosString, minutosString;

   private Jogador jogador = new Jogador();

   private boolean referencia = true;

   List<JogoSimples> jogos = new ArrayList<>();


    DatabaseReference firebase = FirebaseConfig.getFirebaseDatabase();
    FirebaseAuth autenticacao = AuthConfig.getFirebaseAutenticacao();
    DatabaseReference tempoSimples = firebase.child("temposimples");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tempo_simples);

        Bundle bundle = getIntent().getExtras();
        String nome = bundle.getString("nome");
        String email = bundle.getString("email");
        String id = bundle.getString("id");
        referencia = bundle.getBoolean("referencia");

        jogador.setId(id);
        jogador.setNome(nome);
        jogador.setEmail(email);

        Log.i("Dado jogador", jogador.getId()+", "+ jogador.getNome() + ", " + jogador.getEmail()+ ".");

        minutos = findViewById(R.id.editTextMinutos);
        segundos = findViewById(R.id.editTextSegundos);
        minutos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 2){
                    segundos.requestFocus();
                }
            }
        });

        recuperar();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_tempo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.itemSalvar:
                Log.i("Dados jogador", jogador.getNome());

                segundosString = segundos.getText().toString();
                segundosInt = Integer.parseInt(segundosString);


                minutosString = minutos.getText().toString();
                minutosInt = Integer.parseInt(minutosString);

                if (segundosInt < 60) {

                    tempo = new Tempo();
                    tempo.setMinutos(minutosInt);
                    tempo.setSegundos(segundosInt);

                    jogoSimples = new JogoSimples(tempo, jogador);


                    try {
                        if (jogos.size() == 0) {
                            jogoSimples.salvar();
                            mostrarToast("Tempo salvo com sucesso");
                            finish();
                        } else {
                            for (int i = 0; i < jogos.size(); i++) {
                                String id = jogos.get(i).getId();
                                jogoSimples.setId(id);
                            }
                            jogoSimples.atualizar();
                            mostrarToast("Tempo atualizado");
                            finish();
                        }
                    } catch (Exception e) {
                        mostrarToast("Erro ao salvar tempo");
                        e.printStackTrace();
                    }
                    break;
                }
                else{
                    mostrarToast("O campo segundos deve ser menor que 60");
                    finish();
                }
        }

                return super.onOptionsItemSelected(item);
        }





    public void mostrarToast(String msg){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));


        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(msg);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public void recuperar(){

        Query pesqTempoSimples = tempoSimples.orderByChild("/jogador/email").equalTo(jogador.getEmail());

        pesqTempoSimples.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    jogoSimplesRecuperado = new JogoSimples();

                    for(DataSnapshot dados: dataSnapshot.getChildren()) {
                        jogoSimplesRecuperado = dados.getValue(JogoSimples.class);
                        jogoSimplesRecuperado.setId(dados.getKey());
                        jogos.add(jogoSimplesRecuperado);
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






}

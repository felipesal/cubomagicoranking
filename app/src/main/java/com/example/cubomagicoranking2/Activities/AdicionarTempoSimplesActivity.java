package com.example.cubomagicoranking2.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
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

import static android.widget.Toast.LENGTH_SHORT;

public class AdicionarTempoSimplesActivity extends AppCompatActivity {

    private EditText minutos, segundos;

    private Tempo tempo;

    private JogoSimples jogoSimples;

    private int segundosInt, minutosInt;

    private String segundosString, minutosString;

   private Jogador jogador = new Jogador();


    DatabaseReference firebase = FirebaseConfig.getFirebaseDatabase();
    FirebaseAuth autenticacao = AuthConfig.getFirebaseAutenticacao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tempo_simples);

        Bundle bundle = getIntent().getExtras();
        String nome = bundle.getString("nome");
        String email = bundle.getString("email");
        String id = bundle.getString("id");

        jogador.setId(id);
        jogador.setNome(nome);
        jogador.setEmail(email);

        Log.i("Dado jogador", jogador.getId()+", "+ jogador.getNome() + ", " + jogador.getEmail()+ ".");

        minutos = findViewById(R.id.editTextMinutos);
        segundos = findViewById(R.id.editTextSegundos);
     //   this.recuperarJogador();
     //   Log.i("Dados jogador", jogador.getNome());

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
                Log.i("Dados jogador", jogador.getNome());

                segundosString = segundos.getText().toString();
                segundosInt = Integer.parseInt(segundosString);
                minutosString = minutos.getText().toString();
                minutosInt = Integer.parseInt(minutosString);

                tempo = new Tempo();
                tempo.setMinutos(minutosInt);
                tempo.setSegundos(segundosInt);

                jogoSimples = new JogoSimples(tempo ,jogador);

                try {
                    jogoSimples.salvar();
                    mostrarToast("Tempo salvo com sucesso");
                    finish();
                }
                catch(Exception e){
                    mostrarToast("Erro ao salvar tempo");
                    e.printStackTrace();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void mostrarToast(String msg){
        Toast.makeText(AdicionarTempoSimplesActivity.this, msg, LENGTH_SHORT).show();
    }



   /* public static Jogador get(DataSnapshot snapshot) {
        Jogador usuario = new Jogador();
        usuario.setIdUsuario((String) snapshot.child(Base64Custom.codificarBase64()).getValue());
        usuario.setFirstName((String) snapshot.child("firstName").getValue());
        usuario.setLastName((String) snapshot.child("lastName").getValue());
        return usuario;
    }
*/
}

package com.example.cubomagicoranking2.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cubomagicoranking2.Domain.JogoSimples;
import com.example.cubomagicoranking2.Domain.Tempo;
import com.example.cubomagicoranking2.R;

import static android.widget.Toast.LENGTH_SHORT;

public class AdicionarTempoSimplesActivity extends AppCompatActivity {

    private EditText minutos, segundos;

    private Tempo tempo;

    private JogoSimples jogoSimples;

    private int segundosInt, minutosInt;

    private String segundosString, minutosString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tempo_simples);

        minutos = findViewById(R.id.editTextMinutos);
        segundos = findViewById(R.id.editTextSegundos);

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

                segundosString = segundos.getText().toString();
                segundosInt = Integer.parseInt(segundosString);
                minutosString = minutos.getText().toString();
                minutosInt = Integer.parseInt(minutosString);

                tempo = new Tempo();
                tempo.setMinutos(minutosInt);
                tempo.setSegundos(segundosInt);

                jogoSimples = new JogoSimples(tempo);
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

}

package com.example.cubomagicoranking2.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cubomagicoranking2.Domain.MediaDosCentrais;
import com.example.cubomagicoranking2.Domain.Tempo;
import com.example.cubomagicoranking2.R;

import static android.widget.Toast.LENGTH_SHORT;

public class AdicionarTempoMediaActivity extends AppCompatActivity {

    private EditText minutos1, minutos2, minutos3, minutos4, minutos5, segundos1, segundos2, segundos3, segundos4, segundos5;

    private Tempo tempo1, tempo2, tempo3, tempo4, tempo5;

    private MediaDosCentrais mediaDosCentrais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tempo_media);

        minutos1 = findViewById(R.id.editTextMin1);
        minutos2 = findViewById(R.id.editTextMin2);
        minutos3 = findViewById(R.id.editTextMin3);
        minutos4 = findViewById(R.id.editTextMin4);
        minutos5 = findViewById(R.id.editTextMin5);

        segundos1 = findViewById(R.id.editTextSeg1);
        segundos2 = findViewById(R.id.editTextSeg2);
        segundos3 = findViewById(R.id.editTextSeg3);
        segundos4 = findViewById(R.id.editTextSeg4);
        segundos5 = findViewById(R.id.editTextSeg5);




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
                String min1String = minutos1.getText().toString();
                String min2String = minutos2.getText().toString();
                String min3String = minutos3.getText().toString();
                String min4String = minutos4.getText().toString();
                String min5String = minutos5.getText().toString();

                String seg1String = segundos1.getText().toString();
                String seg2String = segundos2.getText().toString();
                String seg3String = segundos3.getText().toString();
                String seg4String = segundos4.getText().toString();
                String seg5String = segundos5.getText().toString();

                int min1Int = Integer.parseInt(min1String);
                int min2Int = Integer.parseInt(min2String);
                int min3Int = Integer.parseInt(min3String);
                int min4Int = Integer.parseInt(min4String);
                int min5Int = Integer.parseInt(min5String);

                int seg1Int = Integer.parseInt(seg1String);
                int seg2Int = Integer.parseInt(seg2String);
                int seg3Int = Integer.parseInt(seg3String);
                int seg4Int = Integer.parseInt(seg4String);
                int seg5Int = Integer.parseInt(seg5String);

                tempo1 = new Tempo();
                tempo2 = new Tempo();
                tempo3 = new Tempo();
                tempo4 = new Tempo();
                tempo5 = new Tempo();

                tempo1.setSegundos(seg1Int);
                tempo1.setMinutos(min1Int);

                tempo2.setSegundos(seg2Int);
                tempo2.setMinutos(min2Int);

                tempo3.setSegundos(seg3Int);
                tempo3.setMinutos(min3Int);

                tempo4.setSegundos(seg4Int);
                tempo4.setMinutos(min4Int);

                tempo5.setSegundos(seg5Int);
                tempo5.setMinutos(min5Int);

                mediaDosCentrais = new MediaDosCentrais(tempo1, tempo2, tempo3, tempo4, tempo5);

                try{
                    mediaDosCentrais.salvar();
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
        Toast.makeText(AdicionarTempoMediaActivity.this, msg, LENGTH_SHORT).show();
    }
}

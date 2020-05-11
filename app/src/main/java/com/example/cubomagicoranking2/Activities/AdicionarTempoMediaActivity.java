package com.example.cubomagicoranking2.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cubomagicoranking2.Domain.Jogador;
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

public class AdicionarTempoMediaActivity extends AppCompatActivity {

    private EditText minutos1, minutos2, minutos3, minutos4, minutos5, segundos1, segundos2, segundos3, segundos4, segundos5;

    private CheckBox dnf1, dnf2, dnf3, dnf4, dnf5;

    private Tempo tempo1, tempo2, tempo3, tempo4, tempo5;

    private MediaDosCentrais mediaDosCentrais, mediaDosCentraisRecuperado;

    private Jogador jogador = new Jogador();

    private Jogador jogadorRecuperado = new Jogador();

    DatabaseReference jogadorRef;

    private boolean referencia = true;

    private List<MediaDosCentrais> jogos = new ArrayList<>();


    DatabaseReference firebase = FirebaseConfig.getFirebaseDatabase();
    DatabaseReference tempoMediaDosCentrais = firebase.child("tempomediadoscentrais");
    FirebaseAuth autenticacao = AuthConfig.getFirebaseAutenticacao();
    ValueEventListener valueEventListenerRecuperarJogador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tempo_media);

        Bundle bundle = getIntent().getExtras();
        String nome = bundle.getString("nome");
        String email = bundle.getString("email");
        String id = bundle.getString("id");
        referencia = bundle.getBoolean("referencia");

        Log.i("Dado referência", String.valueOf(referencia));


        jogador.setId(id);
        jogador.setNome(nome);
        jogador.setEmail(email);

        Log.i("Dado jogador", jogador.getId() + ", " + jogador.getNome() + ", " + jogador.getEmail() + ".");

        dnf1 = findViewById(R.id.checkBoxDnfM1);
        dnf2 = findViewById(R.id.checkBoxDnfM2);
        dnf3 = findViewById(R.id.checkBoxDnfM3);
        dnf4 = findViewById(R.id.checkBoxDnfM4);
        dnf5 = findViewById(R.id.checkBoxDnfM5);


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

        recuperar();

        passarParaProximoEditText(minutos1, segundos1);
        passarParaProximoEditText(segundos1, minutos2);
        passarParaProximoEditText(minutos2, segundos2);
        passarParaProximoEditText(segundos2, minutos3);
        passarParaProximoEditText(minutos3, segundos3);
        passarParaProximoEditText(segundos3, minutos4);
        passarParaProximoEditText(minutos4, segundos4);
        passarParaProximoEditText(segundos4, minutos5);
        passarParaProximoEditText(minutos5, segundos5);


    }


    @Override
    protected void onStart() {
        //recuperarJogador();
        super.onStart();
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
                CheckBox[] dnfs = {dnf1, dnf2, dnf3, dnf4, dnf5};
                int count = 0;
                int posicao = 0;

                for (int i = 0; i < dnfs.length; i++) {
                    if (dnfs[i].isChecked()) {
                        count += 1;

                    }
                }

                if (count == 0) {

                    if (
                            !minutos1.getText().toString().equals("") &&
                                    !minutos2.getText().toString().equals("") &&
                                    !minutos3.getText().toString().equals("") &&
                                    !minutos4.getText().toString().equals("") &&
                                    !minutos5.getText().toString().equals("") &&
                                    !segundos1.getText().toString().equals("") &&
                                    !segundos2.getText().toString().equals("") &&
                                    !segundos3.getText().toString().equals("") &&
                                    !segundos4.getText().toString().equals("") &&
                                    !segundos5.getText().toString().equals("")
                    ) {
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

                        if (seg1Int < 60 && seg2Int < 60 && seg3Int < 60 && seg4Int < 60 && seg5Int < 60) {

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

                            mediaDosCentrais = new MediaDosCentrais(jogador, tempo1, tempo2, tempo3, tempo4, tempo5);

                            try {
                                if (jogos.size() == 0) {
                                    mediaDosCentrais.salvar();
                                    mostrarToast("Tempo salvo com sucesso");
                                    finish();
                                } else {
                                    for (int i = 0; i < jogos.size(); i++) {
                                        String id = jogos.get(i).getId();
                                        mediaDosCentrais.setId(id);
                                    }
                                    mediaDosCentrais.atualizar();
                                    mostrarToast("Tempo atualizado");
                                    finish();
                                }
                            } catch (Exception e) {
                                mostrarToast("Erro ao salvar tempo");
                                e.printStackTrace();
                            }


                            break;
                        } else {
                            mostrarToast("O campo segundos deve ser menor que 60");

                        }
                    } else {
                        mostrarToast("Preencha todos os campos");
                    }
                } else if (count == 1) {

                    if (dnf1.isChecked()) {

                        String min2String = minutos2.getText().toString();
                        String min3String = minutos3.getText().toString();
                        String min4String = minutos4.getText().toString();
                        String min5String = minutos5.getText().toString();


                        String seg2String = segundos2.getText().toString();
                        String seg3String = segundos3.getText().toString();
                        String seg4String = segundos4.getText().toString();
                        String seg5String = segundos5.getText().toString();


                        int min2Int = Integer.parseInt(min2String);
                        int min3Int = Integer.parseInt(min3String);
                        int min4Int = Integer.parseInt(min4String);
                        int min5Int = Integer.parseInt(min5String);


                        int seg2Int = Integer.parseInt(seg2String);
                        int seg3Int = Integer.parseInt(seg3String);
                        int seg4Int = Integer.parseInt(seg4String);
                        int seg5Int = Integer.parseInt(seg5String);



                            tempo1 = new Tempo();
                            tempo2 = new Tempo();
                            tempo3 = new Tempo();
                            tempo4 = new Tempo();
                            tempo5 = new Tempo();

                            tempo1.setSegundos(00);
                            tempo1.setMinutos(1000);

                            tempo2.setSegundos(seg2Int);
                            tempo2.setMinutos(min2Int);

                            tempo3.setSegundos(seg3Int);
                            tempo3.setMinutos(min3Int);

                            tempo4.setSegundos(seg4Int);
                            tempo4.setMinutos(min4Int);

                            tempo5.setSegundos(seg5Int);
                            tempo5.setMinutos(min5Int);

                            mediaDosCentrais = new MediaDosCentrais(jogador, tempo1, tempo2, tempo3, tempo4, tempo5);

                            try {
                                if (jogos.size() == 0) {
                                    mediaDosCentrais.salvar();
                                    mostrarToast("Tempo salvo com sucesso");
                                    finish();
                                } else {
                                    for (int i = 0; i < jogos.size(); i++) {
                                        String id = jogos.get(i).getId();
                                        mediaDosCentrais.setId(id);
                                    }
                                    mediaDosCentrais.atualizar();
                                    mostrarToast("Tempo atualizado");
                                    finish();
                                }
                            } catch (Exception e) {
                                mostrarToast("Erro ao salvar tempo");
                                e.printStackTrace();
                            }


                            break;

                        }

                        if (dnf2.isChecked()) {


                            String min1String = minutos1.getText().toString();

                            String min3String = minutos3.getText().toString();
                            String min4String = minutos4.getText().toString();
                            String min5String = minutos5.getText().toString();

                            String seg1String = segundos1.getText().toString();

                            String seg3String = segundos3.getText().toString();
                            String seg4String = segundos4.getText().toString();
                            String seg5String = segundos5.getText().toString();

                            int min1Int = Integer.parseInt(min1String);

                            int min3Int = Integer.parseInt(min3String);
                            int min4Int = Integer.parseInt(min4String);
                            int min5Int = Integer.parseInt(min5String);

                            int seg1Int = Integer.parseInt(seg1String);

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

                            tempo2.setSegundos(00);
                            tempo2.setMinutos(1000);

                            tempo3.setSegundos(seg3Int);
                            tempo3.setMinutos(min3Int);

                            tempo4.setSegundos(seg4Int);
                            tempo4.setMinutos(min4Int);

                            tempo5.setSegundos(seg5Int);
                            tempo5.setMinutos(min5Int);

                            mediaDosCentrais = new MediaDosCentrais(jogador, tempo1, tempo2, tempo3, tempo4, tempo5);

                            try {
                                if (jogos.size() == 0) {
                                    mediaDosCentrais.salvar();
                                    mostrarToast("Tempo salvo com sucesso");
                                    finish();
                                } else {
                                    for (int i = 0; i < jogos.size(); i++) {
                                        String id = jogos.get(i).getId();
                                        mediaDosCentrais.setId(id);
                                    }
                                    mediaDosCentrais.atualizar();
                                    mostrarToast("Tempo atualizado");
                                    finish();
                                }
                            } catch (Exception e) {
                                mostrarToast("Erro ao salvar tempo");
                                e.printStackTrace();
                            }


                            break;

                        }

                        if (dnf3.isChecked()) {


                            String min1String = minutos1.getText().toString();
                            String min2String = minutos2.getText().toString();

                            String min4String = minutos4.getText().toString();
                            String min5String = minutos5.getText().toString();

                            String seg1String = segundos1.getText().toString();
                            String seg2String = segundos2.getText().toString();

                            String seg4String = segundos4.getText().toString();
                            String seg5String = segundos5.getText().toString();

                            int min1Int = Integer.parseInt(min1String);
                            int min2Int = Integer.parseInt(min2String);

                            int min4Int = Integer.parseInt(min4String);
                            int min5Int = Integer.parseInt(min5String);

                            int seg1Int = Integer.parseInt(seg1String);
                            int seg2Int = Integer.parseInt(seg2String);

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

                            tempo3.setSegundos(00);
                            tempo3.setMinutos(1000);

                            tempo4.setSegundos(seg4Int);
                            tempo4.setMinutos(min4Int);

                            tempo5.setSegundos(seg5Int);
                            tempo5.setMinutos(min5Int);

                            mediaDosCentrais = new MediaDosCentrais(jogador, tempo1, tempo2, tempo3, tempo4, tempo5);

                            try {
                                if (jogos.size() == 0) {
                                    mediaDosCentrais.salvar();
                                    mostrarToast("Tempo salvo com sucesso");
                                    finish();
                                } else {
                                    for (int i = 0; i < jogos.size(); i++) {
                                        String id = jogos.get(i).getId();
                                        mediaDosCentrais.setId(id);
                                    }
                                    mediaDosCentrais.atualizar();
                                    mostrarToast("Tempo atualizado");
                                    finish();
                                }
                            } catch (Exception e) {
                                mostrarToast("Erro ao salvar tempo");
                                e.printStackTrace();
                            }


                            break;

                        }

                        if (dnf4.isChecked()) {


                            String min1String = minutos1.getText().toString();
                            String min2String = minutos2.getText().toString();
                            String min3String = minutos3.getText().toString();

                            String min5String = minutos5.getText().toString();

                            String seg1String = segundos1.getText().toString();
                            String seg2String = segundos2.getText().toString();
                            String seg3String = segundos3.getText().toString();

                            String seg5String = segundos5.getText().toString();

                            int min1Int = Integer.parseInt(min1String);
                            int min2Int = Integer.parseInt(min2String);
                            int min3Int = Integer.parseInt(min3String);

                            int min5Int = Integer.parseInt(min5String);

                            int seg1Int = Integer.parseInt(seg1String);
                            int seg2Int = Integer.parseInt(seg2String);
                            int seg3Int = Integer.parseInt(seg3String);

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

                            tempo4.setSegundos(00);
                            tempo4.setMinutos(1000);

                            tempo5.setSegundos(seg5Int);
                            tempo5.setMinutos(min5Int);

                            mediaDosCentrais = new MediaDosCentrais(jogador, tempo1, tempo2, tempo3, tempo4, tempo5);

                            try {
                                if (jogos.size() == 0) {
                                    mediaDosCentrais.salvar();
                                    mostrarToast("Tempo salvo com sucesso");
                                    finish();
                                } else {
                                    for (int i = 0; i < jogos.size(); i++) {
                                        String id = jogos.get(i).getId();
                                        mediaDosCentrais.setId(id);
                                    }
                                    mediaDosCentrais.atualizar();
                                    mostrarToast("Tempo atualizado");
                                    finish();
                                }
                            } catch (Exception e) {
                                mostrarToast("Erro ao salvar tempo");
                                e.printStackTrace();
                            }


                            break;

                        }

                        if (dnf5.isChecked()) {

                            String min1String = minutos1.getText().toString();
                            String min2String = minutos2.getText().toString();
                            String min3String = minutos3.getText().toString();
                            String min4String = minutos4.getText().toString();


                            String seg1String = segundos1.getText().toString();
                            String seg2String = segundos2.getText().toString();
                            String seg3String = segundos3.getText().toString();
                            String seg4String = segundos4.getText().toString();


                            int min1Int = Integer.parseInt(min1String);
                            int min2Int = Integer.parseInt(min2String);
                            int min3Int = Integer.parseInt(min3String);
                            int min4Int = Integer.parseInt(min4String);


                            int seg1Int = Integer.parseInt(seg1String);
                            int seg2Int = Integer.parseInt(seg2String);
                            int seg3Int = Integer.parseInt(seg3String);
                            int seg4Int = Integer.parseInt(seg4String);




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

                            tempo5.setSegundos(00);
                            tempo5.setMinutos(1000);


                            mediaDosCentrais = new MediaDosCentrais(jogador, tempo1, tempo2, tempo3, tempo4, tempo5);

                            try {
                                if (jogos.size() == 0) {
                                    mediaDosCentrais.salvar();
                                    mostrarToast("Tempo salvo com sucesso");
                                    finish();
                                } else {
                                    for (int i = 0; i < jogos.size(); i++) {
                                        String id = jogos.get(i).getId();
                                        mediaDosCentrais.setId(id);
                                    }
                                    mediaDosCentrais.atualizar();
                                    mostrarToast("Tempo atualizado");
                                    finish();
                                }
                            } catch (Exception e) {
                                mostrarToast("Erro ao salvar tempo");
                                e.printStackTrace();
                            }


                            break;

                        }


                    } else {
                    tempo1 = new Tempo();
                    tempo2 = new Tempo();
                    tempo3 = new Tempo();
                    tempo4 = new Tempo();
                    tempo5 = new Tempo();

                    tempo1.setSegundos(00);
                    tempo1.setMinutos(1000);

                    tempo2.setSegundos(00);
                    tempo2.setMinutos(1000);

                    tempo3.setSegundos(00);
                    tempo3.setMinutos(1000);

                    tempo4.setSegundos(00);
                    tempo4.setMinutos(1000);

                    tempo5.setSegundos(00);
                    tempo5.setMinutos(1000);

                    mediaDosCentrais = new MediaDosCentrais(jogador, tempo1, tempo2, tempo3, tempo4, tempo5);

                        try {
                            if (jogos.size() == 0) {
                                mediaDosCentrais.salvar();
                                mostrarToast("Tempo salvo com sucesso");
                                finish();
                            } else {
                                for (int i = 0; i < jogos.size(); i++) {
                                    String id = jogos.get(i).getId();
                                    mediaDosCentrais.setId(id);
                                }
                                mediaDosCentrais.atualizar();
                                mostrarToast("Tempo atualizado");
                                finish();
                            }
                        } catch (Exception e) {
                            mostrarToast("Erro ao salvar tempo");
                            e.printStackTrace();
                        }


                        break;
                    }
                }


                return super.onOptionsItemSelected(item);
        }

        public void mostrarToast (String msg){
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

        private void recuperar () {
            Query pesqTempoMediaDosCentrais = tempoMediaDosCentrais.orderByChild("/jogador/email").equalTo(jogador.getEmail());

            pesqTempoMediaDosCentrais.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        mediaDosCentraisRecuperado = new MediaDosCentrais();

                        for (DataSnapshot dados : dataSnapshot.getChildren()) {
                            mediaDosCentraisRecuperado = dados.getValue(MediaDosCentrais.class);
                            mediaDosCentraisRecuperado.setId(dados.getKey());
                            jogos.add(mediaDosCentraisRecuperado);
                            Log.i("Nuh", dados.toString());

                        }
                        for (int i = 0; i < jogos.size(); i++) {
                            Log.i("Nuh2", jogos.get(i).getJogador().getNome());
                        }

                    } else {
                        Log.i("JogoSimples", "Não recuperou nada");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        public void recuperarJogador () {
            jogadorRef = firebase.child("tempomediadoscentrais").child("jogador");

            Query pesq = firebase.orderByChild("email").equalTo(autenticacao.getCurrentUser().getEmail());

            pesq.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    jogadorRecuperado = dataSnapshot.getValue(Jogador.class);
                    Log.i("Dados", jogadorRecuperado.getNome());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        public void passarParaProximoEditText (EditText editText1,final EditText editText2){
            editText1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() == 2) {
                        editText2.requestFocus();
                    }
                }
            });
        }

    }


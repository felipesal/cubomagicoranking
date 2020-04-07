package com.example.cubomagicoranking2.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;

    public static String NOME_DB = "DB_JOGADORES";

    public static String TABELA_JOGADORES = "jogadores";


    public DbHelper(Context context) {
        super(context, NOME_DB , null , VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS " + TABELA_JOGADORES
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + " nome TEXT NOT NULL, "+
                " email TEXT NOT NULL, " +
                " senha TEXT NOT NULL, "+
                " jogo_simples INTEGER, " +
                " jogo_melhor INTEGER, " +
                " jogo_media INTEGER ); ";


        try{
            db.execSQL(sql);
            Log.i("INFO_DB", "Sucesso ao criar tabela");
        }catch (Exception e){
            Log.i("INFO_DB", "Erro ao criar a tabela" + e.getMessage() );
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

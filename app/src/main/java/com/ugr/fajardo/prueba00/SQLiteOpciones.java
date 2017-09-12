package com.ugr.fajardo.prueba00;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class SQLiteOpciones extends SQLiteOpenHelper {

    String tabla =  "CREATE TABLE opciones (sensibilidad INTEGER,r INTEGER,g INTEGER,b INTEGER, vibrar boolean)";

    public SQLiteOpciones(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(tabla);
        sqLiteDatabase.execSQL("INSERT INTO opciones VALUES (50,0,0,255,1)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXITS opciones");
        sqLiteDatabase.execSQL(tabla);
        sqLiteDatabase.execSQL("INSERT INTO opciones VALUES (50,0,0,255,1)");
    }
}
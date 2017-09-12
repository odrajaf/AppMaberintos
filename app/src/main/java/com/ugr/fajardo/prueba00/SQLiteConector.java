package com.ugr.fajardo.prueba00;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLiteConector extends SQLiteOpenHelper{

    String tabla =  "CREATE TABLE maberintos (nivel INTEGER)";
    public SQLiteConector(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(tabla);
        sqLiteDatabase.execSQL("INSERT INTO maberintos VALUES (1)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXITS maberintos");
        sqLiteDatabase.execSQL(tabla);
        sqLiteDatabase.execSQL("INSERT INTO maberintos VALUES (1)");
    }
}

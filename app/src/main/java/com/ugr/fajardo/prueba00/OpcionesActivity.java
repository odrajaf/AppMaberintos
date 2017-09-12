package com.ugr.fajardo.prueba00;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class OpcionesActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    SeekBar sensibilidad;

    SeekBar seekR;
    SeekBar seekG;
    SeekBar seekB;
    Button colorBola;
    Button buttonGuardar;
    SQLiteOpciones bdConector;
    SQLiteDatabase bdOpciones;
    CheckBox checbVibrar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        bdConector = new SQLiteOpciones(this,"opciones",null,1);
        bdOpciones = bdConector.getReadableDatabase();

        String query = "SELECT * FROM  opciones";
        Cursor cursor = bdOpciones.rawQuery(query, null);
        cursor.moveToFirst();
        int valorSensibilidad = cursor.getInt(cursor.getColumnIndex("sensibilidad"));
        int valorR = cursor.getInt(cursor.getColumnIndex("r"));
        int valorG = cursor.getInt(cursor.getColumnIndex("g"));
        int valorB = cursor.getInt(cursor.getColumnIndex("b"));
        int vibrar = cursor.getInt(cursor.getColumnIndex("vibrar"));

        colorBola = (Button) findViewById(R.id.buttonColor) ;
        sensibilidad = ((SeekBar)findViewById(R.id.seekBarSensibi));
        buttonGuardar = (Button) findViewById(R.id.buttonGuardarConf) ;
        checbVibrar =  ((CheckBox)findViewById(R.id.checkBoxVibrar));

        seekR = ((SeekBar)findViewById(R.id.seekBarColorR));
        seekG = ((SeekBar)findViewById(R.id.seekBarColorG));
        seekB = ((SeekBar)findViewById(R.id.seekBarColorB));

        sensibilidad.setOnSeekBarChangeListener(this);
        seekR.setOnSeekBarChangeListener(this);
        seekG.setOnSeekBarChangeListener(this);
        seekB.setOnSeekBarChangeListener(this);

        if(vibrar == 0){
            checbVibrar.setChecked(false);
        }

        sensibilidad.setProgress(valorSensibilidad);
        seekR.setProgress(valorR);
        seekG.setProgress(valorG);
        seekB.setProgress(valorB);
        colorBola.setBackgroundColor(Color.rgb(seekR.getProgress(),seekG.getProgress(),seekB.getProgress()));

        buttonGuardar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                int valorVibrar = 0;

                if(checbVibrar.isChecked()){
                    valorVibrar = 1;
                }
                bdOpciones = bdConector.getWritableDatabase();
                bdOpciones.execSQL("UPDATE opciones SET sensibilidad = " + sensibilidad.getProgress() +
                        ",  r = "+seekR.getProgress()+
                        ",  g = "+seekG.getProgress()+
                        ",  b = "+seekB.getProgress()+
                        ",  vibrar = "+valorVibrar+
                        " WHERE sensibilidad != -1");
                bdOpciones.close();

                Toast mensajeGuardado =
                        Toast.makeText(getApplicationContext(),
                                "Cambios Guardados", Toast.LENGTH_SHORT);

                mensajeGuardado.show();
            }
        });

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        colorBola.setBackgroundColor(Color.rgb(seekR.getProgress(),seekG.getProgress(),seekB.getProgress()));
    }
}

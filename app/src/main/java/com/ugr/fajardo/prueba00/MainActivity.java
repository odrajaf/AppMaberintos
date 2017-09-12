package com.ugr.fajardo.prueba00;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity  {


    private Button nuevaPartidaButton;
    private Button continuarButton;
    private Button aboutButton;
    private Button opcionButton;
    SQLiteConector bdConector;
    SQLiteDatabase bdNiveles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        bdConector = new SQLiteConector(this,"maberintos",null,1);
        bdNiveles = bdConector.getReadableDatabase();


        nuevaPartidaButton = (Button) findViewById(R.id.buttonNew);
        nuevaPartidaButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent nuevoJuego = new Intent(MainActivity.this, MainActivityNuevo.class);
                nuevoJuego.putExtra("nivel", 1);
                startActivity(nuevoJuego);
            }
        });

        continuarButton = (Button) findViewById(R.id.buttonCon);
        continuarButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String query = "SELECT * FROM  maberintos";
                Cursor cursor = bdNiveles.rawQuery(query, null);
                cursor.moveToFirst();
                int nivel =cursor.getInt(cursor.getColumnIndex("nivel"));
                if(nivel == 1){
                    Intent nuevoJuego = new Intent(MainActivity.this, MainActivityNuevo.class);
                    nuevoJuego.putExtra("nivel", 1);
                    startActivity(nuevoJuego);
                }else if(nivel == 2){
                    Intent continuar = new Intent(MainActivity.this, MainActivityNuevo.class);
                    continuar.putExtra("nivel", 2);
                    startActivity(continuar);
                }
                cursor.close();

            }
        });

        aboutButton = (Button) findViewById(R.id.buttonAbout);
        aboutButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent acercade = new Intent(MainActivity.this, AcercaDeActivity.class);;
                startActivity(acercade);
            }
        });

        opcionButton = (Button) findViewById(R.id.buttonOp);
        opcionButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent opciones = new Intent(MainActivity.this, OpcionesActivity.class);;
                startActivity(opciones);
            }
        });


         /*List<Sensor> listaSensor = sensorManeger.getSensorList(Sensor.TYPE_ALL);

       for(int i  = 0;i< listaSensor.size(); i++){
            texto.append("\n"+ listaSensor.get(i).getName() +  "\n"+ listaSensor.get(i).getVendor() + "\n"+ listaSensor.get(i).getVersion());
        }*/
    }



}

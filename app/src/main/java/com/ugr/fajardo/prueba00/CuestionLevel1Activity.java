package com.ugr.fajardo.prueba00;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.LogRecord;

public class CuestionLevel1Activity extends AppCompatActivity {

    private Button botonA;
    private Button botonB;
    private Button botonC;

    private int contador = 0;
    private int respuesta = 0;
    SQLiteConector bdConector;
    SQLiteDatabase bdNiveles;
    ImageView imagen ;
    int nivel = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuestion_level1);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
         bdConector = new SQLiteConector(this,"maberintos",null,1);
         bdNiveles = bdConector.getWritableDatabase();

        botonA = (Button) findViewById(R.id.buttonA);
        botonB = (Button) findViewById(R.id.buttonB);
        botonC = (Button) findViewById(R.id.buttonC);
        imagen = (ImageView) findViewById(R.id.imageViewPregunta);

        nivel = (int) getIntent().getExtras().getSerializable("nivel");

        if(nivel == 2) {

            imagen.setImageBitmap(BitmapFactory.decodeResource(getResources(),  R.mipmap.damadebazah));
            botonA.setText(getString(R.string.respuesta1Nlv2));
            botonB.setText(getString(R.string.respuesta2Nlv2));
            botonC.setText(getString(R.string.respuesta3Nlv2));
        }




        if(respuesta == 1){
            botonA.setBackgroundColor(Color.RED);
            botonB.setBackgroundColor(Color.GREEN);
        }else  if(respuesta == 2){
            botonB.setBackgroundColor(Color.GREEN);
        }else  if(respuesta == 3){
            botonB.setBackgroundColor(Color.GREEN);
            botonC.setBackgroundColor(Color.RED);
        }



        botonA.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                contador++;
                if(contador == 1) {
                    respuesta = 1;
                    botonA.setBackgroundColor(Color.RED);
                    botonB.setBackgroundColor(Color.GREEN);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent nuevoJuego = new Intent(CuestionLevel1Activity.this, MainActivityNuevo.class);
                            nuevoJuego.putExtra("nivel", nivel);
                            startActivity(nuevoJuego);
                            finish();
                        }
                    }, 2000);

                }
            }
        });

        botonB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                contador++;
                if(contador == 1) {
                    respuesta = 2;
                    //lo dejamos en nivel 2 ya que solo habrá dos niveles para probarlo
                    //lo correcto sería incrementarlo para el siguiente nivel
                    //nivel = nivel + 1;
                    botonB.setBackgroundColor(Color.GREEN);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(nivel == 1) {
                                nivel =  nivel +1;
                                Intent nuevoNivel = new Intent(CuestionLevel1Activity.this, MainActivityNuevo.class);
                                nuevoNivel.putExtra("nivel", nivel);
                                startActivity(nuevoNivel);
                            }else{
                                Intent nuevoNivel = new Intent(CuestionLevel1Activity.this, FinalActivity.class);
                                startActivity(nuevoNivel);
                            }
                            finish();
                            bdNiveles.execSQL("UPDATE maberintos SET nivel = " + nivel + " WHERE nivel != 0");
                            bdNiveles.close();
                        }
                    }, 2000);

                }
            }
        });

        botonC.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                contador++;
                if(contador == 1) {
                    respuesta = 3;
                    botonB.setBackgroundColor(Color.GREEN);
                    botonC.setBackgroundColor(Color.RED);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent nuevoJuego = new Intent(CuestionLevel1Activity.this, MainActivityNuevo.class);
                            nuevoJuego.putExtra("nivel", nivel);
                            startActivity(nuevoJuego);
                            finish();
                        }
                    }, 2000);
                }
            }
        });
    }



    @Override
    public void onBackPressed (){

    }


}

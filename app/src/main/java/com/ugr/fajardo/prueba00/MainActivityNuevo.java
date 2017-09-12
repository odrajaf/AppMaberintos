package com.ugr.fajardo.prueba00;

import android.content.Context;
import android.content.DialogInterface;
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
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import java.util.Arrays;
import java.util.List;

public class MainActivityNuevo extends AppCompatActivity  implements SensorEventListener {

    String textoPrueba = "";
    SensorManager sensorManeger;
    private Vibrator v ;
    private Sensor acelerometro;
    private int aumentoX = 0;
    private int aumentoY = 0;
    CanvasView papel;

    private List<Linea> laberinto;
    private float contador = 60;
    private float contadorSeguirJuego = 4;
    private boolean juegoPerdido = false;
    Bitmap pregunta;
    Bitmap imagenPregunta;
    Bitmap corazones;
    Bitmap imagenColision;
    Bitmap imagenUno;
    Bitmap imagenDos;
    Bitmap imagenTres;
    Bitmap imagenPreparate;
    Bitmap gameover;
    int anchuraPantalla = 0;
    int alturaPantalla = 0;
    boolean colision = false;
    boolean pantallaGanada = false;
    boolean inicioJuego = true;
    //Meta
    int inicioMeta = 400;
    int inicioYMeta = 800;
    int vida = 3;
    int invocamosPregunta = 0;
    int nivel = 0;

    //para cargar las opciones de configuracion
    SQLiteOpciones bdConector;
    SQLiteDatabase bdOpciones;
    //valores que se cargaran de la configuracion via sqlite
    int valorSensibilidad;
    int valorR;
    int valorG;
    int valorB;
    int vibrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        bdConector = new SQLiteOpciones(this,"opciones",null,1);
        bdOpciones = bdConector.getReadableDatabase();

        String query = "SELECT * FROM  opciones";
        Cursor cursor = bdOpciones.rawQuery(query, null);
        cursor.moveToFirst();
         valorSensibilidad = cursor.getInt(cursor.getColumnIndex("sensibilidad"));
        valorSensibilidad = valorSensibilidad/100;
         valorR = cursor.getInt(cursor.getColumnIndex("r"));
         valorG = cursor.getInt(cursor.getColumnIndex("g"));
         valorB = cursor.getInt(cursor.getColumnIndex("b"));
         vibrar = cursor.getInt(cursor.getColumnIndex("vibrar"));

        nivel = (int) getIntent().getExtras().getSerializable("nivel");
        papel = new CanvasView(this);
        setContentView(papel);

        sensorManeger = (SensorManager) getSystemService(SENSOR_SERVICE);
        v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        acelerometro  = sensorManeger.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        pregunta = BitmapFactory.decodeResource(getResources(),  R.mipmap.pregunta);

        imagenColision = BitmapFactory.decodeResource(getResources(),  R.mipmap.colisiont);
        imagenUno = BitmapFactory.decodeResource(getResources(),  R.mipmap.uno);
        imagenDos = BitmapFactory.decodeResource(getResources(),  R.mipmap.dos);
        imagenTres= BitmapFactory.decodeResource(getResources(),  R.mipmap.tres);
        imagenPreparate = BitmapFactory.decodeResource(getResources(),  R.mipmap.preparatet);
        corazones = BitmapFactory.decodeResource(getResources(),  R.mipmap.corazon);
        gameover = BitmapFactory.decodeResource(getResources(),  R.mipmap.gameover);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        anchuraPantalla  = metrics.widthPixels;
        alturaPantalla = metrics.heightPixels;

        iniciarlizarNivel ();

    }

    public void iniciarlizarNivel (){
        if (nivel == 1) {
            inicioMeta = 400;
            inicioYMeta = 800;
            imagenPregunta = BitmapFactory.decodeResource(getResources(),  R.mipmap.gonzalesf);
            laberinto = Arrays.asList(new Linea(0, 550, 600, 550),
                    new Linea(600, 550, 600, 1100),
                    new Linea(600, 1100, 0, 1100),
                    new Linea(0, 700, 500, 700),
                    new Linea(500, 700, 500, 900),
                    new Linea(500, 900, 400, 900),
                    new Linea(400, 900, 400, 800),
                    new Linea(400, 800, 100, 800),
                    new Linea(100, 900, 300, 900),
                    new Linea(300, 900, 300, 1000),
                    new Linea(300, 1000, 500, 1000),
                    new Linea(inicioMeta, inicioYMeta, inicioMeta + 100, inicioYMeta));
        }else if(nivel ==2){
            inicioMeta = 100;
            inicioYMeta = 800;
            imagenPregunta = BitmapFactory.decodeResource(getResources(),  R.mipmap.damadebazav);
            laberinto = Arrays.asList(new Linea(0, 550, 600, 550),
                    new Linea(600, 550, 600, 1100),
                    new Linea(600, 1100, 0, 1100),
                    new Linea(0, 700, 0, 1100),
                    new Linea(0, 700, 500, 700),
                    new Linea(500, 700, 500, 1000),
                    //new Linea(500, 700, 500, 900),
                    new Linea(400, 900, 400, 800),
                    new Linea(400, 800, 100, 800),
                    new Linea(100, 900, 100, 800),
                    new Linea(100, 900, 300, 900),
                    new Linea(300, 900, 300, 1000),
                    new Linea(300, 1000, 500, 1000),
                    new Linea(inicioMeta+100, inicioYMeta, inicioMeta +100, inicioYMeta +100));

        }
    }

    private class CanvasView extends View {
        public CanvasView(Context context){
            super(context);
        }
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint paint = new Paint();


            if (!juegoPerdido) {
                canvas.drawBitmap(pregunta, 0, 0, null);
                canvas.drawBitmap(imagenPregunta, 150, 100, null);

                if (vida >= 3) {
                    canvas.drawBitmap(corazones, 650, 1005, null);
                }
                if (vida >= 2) {
                    canvas.drawBitmap(corazones, 650, 945, null);
                }
                if (vida >= 1) {
                    canvas.drawBitmap(corazones, 650, 885, null);

                }
                if(vida <=0){
                    juegoPerdido = true;
                    contador = 4;
                }

                //Cuadro
                paint.setColor(Color.rgb(valorR,valorG,valorB));
                canvas.drawRect(0 + aumentoX, 600 + aumentoY, 50 + aumentoX, 650 + aumentoY, paint);
                paint.setColor(Color.BLUE);

                //Meta
                canvas.drawRect(inicioMeta, inicioYMeta, inicioMeta + 25, inicioYMeta + 25, paint);
                canvas.drawRect(inicioMeta + 25, inicioYMeta + 25, inicioMeta + 50, inicioYMeta + 50, paint);
                canvas.drawRect(inicioMeta + 50, inicioYMeta, inicioMeta + 75, inicioYMeta + 25, paint);
                canvas.drawRect(inicioMeta, inicioYMeta + 50, inicioMeta + 25, inicioYMeta + 75, paint);
                canvas.drawRect(inicioMeta + 50, inicioYMeta + 50, inicioMeta + 75, inicioYMeta + 75, paint);
                canvas.drawRect(inicioMeta + 75, inicioYMeta + 25, inicioMeta + 100, inicioYMeta + 50, paint);
                canvas.drawRect(inicioMeta + 75, inicioYMeta + 75, inicioMeta + 100, inicioYMeta + 100, paint);
                canvas.drawRect(inicioMeta + 25, inicioYMeta + 75, inicioMeta + 50, inicioYMeta + 100, paint);

                //Linea de Meta
                canvas.drawLine(laberinto.get(laberinto.size() - 1).getOrigenX(),
                        laberinto.get(laberinto.size() - 1).getOrigenY(),
                        laberinto.get(laberinto.size() - 1).getDestinoX(),
                        laberinto.get(laberinto.size() - 1).getDestinoY(),
                        paint);


                paint.setTextSize(36);
                canvas.rotate((float) 90);
                canvas.drawText("Nivel " + nivel, 50, -650, paint);
                canvas.drawText("Tiempo Restante: " + (int) contador, 350, -650, paint);
                canvas.rotate((float) -90);


                if (!colision) {
                    if(invocamosPregunta==0) {
                        paint.setColor(Color.RED);
                    }else{
                        paint.setColor(Color.GREEN);
                    }
                } else {
                    paint.setColor(Color.BLACK);
                }

                paint.setStrokeWidth(10);

                for (int i = 0; i < laberinto.size() - 1; i++) {
                    if (!pantallaGanada){
                        pantallaGanada = laberinto.get(laberinto.size() - 1).colision(aumentoX + 25, 625 + aumentoY);

                    } else {
                        invocamosPregunta++;
                        if(invocamosPregunta == 1) {
                            paint.setColor(Color.GREEN);
                            Intent nuevoJuego = new Intent(MainActivityNuevo.this, CuestionLevel1Activity.class);
                            nuevoJuego.putExtra("nivel", nivel);
                            startActivity(nuevoJuego);
                            finish();
                        }

                    }
                    canvas.drawLine(laberinto.get(i).getOrigenX(),
                            laberinto.get(i).getOrigenY(),
                            laberinto.get(i).getDestinoX(),
                            laberinto.get(i).getDestinoY(),
                            paint);

                    if (!colision) {
                        colision = laberinto.get(i).colision(aumentoX + 25, 625 + aumentoY);


                    } else {
                        if (contadorSeguirJuego > 5.9) {
                            if(vibrar!=0) {
                                v.vibrate(200);
                            }
                        }
                        if (contadorSeguirJuego > 4) {

                            canvas.drawBitmap(imagenColision, 300, 25, null);
                        } else if (contadorSeguirJuego > 3) {
                            canvas.drawBitmap(imagenTres, 300, 500, null);
                        } else if (contadorSeguirJuego > 2) {
                            canvas.drawBitmap(imagenDos, 300, 500, null);
                        } else if (contadorSeguirJuego > 1) {
                            canvas.drawBitmap(imagenUno, 300, 500, null);
                        } else if (contadorSeguirJuego > 0) {
                            canvas.drawBitmap(imagenPreparate, 300, 50, null);
                        } else {
                            colision = false;
                            contadorSeguirJuego = 6;
                        }
                    }
                }

                //contador de inicio
                if (inicioJuego) {
                    if (contadorSeguirJuego > 3) {
                        canvas.drawBitmap(imagenTres, 300, 500, null);
                    } else if (contadorSeguirJuego > 2) {
                        canvas.drawBitmap(imagenDos, 300, 500, null);
                    } else if (contadorSeguirJuego > 1) {
                        canvas.drawBitmap(imagenUno, 300, 500, null);
                    } else if (contadorSeguirJuego > 0) {
                        canvas.drawBitmap(imagenPreparate, 300, 50, null);
                    } else {
                        contadorSeguirJuego = 6;
                        inicioJuego = false;
                    }
                }

            }else{
                canvas.drawRGB(0,0,0);
                canvas.drawBitmap(gameover, 300, 250, null);
                if(contador < 1){
                    juegoPerdido = false;
                    inicioJuego  = true;
                    vida = 3;
                    nivel = 1;
                    contador = 60;
                    contadorSeguirJuego = 4;
                    iniciarlizarNivel ();
                }
            }
        }
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x,y,z;
        x = sensorEvent.values[0];
        y = sensorEvent.values[1];
        z = sensorEvent.values[2];


        //textoPrueba = "Valor X: "+x+"\n Valor Y: "+y+"\n Valor Z: "+z;
        if(!colision) {
            if(!inicioJuego) {
                contador = contador - 0.01f;
            }
            if(contador< 0.01){
                if(vida>0) {
                    vida--;
                }
                contador = 60;
            }
            if (x > 1.9 - valorSensibilidad) {
                if (aumentoX > 0) {
                    aumentoX = aumentoX - 3;
                }
            } else if (x < -1.9 +valorSensibilidad) {
                if (aumentoX < anchuraPantalla - 50) {
                    aumentoX = aumentoX + 3;
                }
            }

            if (y > 1.9 -valorSensibilidad) {
                if (aumentoY < alturaPantalla - 700) {
                    aumentoY = aumentoY + 3;
                }
            } else if (y < -1.9 +valorSensibilidad) {
                if (aumentoY > -85) {//vertical izquierda
                    aumentoY = aumentoY - 3;
                }
            }
        }else{
            aumentoY = 0;
            aumentoX = 0;
            contadorSeguirJuego = contadorSeguirJuego-0.01f;

        }
        if(inicioJuego){
            aumentoY = 0;
            aumentoX = 0;
            contadorSeguirJuego = contadorSeguirJuego-0.01f;
        }

        papel.invalidate();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManeger.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManeger.unregisterListener(this);
    }

}

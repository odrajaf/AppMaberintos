package com.ugr.fajardo.prueba00;

/**
 * Created by fajardo on 29/08/17.
 */


public class Linea {
    private int origenX;
    private int origenY;
    private int destinoX;
    private int destinoY;

    public Linea(int origenX, int origenY, int destinoX, int destinoY) {
        this.origenX = origenX;
        this.origenY = origenY;
        this.destinoX = destinoX;
        this.destinoY = destinoY;
    }

    public int getOrigenX() {
        return origenX;
    }

    public void setOrigenX(int origenX) {
        this.origenX = origenX;
    }

    public int getOrigenY() {
        return origenY;
    }

    public void setOrigenY(int origenY) {
        this.origenY = origenY;
    }

    public int getDestinoX() {
        return destinoX;
    }

    public void setDestinoX(int destinoX) {
        this.destinoX = destinoX;
    }

    public int getDestinoY() {
        return destinoY;
    }

    public void setDestinoY(int destinoY) {
        this.destinoY = destinoY;
    }

    public boolean colision(int x,int y){

        if(origenY == destinoY && ((y-25 <= origenY && y + 25 >= origenY) || y == origenY)){
            if((origenX <= x && x <=destinoX) || (destinoX <= x && x <=origenX)){
                return true;
            }
        }else if(origenX == destinoX && ((x-25 <= origenX && x + 25 >= origenX) || x == origenX)){
            if((origenY <= y && y <=destinoY) || (destinoY <= y && y <=origenY)) {
                return true;
            }
        }else{
            return false;
        }
        return false;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

import java.util.Random;

/**
 *
 * @author gusta
 */
public class Celdas {

    Monstruos monstruo;
    Jugador jugador;
    Objetos objeto;
    int pared;
    boolean fog;
    int x, y;
    char simbolo;

    public Celdas(int pared, int y, int x, boolean fog) {
        this.pared = pared;
        this.jugador = null;
        this.x = x;
        this.y = y;
        this.fog = fog;

    }

    public void setMonstruo(Monstruos monstruo) {
        this.monstruo = monstruo;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public void setObjeto(Objetos objeto) {
        this.objeto = objeto;
    }

    public void setPared(int pared) {
        this.pared = pared;
    }

    public void setFog(boolean fog) {
        this.fog = fog;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Monstruos getMonstruo() {
        return monstruo;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public Objetos getObjeto() {
        return objeto;
    }

    public int getPared() {
        return pared;
    }

    public boolean isFog() {
        return fog;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getSimbolo() {
        return simbolo;
    }

}

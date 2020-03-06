/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

import java.util.Arrays;
import java.util.Random;
import static proyecto.Proyecto.jugador;
import java.util.Scanner;

/**
 *
 * @author gusta
 */
public class Jugador extends Personajes {

    int vidas, x, y, fuerzaPociones, contMonstruos;
    public Objetos[] bolso;

    public Jugador() {
        vidas = 5;
        fuerza = 10;
        fuerzaPociones = 0;
        contMonstruos = 0;
    }

    public void Move(Celdas[][] laberinto, Celdas celdaFinal) {

        if (celdaFinal.getPared() == 0) {

            this.setX(celdaFinal.getX());
            this.setY(celdaFinal.getY());

            if (celdaFinal.getObjeto() != null && celdaFinal.getMonstruo() != null) {
                System.out.println("Te has conseguido con un Mountruo de fuerza " + celdaFinal.getMonstruo().getFuerza());
                if (this.Pelear(laberinto, celdaFinal)) {

                    Random rand = new Random();
                    int chancePocion = rand.nextInt(10);
                    if (chancePocion == 0) {
                        for (int i = 0; i < this.bolso.length; i++) {
                            if (bolso[i] == null) {
                                bolso[i] = new Pociones();
                                Pociones pocion = (Pociones) bolso[i];
                                System.out.println("El monstruo ha soltado una pocion " + pocion.getTipo() + " de calidad " + pocion.getCalidad());
                                break;
                            }
                        }
                    }
                }
                this.Pickup(celdaFinal);

            } else if (celdaFinal.getMonstruo() != null) {
                System.out.println("Te has conseguido con un Mountruo de fuerza " + celdaFinal.getMonstruo().getFuerza());
                if (this.Pelear(laberinto, celdaFinal)) {

                    Random rand = new Random();
                    int chancePocion = rand.nextInt(10);
                    if (chancePocion == 0) {
                        for (int i = 0; i < this.bolso.length; i++) {
                            if (bolso[i] == null) {
                                bolso[i] = new Pociones();
                                Pociones pocion = (Pociones) bolso[i];
                                System.out.println("El monstruo ha soltado una pocion " + pocion.getTipo() + " de calidad " + pocion.getCalidad());
                                break;
                            }
                        }
                    }
                }
            } else if (celdaFinal.getObjeto() != null) {
                this.Pickup(celdaFinal);
            }
        } else if (celdaFinal.getPared() == 1) {
            System.out.println("Hay una pared");
        }

    }

    private boolean Pelear(Celdas[][] laberinto, Celdas celda) {

        Random rand = new Random();

        if (celda.getMonstruo() instanceof Magos) {
            int chanceTele = rand.nextInt(4);

            if (chanceTele == 1) {
                Magos mago = (Magos) celda.getMonstruo();
                mago.Teleport(laberinto, jugador);
            }
            return false;
        }

        if (this.fuerza + this.fuerzaPociones > celda.getMonstruo().fuerza) {
            System.out.println("Ganaste la pelea");
            System.out.println("Tu fuerza ha aumentado 1!");
            celda.setMonstruo(null);
            this.setFuerza(this.fuerza + 1);
            return true;
        } else {

            int chanceGanar = rand.nextInt(10);

            if (chanceGanar < 2) {
                System.out.println("Ganaste la pelea Heroicamente!");
                System.out.println("Tu fuerza ha aumentado 2");
                celda.setMonstruo(null);
                this.setFuerza(this.fuerza + 2);
                return true;
            } else {
                System.out.println("Perdiste la pelea");
                laberinto[this.getY()][this.getX()].setJugador(null);
                Proyecto.SetJugador(laberinto, this);
                this.setVidas(this.vidas - 1);
                System.out.println("Te quedan " + this.getVidas() + " vidas");
                return false;
            }
        }

    }

    private void Pickup(Celdas celdaFinal) {

        if (celdaFinal.getObjeto() instanceof Pociones) {
            Pociones pocion = (Pociones) celdaFinal.getObjeto();
            System.out.println("Has conseguido una Pocion " + pocion.getTipo() + " de calidad " + pocion.getCalidad());
        } else if (celdaFinal.getObjeto() instanceof Pico) {
            System.out.println("Has conseguido un pico");
        }

        for (int i = 0; i < this.bolso.length; i++) {

            if (bolso[i] == null) {

                bolso[i] = celdaFinal.getObjeto();
                celdaFinal.setObjeto(null);
                break;
            } else if (i == this.bolso.length - 1 && this.bolso[i] != null) {
                System.out.println("Tu bolso esta lleno");
                System.out.println("Por cual Objeto quieres cambiar?");

                Scanner sc = new Scanner(System.in);

                for (int j = 0; j <= jugador.getBolso().length; j++) {
                    if (j == jugador.getBolso().length) {
                        System.out.println(j + ") No cambiar objeto");
                    } else if ((jugador.bolso[j] instanceof Pociones)) {

                        Pociones pocion = (Pociones) jugador.bolso[j];
                        System.out.println(j + ") Pocion " + pocion.getTipo() + " de calidad " + pocion.getCalidad());
                    } else if ((jugador.bolso[j] instanceof Pico)) {

                        System.out.println(j + ") Pico");
                    }
                }
                int cambiarObjeto = 10;
                String input = sc.next();
                try {
                    cambiarObjeto = Integer.parseInt(input);
                } catch (NumberFormatException ex) {
                    System.out.println("");
                }

                while (cambiarObjeto > jugador.getBolso().length) {
                    System.out.println("El numero que ha ingresado no es  valido ingrese uno del 0 al 10");
                    cambiarObjeto = sc.nextInt();
                }
                if (cambiarObjeto == 10) {
                    break;
                } else {
                    this.bolso[cambiarObjeto] = celdaFinal.getObjeto();
                    celdaFinal.setObjeto(null);
                    break;
                }
            }

        }
    }

    public void UsarObjeto(int objeto, Celdas[][] laberinto) {

        if (objeto < 0 || objeto > 9) {
        } else if (this.bolso[objeto] instanceof Pociones) {
            Pociones pocion = (Pociones) this.bolso[objeto];
            pocion.Usar(this);
            System.out.println("Tienes un bono de fuerza por Pociones de: " + this.getFuerzaPociones());
            this.bolso[objeto] = null;
            this.OrdenarBolso(bolso);
        } else if (this.bolso[objeto] instanceof Mapa) {
            Mapa mapa = (Mapa) this.bolso[objeto];
            this.bolso[objeto] = null;
            this.OrdenarBolso(bolso);
            mapa.Usar(laberinto);
        } else if (this.bolso[objeto] instanceof Pico) {

            Scanner sc = new Scanner(System.in);
            System.out.println("Que pared desea destruir?");
            System.out.println("w/ la de arriba");
            System.out.println("s/ la de abajo");
            System.out.println("d/ la de la derecha");
            System.out.println("a/ la de la izquierda");
            System.out.println("cualquier otra tecla para cancelar");

            String input = sc.next();

            char opc = input.charAt(0);

            Pico pico = (Pico) this.bolso[objeto];

            switch (opc) {
                case 'w':

                    pico.Usar(laberinto[this.getY() - 1][this.getX()], laberinto.length);
                    this.bolso[objeto] = null;
                    this.OrdenarBolso(bolso);
                    break;

                case 's':

                    pico.Usar(laberinto[this.getY() + 1][this.getX()], laberinto.length);
                    this.bolso[objeto] = null;
                    this.OrdenarBolso(bolso);

                    break;

                case 'd':

                    pico.Usar(laberinto[this.getY()][this.getX() + 1], laberinto.length);
                    this.bolso[objeto] = null;
                    this.OrdenarBolso(bolso);

                    break;

                case 'a':

                    pico.Usar(laberinto[this.getY()][this.getX() - 1], laberinto.length);
                    this.bolso[objeto] = null;
                    this.OrdenarBolso(bolso);

                    break;

            }
        }

    }

    private void OrdenarBolso(Objetos[] bolso) {

        for (int i = 0; i < bolso.length - 1; i++) {

            if (bolso[i] == null) {
                bolso[i] = bolso[i + 1];
                bolso[i + 1] = null;
            }
        }

    }

    public int getVidas() {
        return vidas;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Objetos[] getBolso() {
        return bolso;
    }

    public int getFuerza() {
        return fuerza;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setBolso(Objetos[] bolso) {
        this.bolso = bolso;
    }

    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    public int getFuerzaPociones() {
        return fuerzaPociones;
    }

    public void setFuerzaPociones(int fuerzaPociones) {
        this.fuerzaPociones = fuerzaPociones;
    }

}

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
public class Pociones extends Objetos {

    int calidad;
    String tipo;

    public int getCalidad() {
        return calidad;
    }

    public String getTipo() {
        return tipo;
    }

    public Pociones() {

        Random rand = new Random();

        int tipo = rand.nextInt(2);

        if (tipo == 1) {
            this.tipo = "multiplicativa";
        } else {
            this.tipo = "aditiva";
        }

        calidad = rand.nextInt(5);
    }

    public void Usar(Jugador jugador) {

        if ("multiplicativa".equals(this.tipo)) {

            switch (calidad) {

                case 0:
                    jugador.setFuerzaPociones(jugador.getFuerzaPociones() + ((jugador.getFuerza() * 10) / 100));
                    break;
                case 1:
                    jugador.setFuerzaPociones(jugador.getFuerzaPociones() + ((jugador.getFuerza() * 20) / 100));
                    break;
                case 2:
                    jugador.setFuerzaPociones(jugador.getFuerzaPociones() + ((jugador.getFuerza() * 25) / 100));
                    break;
                case 3:
                    jugador.setFuerzaPociones(jugador.getFuerzaPociones() + ((jugador.getFuerza() * 50) / 100));
                    break;
                case 4:
                    jugador.setFuerzaPociones(jugador.getFuerzaPociones() + jugador.getFuerza());
                    break;

            }
        } else if ("aditiva".equals(this.tipo)) {

            switch (calidad) {

                case 0:
                    jugador.setFuerzaPociones(jugador.getFuerzaPociones() + 1);
                    break;
                case 1:
                    jugador.setFuerzaPociones(jugador.getFuerzaPociones() + 2);
                    break;
                case 2:
                    jugador.setFuerzaPociones(jugador.getFuerzaPociones() + 5);
                    break;
                case 3:
                    jugador.setFuerzaPociones(jugador.getFuerzaPociones() + 10);
                    break;
                case 4:
                    jugador.setFuerzaPociones(jugador.getFuerzaPociones() + 20);
                    break;

            }
        }

    }

}

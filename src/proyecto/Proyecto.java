/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author gusta
 */
public class Proyecto {

    public static Jugador jugador;
    public static Celdas[][] laberinto;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("*************************************");
        System.out.println("********BIENVENIDO AL JUEGO**********");
        System.out.println("**********IMPOSIBLE MAZE*************");
        System.out.println("*************GOOD LUCK***************");
        System.out.println("*************************************");

        System.out.println("Ingrese un nombre a su jugador: ");
        String nombre = sc.next();

        jugador = new Jugador();
        jugador.setBolso(BolsoInicial());

        int tiempoInicial = (int) TimeUnit.NANOSECONDS.toSeconds(System.nanoTime());
        int nivel = 1;

        while (nivel <= 30 && jugador.getVidas() > 0) {

            laberinto = CreateMaze(nivel);

            SetVainas(laberinto, nivel);
            SetJugador(laberinto, jugador);
            CrearMapa(nivel, jugador);
            ClearFog(jugador, laberinto);

            while (jugador.getX() < laberinto.length - 1 && jugador.getVidas() > 0) {

                PrintLaberinto(laberinto);

                Proyecto.Turno(nivel);

            }
            jugador.setFuerzaPociones(0);
            if (jugador.getVidas() > 0) {
                nivel++;
            }
        }

        int tiempoFinal = (int) TimeUnit.NANOSECONDS.toSeconds(System.nanoTime());
        int tiempoPromedio = (tiempoFinal - tiempoInicial) / nivel;

        System.out.println("Fin del Juego!");
        System.out.println("Lograste resolver " + (nivel - 1) + " laberintos");
        System.out.println("Tu tiempo total fue de: " + (tiempoFinal - tiempoInicial) + " segundos");
        System.out.println("Tiempo Promedio por Nivel: " + tiempoPromedio + " segundos");
    }

    private static Celdas[][] CreateMaze(int nivel) { // Creacion de matriz y bordes.

        int size = 0;

        if (nivel >= 1 && nivel <= 10) {
            size = 10;
        } else if (nivel > 10 && nivel <= 20) {
            size = 20;
        } else if (nivel > 20 && nivel <= 30) {
            size = 30;
        }

        Celdas[][] laberinto = new Celdas[size][size];  // Creacion laberinto.

        int entradaX = 0, entradaY = RandomNumber(1, laberinto.length - 2);
        int salidaX = laberinto.length - 1, salidaY = RandomNumber(1, laberinto.length - 2);

        for (int i = 0; i < size; i++) {

            for (int j = 0; j < size; j++) {

                if (i == 0 || i == size - 1 || j == 0 || j == size - 1) // crear celdas.
                {
                    laberinto[i][j] = new Celdas(1, i, j, true);
                } else {
                    laberinto[i][j] = new Celdas(0, i, j, true);
                }
            }
        }
        int minCol = 1, minRow = 1, maxCol = laberinto.length - 2, maxRow = laberinto.length - 2;

        laberinto[entradaY][entradaX].setPared(0);

        laberinto[salidaY][salidaX].setPared(0);

        RandomMaze(laberinto, minRow, minCol, maxRow, maxCol, entradaY, entradaX, salidaY, salidaX);

        return laberinto;
    }

    private static void RandomMaze(Celdas[][] laberinto, int minRow, int minCol, int maxRow, int maxCol, int entradaY, int entradaX, int salidaY, int salidaX) {

        String direccion = null;
        int width = (maxCol - minCol) + 1, height = (maxRow - minRow) + 1;

        if (width >= height || height > width) // division.
        {
            direccion = "vertical";
        }
        //else if(height > width)
        //direccion = "horizontal";

        if (width > 1 && height > 1) {

            if ("vertical".equals(direccion)) {

                int randomCol = RandomNumber(minCol, maxCol);

                while (randomCol == entradaX) {
                    randomCol = RandomNumber(minCol, maxCol);
                }

                for (int i = minRow; i <= maxRow; i++) {

                    laberinto[i][randomCol].setPared(1);
                }

                int randomRow = RandomNumber(minRow, maxRow);

                if (randomCol == minCol) {

                    randomRow = entradaY;
                } else if (randomCol == maxCol) {

                    randomRow = salidaY;
                }

                laberinto[randomRow][randomCol].setPared(0);

                RandomMaze(laberinto, minRow, minCol, maxRow, randomCol - 1, entradaY, entradaX, randomRow, randomCol);
                RandomMaze(laberinto, minRow, randomCol + 1, maxRow, maxCol, randomRow, randomCol, salidaY, salidaX);

            } else if ("horizontal".equals(direccion)) {

                int randomRow = RandomNumber(minRow, maxRow);

                while (randomRow == entradaY || randomRow == salidaY) {
                    randomRow = RandomNumber(minRow, maxRow);
                }

                for (int i = minCol + 1; i <= maxCol - 1; i++) {

                    laberinto[randomRow][i].setPared(1);
                }

                int randomCol = RandomNumber(minCol, maxCol);

                laberinto[randomRow][randomCol].setPared(0);

                RandomMaze(laberinto, randomRow + 1, minCol, maxRow, randomCol, entradaY, entradaX, randomRow, randomCol);
                RandomMaze(laberinto, randomRow + 1, randomCol + 1, maxRow, maxCol, entradaY, entradaX, randomRow, randomCol);

                RandomMaze(laberinto, minRow, minCol, randomRow - 1, randomCol, entradaY, entradaX, randomRow, randomCol);
                RandomMaze(laberinto, minRow, randomCol, randomRow - 1, maxCol, entradaY, entradaX, randomRow, randomCol);

            }
        }

    }

    private static int RandomNumber(int min, int max) {

        int random;
        Random rand = new Random();
        random = rand.nextInt((max - min) + 1) + min;

        return random;
    }

    private static void Turno(int nivel) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Ingrese:                                      Nivel:  " + nivel);
        System.out.println("w/ moverse hacia arriba                       Vidas: " + jugador.getVidas());
        System.out.println("s/ moverse hacia abajo                        Fuerza: " + jugador.getFuerza() + "  Bono de Pociones: " + jugador.getFuerzaPociones());
        System.out.println("d/ moverse hacia la derecha                   Leyenda:              * = Salida");
        System.out.println("a/ moverse hacia la izquierda                 @ = Jugador           $ = Monstruo");
        System.out.println("O/ para usar un objeto                        ! = Objeto            # = Pared");

        String input = sc.next();

        char opc = input.charAt(0);

        switch (opc) {

            case 'w':

                laberinto[jugador.getY()][jugador.getX()].setJugador(null);
                jugador.Move(laberinto, laberinto[jugador.getY() - 1][jugador.getX()]);
                laberinto[jugador.getY()][jugador.getX()].setJugador(jugador);
                Proyecto.ClearFog(jugador, laberinto);

                break;

            case 's':

                laberinto[jugador.getY()][jugador.getX()].setJugador(null);
                jugador.Move(laberinto, laberinto[jugador.getY() + 1][jugador.getX()]);
                laberinto[jugador.getY()][jugador.getX()].setJugador(jugador);
                Proyecto.ClearFog(jugador, laberinto);

                break;

            case 'd':

                laberinto[jugador.getY()][jugador.getX()].setJugador(null);
                jugador.Move(laberinto, laberinto[jugador.getY()][jugador.getX() + 1]);
                laberinto[jugador.getY()][jugador.getX()].setJugador(jugador);
                Proyecto.ClearFog(jugador, laberinto);

                break;

            case 'a':

                laberinto[jugador.getY()][jugador.getX()].setJugador(null);
                if (jugador.getX() >= 1) {
                    jugador.Move(laberinto, laberinto[jugador.getY()][jugador.getX() - 1]);
                } else {
                    System.out.println("Antonio para de intentar explotar mi programa");
                }
                laberinto[jugador.getY()][jugador.getX()].setJugador(jugador);
                Proyecto.ClearFog(jugador, laberinto);
                break;

            case 'o':
                System.out.println("Has seleccionado para usar un Objeto");
                for (int i = 0; i <= jugador.getBolso().length; i++) {
                    if (i == jugador.getBolso().length) {
                        System.out.println(i + ") Cancelar");
                    } else if (jugador.getBolso()[i] == null) {
                        break;
                    } else if ((jugador.bolso[i] instanceof Pociones)) {
                        Pociones pocion = (Pociones) jugador.bolso[i];
                        System.out.println(i + ") Pocion " + pocion.getTipo() + " de calidad " + pocion.getCalidad());
                    } else if ((jugador.bolso[i] instanceof Pico)) {
                        System.out.println(i + ") Pico");
                    } else if (jugador.getBolso()[i] instanceof Mapa) {
                        System.out.println(i + ") Mapa");
                    }
                }

                int objeto = jugador.bolso.length;

                String input2 = sc.next();
                try {
                    objeto = Integer.parseInt(input2);
                } catch (NumberFormatException ex) {
                    System.out.println("");
                }

                if (objeto == jugador.getBolso().length) {
                    break;
                } else {
                    jugador.UsarObjeto(objeto, laberinto);
                    break;
                }

        }
    }

    public static void SetJugador(Celdas[][] laberinto, Jugador jugador) {

        for (int i = 1; i < laberinto.length - 1; i++) {

            if (laberinto[i][0].getPared() == 0) {
                jugador.setY(i);
                jugador.setX(0);
                laberinto[i][0].setJugador(jugador);
            }
        }
    }

    private static void PrintLaberinto(Celdas[][] laberinto) {

        for (int i = 0; i < laberinto.length; i++) {

            for (int j = 0; j < laberinto.length; j++) {

                if (laberinto[i][j].getJugador() == jugador) {
                    System.out.print("@" + " ");
                } else if (laberinto[i][j].isFog() == true) {
                    System.out.print(" " + " ");
                } else {
                    if (laberinto[i][j].getPared() == 1) {
                        System.out.print("#" + " ");
                    } else {
                        if (laberinto[i][j].getMonstruo() != null) {
                            System.out.print("$" + " ");
                        } else if (laberinto[i][j].getObjeto() != null) {
                            System.out.print("!" + " ");
                        } else {
                            if (laberinto[i][j].getX() == laberinto.length - 1) {
                                System.out.println("*" + " ");
                            } else {
                                System.out.print(" " + " ");
                            }
                        }
                    }
                }
            }
            System.out.println("\n");
        }

    }

    private static void SetVainas(Celdas[][] laberinto, int nivel) {

        Random rand = new Random();

        for (int i = 1; i < laberinto.length - 1; i++) {
            for (int j = 1; j < laberinto.length - 1; j++) {

                int contador = laberinto[i + 1][j].getPared() + laberinto[i - 1][j].getPared() + laberinto[i][j + 1].getPared() + laberinto[i][j - 1].getPared();
                int chanceObjeto;

                int chanceMonster = rand.nextInt((200 - 1) + 1) + 1;

                if (chanceMonster <= 15) {
                    laberinto[i][j].setMonstruo(new Monstruos(nivel));
                }

                chanceObjeto = rand.nextInt((200 - 1) + 1) + 1;

                if (chanceObjeto <= 2) {

                    int chanceTipoObjeto = rand.nextInt((10 - 1) + 1) + 1;

                    if (chanceTipoObjeto <= 8) {
                        laberinto[i][j].setObjeto(new Pociones());
                    } else {
                        laberinto[i][j].setObjeto(new Pico());
                    }
                }

                if (contador == 0 || contador == 1) {

                    int chanceGuerrero = rand.nextInt((2 - 1) + 1) + 1;

                    if (chanceGuerrero == 1) {
                        laberinto[i][j].setMonstruo(new Guerreros(nivel));
                    }

                }

                if (contador == 3) {

                    int chanceTipoObjeto2 = rand.nextInt((10 - 1) + 1) + 1;

                    if (chanceTipoObjeto2 <= 80) {
                        laberinto[i][j].setObjeto(new Pociones());
                    } else {
                        laberinto[i][j].setObjeto(new Pico());
                    }
                }

                if (laberinto[i + 1][j].getPared() + laberinto[i - 1][j].getPared() == 1 && laberinto[i][j + 1].getPared() + laberinto[i][j - 1].getPared() == 1) {

                    int chanceMago = rand.nextInt((100 - 1) + 1) + 1;

                    if (chanceMago <= 5) {
                        laberinto[i][j].setMonstruo(new Magos(nivel));
                    }

                }
            }

        }
    }

    private static Objetos[] BolsoInicial() {

        Random rand = new Random();

        int tipoObjeto;

        Objetos[] bolso = new Objetos[10];

        for (int i = 0; i < 3; i++) {

            tipoObjeto = rand.nextInt(10);

            if (tipoObjeto < 8) {
                bolso[i] = new Pociones();
            } else {
                bolso[i] = new Pico();
            }

        }

        return bolso;
    }

    public static void ClearFog(Jugador jugador, Celdas[][] laberinto) {

        int i = jugador.getX();

        if (jugador.getX() < 1) {
        } else {
            laberinto[jugador.getY()][jugador.getX() - 1].setFog(false);
            laberinto[jugador.getY() - 1][jugador.getX() - 1].setFog(false);
            laberinto[jugador.getY() + 1][jugador.getX() - 1].setFog(false);
        }
        if (jugador.getX() == laberinto.length - 1) {
        } else {
            laberinto[jugador.getY()][jugador.getX() + 1].setFog(false);
            laberinto[jugador.getY() + 1][jugador.getX() + 1].setFog(false);
            laberinto[jugador.getY() - 1][jugador.getX() + 1].setFog(false);
        }

        laberinto[jugador.getY() + 1][jugador.getX()].setFog(false);
        laberinto[jugador.getY() - 1][jugador.getX()].setFog(false);

    }

    public static void CrearMapa(int nivel, Jugador jugador) {

        if (nivel % 5 == 0) {
            for (int i = 0; i < jugador.bolso.length; i++) {
                if (jugador.getBolso()[i] == null || i == jugador.bolso.length - 1) {
                    jugador.getBolso()[i] = new Mapa();
                    System.out.println("Has conseguido un mapa! ");
                    break;
                }
            }
        }
    }
}

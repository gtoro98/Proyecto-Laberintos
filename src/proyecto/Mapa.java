/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

/**
 *
 * @author gusta
 */
public class Mapa extends Objetos{
    
    
public static void Usar(Celdas[][] laberinto){

     for (int i = 0; i < laberinto.length; i++){
        
            for (int j = 0; j < laberinto.length; j++){
            
            laberinto[i][j].setFog(false);
               
            }
        }    

}
    
}

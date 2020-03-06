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
public class Monstruos extends Personajes{
    
 int fuerza;

    public Monstruos(int nivel) {
        
        Random rand = new Random();
    int max = nivel * nivel;
    int min = max/2;
    
     fuerza = rand.nextInt((max - min )+1)+ min;   
    }

    public int getFuerza() {
        return fuerza;
    }

  
 
    
    
    
    
    
}

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
public class Pico extends Objetos{
 
public void Usar(Celdas celdaRomper, int laberintoLength){
    

  if(celdaRomper.getX() == 0 || celdaRomper.getY() == 0 || celdaRomper.getX() == laberintoLength - 1 || celdaRomper.getY() == laberintoLength - 1){
  
      System.out.println("La pared es muy dura! Necesitas mas picos para romperla");
  }
  else{
    celdaRomper.setPared(0);
  }


}    
    
    
}

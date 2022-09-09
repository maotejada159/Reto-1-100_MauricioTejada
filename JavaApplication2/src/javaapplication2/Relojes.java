/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

/**
 *
 * @author otejada
 */
public class Relojes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    Reloj reloj1 = new Reloj();        
    reloj1.ponerEnHora(24,17,10,10);

    reloj1.setmodo(12);//cambiamos el modo por defecto de 24 a 12

    System.out.println(reloj1.verHora());
    reloj1.incrementar();
    System.out.println(reloj1.verHora());
    reloj1.decrementa();
    System.out.println(reloj1.verHora());

}
}



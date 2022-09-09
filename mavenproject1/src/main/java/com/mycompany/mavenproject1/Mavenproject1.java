/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.mavenproject1;

/**
 *
 * @author otejada
 */
import javax.swing.JOptionPane;
 
public class Mavenproject1 {
 
    public static void main(String[] args) {
 
        String texto = JOptionPane.showInputDialog("Introduce un numero");
        int numero = Integer.parseInt(texto);
 
        //Un numero negativo, el 0 y el 1, son directamente no primos.
        if (numero <= 1) {
            System.out.println("El numero " + numero + " no es primo");
        } else {
 
            //Hacemos un casting para que nos devuelva un numero entero
            int raiz = (int) Math.sqrt(numero);
            int contador = 0;
 
            //Contamos el numero de divisibles
            for (int i = raiz; i > 1; i--) {
                if (numero % i == 0) {
                    contador++;
                }
            }
 
            /*Segun el numero de divisibles, sabemos si es primo o no
                 * Si es primo el contador sera 0
             */
            //Mensaje de traza
            System.out.println(">>" + contador);
 
            if (contador < 1) {
                System.out.println("El numero " + numero + " es primo");
            } else {
                System.out.println("El numero " + numero + " no es primo");
            }
 
        }
    }
}

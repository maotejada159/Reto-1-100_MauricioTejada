/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.muestraprimos;

import javax.swing.JOptionPane;

/**
 *
 * @author otejada
 */
public class MuestraPrimos {
 
    public static void main(String[] args) {
 
        for (int i=1;i<=100;i++){
            //Hacemos un casting para que nos devuelva un numero entero
            int raiz=(int)Math.sqrt(i);
            int contador=0;
 
            //Hacemos otro bucle para contar los divisibles
            for (int j=raiz;j>1;j--){
                if (i%j==0){
                    contador++;
                }
            }
 
            /*Segun el numero de divisibles, sabemos si es primo o no
             * Si es primo el contador sera 0
             */
 
            if (contador<1){


                JOptionPane.showMessageDialog(null, "El numero "+i+" es primo", "Primo", JOptionPane.INFORMATION_MESSAGE);
            }
 
        }
    }
}

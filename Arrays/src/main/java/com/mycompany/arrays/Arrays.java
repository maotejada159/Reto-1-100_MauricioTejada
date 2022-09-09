/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.arrays;

/**
 *
 * @author otejada
 */
import javax.swing.JOptionPane;
 
public class Arrays {
 
    public static void main(String[] args) {
         
        String texto=JOptionPane.showInputDialog(null, 
                                                "Inserta una frase", 
                                                "Inserción", 
                                                JOptionPane.INFORMATION_MESSAGE);
         
        //usado para almacenar el texto final
        String texto_sin_espacios="";
         
        char caracterActual;
         
        //recorro el array
        for(int i=0;i<texto.length();i++){
             
            caracterActual=texto.charAt(i);
             
            //Cuando es un espacio no lo copia a la cadena
            if(caracterActual!=' '){
                texto_sin_espacios+=String.valueOf(caracterActual);   
            }
             
        }
         
         JOptionPane.showMessageDialog(null, 
                                      "La frase tiene sin espacios es "+texto_sin_espacios, 
                                      "Resultado", 
                                      JOptionPane.INFORMATION_MESSAGE);
         
    }
     
}

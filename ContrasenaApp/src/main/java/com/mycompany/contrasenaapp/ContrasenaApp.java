/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.contrasenaapp;

/**
 *
 * @author otejada
 */
import java.util.Scanner;
public class ContrasenaApp {
  
    public static void main(String[] args) {
  
        Scanner sc = new Scanner(System.in);
        String contraseña="eureka";
  
        final int INTENTOS = 3;
         
        //Esta variable booleana, nos controlara que en caso de que acierte la condicion cambie
        boolean acierto=false;
  
        String password;
        for (int i=0;i<INTENTOS && !acierto;i++){
            System.out.println("Introduce una contraseña");
            password = sc.next();
  
            //Comprobamos si coincide, no usamos ==, usamos el metodo equals
            if (password.equals(contraseña)){
                System.out.println("Enhorabuena, acertaste");
                acierto=true;
            }
        }
    }
}

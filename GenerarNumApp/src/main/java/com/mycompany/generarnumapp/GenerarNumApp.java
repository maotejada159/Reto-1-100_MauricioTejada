/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.generarnumapp;

/**
 *
 * @author otejada
 */
import java.util.Scanner;
public class GenerarNumApp {
  
    public static void main(String[] args) {
  
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el primero numero");
        int num1=sc.nextInt();
  
        System.out.println("Introduce el segundo numero");
        int num2=sc.nextInt();
  
        for (int i=0;i<10;i++){
            //Generamos un numero aleatorio
            int numAleatorio=(int)Math.floor(Math.random()*(num1-num2)+num2);
            System.out.println(numAleatorio);
        }
    }
}

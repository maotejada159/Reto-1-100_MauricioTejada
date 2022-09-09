/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.diaapp;

/**
 *
 * @author otejada
 */
import java.util.Scanner;
public class DiaApp {
  
    public static void main(String[] args) {
  
        Scanner sc = new Scanner(System.in);
         
        System.out.println("Introduce un dia de la semana");
        String dia = sc.next();
         
        switch(dia){
            case "lunes":
            case "martes":
            case "miercoles":
            case "jueves":
            case "viernes":
                System.out.println("Es un dia laboral");
                break;
            case "sabado":
            case "domingo":
                System.out.println("Es un dia festivo");
            default:
                System.out.println("Introduce un dia de la semana");
        }
    }
}

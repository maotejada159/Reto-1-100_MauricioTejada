/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.ecuacion2gradoapp;

/**
 *
 * @author otejada
 */
import java.util.Scanner;
public class Ecuacion2GradoApp {
  
    public static void main(String[] args) {
         
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el valor de a");
        int a=sc.nextInt();
        System.out.println("Introduce el valor de b");
        int b=sc.nextInt();
        System.out.println("Introduce el valor de c");
        int c=sc.nextInt();
  
        double discriminante=Math.pow(b, 2)-(4*a*c);
  
        //Mensaje de traza
        System.out.println(">>"+discriminante);
  
        if (discriminante>0){
            //Tambien se puede hacer por parte si lo ves más facil
            //recuerda que debes tener en cuenta la prioridad de elementos
            double x1=((b*(-1))+Math.sqrt(discriminante))/(2*a);
            double x2=((b*(-1))-Math.sqrt(discriminante))/(2*a);
  
            System.out.println("El valor de x1 es "+x1+" y el valor de x2 es "+x2);
        }else{
            System.out.println("El discriminante es negativo");
        }
    }
}

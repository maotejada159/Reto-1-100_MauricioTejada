/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.calculadora2;

/**
 *
 * @author otejada
 */
import java.util.Locale;
import java.util.Scanner;
public class Calculadora2 {
  
    public static void main(String[] args) {
  
        Scanner sc = new Scanner(System.in);
        sc.useLocale(Locale.US);
        //no importa que sean int o double
        double operando1;
        double operando2;
        double resultado=0;
  
        //Nos pide los operandos y el signo de operacion
        System.out.println("Escribe el operando 1");
        operando1=sc.nextDouble();
         
        System.out.println("Escribe el codigo de operacion");
        String operacion = sc.next();
         
        System.out.println("Escribe el operando 2");
        operando2=sc.nextDouble();
  
        //segun el codigo de operacion, haremos una u otra accion
        switch (operacion){
            case "+":
                resultado=operando1+operando2;
                break;
            case "-":
                resultado=operando1-operando2;
                break;
            case "*":
                resultado=operando1*operando2;
                break;
            case "/":
                resultado=operando1/operando2;
                break;
            case "^":
                resultado=(int)Math.pow(operando1, operando2);
                break;
            case "%":
                resultado=operando1%operando2;
                break;
        }
  
        System.out.println( operando1+" "+operacion+" "+operando2+" = "+resultado);
  
    }
}

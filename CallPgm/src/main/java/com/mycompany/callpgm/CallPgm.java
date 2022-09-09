/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.callpgm;

/**
 *
 * @author otejada
 */
import java.io.*;
 
public class CallPgm
{
   public static void main(String args[])
   {
      Process theProcess = null;
      BufferedReader inStream = null;
 
      System.out.println("CallPgm.main() invocado");
 
      // llamar al programa CSAMP1
      try
      {
          theProcess = Runtime.getRuntime().exec(
                       "/QSYS.LIB/JAVSAMPLIB.LIB/CSAMP1.PGM");
      }
      catch(IOException e)
      {
         System.err.println("Error en el método exec()");
         e.printStackTrace();
      }
 
      // leer en la corriente de salida estándar del programa llamado.
      try
      {
         inStream = new BufferedReader(new InputStreamReader
                           (theProcess.getInputStream()));
         System.out.println(inStream.readLine());
      }
      catch(IOException e)
      {
         System.err.println("Error en inStream.readLine()");
         e.printStackTrace();
      }
   } 
 } 

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.leerarchivo;

/**
 *
 * @author otejada
 */
import java.io.File;
import java.util.Scanner;
public class LeerArchivo {
    public static void main(String[] args) {
        try {
            Scanner input = new Scanner(new File("/ruta/filename.txt"));
            while (input.hasNextLine()) {
                String line = input.nextLine();
                System.out.println(line);
            }
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

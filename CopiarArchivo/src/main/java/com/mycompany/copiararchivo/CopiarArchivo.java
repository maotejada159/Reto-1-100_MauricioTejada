/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.copiararchivo;

/**
 *
 * @author otejada
 */


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author decodigo
 */
public class CopiarArchivo {

    public static void main(String[] args) {

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {

            File archivoOriginal = new File("/home/decodigo/Documentos/java/archivos/original.txt");
            File archivoCopia = new File("/home/decodigo/Documentos/java/archivos/copia.txt");

            inputStream = new FileInputStream(archivoOriginal);
            outputStream = new FileOutputStream(archivoCopia);

            byte[] buffer = new byte[1024];

            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            inputStream.close();
            outputStream.close();

            System.out.println("Archivo copiado.");

        } catch (IOException e) {
        }
    }
}

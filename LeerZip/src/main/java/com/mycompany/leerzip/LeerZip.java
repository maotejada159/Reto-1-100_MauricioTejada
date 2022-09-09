/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.leerzip;

/**
 *
 * @author otejada
 */
package com.decodigo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import java.io.IOException;

import java.util.List;

import java.io.File;

import java.io.File;

import java.io.File;

import java.io.File;

import java.util.List;

import java.util.List;


public class LeerZip {
    List listaArchivos;
    private static final String ARCHIVO_ZIP = "/home/decodigo/Documentos/java/archivos/archivos.zip";
    private static final String RUTA_SALIDA = "/home/decodigo/Documentos/java/archivos/";
    public static void main(String[] args) {
        LeerZip leerZip = new LeerZip();
        leerZip.unZip(ARCHIVO_ZIP, RUTA_SALIDA);
    }

    public void unZip(String archivoZip, String rutaSalida) {
        byte[] buffer = new byte[1024];
        try {
            File folder = new File(RUTA_SALIDA);
            if (!folder.exists()) {
                folder.mkdir();
            }

            ZipInputStream zis = new ZipInputStream(new FileInputStream(archivoZip));
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                String nombreArchivo = ze.getName();
                File archivoNuevo = new File(rutaSalida + File.separator + nombreArchivo);
                System.out.println("archivo descomprimido : " + archivoNuevo.getAbsoluteFile());
                new File(archivoNuevo.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(archivoNuevo);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                ze = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
            System.out.println("Listo");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
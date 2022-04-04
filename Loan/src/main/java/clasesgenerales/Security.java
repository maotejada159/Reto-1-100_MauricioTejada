
package clasesgenerales;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * CON ESTA CLASE PUEDE GENERAR A PARTIR DE CADENAS RECIBIDAS CODIGOS EN LOS FORMATOS
 * DESEADOS, BRINDANDO SEGURIDAD PARA ALMACENAR INFORMACION EN LA BD.
 * RECUERDE QUE ES UN METODO ONE-WAY, UNA VEZ GENERADA LA CLAVE NO SE PUEDE RECONSTITUIR LA CADENA
 * ORIGINAL, DEBERA GENERARSE UNA NUEVA.
 * @author Foxmax
 */
public class Security {

    public static String MD2 = "MD2";
    public static String MD5 = "MD5";
    public static String SHA1 = "SHA-1";
    public static String SHA256 = "SHA-256";
    public static String SHA384 = "SHA-384";
    public static String SHA512 = "SHA-512";
/**
 * RECIBE UNA CADENA DE CUALQUIER LONGITUD Y LA CONVIERTE EN UNA CADENA DE 32 BITS
 * @param input
 * @return 
 */
    public String setMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance(MD5);
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * RECIBE UNA CADENA DE CUALQUIER LONGITUD Y LA CONVIERTE
     * EN UNA CADENA DE  128 BITS
     * @param texto
     * @return
     * @throws NoSuchAlgorithmException 
     */
public String setSHA512(String texto) throws NoSuchAlgorithmException {
        MessageDigest md;
        String output = "";
        try {
            md= MessageDigest.getInstance(SHA512);
            md.update(texto.getBytes());
            byte[] mb = md.digest();
            for (int i = 0; i < mb.length; i++) {
                byte temp = mb[i];
                String s = Integer.toHexString(new Byte(temp));
                while (s.length() < 2) {
                    s = "0" + s;
                }
                s = s.substring(s.length() - 2);
                output += s;
            }
        } catch (NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
        }
        
        return output;
    }
    /**
     * PARA EFECTOS DE PRUEBA
     * @param args
     * @throws NoSuchAlgorithmException 
     */
/*
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Security sec=new Security();
        System.out.println(sec.setMD5("hola"));
        System.out.println(sec.setSHA512("Consati308"));
    }
 */
}
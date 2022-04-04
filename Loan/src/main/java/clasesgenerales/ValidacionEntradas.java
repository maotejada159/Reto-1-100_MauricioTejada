/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasesgenerales;


import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author jgonzalez
 */
public class ValidacionEntradas {

    private final Properties properties;
    private final String pathproperties = "../parametros.properties";

    public ValidacionEntradas() {
        this.properties = new Properties();
    }

    /*
    Metodo que permite validar si una entrada de texto es un número
     */
    public boolean esNumero(String cadena) {

        boolean resultado;

        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }

    /*
    Metodo que permite validar la longitud de una entrada comparada con dos valores
    de referencia para ser usados como rango de comparación
     */
    public boolean longitudCadena(String cadena, int longitudMin, int longitudMax) {
        boolean resultado;
        int longitudCadena = cadena.length();
        if (longitudCadena >= longitudMin && longitudCadena <= longitudMax) {
            resultado = true;
        } else {
            resultado = false;
        }
        //System.out.println("validación de: "+cadena+" \nRESULTADO: "+resultado);
        return resultado;
    }

    public boolean caracteresEspeciales(String cadena) throws IOException {
        properties.load(getClass().getResourceAsStream(pathproperties));
        String regex = properties.getProperty("regexespecial");
        String cadena2 = "";
        boolean resultado;
        //System.out.println(cadena);
        cadena2 = cadena.replaceAll("["+regex+"]", "");
        //cadena2 = cadena.replaceAll("[\\[<('-,+\")>\\]]", "");
        resultado = cadena2.equals(cadena);
        //System.out.println(cadena2);
        /*
        if (cadena.matches(".*[a-z].*") || cadena.matches(".*[A-Z].*") || cadena.matches(".*[0-9].*")) {
            cadena2 = true;
        } else {
            cadena2 = false;
        }
         */
        //System.out.println("validación de: "+cadena+" \nRESULTADO: "+resultado);
        return resultado;
    }

    public String limpiarCaracteresEspeciales(String cadena) {
        String cadena2 = "";
        //boolean resultado;
        cadena2 = cadena.replaceAll("[\\[<('-,+\")>\\]]", "");
        //resultado = cadena2.equals(cadena);
        /*
        if (cadena.matches(".*[a-z].*") || cadena.matches(".*[A-Z].*") || cadena.matches(".*[0-9].*")) {
            cadena2 = true;
        } else {
            cadena2 = false;
        }
         */
        return cadena2;
    }
/*
    public static void main(String[] args) throws NoSuchAlgorithmException, SQLException {

        try {
            ValidacionEntradas ve = new ValidacionEntradas();
            String entrada = "0#xQ;t\\B*6,2%0$\"M'";
            System.out.println(ve.caracteresEspeciales(entrada));
            
            System.out.print(new ValidacionEntradas().limpiarCaracteresEspeciales("prueba con [] y () .. ,, probando"));
            
            System.out.println("resultado1: "+new ValidacionEntradas().caracteresEspeciales("minusculas"));
            System.out.println("resultado2: "+new ValidacionEntradas().caracteresEspeciales("MAYUSCULAS"));
            System.out.println("resultado3: "+new ValidacionEntradas().caracteresEspeciales("01840"));
            System.out.println("resultado4: "+new ValidacionEntradas().caracteresEspeciales("Hola111\""));
            System.out.println("resultado5: "+new ValidacionEntradas().caracteresEspeciales("minusculas'"));
            
            String nusuario = (String) "admon";
            String cusuario = (String) "Consati308";
            boolean esnumero = new ValidacionEntradas().esNumero(nusuario);
            boolean longitudUsuario = new ValidacionEntradas().longitudCadena(nusuario, 5, 20);
            boolean longitudClave = new ValidacionEntradas().longitudCadena(cusuario, 6, 20);
            System.out.println("1. "+esnumero+" 2. "+longitudUsuario+" 3. "+longitudClave);
            System.out.println(new ValidacionEntradas().esNumero("12345Maria"));
            System.out.println(new ValidacionEntradas().longitudCadena("Consati308",10,15));
            
            LoginDAO loginDAO = new LoginDAO();
            String cusuarioCrypt = new Security().setSHA512(cusuario);
            boolean vUsuario = loginDAO.validarLogin(nusuario,cusuarioCrypt);
            System.out.println("cusuario: "+cusuarioCrypt+" cadena2: "+vUsuario);
            
            
        } catch (IOException ex) {
            Logger.getLogger(ValidacionEntradas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
*/

}

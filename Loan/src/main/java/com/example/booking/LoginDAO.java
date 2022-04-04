/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.booking;

import entidades.MensajesEntidad;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;


public class LoginDAO extends ConexionSQLServer {

    private final Properties properties;

    public LoginDAO() {
        this.properties = new Properties();
    }

    public MensajesEntidad validarLogin(String username, String claveusuario) throws SQLException, IOException {
        properties.load(getClass().getResourceAsStream("../../../configDB.properties"));
        String db = properties.getProperty("nombreDataBase");
        //System.out.println("Nombre base de datos: "+db);
        ResultSet resultado = null;
        MensajesEntidad mensajesentidadsalida = new MensajesEntidad();
        String user = "";
        String password = "";
        if(mensajeentidadconexionsql.isEstado()){
            try {
            //declaro la sentencia SQL que quiero ejecutar
            stmt = conexion.prepareStatement("USE " + db + "; SELECT * FROM usuario where username=? and claveusuario=?",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setString(1, username);
            stmt.setString(2, claveusuario);
            //asigno en resultado lo devuelto por la BD            
            resultado = stmt.executeQuery();
            //recorro resultado para sacar los valores individualmente
            while (resultado.next()) {
                user = resultado.getString("username");
                password = resultado.getString("claveusuario");
            }
            //System.out.println("passwordDAO = "+password);
            if (user.equals(username) && password.equals(claveusuario)) {

                mensajesentidadsalida.setEstado(true);
                mensajesentidadsalida.setSeverity(FacesMessage.SEVERITY_INFO);
                mensajesentidadsalida.setTitulo("Login Correcto");
                mensajesentidadsalida.setMensaje("Bienvenido");

            } else {
                mensajesentidadsalida.setEstado(false);
                mensajesentidadsalida.setSeverity(FacesMessage.SEVERITY_WARN);
                mensajesentidadsalida.setTitulo("Login Incorrecto");
                mensajesentidadsalida.setMensaje("Combinación de Usuario y Clave no existe");
            }
            //System.out.println("Usuario Valido?: "+valido);
        } catch (Exception ex) {
            System.out.println("Error durante la consulta: " + ex.getMessage());

            mensajesentidadsalida.setEstado(false);
            mensajesentidadsalida.setSeverity(FacesMessage.SEVERITY_FATAL);
            mensajesentidadsalida.setTitulo("Error SQL");
            mensajesentidadsalida.setMensaje("Excepcion: " + ex.getMessage());

        } finally {
            try {
                stmt.close();
                //conexion.close();
            } catch (Exception ex) {

                mensajesentidadsalida.setEstado(false);
                mensajesentidadsalida.setSeverity(FacesMessage.SEVERITY_FATAL);
                mensajesentidadsalida.setTitulo("Error SQL");
                mensajesentidadsalida.setMensaje("Excepcion: " + ex.getMessage());

            }
        }
        }else{
            mensajesentidadsalida = mensajeentidadconexionsql;
        }
        

        //imprimo la respuesta de la consulta
        return mensajesentidadsalida;

    }

    public boolean validarUsuarioActivo(String nombreusuario) throws IOException {
        properties.load(getClass().getResourceAsStream("../../../configDB.properties"));
        String db = properties.getProperty("nombreDataBase");
        ResultSet resultado = null;
        boolean estadousuario = false;
        try {
            //declaro la sentencia SQL que quiero ejecutar
            stmt = conexion.prepareStatement("USE " + db + "; SELECT estadousuario FROM usuario where username=?;",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setString(1, nombreusuario);
            //asigno en resultado lo devuelto por la BD            
            resultado = stmt.executeQuery();
            //recorro resultado para sacar los valores individualmente
            while (resultado.next()) {
                estadousuario = resultado.getBoolean("estadousuario");
            }
            //System.out.println("passwordDAO = "+password);

        } catch (SQLException ex) {
            System.out.println("Error durante la consulta: " + ex.getMessage());
        } finally {
            try {
                stmt.close();
                //conexion.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Excepción validar Login: " + ex.getMessage());
            }
        }
        return estadousuario;
    }

}

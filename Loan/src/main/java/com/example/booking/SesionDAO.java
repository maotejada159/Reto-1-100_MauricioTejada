/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.booking;


import entidades.UsuarioEntidad;
import entidades.PerfilUsuarioEntidad;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SesionDAO extends ConexionSQLServer {
    
    private final Properties properties;
    
    public SesionDAO (){
        this.properties = new Properties();
    }
    
    public Map<Integer,PerfilUsuarioEntidad> obtenerPerfilUsuario(UsuarioEntidad ue) throws IOException{
        properties.load(getClass().getResourceAsStream("../../../configDB.properties"));
      String db = properties.getProperty("nombreDataBase");
       ResultSet resultado = null;
       Map<Integer,PerfilUsuarioEntidad> listaPerfiles = new TreeMap<>();
        PerfilUsuarioEntidad pue = new PerfilUsuarioEntidad();
        int indicelista=0;
        try {
            //declaro la sentencia SQL que quiero ejecutar
            stmt = conexion.prepareStatement("SELECT [idperfilusuario]\n"
                    + "      ,[nombreperfilusuario]\n"
                    + "      ,[idusuario]\n"
                    + "  FROM ["+db+"].[dbo].[vista_perfilesusuario] where idusuario=?;",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, ue.getIdusuario());
            //asigno en resultado lo devuelto por la BD            
            resultado = stmt.executeQuery();
            //recorro resultado para sacar los valores individualmente
            while (resultado.next()) {                
                pue.setIdperfilusuario(resultado.getInt("idperfilusuario"));
                pue.setNombreperfilusuario(resultado.getString("nombreperfilusuario"));
                pue.setIdusuario(resultado.getInt("idusuario"));
                listaPerfiles.put(indicelista, pue);
                indicelista ++;
            }
            //System.out.println("passwordDAO = "+password);
            
            
        } catch (SQLException ex) {
            System.out.println("Error durante la consulta_SesionDAO_ObtenerPerfilUsuario: " + ex.getMessage());
        } finally {
            try {
                stmt.close();
                //conexion.close();
            } catch (SQLException ex) {
                //Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaPerfiles;
    }
    
    public UsuarioEntidad obtenerDatosUsuario (String nombreusuario) throws IOException{
        properties.load(getClass().getResourceAsStream("../../../configDB.properties"));
      String db = properties.getProperty("nombreDataBase");
        ResultSet resultado = null;
        UsuarioEntidad usuarioentidad = new UsuarioEntidad();
        try {
            //declaro la sentencia SQL que quiero ejecutar
            stmt = conexion.prepareStatement("USE "+db+"; select * from usuario where username=?;",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setString(1, nombreusuario);
            //asigno en resultado lo devuelto por la BD            
            resultado = stmt.executeQuery();
            //recorro resultado para sacar los valores individualmente
            while (resultado.next()) {
                usuarioentidad.setIdusuario(resultado.getInt("idusuario"));
                usuarioentidad.setUsername(resultado.getString("username"));
                usuarioentidad.setNombrecompletousuario(resultado.getString("nombrecompletousuario"));
                usuarioentidad.setCorreousuario(resultado.getString("correousuario"));
                usuarioentidad.setDatetimecreacionusuario(resultado.getDate("datetimecreacionusuario"));
                usuarioentidad.setEstadousuario(resultado.getBoolean("estadousuario")); 
            }
            //System.out.println("passwordDAO = "+password);
            
            
        } catch (SQLException ex) {
            System.out.println("Error durante la consulta_SESIODAO_obtenerdatosusuario: " + ex.getMessage());
        } finally {
            try {
                stmt.close();
                //conexion.close();
            } catch (SQLException ex) {
                //Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        return usuarioentidad;
    }
    
}


package com.example.booking;

import entidades.MensajesEntidad;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;


public class ConexionSQLServer {
    
    //private String usuario = "prueba";//colocar el nombre de un usuario con privilegios en la BD
    //private String contraseña = "prueba";//contraseña del usuario en la BD
    protected  static Connection conexion;//es protegido para poder usarlo desde la clase usuario
    private static Statement sentenciaSQL;
    private ResultSet resulSet;
    static PreparedStatement stmt = null;
    //parametros para acceder a la BD
    private String indice;
    private String tabla;
    static private ResultSet resultado = null;
    
    private String usuario;
    private String contraseña;
    private String direccionip;
    private String instancia;
    private String puerto;
    private String nombredatabase;
    private MensajesBean mbean ;
    static MensajesEntidad mensajeentidadconexionsql;
    
    private final Properties properties;
    //Constructor
    public ConexionSQLServer (){
        this.properties = new Properties();
        mbean = new MensajesBean();
        mensajeentidadconexionsql = new MensajesEntidad();

        try {
            //cargar el controlador de la JDBC
            String controlador = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            Class.forName(controlador).newInstance();
                      
            //Realice el cargue de los datos del archivo en el objeto creado
            properties.load(getClass().getResourceAsStream("../../../configDB.properties"));
            
            usuario = properties.getProperty("nombreUsuarioDB");
            contraseña = properties.getProperty("claveUsuarioDB");
            direccionip = properties.getProperty("direccionServer");
            instancia = properties.getProperty("nombreInstanciaDB");
            puerto = properties.getProperty("puertoDataBase");
            nombredatabase = properties.getProperty("nombreDataBase");          
            
            
            conectar();
            
        } catch (Exception ex) {
            mensajeentidadconexionsql.setEstado(false);
            mensajeentidadconexionsql.setSeverity(FacesMessage.SEVERITY_FATAL);
            mensajeentidadconexionsql.setTitulo("Excepción Conexion SQL");
            mensajeentidadconexionsql.setMensaje("No se pudo conectar con la base de datos");
        } 
    }
    
    private void conectar()  {
        try {

            //String url = "jdbc:sqlserver://localhost\\MSSQLSERVER:1433;databaseName=pruebacompartida;";
            String url = "jdbc:sqlserver://"+direccionip+"\\"+instancia+":"+puerto+";databaseName="+nombredatabase+";";
            //System.out.println("la url: "+url);
            conexion = (Connection) DriverManager.getConnection(url,usuario,contraseña);
            //crear una sentencia SQL
            //sentenciaSQL = (Statement) conexion.createStatement(
            //     ResultSet.TYPE_SCROLL_INSENSITIVE,
            //    ResultSet.CONCUR_UPDATABLE);
            sentenciaSQL = (Statement) conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            //System.out.println("\nConexion realizada con exito.\n");
            mensajeentidadconexionsql.setEstado(true);
        } catch (SQLException ex) {
            //Logger.getLogger(ConexionSQLServer.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Excepción conexión BD: "+ex.getMessage());
            mensajeentidadconexionsql.setSeverity(FacesMessage.SEVERITY_FATAL);
            mensajeentidadconexionsql.setTitulo("Excepción Conexion SQL");
            mensajeentidadconexionsql.setEstado(false);
            mensajeentidadconexionsql.setMensaje("No se pudo conectar con la base de datos");
        }
            }
    
    public ResultSet ejecutarQuery(String sql) {
        ResultSet resultado = null;
        try {
            resultado = sentenciaSQL.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ConexionSQLServer.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return resultado;
    }
    
    public void cerrarConexion() {        
         try {        
             conexion.close();
         } catch (SQLException ex) {
             Logger.getLogger(ConexionSQLServer.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
  /*
    public static void main(String[] args) throws SQLException, IOException 
   {     
       ConexionSQLServer prueba = new ConexionSQLServer();

   }
     */       
   
    
}
  
  

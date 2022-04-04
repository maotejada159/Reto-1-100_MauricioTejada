/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.booking;

import entidades.MarcaEntidad;
import entidades.MensajesEntidad;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.faces.application.FacesMessage;


public class MarcaDAO extends ConexionSQLServer {

    private final Properties properties;
    private final String pathresource = "../../../configDB.properties";

    public MarcaDAO() {
        this.properties = new Properties();
    }
    
    public List<MarcaEntidad> consultarListaMarcas() {
        List<MarcaEntidad> salida = new ArrayList<>();
        ResultSet resultado = null;
        try {
            properties.load(getClass().getResourceAsStream(pathresource));
            String db = properties.getProperty("nombreDataBase");
            //declaro la sentencia SQL que quiero ejecutar

            stmt = conexion.prepareStatement("SELECT * FROM marca order by nombremarca asc;",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            resultado = stmt.executeQuery();

            while (resultado.next()) {
                MarcaEntidad centidad = new MarcaEntidad();
                centidad.setIdregistro(resultado.getInt("idregistro"));
                centidad.setNombremarca(resultado.getString("nombremarca"));
                salida.add(centidad);
            }

        } catch (SQLException | IOException ex) {
            System.out.println("Error durante la consulta: " + ex.getMessage());

        } finally {
            try {
                stmt.close();
                //conexion.close();
            } catch (SQLException ex) {
                System.out.println("Error durante la consulta: " + ex.getMessage());
            }
        }
        return salida;
    }

    /*
    Metodo que realiza el registro de una nueva marca
     */
    public MensajesEntidad insertarNuevaMarca(MarcaEntidad centidad) {
        MensajesEntidad me = new MensajesEntidad();
        ResultSet resultado = null;
        try {
            properties.load(getClass().getResourceAsStream(pathresource));
            String db = properties.getProperty("nombreDataBase");
            //declaro la sentencia SQL que quiero ejecutar

            stmt = conexion.prepareStatement("SELECT * FROM marca \n"
                    + " WHERE nombremarca = ?;",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            stmt.setString(1, centidad.getNombremarca());
            resultado = stmt.executeQuery();
            int cantidadresultados = 0;
            while (resultado.next()) {
                cantidadresultados++;
            }

            if (cantidadresultados == 0) {

                stmt = conexion.prepareStatement("SELECT MAX(idregistro) AS idregistro FROM marca;",
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                resultado = null;
                resultado = stmt.executeQuery();
                int id = 0;
                while (resultado.next()) {
                    id = resultado.getInt("idregistro");
                    id++;
                }
                
                if (id > 0) {
                    
                    stmt = conexion.prepareStatement("USE " + db + "; INSERT INTO [dbo].[marca]\n"
                            + "     ([idregistro]"
                            + "     ,[nombremarca]"
                            + "      )\n"
                            + "     VALUES\n"
                            + "           (?\n"
                            + "           ,?)",
                            ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    stmt.setInt(1, id);
                    stmt.setString(2, centidad.getNombremarca());
                    //asigno en resultado lo devuelto por la BD
                    stmt.executeUpdate();
                    //recorro resultado para sacar los valores individualmente
                    me.setEstado(true);
                    me.setMensaje("Se insertó el registro correctamente");
                    me.setTitulo("Operación Exitosa");
                    me.setSeverity(FacesMessage.SEVERITY_INFO);
                } else {
                    stmt = conexion.prepareStatement("USE " + db + "; INSERT INTO [dbo].[marca]\n"
                            + "     ([idregistro]"
                            + "     ,[nombremarca]"
                            + "      )\n"
                            + "     VALUES\n"
                            + "           (?\n"
                            + "           ,?)",
                            ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    stmt.setInt(1, 1);
                    stmt.setString(2, centidad.getNombremarca());
                    //asigno en resultado lo devuelto por la BD
                    stmt.executeUpdate();
                    //recorro resultado para sacar los valores individualmente
                    me.setEstado(true);
                    me.setMensaje("Se insertó el registro correctamente");
                    me.setTitulo("Operación Exitosa");
                    me.setSeverity(FacesMessage.SEVERITY_INFO);
                }
            }else{
                me.setEstado(false);
                    me.setMensaje("Ya existe un registro duplicado");
                    me.setTitulo("No se Insertó");
                    me.setSeverity(FacesMessage.SEVERITY_WARN);
            }

        } catch (SQLException ex) {
            //System.out.println("Error durante la consulta: " + ex.getMessage());
            me.setEstado(false);
            me.setMensaje("Error de SQL: " + ex.getMessage());
            me.setTitulo("Error SQL Excepción");
            me.setSeverity(FacesMessage.SEVERITY_ERROR);
        } catch (IOException ex) {
            me.setEstado(false);
            me.setMensaje("Error de IO: " + ex.getMessage());
            me.setTitulo("Error IO Excepción");
            me.setSeverity(FacesMessage.SEVERITY_ERROR);
        } finally {
            try {
                stmt.close();
                //conexion.close();
            } catch (SQLException ex) {
                me.setEstado(false);
                me.setMensaje("Error de SQL en cierre conexión: " + ex.getMessage());
                me.setTitulo("Error SQL Excepción");
                me.setSeverity(FacesMessage.SEVERITY_ERROR);
            }
        }
        return me;
    }

    public MensajesEntidad modificarMarca(MarcaEntidad centidad) {
        MensajesEntidad me = new MensajesEntidad();
        ResultSet resultado = null;
        try {
            properties.load(getClass().getResourceAsStream(pathresource));
            String db = properties.getProperty("nombreDataBase");
            //declaro la sentencia SQL que quiero ejecutar

            stmt = conexion.prepareStatement("SELECT * FROM marca \n"
                    + " WHERE nombremarca = ? and idregistro <> ?;",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            stmt.setString(1, centidad.getNombremarca());
            stmt.setInt(2, centidad.getIdregistro());
            resultado = stmt.executeQuery();
            int cantidadresultados = 0;
            while (resultado.next()) {
                cantidadresultados++;
            }

            if (cantidadresultados == 0) {
                stmt = conexion.prepareStatement("UPDATE [dbo].[marca]\n"
                        + "     SET [nombremarca] = ?"
                        + " WHERE [idregistro] = ?",
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                stmt.setString(1, centidad.getNombremarca());
                stmt.setInt(2, centidad.getIdregistro());                
                //asigno en resultado lo devuelto por la BD
                stmt.executeUpdate();
                //recorro resultado para sacar los valores individualmente
                me.setEstado(true);
                me.setMensaje("Se actualizó el registro correctamente");
                me.setTitulo("Operación Exitosa");
                me.setSeverity(FacesMessage.SEVERITY_INFO);
            } else {
                me.setEstado(false);
                me.setMensaje("Ya existe un registro duplicado");
                me.setTitulo("No se Insertó");
                me.setSeverity(FacesMessage.SEVERITY_WARN);
            }

        } catch (SQLException ex) {
            //System.out.println("Error durante la consulta: " + ex.getMessage());
            me.setEstado(false);
            me.setMensaje("Error de SQL: " + ex.getMessage());
            me.setTitulo("Error SQL Excepción");
            me.setSeverity(FacesMessage.SEVERITY_ERROR);
        } catch (IOException ex) {
            me.setEstado(false);
            me.setMensaje("Error de IO: " + ex.getMessage());
            me.setTitulo("Error IO Excepción");
            me.setSeverity(FacesMessage.SEVERITY_ERROR);
        } finally {
            try {
                stmt.close();
                //conexion.close();
            } catch (SQLException ex) {
                me.setEstado(false);
                me.setMensaje("Error de SQL en cierre conexión: " + ex.getMessage());
                me.setTitulo("Error SQL Excepción");
                me.setSeverity(FacesMessage.SEVERITY_ERROR);
            }
        }
        return me;
    }

    public MensajesEntidad eliminarMarca(MarcaEntidad mentidad) {
        MensajesEntidad me = new MensajesEntidad();
        ResultSet resultado = null;
        try {
            properties.load(getClass().getResourceAsStream(pathresource));
            String db = properties.getProperty("nombreDataBase");
            //declaro la sentencia SQL que quiero ejecutar

            stmt = conexion.prepareStatement("DELETE  FROM marca \n"
                    + " WHERE idregistro = ?;",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            stmt.setInt(1, mentidad.getIdregistro());
            stmt.executeUpdate();

            stmt = conexion.prepareStatement("SELECT * FROM [dbo].[marca]\n"
                    + " WHERE [idregistro] = ?",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            stmt.setInt(1, mentidad.getIdregistro());
            //asigno en resultado lo devuelto por la BD
            resultado = stmt.executeQuery();
            //recorro resultado para sacar los valores individualmente
            int resultadoconsulta = 0;
            while (resultado.next()) {
                resultadoconsulta++;
            }

            if (resultadoconsulta == 0) {
                me.setEstado(true);
                me.setMensaje("Se eliminó el registro correctamente");
                me.setTitulo("Operación Exitosa");
                me.setSeverity(FacesMessage.SEVERITY_INFO);
            } else {
                me.setEstado(false);
                me.setMensaje("No fue posible eliminar el registro");
                me.setTitulo("No se eliminó");
                me.setSeverity(FacesMessage.SEVERITY_WARN);
            }

        } catch (SQLException ex) {
            //System.out.println("Error durante la consulta: " + ex.getMessage());
            me.setEstado(false);
            me.setMensaje("Error de SQL: " + ex.getMessage());
            me.setTitulo("Error SQL Excepción");
            me.setSeverity(FacesMessage.SEVERITY_ERROR);
        } catch (IOException ex) {
            me.setEstado(false);
            me.setMensaje("Error de IO: " + ex.getMessage());
            me.setTitulo("Error IO Excepción");
            me.setSeverity(FacesMessage.SEVERITY_ERROR);
        } finally {
            try {
                stmt.close();
                //conexion.close();
            } catch (SQLException ex) {
                me.setEstado(false);
                me.setMensaje("Error de SQL en cierre conexión: " + ex.getMessage());
                me.setTitulo("Error SQL Excepción");
                me.setSeverity(FacesMessage.SEVERITY_ERROR);
            }
        }
        return me;
    }
    
}

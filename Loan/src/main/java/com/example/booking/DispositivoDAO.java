/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.booking;

import entidades.DependenciaEntidad;
import entidades.DispositivoEntidad;
import entidades.MensajesEntidad;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.faces.application.FacesMessage;


public class DispositivoDAO extends ConexionSQLServer {

    private final Properties properties;
    private final String pathresource = "../../../configDB.properties";

    public DispositivoDAO() {
        this.properties = new Properties();
    }
    
    public List<DispositivoEntidad> consultarListaCompletaDispositivo() {
        List<DispositivoEntidad> salida = new ArrayList<>();
        ResultSet resultado = null;
        try {
            properties.load(getClass().getResourceAsStream(pathresource));
            String db = properties.getProperty("nombreDataBase");
            //declaro la sentencia SQL que quiero ejecutar

            stmt = conexion.prepareStatement("SELECT * FROM dispositivo order by nombredispositivo asc;",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            resultado = stmt.executeQuery();

            while (resultado.next()) {
                DispositivoEntidad centidad = new DispositivoEntidad();
                centidad.setIdregistro(resultado.getInt("idregistro"));
                centidad.setNombredispositivo(resultado.getString("nombredispositivo"));
                centidad.setEstadodispositivo(resultado.getBoolean("estadodispositivo"));
                centidad.setDisponibledispositivo(resultado.getBoolean("disponibledispositivo"));
                centidad.setCodigoactivo(resultado.getString("codigoactivo"));
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
    
    public List<DispositivoEntidad> consultarListaDispositivosActivos() {
        List<DispositivoEntidad> salida = new ArrayList<>();
        ResultSet resultado = null;
        try {
            properties.load(getClass().getResourceAsStream(pathresource));
            String db = properties.getProperty("nombreDataBase");
            //declaro la sentencia SQL que quiero ejecutar

            stmt = conexion.prepareStatement("SELECT * FROM dispositivo where estadodispositivo = ? "
                    + " order by nombredispositivo asc;",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            stmt.setBoolean(1, true);
            resultado = stmt.executeQuery();

            while (resultado.next()) {
                DispositivoEntidad centidad = new DispositivoEntidad();
                centidad.setIdregistro(resultado.getInt("idregistro"));
                centidad.setNombredispositivo(resultado.getString("nombredispositivo"));
                centidad.setEstadodispositivo(resultado.getBoolean("estadodispositivo"));
                centidad.setDisponibledispositivo(resultado.getBoolean("disponibledispositivo"));
                centidad.setCodigoactivo(resultado.getString("codigoactivo"));
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
    
    public List<DispositivoEntidad> consultarListaDispositivosActivosDisponibles() {
        List<DispositivoEntidad> salida = new ArrayList<>();
        ResultSet resultado = null;
        try {
            properties.load(getClass().getResourceAsStream(pathresource));
            String db = properties.getProperty("nombreDataBase");
            //declaro la sentencia SQL que quiero ejecutar

            stmt = conexion.prepareStatement("SELECT * FROM dispositivo where estadodispositivo = ? AND "
                    + " disponibledispositivo = ?\n"
                    + " order by nombredispositivo asc;",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            stmt.setBoolean(1, true);
            stmt.setBoolean(2, true);
            resultado = stmt.executeQuery();

            while (resultado.next()) {
                DispositivoEntidad centidad = new DispositivoEntidad();
                centidad.setIdregistro(resultado.getInt("idregistro"));
                centidad.setNombredispositivo(resultado.getString("nombredispositivo"));
                centidad.setEstadodispositivo(resultado.getBoolean("estadodispositivo"));
                centidad.setDisponibledispositivo(resultado.getBoolean("disponibledispositivo"));
                centidad.setCodigoactivo(resultado.getString("codigoactivo"));
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
    Metodo que realiza el registro de un nuevo dispositivo
     */
    public MensajesEntidad insertarNuevoDispositivo(DispositivoEntidad dentidad) {
        MensajesEntidad me = new MensajesEntidad();
        ResultSet resultado = null;
        try {
            properties.load(getClass().getResourceAsStream(pathresource));
            String db = properties.getProperty("nombreDataBase");
            //declaro la sentencia SQL que quiero ejecutar

            stmt = conexion.prepareStatement("SELECT * FROM dispositivo \n"
                    + " WHERE nombredispositivo = ?;",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            stmt.setString(1, dentidad.getNombredispositivo());
            resultado = stmt.executeQuery();
            int cantidadresultados = 0;
            while (resultado.next()) {
                cantidadresultados++;
            }

            if (cantidadresultados == 0) {

                stmt = conexion.prepareStatement("SELECT MAX(idregistro) AS idregistro FROM dispositivo;",
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                resultado = null;
                resultado = stmt.executeQuery();
                int id = 0;
                while (resultado.next()) {
                    id = resultado.getInt("idregistro");
                    id++;
                }
                
                if (id > 0) {
                    
                    stmt = conexion.prepareStatement("USE " + db + "; INSERT INTO [dbo].[dispositivo]\n"
                            + "     ([idregistro]"
                            + "     ,[nombredispositivo]"
                            + "     ,[estadodispositivo]"
                            + "     ,[disponibledispositivo]"
                            + "     ,[codigoactivo]"
                            + "      )\n"
                            + "     VALUES\n"
                            + "           (?\n"
                            + "           ,?\n"
                            + "           ,?\n"
                            + "           ,?\n"
                            + "           ,?)",
                            ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    stmt.setInt(1, id);
                    stmt.setString(2, dentidad.getNombredispositivo());
                    stmt.setBoolean(3, dentidad.isEstadodispositivo());
                    stmt.setBoolean(4, dentidad.isDisponibledispositivo());
                    stmt.setString(5, dentidad.getCodigoactivo());
                    //asigno en resultado lo devuelto por la BD
                    stmt.executeUpdate();
                    //recorro resultado para sacar los valores individualmente
                    me.setEstado(true);
                    me.setMensaje("Se insertó el registro correctamente");
                    me.setTitulo("Operación Exitosa");
                    me.setSeverity(FacesMessage.SEVERITY_INFO);
                } else {
                    stmt = conexion.prepareStatement("USE " + db + "; INSERT INTO [dbo].[dispositivo]\n"
                            + "     ([idregistro]"
                            + "     ,[nombredispositivo]"
                            + "     ,[estadodispositivo]"
                            + "     ,[disponibledispositivo]"
                            + "     ,[codigoactivo]"
                            + "      )\n"
                            + "     VALUES\n"
                            + "           (?\n"
                            + "           ,?\n"
                            + "           ,?\n"
                            + "           ,?\n"
                            + "           ,?)",
                            ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    stmt.setInt(1, id);
                    stmt.setString(2, dentidad.getNombredispositivo());
                    stmt.setBoolean(3, dentidad.isEstadodispositivo());
                    stmt.setBoolean(4, dentidad.isDisponibledispositivo());
                    stmt.setString(5, dentidad.getCodigoactivo());
                    //recorro resultado para sacar los valores individualmente
                    stmt.executeUpdate();
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

    public MensajesEntidad modificarDispositivo(DispositivoEntidad dentidad) {
        MensajesEntidad me = new MensajesEntidad();
        ResultSet resultado = null;
        try {
            properties.load(getClass().getResourceAsStream(pathresource));
            String db = properties.getProperty("nombreDataBase");
            //declaro la sentencia SQL que quiero ejecutar

            stmt = conexion.prepareStatement("SELECT * FROM dispositivo \n"
                    + " WHERE nombredispositivo = ? and idregistro <> ?;",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            stmt.setString(1, dentidad.getNombredispositivo());
            stmt.setInt(2, dentidad.getIdregistro());
            resultado = stmt.executeQuery();
            int cantidadresultados = 0;
            while (resultado.next()) {
                cantidadresultados++;
            }

            if (cantidadresultados == 0) {
                stmt = conexion.prepareStatement("UPDATE [dbo].[dispositivo]\n"
                        + "     SET [nombredispositivo] = ?\n"
                        + "        ,[estadodispositivo] = ?\n"
                        + "        ,[disponibledispositivo] = ?\n"
                        + "        ,[codigoactivo] = ?\n"
                        + " WHERE [idregistro] = ?",
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                stmt.setString(1, dentidad.getNombredispositivo());
                stmt.setBoolean(2, dentidad.isEstadodispositivo());
                stmt.setBoolean(3, dentidad.isDisponibledispositivo());
                stmt.setString(4, dentidad.getCodigoactivo());
                stmt.setInt(5, dentidad.getIdregistro());                
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

    public MensajesEntidad eliminarDispositivo(DispositivoEntidad dentidad) {
        MensajesEntidad me = new MensajesEntidad();
        ResultSet resultado = null;
        try {
            properties.load(getClass().getResourceAsStream(pathresource));
            String db = properties.getProperty("nombreDataBase");
            //declaro la sentencia SQL que quiero ejecutar

            stmt = conexion.prepareStatement("DELETE  FROM dispositivo \n"
                    + " WHERE idregistro = ?;",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            stmt.setInt(1, dentidad.getIdregistro());
            stmt.executeUpdate();

            stmt = conexion.prepareStatement("SELECT * FROM [dbo].[dispositivo]\n"
                    + " WHERE [idregistro] = ?",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            stmt.setInt(1, dentidad.getIdregistro());
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

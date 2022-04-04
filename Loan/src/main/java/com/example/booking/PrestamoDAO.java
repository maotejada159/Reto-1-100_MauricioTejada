/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.booking;

import entidades.MensajesEntidad;
import entidades.PrestamoEntidad;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.faces.application.FacesMessage;


public class PrestamoDAO extends ConexionSQLServer {

    private final Properties properties;
    private final String pathresource = "../../../configDB.properties";

    public PrestamoDAO() {
        this.properties = new Properties();
    }

    public List<PrestamoEntidad> consultarListaCompletaPrestamos() {
        List<PrestamoEntidad> salida = new ArrayList<>();
        ResultSet resultado = null;
        try {
            properties.load(getClass().getResourceAsStream(pathresource));
            String db = properties.getProperty("nombreDataBase");
            //declaro la sentencia SQL que quiero ejecutar

            stmt = conexion.prepareStatement("SELECT prestamo.[idregistro]\n"
                    + "      ,prestamo.[datetimeprestamo]\n"
                    + "      ,prestamo.[nombrecompletoprestamo]\n"
                    + "      ,prestamo.[dependencia]\n"
                    + "      ,prestamo.[dispositivo]\n"
                    + "      ,prestamo.[marca]\n"
                    + "      ,prestamo.[firmaprestamo]\n"
                    + "      ,prestamo.[firmadevolucion]\n"
                    + "      ,prestamo.[nombrecompletodevolucion]\n"
                    + "      ,prestamo.[datetimedevolucion]\n"
                    + "      ,prestamo.[idusuariorecibe]\n"
                    + "      ,prestamo.[anulado]\n"
                    + "      ,prestamo.[observacionesanulado]\n"
                    + "      ,prestamo.[observacionesdevolucion]\n"
                    + "      ,prestamo.[datetimeanulado]\n"
                    + "      ,prestamo.[idusuarioanula]\n"
                    + "	  ,usuario.[username]\n"
                    + "	  ,usuario.[nombrecompletousuario]\n"
                    + "	  ,usuario2.[username] as 'username2' \n"
                    + "	  ,usuario2.[nombrecompletousuario] as 'nombrecompletousuario2' \n"
                    + "      ,prestamo.[estadodevolucion]\n"
                    + "  FROM [Loan].[dbo].[prestamo] "
                    + "LEFT JOIN usuario ON usuario.idusuario = prestamo.idusuariorecibe \n"
                    + "LEFT JOIN usuario usuario2 ON usuario2.idusuario = prestamo.idusuarioanula \n"
                    + "  order by prestamo.idregistro asc;",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            resultado = stmt.executeQuery();

            while (resultado.next()) {
                PrestamoEntidad pentidad = new PrestamoEntidad();
                pentidad.setIdregistro(resultado.getInt("idregistro"));
                pentidad.setNombrecompletoprestamo(resultado.getString("nombrecompletoprestamo"));
                pentidad.setDatetimeprestamo(resultado.getTimestamp("datetimeprestamo"));
                pentidad.setDependencia(resultado.getString("dependencia"));
                pentidad.setDispositivo(resultado.getString("dispositivo"));
                pentidad.setMarca(resultado.getString("marca"));
                pentidad.setFirmaprestamo(resultado.getString("firmaprestamo"));
                pentidad.setFirmadevolucion(resultado.getString("firmadevolucion"));
                pentidad.setNombrecompletodevolucion(resultado.getString("nombrecompletodevolucion"));
                pentidad.setDatetimedevolucion(resultado.getTimestamp("datetimedevolucion"));
                pentidad.setIdusuariorecibe(resultado.getInt("idusuariorecibe"));
                pentidad.setUsername(resultado.getString("username"));
                pentidad.setNombrecompletousuario(resultado.getString("nombrecompletousuario"));
                pentidad.setEstadodevolucion(resultado.getBoolean("estadodevolucion"));
                pentidad.setAnulado(resultado.getBoolean("anulado"));
                pentidad.setDatetimeanulado(resultado.getTimestamp("datetimeanulado"));
                pentidad.setObservacionesdevolucion(resultado.getString("observacionesdevolucion"));
                pentidad.setObservacionesanulado(resultado.getString("observacionesanulado"));
                pentidad.setIdusuarioanula(resultado.getInt("idusuarioanula"));
                pentidad.setUsernameanula(resultado.getString("username2"));
                pentidad.setNombrecompletousuarioanula(resultado.getString("nombrecompletousuario2"));
                salida.add(pentidad);
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

    public List<PrestamoEntidad> consultarListaPrestamosActivos() {
        List<PrestamoEntidad> salida = new ArrayList<>();
        ResultSet resultado = null;
        try {
            properties.load(getClass().getResourceAsStream(pathresource));
            String db = properties.getProperty("nombreDataBase");
            //declaro la sentencia SQL que quiero ejecutar

            stmt = conexion.prepareStatement("SELECT * FROM prestamo "
                    + " where estadodevolucion = ? AND anulado = ? "
                    + " order by dispositivo asc;",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            stmt.setBoolean(1, false);
            stmt.setBoolean(2, false);
            resultado = stmt.executeQuery();

            while (resultado.next()) {
                PrestamoEntidad pentidad = new PrestamoEntidad();
                pentidad.setIdregistro(resultado.getInt("idregistro"));
                pentidad.setNombrecompletoprestamo(resultado.getString("nombrecompletoprestamo"));
                pentidad.setDatetimeprestamo(resultado.getTimestamp("datetimeprestamo"));
                pentidad.setDependencia(resultado.getString("dependencia"));
                pentidad.setDispositivo(resultado.getString("dispositivo"));
                pentidad.setMarca(resultado.getString("marca"));
                pentidad.setFirmaprestamo(resultado.getString("firmaprestamo"));

                pentidad.setEstadodevolucion(resultado.getBoolean("estadodevolucion"));
                salida.add(pentidad);
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
    Metodo que realiza el registro de un nuevo prestamo
     */
    public MensajesEntidad insertarNuevoPrestamo(PrestamoEntidad prestamoentidad) {
        MensajesEntidad me = new MensajesEntidad();
        ResultSet resultado = null;
        try {
            properties.load(getClass().getResourceAsStream(pathresource));
            String db = properties.getProperty("nombreDataBase");
            //declaro la sentencia SQL que quiero ejecutar        
            stmt = conexion.prepareStatement("SELECT MAX(idregistro) AS idregistro FROM prestamo;",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultado = null;
            resultado = stmt.executeQuery();
            int id = 0;
            while (resultado.next()) {
                id = resultado.getInt("idregistro");
                id++;
            }

            if (id > 0) {

                stmt = conexion.prepareStatement("USE " + db + "; "
                        + "Begin transaction \n"
                        + "Begin Try \n"
                        + "INSERT INTO [dbo].[prestamo]\n"
                        + "           ([idregistro]\n"
                        + "           ,[datetimeprestamo]\n"
                        + "           ,[nombrecompletoprestamo]\n"
                        + "           ,[dependencia]\n"
                        + "           ,[dispositivo]\n"
                        + "           ,[marca]\n"
                        + "           ,[firmaprestamo]\n"
                        + "           ,[estadodevolucion]"
                        + "           ,[anulado]"
                        + ")\n"
                        + "     VALUES\n"
                        + "           (?\n"
                        + "           ,?\n"
                        + "           ,?\n"
                        + "           ,?\n"
                        + "           ,?\n"
                        + "           ,?\n"
                        + "           ,?\n"
                        + "           ,?\n"
                        + "           ,?\n"
                        + "		   );"
                        + " UPDATE dispositivo \n"
                        + " SET [disponibledispositivo] = ? "
                        + " WHERE idregistro = ?;"
                        + "Commit transaction;\n"
                        + "End Try\n"
                        + "Begin Catch\n"
                        + "Rollback transaction;\n"
                        + "End Catch",
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                stmt.setInt(1, id);
                stmt.setTimestamp(2, new Timestamp(prestamoentidad.getDatetimeprestamo().getTime()));
                stmt.setString(3, prestamoentidad.getNombrecompletoprestamo());
                stmt.setString(4, prestamoentidad.getDependencia());
                stmt.setString(5, prestamoentidad.getDispositivo());
                stmt.setString(6, prestamoentidad.getMarca());
                stmt.setString(7, prestamoentidad.getFirmaprestamo());
                stmt.setBoolean(8, prestamoentidad.isEstadodevolucion());
                stmt.setBoolean(9, false);
                stmt.setBoolean(10, false);
                stmt.setInt(11, prestamoentidad.getIdregistrodispositivo());
                //asigno en resultado lo devuelto por la BD
                stmt.executeUpdate();
                //recorro resultado para sacar los valores individualmente
                me.setEstado(true);
                me.setMensaje("Se insertó el registro correctamente");
                me.setTitulo("Operación Exitosa");
                me.setSeverity(FacesMessage.SEVERITY_INFO);
                return me;
            } else {
                stmt = conexion.prepareStatement("USE " + db + "; "
                        + "Begin transaction \n"
                        + "Begin Try \n"
                        + "INSERT INTO [dbo].[prestamo]\n"
                        + "           ([idregistro]\n"
                        + "           ,[datetimeprestamo]\n"
                        + "           ,[nombrecompletoprestamo]\n"
                        + "           ,[dependencia]\n"
                        + "           ,[dispositivo]\n"
                        + "           ,[marca]\n"
                        + "           ,[firmaprestamo]\n"
                        + "           ,[estadodevolucion]"
                        + "           ,[anulado]"
                        + ")\n"
                        + "     VALUES\n"
                        + "           (?\n"
                        + "           ,?\n"
                        + "           ,?\n"
                        + "           ,?\n"
                        + "           ,?\n"
                        + "           ,?\n"
                        + "           ,?\n"
                        + "           ,?\n"
                        + "           ,?\n"
                        + "		   );"
                        + " UPDATE dispositivo \n"
                        + " SET [disponibledispositivo] = ? "
                        + " WHERE idregistro = ?;"
                        + "Commit transaction;\n"
                        + "End Try\n"
                        + "Begin Catch\n"
                        + "Rollback transaction;\n"
                        + "End Catch",
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                stmt.setInt(1, 1);
                stmt.setTimestamp(2, new Timestamp(prestamoentidad.getDatetimeprestamo().getTime()));
                stmt.setString(3, prestamoentidad.getNombrecompletoprestamo());
                stmt.setString(4, prestamoentidad.getDependencia());
                stmt.setString(5, prestamoentidad.getDispositivo());
                stmt.setString(6, prestamoentidad.getMarca());
                stmt.setString(7, prestamoentidad.getFirmaprestamo());
                stmt.setBoolean(8, prestamoentidad.isEstadodevolucion());
                stmt.setBoolean(9, false);
                stmt.setBoolean(10, false);
                stmt.setInt(11, prestamoentidad.getIdregistrodispositivo());
                //asigno en resultado lo devuelto por la BD
                stmt.executeUpdate();

                me.setEstado(true);
                me.setMensaje("Se insertó el registro correctamente");
                me.setTitulo("Operación Exitosa");
                me.setSeverity(FacesMessage.SEVERITY_INFO);
                return me;
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

    public MensajesEntidad registrarDevolucion(PrestamoEntidad pentidad) {
        MensajesEntidad me = new MensajesEntidad();
        ResultSet resultado = null;
        try {
            properties.load(getClass().getResourceAsStream(pathresource));
            String db = properties.getProperty("nombreDataBase");
            //declaro la sentencia SQL que quiero ejecutar

            stmt = conexion.prepareStatement(""
                    + "Begin transaction \n"
                    + "Begin Try \n"
                    + "UPDATE [dbo].[prestamo]\n"
                    + "   SET \n"
                    + "       [firmadevolucion] = ?\n"
                    + "      ,[nombrecompletodevolucion] = ?\n"
                    + "      ,[datetimedevolucion] = ?\n"
                    + "      ,[idusuariorecibe] = ?\n"
                    + "      ,[estadodevolucion] = ?\n"
                    + "      ,[observacionesdevolucion] = ?\n"
                    + " WHERE [idregistro] = ?;\n"
                    + " "
                    + " UPDATE dispositivo \n"
                    + " SET [disponibledispositivo] = ? "
                    + " WHERE idregistro = ?;"
                    + "Commit transaction;\n"
                    + "End Try\n"
                    + "Begin Catch\n"
                    + "Rollback transaction;\n"
                    + "End Catch",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setString(1, pentidad.getFirmadevolucion());
            stmt.setString(2, pentidad.getNombrecompletodevolucion());
            stmt.setTimestamp(3, new Timestamp(pentidad.getDatetimedevolucion().getTime()));
            stmt.setInt(4, pentidad.getIdusuariorecibe());
            stmt.setBoolean(5, pentidad.isEstadodevolucion());
            stmt.setString(6, pentidad.getObservacionesdevolucion());
            stmt.setInt(7, pentidad.getIdregistro());
            stmt.setBoolean(8, true);
            stmt.setInt(9, pentidad.getIdregistrodispositivo());

            stmt.executeUpdate();

            //consulta para validar correcta actualización
            stmt = conexion.prepareStatement("SELECT idregistro FROM prestamo \n"
                    + " WHERE [idregistro] = ? and [estadodevolucion] = ?",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, pentidad.getIdregistro());
            stmt.setBoolean(2, pentidad.isEstadodevolucion());
            resultado = stmt.executeQuery();
            int numeroresultados = 0;
            while (resultado.next()) {
                numeroresultados++;
            }
            if (numeroresultados > 0) {
                me.setEstado(true);
                me.setMensaje("Se actualizó el registro correctamente");
                me.setTitulo("Operación Exitosa");
                me.setSeverity(FacesMessage.SEVERITY_INFO);
            } else {
                me.setEstado(false);
                me.setMensaje("No se actualizó");
                me.setTitulo("Sin Éxito");
                me.setSeverity(FacesMessage.SEVERITY_INFO);
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
    
    public MensajesEntidad anularPrestamo(PrestamoEntidad pentidad) {
        MensajesEntidad me = new MensajesEntidad();
        ResultSet resultado = null;
        try {
            properties.load(getClass().getResourceAsStream(pathresource));
            String db = properties.getProperty("nombreDataBase");
            //declaro la sentencia SQL que quiero ejecutar

            stmt = conexion.prepareStatement(""
                    + "Begin transaction \n"
                    + "Begin Try \n"
                    + "UPDATE [dbo].[prestamo]\n"
                    + "   SET \n"
                    + "       [anulado] = ?\n"
                    + "      ,[datetimeanulado] = ?\n"
                    + "      ,[observacionesanulado] = ?\n"
                    + "      ,[idusuarioanula] = ?\n"                   
                    + " WHERE [idregistro] = ?;\n"
                    + " "
                    + " UPDATE dispositivo \n"
                    + " SET [disponibledispositivo] = ? "
                    + " WHERE idregistro = ?;"
                    + "Commit transaction;\n"
                    + "End Try\n"
                    + "Begin Catch\n"
                    + "Rollback transaction;\n"
                    + "End Catch",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setBoolean(1, pentidad.isAnulado());            
            stmt.setTimestamp(2, new Timestamp(pentidad.getDatetimeanulado().getTime()));
            stmt.setString(3, pentidad.getObservacionesanulado());
            stmt.setInt(4, pentidad.getIdusuarioanula());
            stmt.setInt(5, pentidad.getIdregistro());
            stmt.setBoolean(6, true);
            stmt.setInt(7, pentidad.getIdregistrodispositivo());

            stmt.executeUpdate();

            //consulta para validar correcta actualización
            stmt = conexion.prepareStatement("SELECT idregistro FROM prestamo \n"
                    + " WHERE [idregistro] = ? and [anulado] = ?",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, pentidad.getIdregistro());
            stmt.setBoolean(2, pentidad.isAnulado());
            resultado = stmt.executeQuery();
            int numeroresultados = 0;
            while (resultado.next()) {
                numeroresultados++;
            }
            if (numeroresultados > 0) {
                me.setEstado(true);
                me.setMensaje("Se actualizó el registro correctamente");
                me.setTitulo("Operación Exitosa");
                me.setSeverity(FacesMessage.SEVERITY_INFO);
            } else {
                me.setEstado(false);
                me.setMensaje("No se actualizó");
                me.setTitulo("Sin Éxito");
                me.setSeverity(FacesMessage.SEVERITY_INFO);
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

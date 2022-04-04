/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.booking;

import entidades.MensajesEntidad;
import entidades.UsuarioEntidad;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.faces.application.FacesMessage;


public class UsuariosDAO extends ConexionSQLServer {

    private final Properties properties;
    private final String pathresource = "../../../configDB.properties";

    public UsuariosDAO() {
        this.properties = new Properties();
    }

    public List<UsuarioEntidad> consultarListaCompletaUsuarios() {
        List<UsuarioEntidad> salida = new ArrayList<>();
        ResultSet resultado = null;
        try {
            properties.load(getClass().getResourceAsStream(pathresource));
            String db = properties.getProperty("nombreDataBase");
            //declaro la sentencia SQL que quiero ejecutar

            stmt = conexion.prepareStatement("SELECT * FROM usuario order by idusuario asc;",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            resultado = stmt.executeQuery();

            while (resultado.next()) {
                UsuarioEntidad uentidad = new UsuarioEntidad();
                uentidad.setIdusuario(resultado.getInt("idusuario"));
                uentidad.setUsername(resultado.getString("username"));
                uentidad.setNombrecompletousuario(resultado.getString("nombrecompletousuario"));
                uentidad.setCorreousuario(resultado.getString("correousuario"));
                uentidad.setDatetimecreacionusuario(resultado.getDate("datetimecreacionusuario"));
                uentidad.setEstadousuario(resultado.getBoolean("estadousuario"));
                salida.add(uentidad);
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
    
    public MensajesEntidad consultarUsuario(String username) {
        MensajesEntidad salida = new MensajesEntidad();
        ResultSet resultado = null;
        try {
            properties.load(getClass().getResourceAsStream(pathresource));
            String db = properties.getProperty("nombreDataBase");
            //declaro la sentencia SQL que quiero ejecutar

            stmt = conexion.prepareStatement("SELECT * FROM usuario where username = ?;",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            stmt.setString(1, username);
            resultado = stmt.executeQuery();

            while (resultado.next()) {
                UsuarioEntidad ue = new UsuarioEntidad();
                ue.setIdusuario(resultado.getInt("idusuario"));
                ue.setUsername(resultado.getString("username"));
                ue.setNombrecompletousuario(resultado.getString("nombrecompletousuario"));
                ue.setCorreousuario(resultado.getString("correousuario"));
                ue.setDatetimecreacionusuario(resultado.getDate("datetimecreacionusuario"));
                ue.setEstadousuario(resultado.getBoolean("estadousuario"));
                salida.setUsuarioentidad(ue);
            }
            salida.setMensaje("Login correcto");
            salida.setTitulo("Bienvenido");
            salida.setSeverity(FacesMessage.SEVERITY_INFO);
            salida.setEstado(true);

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

    public List<UsuarioEntidad> consultarListaUsuariosActivos() {
        List<UsuarioEntidad> salida = new ArrayList<>();
        ResultSet resultado = null;
        try {
            properties.load(getClass().getResourceAsStream(pathresource));
            String db = properties.getProperty("nombreDataBase");
            //declaro la sentencia SQL que quiero ejecutar

            stmt = conexion.prepareStatement("SELECT * FROM usuario where estadousuario = ? "
                    + " order by username asc;",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            stmt.setBoolean(1, true);
            resultado = stmt.executeQuery();

            while (resultado.next()) {
                UsuarioEntidad uentidad = new UsuarioEntidad();
                uentidad.setIdusuario(resultado.getInt("idusuario"));
                uentidad.setUsername(resultado.getString("username"));
                uentidad.setNombrecompletousuario(resultado.getString("nombrecompletousuario"));
                uentidad.setCorreousuario(resultado.getString("correousuario"));
                uentidad.setDatetimecreacionusuario(resultado.getDate("datetimecreacionusuario"));
                uentidad.setEstadousuario(resultado.getBoolean("estadousuario"));
                salida.add(uentidad);
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
    Metodo que realiza el registro de un nuevo usuario
     */
    public MensajesEntidad insertarNuevoUsuario(UsuarioEntidad uentidad) {
        MensajesEntidad me = new MensajesEntidad();
        ResultSet resultado = null;
        try {
            properties.load(getClass().getResourceAsStream(pathresource));
            String db = properties.getProperty("nombreDataBase");
            //declaro la sentencia SQL que quiero ejecutar

            stmt = conexion.prepareStatement("SELECT * FROM usuario \n"
                    + " WHERE username = ?;",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            stmt.setString(1, uentidad.getUsername());
            resultado = stmt.executeQuery();
            int cantidadresultados = 0;
            while (resultado.next()) {
                cantidadresultados++;
            }

            if (cantidadresultados == 0) {

                stmt = conexion.prepareStatement("SELECT MAX(idusuario) AS idusuario FROM usuario;",
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                resultado = null;
                resultado = stmt.executeQuery();
                int id = 0;
                while (resultado.next()) {
                    id = resultado.getInt("idusuario");
                    id++;
                }

                if (id > 0) {

                    stmt = conexion.prepareStatement("USE " + db + "; INSERT INTO [dbo].[usuario]\n"
                            + "           ([idusuario]\n"
                            + "           ,[username]\n"
                            + "           ,[nombrecompletousuario]\n"
                            + "           ,[correousuario]\n"
                            + "           ,[claveusuario]\n"
                            + "           ,[datetimecreacionusuario]\n"
                            + "           ,[estadousuario])\n"
                            + "     VALUES\n"
                            + "           (?\n"
                            + "           ,?\n"
                            + "           ,?\n"
                            + "           ,?\n"
                            + "           ,?\n"
                            + "           ,?\n"
                            + "           ,?\n"
                            + "		   )",
                            ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    stmt.setInt(1, id);
                    stmt.setString(2, uentidad.getUsername());
                    stmt.setString(3, uentidad.getNombrecompletousuario());
                    stmt.setString(4, uentidad.getCorreousuario());
                    stmt.setString(5, uentidad.getClaveusuario());
                    stmt.setTimestamp(6, new Timestamp(uentidad.getDatetimecreacionusuario().getTime()));
                    stmt.setBoolean(7, uentidad.isEstadousuario());
                    //asigno en resultado lo devuelto por la BD
                    stmt.executeUpdate();
                    //recorro resultado para sacar los valores individualmente
                    me.setEstado(true);
                    me.setMensaje("Se insertó el registro correctamente");
                    me.setTitulo("Operación Exitosa");
                    me.setSeverity(FacesMessage.SEVERITY_INFO);
                } else {
                    stmt = conexion.prepareStatement("USE " + db + "; INSERT INTO [dbo].[usuario]\n"
                            + "           ([idusuario]\n"
                            + "           ,[username]\n"
                            + "           ,[nombrecompletousuario]\n"
                            + "           ,[correousuario]\n"
                            + "           ,[claveusuario]\n"
                            + "           ,[datetimecreacionusuario]\n"
                            + "           ,[estadousuario])\n"
                            + "     VALUES\n"
                            + "           (?\n"
                            + "           ,?\n"
                            + "           ,?\n"
                            + "           ,?\n"
                            + "           ,?\n"
                            + "           ,?\n"
                            + "           ,?\n"
                            + "		   )",
                            ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    stmt.setInt(1, 1);
                    stmt.setString(2, uentidad.getUsername());
                    stmt.setString(3, uentidad.getNombrecompletousuario());
                    stmt.setString(4, uentidad.getCorreousuario());
                    stmt.setString(5, uentidad.getClaveusuario());
                    stmt.setTimestamp(6, new Timestamp(uentidad.getDatetimecreacionusuario().getTime()));
                    stmt.setBoolean(7, uentidad.isEstadousuario());
                    //asigno en resultado lo devuelto por la BD
                    stmt.executeUpdate();
                    me.setEstado(true);
                    me.setMensaje("Se insertó el registro correctamente");
                    me.setTitulo("Operación Exitosa");
                    me.setSeverity(FacesMessage.SEVERITY_INFO);
                }
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

    public MensajesEntidad modificarUsuario(UsuarioEntidad uentidad) {
        MensajesEntidad me = new MensajesEntidad();
        ResultSet resultado = null;
        try {
            properties.load(getClass().getResourceAsStream(pathresource));
            String db = properties.getProperty("nombreDataBase");
            //declaro la sentencia SQL que quiero ejecutar

            stmt = conexion.prepareStatement("SELECT * FROM usuario \n"
                    + " WHERE username = ? and idusuario <> ?;",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            stmt.setString(1, uentidad.getUsername());
            stmt.setInt(2, uentidad.getIdusuario());
            resultado = stmt.executeQuery();
            int cantidadresultados = 0;
            while (resultado.next()) {
                cantidadresultados++;
            }

            if (cantidadresultados == 0) {
                stmt = conexion.prepareStatement("UPDATE [dbo].[usuario]\n"
                        + "     SET [nombrecompletousuario] = ?\n"
                        + "        ,[correousuario] = ?\n"
                        + "        ,[estadousuario] = ?\n"
                        + " WHERE [idusuario] = ?",
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                stmt.setString(1, uentidad.getNombrecompletousuario());
                stmt.setString(2, uentidad.getCorreousuario());
                stmt.setBoolean(3, uentidad.isEstadousuario());
                stmt.setInt(4, uentidad.getIdusuario());
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

    public MensajesEntidad cambiarClaveUsuario(UsuarioEntidad uentidad) {
        MensajesEntidad me = new MensajesEntidad();
        ResultSet resultado = null;
        try {
            properties.load(getClass().getResourceAsStream(pathresource));
            String db = properties.getProperty("nombreDataBase");
            //declaro la sentencia SQL que quiero ejecutar

            stmt = conexion.prepareStatement("UPDATE usuario "
                    + " SET claveusuario = ? \n"
                    + " WHERE idusuario = ?;",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            stmt.setString(1, uentidad.getClaveusuario());
            //System.out.println("clave usuario en update: "+uentidad.getClaveusuario());
            stmt.setInt(2, uentidad.getIdusuario());
            //System.out.println("idusuario en update: "+uentidad.getIdusuario());
            stmt.executeUpdate();

            stmt = conexion.prepareStatement("SELECT * FROM [dbo].[usuario]\n"
                    + " WHERE [claveusuario] = ? AND [idusuario] = ?;",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            stmt.setString(1, uentidad.getClaveusuario());
            //System.out.println("clave usuario en select: "+uentidad.getClaveusuario());
            stmt.setInt(2, uentidad.getIdusuario());
            //System.out.println("idusuario en select: "+uentidad.getIdusuario());
            //asigno en resultado lo devuelto por la BD
            resultado = null;
            resultado = stmt.executeQuery();
            //recorro resultado para sacar los valores individualmente
            int resultadoconsulta = 0;
            while (resultado.next()) {
                resultadoconsulta++;
            }

            if (resultadoconsulta > 0) {
                me.setEstado(true);
                me.setMensaje("Se actualizó la clave correctamente");
                me.setTitulo("Operación Exitosa");
                me.setSeverity(FacesMessage.SEVERITY_INFO);
            } else {
                me.setEstado(false);
                me.setMensaje("No fue posible actualizar la clave");
                me.setTitulo("No se actualizó");
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

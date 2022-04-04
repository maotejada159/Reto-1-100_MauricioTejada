/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.booking;

import clasesgenerales.Security;
import clasesgenerales.ValidacionEntradas;
import entidades.MensajesEntidad;
import entidades.UsuarioEntidad;
import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;


@Named(value = "usuariosBean")
@ViewScoped
@ManagedBean
public class UsuariosBean implements Serializable {

    private UsuarioEntidad uentidad = new UsuarioEntidad();
    private UsuarioEntidad uentidadeditar = new UsuarioEntidad();
    private UsuarioEntidad uentidadcambioclave = new UsuarioEntidad();
    private List<UsuarioEntidad> listausuarios = new ArrayList<>();
    private List<UsuarioEntidad> listausuariossactivos = new ArrayList<>();
    private boolean paneleditar = false;
    private boolean panelcambioclave = false;
    private FacesContext facescontext;

    /**
     * Creates a new instance of DispositivoBean
     */
    public UsuariosBean() {
    }

    @PostConstruct
    public void init() {
        cargarListaCompletaUsuarios();
        cargarListaUsuariosActivos();

    }

    public void cargarListaCompletaUsuarios() {
        UsuariosDAO udao = new UsuariosDAO();
        facescontext = FacesContext.getCurrentInstance();
        listausuarios = udao.consultarListaCompletaUsuarios();
        PrimeFaces.current().executeScript("evitarRedirect();");
    }

    public void cargarListaUsuariosActivos() {
        UsuariosDAO udao = new UsuariosDAO();
        facescontext = FacesContext.getCurrentInstance();
        listausuariossactivos = udao.consultarListaUsuariosActivos();
        PrimeFaces.current().executeScript("evitarRedirect();");
    }

    public void insertarNuevoUsuario() {
        try {
            UsuariosDAO mdao = new UsuariosDAO();
            MensajesBean mbean = new MensajesBean();
            MensajesEntidad me = new MensajesEntidad();
            facescontext = FacesContext.getCurrentInstance();
            uentidad.setUsername(uentidad.getUsername().trim());
            uentidad.setUsername(uentidad.getUsername().toLowerCase());

            uentidad.setNombrecompletousuario(uentidad.getNombrecompletousuario().trim());
            uentidad.setNombrecompletousuario(uentidad.getNombrecompletousuario().toUpperCase());

            uentidad.setCorreousuario(uentidad.getCorreousuario().trim());
            uentidad.setCorreousuario(uentidad.getCorreousuario().toLowerCase());

            uentidad.setDatetimecreacionusuario(new java.util.Date());

            ValidacionEntradas ventradas = new ValidacionEntradas();
            Security sec = new Security();
            uentidad.setClaveusuario(sec.setSHA512(uentidad.getClaveusuario()));

            boolean valusername = ventradas.caracteresEspeciales(uentidad.getUsername());
            boolean longusername = ventradas.longitudCadena(uentidad.getUsername(), 1, 200);
            boolean valnombre = ventradas.caracteresEspeciales(uentidad.getNombrecompletousuario());
            boolean longnombre = ventradas.longitudCadena(uentidad.getNombrecompletousuario(), 1, 200);
            boolean valcorreo = ventradas.caracteresEspeciales(uentidad.getCorreousuario());
            boolean longcorreo = ventradas.longitudCadena(uentidad.getCorreousuario(), 1, 200);

            if (longusername && valusername
                    && valnombre && longnombre
                    && valcorreo && longcorreo) {

                me = mdao.insertarNuevoUsuario(uentidad);

            } else {
                me.setSeverity(FacesMessage.SEVERITY_WARN);
                me.setTitulo("Error - Validación");
                me.setMensaje("Los valores ingresados son inválidos");
            }

            if (me.isEstado()) {
                limpiarFormulario();

            }
            cargarListaCompletaUsuarios();
            PrimeFaces.current().executeScript("evitarRedirect();");
            mbean.mostrarMensaje(me);
        } catch (IOException ex) {
            Logger.getLogger(MarcaBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UsuariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarUsuario() {
        try {
            MensajesEntidad me = new MensajesEntidad();
            MensajesBean mbean = new MensajesBean();
            UsuariosDAO udao = new UsuariosDAO();
            //System.out.println("idempresa: "+idempresa);
            //System.out.println("nombreempresa: "+nombreempresa);
            //System.out.println("Nit Empresa: "+nitempresa);
            facescontext = FacesContext.getCurrentInstance();
            uentidadeditar.setNombrecompletousuario(uentidadeditar.getNombrecompletousuario().trim());
            uentidadeditar.setNombrecompletousuario(uentidadeditar.getNombrecompletousuario().toUpperCase());

            uentidadeditar.setCorreousuario(uentidadeditar.getCorreousuario().trim());
            uentidadeditar.setCorreousuario(uentidadeditar.getCorreousuario().toLowerCase());

            ValidacionEntradas ventradas = new ValidacionEntradas();

            boolean valnombre = ventradas.caracteresEspeciales(uentidadeditar.getNombrecompletousuario());
            boolean longnombre = ventradas.longitudCadena(uentidadeditar.getNombrecompletousuario(), 1, 200);
            boolean valcorreo = ventradas.caracteresEspeciales(uentidadeditar.getCorreousuario());
            boolean longcorreo = ventradas.longitudCadena(uentidadeditar.getCorreousuario(), 1, 200);

            if (valnombre && longnombre
                    && valcorreo && longcorreo) {

                me = udao.modificarUsuario(uentidadeditar);

            } else {
                me.setEstado(false);
                me.setTitulo("Error Validación");
                me.setSeverity(FacesMessage.SEVERITY_ERROR);
                me.setMensaje("Los valores ingresados no son validos");
            }

            if (me.isEstado()) {
                ocultarVentanaEditar();
                limpiarFormulario();
            } else {
                cargarVentanaEditar(uentidadeditar);
            }

            cargarListaCompletaUsuarios();
            PrimeFaces.current().executeScript("evitarRedirect();");
            mbean.mostrarMensaje(me);
        } catch (IOException ex) {
            Logger.getLogger(UsuariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cambiarClaveUsuario() {
        MensajesEntidad me = new MensajesEntidad();
        MensajesBean mbean = new MensajesBean();
        UsuariosDAO udao = new UsuariosDAO();

        Security sec = new Security();
        try {
            uentidadcambioclave.setClaveusuario(sec.setSHA512(uentidadcambioclave.getClaveusuario()));
            me = udao.cambiarClaveUsuario(uentidadcambioclave);
        } catch (NoSuchAlgorithmException ex) {
            
            me.setTitulo("Excepción en seguridad");
            me.setMensaje("No se pudo convertir la clave");
            me.setSeverity(FacesMessage.SEVERITY_FATAL);
        }

        limpiarFormulario();
        cargarListaCompletaUsuarios();
        PrimeFaces.current().executeScript("evitarRedirect();");
        mbean.mostrarMensaje(me);
    }

    public void limpiarFormulario() {
        uentidad = new UsuarioEntidad();
        uentidadeditar = new UsuarioEntidad();
        uentidadcambioclave = new UsuarioEntidad();
        paneleditar = false;
        panelcambioclave = false;
        PrimeFaces.current().executeScript("evitarRedirect();");
    }

    public void cargarVentanaEditar(UsuarioEntidad depentidad) {
        //System.out.println("En cargarventada editar: " + mentidad.getIdregistro());
        uentidadeditar = depentidad;
        paneleditar = true;
        panelcambioclave = false;
        PrimeFaces.current().executeScript("evitarRedirect();");

    }

    public void ocultarVentanaEditar() {
        limpiarFormulario();
        PrimeFaces.current().executeScript("evitarRedirect();");

    }

    /*
    MEtodo invocado desde el botón cambiar clave de la tabla usuarios
    
     */
    public void cargarVentanaCambiarClave(UsuarioEntidad depentidad) {
        //System.out.println("En cargarventada editar: " + mentidad.getIdregistro());
        uentidadcambioclave = depentidad;
        paneleditar = false;
        panelcambioclave = true;
        PrimeFaces.current().executeScript("evitarRedirect();");

    }

    public void ocultarVentanaCambiarClave() {
        limpiarFormulario();
        PrimeFaces.current().executeScript("evitarRedirect();");

    }

    public UsuarioEntidad getUentidad() {
        return uentidad;
    }

    public void setUentidad(UsuarioEntidad uentidad) {
        this.uentidad = uentidad;
    }

    public UsuarioEntidad getUentidadeditar() {
        return uentidadeditar;
    }

    public void setUentidadeditar(UsuarioEntidad uentidadeditar) {
        this.uentidadeditar = uentidadeditar;
    }

    
    public List<UsuarioEntidad> getListausuarios() {
        return listausuarios;
    }

    public void setListausuarios(List<UsuarioEntidad> listausuarios) {
        this.listausuarios = listausuarios;
    }

    public List<UsuarioEntidad> getListausuariossactivos() {
        return listausuariossactivos;
    }

    public void setListausuariossactivos(List<UsuarioEntidad> listausuariossactivos) {
        this.listausuariossactivos = listausuariossactivos;
    }

    public boolean isPaneleditar() {
        return paneleditar;
    }

    public void setPaneleditar(boolean paneleditar) {
        this.paneleditar = paneleditar;
    }

    public boolean isPanelcambioclave() {
        return panelcambioclave;
    }

    public void setPanelcambioclave(boolean panelcambioclave) {
        this.panelcambioclave = panelcambioclave;
    }

    public UsuarioEntidad getUentidadcambioclave() {
        return uentidadcambioclave;
    }

    public void setUentidadcambioclave(UsuarioEntidad uentidadcambioclave) {
        this.uentidadcambioclave = uentidadcambioclave;
    }

}

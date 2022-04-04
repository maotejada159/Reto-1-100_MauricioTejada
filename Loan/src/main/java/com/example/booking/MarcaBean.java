/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.booking;

import clasesgenerales.ValidacionEntradas;
import entidades.MarcaEntidad;
import entidades.MensajesEntidad;
import java.io.IOException;
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


@Named(value = "marcaBean")
@ViewScoped
@ManagedBean
public class MarcaBean {

    private MarcaEntidad mentidad = new MarcaEntidad();
    private MarcaEntidad mentidadeditar = new MarcaEntidad();
    private MarcaEntidad mentidadeliminar = new MarcaEntidad();
    private List<MarcaEntidad> listaMarca = new ArrayList<>();
    private boolean paneleditar = false;
    private boolean paneleliminar = false;
    private FacesContext facescontext;

    /**
     * Creates a new instance of MarcaBean
     */
    public MarcaBean() {
    }

    @PostConstruct
    public void init() {
        cargarListaMarca();
    }

    public void cargarListaMarca() {
        MarcaDAO mdao = new MarcaDAO();
        facescontext = FacesContext.getCurrentInstance();
        listaMarca = mdao.consultarListaMarcas();
        PrimeFaces.current().executeScript("evitarRedirect();");
    }

    public void insertarNuevaMarca() {
        try {
            MarcaDAO mdao = new MarcaDAO();
            MensajesBean mbean = new MensajesBean();
            MensajesEntidad me = new MensajesEntidad();
            facescontext = FacesContext.getCurrentInstance();
            mentidad.setNombremarca(mentidad.getNombremarca().trim());
            mentidad.setNombremarca(mentidad.getNombremarca().toUpperCase());

            ValidacionEntradas ventradas = new ValidacionEntradas();

            boolean valnombremarca = ventradas.caracteresEspeciales(mentidad.getNombremarca());
            boolean longnombremarca = ventradas.longitudCadena(mentidad.getNombremarca(), 1, 200);

            if (longnombremarca && valnombremarca) {

                me = mdao.insertarNuevaMarca(mentidad);

            } else {
                me.setSeverity(FacesMessage.SEVERITY_WARN);
                me.setTitulo("Error - Validación");
                me.setMensaje("Los valores ingresados son inválidos");
            }

            if (me.isEstado()) {
                limpiarFormulario();

            }
            cargarListaMarca();
            PrimeFaces.current().executeScript("evitarRedirect();");
            mbean.mostrarMensaje(me);
        } catch (IOException ex) {
            Logger.getLogger(MarcaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarMarca() {
        MensajesEntidad me = new MensajesEntidad();
        MensajesBean mbean = new MensajesBean();
        MarcaDAO mdao = new MarcaDAO();
        //System.out.println("idempresa: "+idempresa);
        //System.out.println("nombreempresa: "+nombreempresa);
        //System.out.println("Nit Empresa: "+nitempresa);
        facescontext = FacesContext.getCurrentInstance();
        mentidadeditar.setNombremarca(mentidadeditar.getNombremarca().trim());
        mentidadeditar.setNombremarca(mentidadeditar.getNombremarca().toUpperCase());

        ValidacionEntradas ventradas = new ValidacionEntradas();

        boolean longnombremarca = ventradas.longitudCadena(mentidadeditar.getNombremarca(), 1, 200);

        if (longnombremarca) {

            me = mdao.modificarMarca(mentidadeditar);

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
            cargarVentanaEditar(mentidadeditar);
        }

        cargarListaMarca();
        PrimeFaces.current().executeScript("evitarRedirect();");
        mbean.mostrarMensaje(me);
    }

    public void eliminarMarca() {
        MensajesEntidad me;
        MensajesBean mbean = new MensajesBean();
        MarcaDAO adao = new MarcaDAO();

        me = adao.eliminarMarca(mentidadeliminar);

        limpiarFormulario();
        cargarListaMarca();
        PrimeFaces.current().executeScript("evitarRedirect();");
        PrimeFaces.current().executeScript("PF('DeleteDialog').hide();");
        mbean.mostrarMensaje(me);
    }

    public void limpiarFormulario() {
        mentidad = new MarcaEntidad();
        mentidadeditar = new MarcaEntidad();
        mentidadeliminar = new MarcaEntidad();
        paneleditar = false;
        paneleliminar = false;
        PrimeFaces.current().executeScript("evitarRedirect();");
    }

    public void cargarVentanaEditar(MarcaEntidad mentidad) {
        //System.out.println("En cargarventada editar: " + mentidad.getIdregistro());
        mentidadeditar = mentidad;
        paneleditar = true;
        paneleliminar = false;
        PrimeFaces.current().executeScript("evitarRedirect();");
        

    }

    public void ocultarVentanaEditar() {
        limpiarFormulario();
        PrimeFaces.current().executeScript("evitarRedirect();");
        
    }

    /*
    MEtodo invocado desde el botón eliminar en registro de la tabla
    
     */
    public void cargarVentanaEliminar(MarcaEntidad mentidad) {
        //System.out.println("En cargarventada editar: " + mentidad.getIdregistro());
        mentidadeliminar = mentidad;
        paneleditar = false;
        paneleliminar = true;
        PrimeFaces.current().executeScript("evitarRedirect();");
        

    }

    public void ocultarVentanaEliminar() {
        limpiarFormulario();
        PrimeFaces.current().executeScript("evitarRedirect();");        

    }

    public MarcaEntidad getMentidad() {
        return mentidad;
    }

    public void setMentidad(MarcaEntidad mentidad) {
        this.mentidad = mentidad;
    }

    public List<MarcaEntidad> getListaMarca() {
        return listaMarca;
    }

    public void setListaMarca(List<MarcaEntidad> listaMarca) {
        this.listaMarca = listaMarca;
    }

    public MarcaEntidad getMentidadeditar() {
        return mentidadeditar;
    }

    public void setMentidadeditar(MarcaEntidad mentidadeditar) {
        this.mentidadeditar = mentidadeditar;
    }

    public MarcaEntidad getMentidadeliminar() {
        return mentidadeliminar;
    }

    public void setMentidadeliminar(MarcaEntidad mentidadeliminar) {
        this.mentidadeliminar = mentidadeliminar;
    }

    public boolean isPaneleditar() {
        return paneleditar;
    }

    public void setPaneleditar(boolean paneleditar) {
        this.paneleditar = paneleditar;
    }

    public boolean isPaneleliminar() {
        return paneleliminar;
    }

    public void setPaneleliminar(boolean paneleliminar) {
        this.paneleliminar = paneleliminar;
    }

}

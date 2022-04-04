/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.booking;

import clasesgenerales.ValidacionEntradas;
import entidades.DependenciaEntidad;
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


@Named(value = "dependenciaBean")
@ViewScoped
@ManagedBean
public class DependenciaBean {

    private DependenciaEntidad dentidad = new DependenciaEntidad();
    private DependenciaEntidad dentidadeditar = new DependenciaEntidad();
    private DependenciaEntidad dentidadeliminar = new DependenciaEntidad();
    private List<DependenciaEntidad> listadependencias = new ArrayList<>();
    private List<DependenciaEntidad> listadependenciasactivas = new ArrayList<>();
    private boolean paneleditar = false;
    private boolean paneleliminar = false;
    private FacesContext facescontext;
    
    
    /**
     * Creates a new instance of DependenciaBean
     */
    public DependenciaBean() {
    }
    
    @PostConstruct
    public void init(){
        cargarListaCompletaDependencias();
        cargarListaDependenciasActivas();
    }
    
    public void cargarListaCompletaDependencias(){
        DependenciaDAO mdao = new DependenciaDAO();
        facescontext = FacesContext.getCurrentInstance();
        listadependencias = mdao.consultarListaCompletaDependencias();
        PrimeFaces.current().executeScript("evitarRedirect();");
    }
    
    public void cargarListaDependenciasActivas(){
        DependenciaDAO mdao = new DependenciaDAO();
        facescontext = FacesContext.getCurrentInstance();
        listadependenciasactivas = mdao.consultarListaDependenciasActivas();
        PrimeFaces.current().executeScript("evitarRedirect();");
    }
    
    public void insertarNuevaDependencia() {
        try {
            DependenciaDAO mdao = new DependenciaDAO();
            MensajesBean mbean = new MensajesBean();
            MensajesEntidad me = new MensajesEntidad();
            facescontext = FacesContext.getCurrentInstance();
            dentidad.setNombredependencia(dentidad.getNombredependencia().trim());
            dentidad.setNombredependencia(dentidad.getNombredependencia().toUpperCase());

            ValidacionEntradas ventradas = new ValidacionEntradas();

            boolean valnombredependencia = ventradas.caracteresEspeciales(dentidad.getNombredependencia());
            boolean longnombredependencia = ventradas.longitudCadena(dentidad.getNombredependencia(), 1, 200);

            if (longnombredependencia && valnombredependencia) {

                me = mdao.insertarNuevaDependencia(dentidad);

            } else {
                me.setSeverity(FacesMessage.SEVERITY_WARN);
                me.setTitulo("Error - Validación");
                me.setMensaje("Los valores ingresados son inválidos");
            }

            if (me.isEstado()) {
                limpiarFormulario();

            }
            cargarListaCompletaDependencias();
            PrimeFaces.current().executeScript("evitarRedirect();");
            mbean.mostrarMensaje(me);
        } catch (IOException ex) {
            Logger.getLogger(MarcaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarDependencia() {
        MensajesEntidad me = new MensajesEntidad();
        MensajesBean mbean = new MensajesBean();
        DependenciaDAO mdao = new DependenciaDAO();
        //System.out.println("idempresa: "+idempresa);
        //System.out.println("nombreempresa: "+nombreempresa);
        //System.out.println("Nit Empresa: "+nitempresa);
        facescontext = FacesContext.getCurrentInstance();
        dentidadeditar.setNombredependencia(dentidadeditar.getNombredependencia().trim());
        dentidadeditar.setNombredependencia(dentidadeditar.getNombredependencia().toUpperCase());

        ValidacionEntradas ventradas = new ValidacionEntradas();

        boolean longnombremarca = ventradas.longitudCadena(dentidadeditar.getNombredependencia(), 1, 100);

        if (longnombremarca) {

            me = mdao.modificarDependencia(dentidadeditar);

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
            cargarVentanaEditar(dentidadeditar);
        }

        cargarListaCompletaDependencias();
        PrimeFaces.current().executeScript("evitarRedirect();");
        mbean.mostrarMensaje(me);
    }

    public void eliminarDependencia() {
        MensajesEntidad me;
        MensajesBean mbean = new MensajesBean();
        DependenciaDAO adao = new DependenciaDAO();

        me = adao.eliminarDependencia(dentidadeliminar);

        limpiarFormulario();
        cargarListaCompletaDependencias();
        PrimeFaces.current().executeScript("evitarRedirect();");
        mbean.mostrarMensaje(me);
    }

    public void limpiarFormulario() {
        dentidad = new DependenciaEntidad();
        dentidadeditar = new DependenciaEntidad();
        dentidadeliminar = new DependenciaEntidad();
        paneleditar = false;
        paneleliminar = false;
        PrimeFaces.current().executeScript("evitarRedirect();");
    }

    public void cargarVentanaEditar(DependenciaEntidad depentidad) {
        //System.out.println("En cargarventada editar: " + mentidad.getIdregistro());
        dentidadeditar = depentidad;
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
    public void cargarVentanaEliminar(DependenciaEntidad depentidad) {
        //System.out.println("En cargarventada editar: " + mentidad.getIdregistro());
        dentidadeliminar = depentidad;
        paneleditar = false;
        paneleliminar = true;
        PrimeFaces.current().executeScript("evitarRedirect();");
        

    }

    public void ocultarVentanaEliminar() {
        limpiarFormulario();
        PrimeFaces.current().executeScript("evitarRedirect();");        

    }
    

    public DependenciaEntidad getDentidad() {
        return dentidad;
    }

    public void setDentidad(DependenciaEntidad dentidad) {
        this.dentidad = dentidad;
    }

    public List<DependenciaEntidad> getListadependencias() {
        return listadependencias;
    }

    public void setListadependencias(List<DependenciaEntidad> listadependencias) {
        this.listadependencias = listadependencias;
    }

    public List<DependenciaEntidad> getListadependenciasactivas() {
        return listadependenciasactivas;
    }

    public void setListadependenciasactivas(List<DependenciaEntidad> listadependenciasactivas) {
        this.listadependenciasactivas = listadependenciasactivas;
    }

    public DependenciaEntidad getDentidadeditar() {
        return dentidadeditar;
    }

    public void setDentidadeditar(DependenciaEntidad dentidadeditar) {
        this.dentidadeditar = dentidadeditar;
    }

    public DependenciaEntidad getDentidadeliminar() {
        return dentidadeliminar;
    }

    public void setDentidadeliminar(DependenciaEntidad dentidadeliminar) {
        this.dentidadeliminar = dentidadeliminar;
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

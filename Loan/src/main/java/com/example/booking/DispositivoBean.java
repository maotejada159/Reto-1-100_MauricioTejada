/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.booking;

import clasesgenerales.ValidacionEntradas;
import entidades.DependenciaEntidad;
import entidades.DispositivoEntidad;
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


@Named(value = "dispositivoBean")
@ViewScoped
@ManagedBean
public class DispositivoBean {

    private DispositivoEntidad dentidad = new DispositivoEntidad();
    private DispositivoEntidad dentidadeditar = new DispositivoEntidad();
    private DispositivoEntidad dentidadeliminar = new DispositivoEntidad();
    private List<DispositivoEntidad> listadispositivos = new ArrayList<>();
    private List<DispositivoEntidad> listadispositivosactivos = new ArrayList<>();
    private List<DispositivoEntidad> listadispositivosactivosdisponibles = new ArrayList<>();
    private boolean paneleditar = false;
    private boolean paneleliminar = false;
    private FacesContext facescontext;

    /**
     * Creates a new instance of DispositivoBean
     */
    public DispositivoBean() {
    }

    @PostConstruct
    public void init() {
        cargarListaCompletoDispositivos();
        cargarListaDispositivosActivos();
        cargarListaDispositivosActivosDisponibles();
    }

    public void cargarListaCompletoDispositivos() {
        DispositivoDAO mdao = new DispositivoDAO();
        facescontext = FacesContext.getCurrentInstance();
        listadispositivos = mdao.consultarListaCompletaDispositivo();
        PrimeFaces.current().executeScript("evitarRedirect();");
    }

    public void cargarListaDispositivosActivos() {
        DispositivoDAO mdao = new DispositivoDAO();
        facescontext = FacesContext.getCurrentInstance();
        listadispositivosactivos = mdao.consultarListaDispositivosActivos();
        PrimeFaces.current().executeScript("evitarRedirect();");
    }

    public void cargarListaDispositivosActivosDisponibles() {
        DispositivoDAO mdao = new DispositivoDAO();
        facescontext = FacesContext.getCurrentInstance();
        listadispositivosactivosdisponibles = mdao.consultarListaDispositivosActivosDisponibles();
        PrimeFaces.current().executeScript("evitarRedirect();");
    }

    public void insertarNuevoDispositivo() {
        try {
            DispositivoDAO mdao = new DispositivoDAO();
            MensajesBean mbean = new MensajesBean();
            MensajesEntidad me = new MensajesEntidad();
            facescontext = FacesContext.getCurrentInstance();
            dentidad.setNombredispositivo(dentidad.getNombredispositivo().trim());
            dentidad.setNombredispositivo(dentidad.getNombredispositivo().toUpperCase());
            dentidad.setCodigoactivo(dentidad.getCodigoactivo().trim());
            dentidad.setCodigoactivo(dentidad.getCodigoactivo().toUpperCase());

            ValidacionEntradas ventradas = new ValidacionEntradas();

            boolean valnombredispositivo = ventradas.caracteresEspeciales(dentidad.getNombredispositivo());
            boolean longnombredispositivo = ventradas.longitudCadena(dentidad.getNombredispositivo(), 1, 200);
            boolean valcodigoactivo = ventradas.caracteresEspeciales(dentidad.getCodigoactivo());
            boolean longcodigoactivo = ventradas.longitudCadena(dentidad.getCodigoactivo(), 1, 200);

            if (longnombredispositivo && valnombredispositivo
                    && valcodigoactivo && longcodigoactivo) {

                me = mdao.insertarNuevoDispositivo(dentidad);

            } else {
                me.setSeverity(FacesMessage.SEVERITY_WARN);
                me.setTitulo("Error - Validación");
                me.setMensaje("Los valores ingresados son inválidos");
            }

            if (me.isEstado()) {
                limpiarFormulario();

            }
            cargarListaCompletoDispositivos();
            PrimeFaces.current().executeScript("evitarRedirect();");
            mbean.mostrarMensaje(me);
        } catch (IOException ex) {
            Logger.getLogger(MarcaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarDispositivo() {
        try {
            MensajesEntidad me = new MensajesEntidad();
            MensajesBean mbean = new MensajesBean();
            DispositivoDAO mdao = new DispositivoDAO();
            //System.out.println("idempresa: "+idempresa);
            //System.out.println("nombreempresa: "+nombreempresa);
            //System.out.println("Nit Empresa: "+nitempresa);
            facescontext = FacesContext.getCurrentInstance();
            dentidadeditar.setNombredispositivo(dentidadeditar.getNombredispositivo().trim());
            dentidadeditar.setNombredispositivo(dentidadeditar.getNombredispositivo().toUpperCase());
            dentidadeditar.setCodigoactivo(dentidadeditar.getCodigoactivo().trim());
            dentidadeditar.setCodigoactivo(dentidadeditar.getCodigoactivo().toUpperCase());
            
            ValidacionEntradas ventradas = new ValidacionEntradas();
            
            boolean valnombredispositivo = ventradas.caracteresEspeciales(dentidadeditar.getNombredispositivo());
            boolean longnombredispositivo = ventradas.longitudCadena(dentidadeditar.getNombredispositivo(), 1, 200);
            boolean valcodigoactivo = ventradas.caracteresEspeciales(dentidadeditar.getCodigoactivo());
            boolean longcodigoactivo = ventradas.longitudCadena(dentidadeditar.getCodigoactivo(), 1, 200);
            
            if (longnombredispositivo && valnombredispositivo 
                    && valcodigoactivo && longcodigoactivo) {
                
                me = mdao.modificarDispositivo(dentidadeditar);
                
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
            
            cargarListaCompletoDispositivos();
            PrimeFaces.current().executeScript("evitarRedirect();");
            mbean.mostrarMensaje(me);
        } catch (IOException ex) {
            Logger.getLogger(DispositivoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarDispositivo() {
        MensajesEntidad me;
        MensajesBean mbean = new MensajesBean();
        DispositivoDAO adao = new DispositivoDAO();

        me = adao.eliminarDispositivo(dentidadeliminar);

        limpiarFormulario();
        cargarListaCompletoDispositivos();
        PrimeFaces.current().executeScript("evitarRedirect();");
        mbean.mostrarMensaje(me);
    }

    public void limpiarFormulario() {
        dentidad = new DispositivoEntidad();
        dentidadeditar = new DispositivoEntidad();
        dentidadeliminar = new DispositivoEntidad();
        paneleditar = false;
        paneleliminar = false;
        PrimeFaces.current().executeScript("evitarRedirect();");
    }

    public void cargarVentanaEditar(DispositivoEntidad depentidad) {
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
    public void cargarVentanaEliminar(DispositivoEntidad depentidad) {
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

    public DispositivoEntidad getDentidad() {
        return dentidad;
    }

    public void setDentidad(DispositivoEntidad dentidad) {
        this.dentidad = dentidad;
    }

    public List<DispositivoEntidad> getListadispositivos() {
        return listadispositivos;
    }

    public void setListadispositivos(List<DispositivoEntidad> listadispositivos) {
        this.listadispositivos = listadispositivos;
    }

    public List<DispositivoEntidad> getListadispositivosactivos() {
        return listadispositivosactivos;
    }

    public void setListadispositivosactivos(List<DispositivoEntidad> listadispositivosactivos) {
        this.listadispositivosactivos = listadispositivosactivos;
    }

    public List<DispositivoEntidad> getListadispositivosactivosdisponibles() {
        cargarListaDispositivosActivosDisponibles();
        return listadispositivosactivosdisponibles;
    }

    public void setListadispositivosactivosdisponibles(List<DispositivoEntidad> listadispositivosactivosdisponibles) {
        this.listadispositivosactivosdisponibles = listadispositivosactivosdisponibles;
    }

    public DispositivoEntidad getDentidadeditar() {
        return dentidadeditar;
    }

    public void setDentidadeditar(DispositivoEntidad dentidadeditar) {
        this.dentidadeditar = dentidadeditar;
    }

    public DispositivoEntidad getDentidadeliminar() {
        return dentidadeliminar;
    }

    public void setDentidadeliminar(DispositivoEntidad dentidadeliminar) {
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.booking;

import clasesgenerales.ValidacionEntradas;
import entidades.MensajesEntidad;
import entidades.PrestamoEntidad;
import entidades.UsuarioEntidad;
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


@Named(value = "prestamoBean")
@ViewScoped
@ManagedBean
public class PrestamoBean {

    private PrestamoEntidad pentidad = new PrestamoEntidad();
    private List<PrestamoEntidad> listaprestamoscompleta = new ArrayList<>();
    private List<PrestamoEntidad> listaprestamospendientedevolucion = new ArrayList<>();
    private boolean panelregistrodevolucion = false;
    private boolean panelanularprestamo = false;
    private boolean panelconsultaprestamo = false;

    /**
     * Creates a new instance of PrestamoBean
     */
    public PrestamoBean() {

    }

    @PostConstruct
    public void init() {
        pentidad.setDatetimeprestamo(new java.util.Date());
        cargarListaPrestamosCompleta();
        cargarListaPrestamosPendienteDevolucion();
    }

    public void cargarListaPrestamosCompleta() {
        listaprestamoscompleta = new PrestamoDAO().consultarListaCompletaPrestamos();
        PrimeFaces.current().executeScript("evitarRedirect();");
    }

    public void cargarListaPrestamosPendienteDevolucion() {
        listaprestamospendientedevolucion = new PrestamoDAO().consultarListaPrestamosActivos();
        PrimeFaces.current().executeScript("evitarRedirect();");
    }

    public void registrarPrestamo() {
        PrestamoDAO prestamodao = new PrestamoDAO();
        ValidacionEntradas valentrada = new ValidacionEntradas();
        MensajesEntidad me = new MensajesEntidad();
        MensajesBean mbean = new MensajesBean();
        try {

            pentidad.setDatetimeprestamo(new java.util.Date());
            pentidad.setEstadodevolucion(false);
            pentidad.setAnulado(false);

            pentidad.setNombrecompletoprestamo(pentidad.getNombrecompletoprestamo().trim());
            pentidad.setNombrecompletoprestamo(pentidad.getNombrecompletoprestamo().toUpperCase());

            pentidad.setDispositivo(pentidad.getDispositivo().trim());
            pentidad.setDispositivo(pentidad.getDispositivo().toUpperCase());

            String idregistrodispositivo = pentidad.getDispositivo().substring(0, pentidad.getDispositivo().indexOf(" "));
            //System.out.println("idregistrodispositivo seleccionado: "+idregistrodispositivo);
            int iddispositivo = Integer.parseInt(idregistrodispositivo);
            //System.out.println("iddispositivo seleccionado: "+iddispositivo);
            pentidad.setIdregistrodispositivo(iddispositivo);

            pentidad.setDependencia(pentidad.getDependencia().trim());
            pentidad.setDependencia(pentidad.getDependencia().toUpperCase());

            pentidad.setMarca(pentidad.getMarca().trim());
            pentidad.setMarca(pentidad.getMarca().toUpperCase());
            //System.out.println("Longitud valor firma: "+pentidad.getFirmaprestamo().length());
            boolean vallongentradas = valentrada.longitudCadena(pentidad.getNombrecompletoprestamo(), 1, 200)
                    && valentrada.longitudCadena(pentidad.getDispositivo(), 1, 200)
                    && valentrada.longitudCadena(pentidad.getDependencia(), 1, 200)
                    && valentrada.longitudCadena(pentidad.getMarca(), 1, 200)
                    && valentrada.longitudCadena(pentidad.getFirmaprestamo(), 1, 8000);
            boolean valcaracteres = valentrada.caracteresEspeciales(pentidad.getNombrecompletoprestamo())
                    && valentrada.caracteresEspeciales(pentidad.getDispositivo())
                    && valentrada.caracteresEspeciales(pentidad.getDependencia())
                    && valentrada.caracteresEspeciales(pentidad.getMarca());

            //System.out.println("vallongentrada: "+vallongentradas);
            //System.out.println("valcaracteres: "+valcaracteres);
            if (vallongentradas && valcaracteres) {
                me = prestamodao.insertarNuevoPrestamo(pentidad);
            } else {
                me.setTitulo("Error validación entradas");
                me.setMensaje("Se recibieron caractéres no compatibles");
                me.setSeverity(FacesMessage.SEVERITY_ERROR);
            }

        } catch (IOException ex) {
            Logger.getLogger(PrestamoBean.class.getName()).log(Level.SEVERE, null, ex);
            me.setMensaje(ex.getMessage());
            me.setTitulo("Excepción validación entradas: ");
            me.setSeverity(FacesMessage.SEVERITY_ERROR);
        }

        limpiarFormulario();
        PrimeFaces.current().executeScript("evitarRedirect();");
        mbean.mostrarMensaje(me);

    }

    public void registrarDevolucion() {
        PrestamoDAO prestamodao = new PrestamoDAO();
        ValidacionEntradas valentrada = new ValidacionEntradas();
        MensajesEntidad me = new MensajesEntidad();
        MensajesBean mbean = new MensajesBean();
        UsuarioEntidad ue = (UsuarioEntidad) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("usuario");
        pentidad.setIdusuariorecibe(ue.getIdusuario());

        try {

            pentidad.setDatetimedevolucion(new java.util.Date());
            pentidad.setEstadodevolucion(true);

            pentidad.setNombrecompletodevolucion(pentidad.getNombrecompletodevolucion().trim());
            pentidad.setNombrecompletodevolucion(pentidad.getNombrecompletodevolucion().toUpperCase());

            pentidad.setObservacionesdevolucion(pentidad.getObservacionesdevolucion().trim());
            pentidad.setObservacionesdevolucion(pentidad.getObservacionesdevolucion().toUpperCase());

            String idregistrodispositivo = pentidad.getDispositivo().substring(0, pentidad.getDispositivo().indexOf(" "));
            //System.out.println("idregistrodispositivo seleccionado: "+idregistrodispositivo);
            int iddispositivo = Integer.parseInt(idregistrodispositivo);
            //System.out.println("iddispositivo seleccionado: "+iddispositivo);
            pentidad.setIdregistrodispositivo(iddispositivo);

            //System.out.println("Longitud valor firma: "+pentidad.getFirmaprestamo().length());
            boolean vallongentradas = valentrada.longitudCadena(pentidad.getNombrecompletodevolucion(), 1, 200)
                    && valentrada.longitudCadena(pentidad.getFirmadevolucion(), 1, 8000)
                    && valentrada.longitudCadena(pentidad.getObservacionesdevolucion(), 1, 200);
            boolean valcaracteres = valentrada.caracteresEspeciales(pentidad.getNombrecompletodevolucion());

            //System.out.println("vallongentrada: "+vallongentradas);
            //System.out.println("valcaracteres: "+valcaracteres);
            if (vallongentradas && valcaracteres) {
                me = prestamodao.registrarDevolucion(pentidad);
                if (me.isEstado()) {
                    cargarListaPrestamosPendienteDevolucion();
                    limpiarFormulario();
                }
            } else {
                me.setTitulo("Error validación entradas");
                me.setMensaje("Se recibieron caractéres no compatibles");
                me.setSeverity(FacesMessage.SEVERITY_ERROR);
                panelregistrodevolucion = true;
                panelanularprestamo = false;

            }

        } catch (IOException ex) {
            Logger.getLogger(PrestamoBean.class.getName()).log(Level.SEVERE, null, ex);
            me.setMensaje(ex.getMessage());
            me.setTitulo("Excepción validación entradas: ");
            me.setSeverity(FacesMessage.SEVERITY_ERROR);
            panelregistrodevolucion = true;
            panelanularprestamo = false;
        }

        PrimeFaces.current().executeScript("evitarRedirect();");
        mbean.mostrarMensaje(me);

    }

    public void anularPrestamo() {
        PrestamoDAO prestamodao = new PrestamoDAO();
        ValidacionEntradas valentrada = new ValidacionEntradas();
        MensajesEntidad me = new MensajesEntidad();
        MensajesBean mbean = new MensajesBean();
        UsuarioEntidad ue = (UsuarioEntidad) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("usuario");
        pentidad.setIdusuarioanula(ue.getIdusuario());

        try {

            pentidad.setDatetimeanulado(new java.util.Date());
            pentidad.setEstadodevolucion(false);
            pentidad.setAnulado(true);

            pentidad.setObservacionesanulado(pentidad.getObservacionesanulado().trim());
            pentidad.setObservacionesanulado(pentidad.getObservacionesanulado().toUpperCase());

            String idregistrodispositivo = pentidad.getDispositivo().substring(0, pentidad.getDispositivo().indexOf(" "));
            //System.out.println("idregistrodispositivo seleccionado: "+idregistrodispositivo);
            int iddispositivo = Integer.parseInt(idregistrodispositivo);
            //System.out.println("iddispositivo seleccionado: "+iddispositivo);
            pentidad.setIdregistrodispositivo(iddispositivo);

            //System.out.println("Longitud valor firma: "+pentidad.getFirmaprestamo().length());
            boolean vallongentradas = valentrada.longitudCadena(pentidad.getObservacionesanulado(), 1, 200);
            boolean valcaracteres = valentrada.caracteresEspeciales(pentidad.getObservacionesanulado());

            //System.out.println("vallongentrada: "+vallongentradas);
            //System.out.println("valcaracteres: "+valcaracteres);
            if (vallongentradas && valcaracteres) {
                me = prestamodao.anularPrestamo(pentidad);
                if (me.isEstado()) {
                    cargarListaPrestamosPendienteDevolucion();
                    limpiarFormulario();
                }
            } else {
                me.setTitulo("Error validación entradas");
                me.setMensaje("Se recibieron caractéres no compatibles");
                me.setSeverity(FacesMessage.SEVERITY_ERROR);
                panelregistrodevolucion = true;
                panelanularprestamo = false;

            }

        } catch (IOException ex) {
            Logger.getLogger(PrestamoBean.class.getName()).log(Level.SEVERE, null, ex);
            me.setMensaje(ex.getMessage());
            me.setTitulo("Excepción validación entradas: ");
            me.setSeverity(FacesMessage.SEVERITY_ERROR);
            panelregistrodevolucion = true;
            panelanularprestamo = false;
        }

        PrimeFaces.current().executeScript("evitarRedirect();");
        mbean.mostrarMensaje(me);

    }

    public void mostrarPanelRegistroDevolucion(PrestamoEntidad pentidad) {
        this.pentidad = pentidad;
        //System.out.println("Firma prestamo: "+pentidad.getFirmaprestamo());
        panelregistrodevolucion = true;
        panelanularprestamo = false;
    }

    public void mostrarPanelAnularPrestamo(PrestamoEntidad pentidad) {
        this.pentidad = pentidad;
        panelanularprestamo = true;
        panelregistrodevolucion = false;

    }
    
    public void mostrarPanelConsultaPrestamo(PrestamoEntidad pentidad) {
        this.pentidad = pentidad;
        panelconsultaprestamo = true;

    }

    public void limpiarFormulario() {
        pentidad = new PrestamoEntidad();
        pentidad.setDatetimeprestamo(new java.util.Date());
        panelanularprestamo = false;
        panelregistrodevolucion = false;
        panelconsultaprestamo = false;
        PrimeFaces.current().executeScript("evitarRedirect();");
    }

    public PrestamoEntidad getPentidad() {
        return pentidad;
    }

    public void setPentidad(PrestamoEntidad pentidad) {
        this.pentidad = pentidad;
    }

    public List<PrestamoEntidad> getListaprestamoscompleta() {
        return listaprestamoscompleta;
    }

    public void setListaprestamoscompleta(List<PrestamoEntidad> listaprestamoscompleta) {
        this.listaprestamoscompleta = listaprestamoscompleta;
    }

    public List<PrestamoEntidad> getListaprestamospendientedevolucion() {
        return listaprestamospendientedevolucion;
    }

    public void setListaprestamospendientedevolucion(List<PrestamoEntidad> listaprestamospendientedevolucion) {
        this.listaprestamospendientedevolucion = listaprestamospendientedevolucion;
    }

    public boolean isPanelregistrodevolucion() {
        return panelregistrodevolucion;
    }

    public void setPanelregistrodevolucion(boolean panelregistrodevolucion) {
        this.panelregistrodevolucion = panelregistrodevolucion;
    }

    public boolean isPanelanularprestamo() {
        return panelanularprestamo;
    }

    public void setPanelanularprestamo(boolean panelanularprestamo) {
        this.panelanularprestamo = panelanularprestamo;
    }

    public boolean isPanelconsultaprestamo() {
        return panelconsultaprestamo;
    }

    public void setPanelconsultaprestamo(boolean panelconsultaprestamo) {
        this.panelconsultaprestamo = panelconsultaprestamo;
    }

}

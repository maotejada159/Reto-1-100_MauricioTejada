/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.booking;

import entidades.MensajesEntidad;
import javax.inject.Named;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;


@Named(value = "mensajesBean")
@ManagedBean
@RequestScoped
public class MensajesBean implements Serializable {

    private FacesContext facescontext;
    /**
     * Creates a new instance of MensajesBean
     */
    public MensajesBean() {
        this.facescontext= FacesContext.getCurrentInstance();
    }
    
    public void mostrarMensaje (MensajesEntidad me){
        facescontext.addMessage(null,
                        new FacesMessage(me.getSeverity(),
                                me.getTitulo(),
                                me.getMensaje()));
        
    }
    
}

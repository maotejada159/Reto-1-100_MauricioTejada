/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.booking;

import entidades.PerfilUsuarioEntidad;
import entidades.UsuarioEntidad;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;


@Named(value = "verificarSesionBean")
@RequestScoped
@ManagedBean
public final class ControladorSesionBean implements Serializable {

    private FacesContext facescontext;

    /**
     * Creates a new instance of VerificarSesionBean
     */
    public ControladorSesionBean() {

    }

    //Metodo que será ejecutado justo antes de el renderizado de las páginas, 
    //con el objetivo de verificar si existe una sesión activa y se utiliza para
    //los permisos de acceso a los recursos
    public void verificarEstadoSesion() throws IOException {
        facescontext = FacesContext.getCurrentInstance();
        UsuarioEntidad usuarioentidad = null;
        boolean urlpermitida = false;
        usuarioentidad
                = (UsuarioEntidad) facescontext.getExternalContext().getSessionMap().get("usuario");
        //System.out.println("plantillacontroller usuario sesion: "+usuarioentidad.getUsername());

        HttpServletRequest origRequest = (HttpServletRequest) facescontext.getExternalContext().getRequest();

        String peticionURL = origRequest.getRequestURL().toString();
        String pathAppURL = facescontext.getExternalContext().
                getApplicationContextPath();
        //System.out.println("valor del index: " + peticionURL);
        //System.out.println("Valor de path: " + pathAppURL);
        /*
        INICIO DE VALIDACIÓN DE PETICIÓN
        EN CASO QUE EL USUARIO SOLICITE LA PAGINA DE LOGIN Y TIENE O NO UNA SESIÓN 
        ACTIVA
         */

        if (!peticionURL.contains("admin") && usuarioentidad == null) {
            //DEJO PASAR           
            //System.out.println("En validación 1");
        } else if (usuarioentidad == null && peticionURL.contains("login")) {
            //DEJO PASAR
            //System.out.println("En validación 2");
        } else if (usuarioentidad != null && peticionURL.contains("login")) {
            //System.out.println("En validación 3");
            FacesContext.getCurrentInstance().getExternalContext().redirect(pathAppURL + "/faces/admin/admin.xhtml");
        } else if (usuarioentidad != null && !peticionURL.contains("login")){
            //DEJO PASAR
            //System.out.println("En validación 4");            
        } else if (usuarioentidad == null && peticionURL.contains("admin")){
            //System.out.println("En validación 5");
            FacesContext.getCurrentInstance().getExternalContext().redirect(pathAppURL + "/faces/login.xhtml");
            //System.out.println("En validación 6");
        } else if (usuarioentidad != null && peticionURL.contains("admin")){
            //System.out.println("En validación 7");
        }
        

    }

    public boolean cargarSesionUsuario(UsuarioEntidad ue) throws IOException {
        facescontext = FacesContext.getCurrentInstance();        
        boolean resultado = false;        
        facescontext.getExternalContext().getSessionMap().put("usuario", ue); 
        if (ue != null) {
            resultado = true;
        }
        return resultado;
    }

    public FacesContext getFacescontext() {
        return facescontext;
    }

    public void setFacescontext(FacesContext facescontext) {
        this.facescontext = facescontext;
    }

}

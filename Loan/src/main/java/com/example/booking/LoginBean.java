/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.booking;

import clasesgenerales.Security;
import entidades.MensajesEntidad;
import entidades.UsuarioEntidad;
import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


@Named(value = "loginBean")
@ViewScoped
@ManagedBean
public class LoginBean implements Serializable {

    private String username;
    private String pass;

    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
    }

    public void cargarLoginPage() {
        try {
            String contextpath
                    = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath();
            //System.out.println("context path en cargar pagina login: " + contextpath);
            FacesContext.getCurrentInstance().getExternalContext().redirect(contextpath + "/faces/login.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void validarLogin() {
        MensajesEntidad me = new MensajesEntidad();
        MensajesBean mbean = new MensajesBean();
        Security sec = new Security();
        LoginDAO logindao = new LoginDAO();
        UsuarioEntidad ue = new UsuarioEntidad();
        UsuariosDAO usuariodao = new UsuariosDAO();
        try {
            username = username.trim();
            username = username.toLowerCase();

            boolean boollongusername = username.length() >= 5 && username.length() <= 100;
            boolean boollongpass = pass.length() >= 5 && pass.length() <= 500;
            if (boollongusername && boollongpass) {

                me = logindao.validarLogin(username, sec.setSHA512(pass));

                if (me.isEstado()) {
                    boolean useractivo = logindao.validarUsuarioActivo(username);
                    me = usuariodao.consultarUsuario(username);
                    if (me.getUsuarioentidad().isEstadousuario()) {
                        ControladorSesionBean csesion = new ControladorSesionBean();
                        if (csesion.cargarSesionUsuario(me.getUsuarioentidad())) {
                            String pathapp = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath();
                            FacesContext.getCurrentInstance().getExternalContext().redirect(pathapp + "/faces/admin/admin.xhtml");

                        }

                    } else {
                        me.setTitulo("Usuario Inactivo");
                        me.setMensaje("El usuario se encuentra inactivo");
                        me.setSeverity(FacesMessage.SEVERITY_WARN);

                    }
                }

            } else {
                me.setTitulo("Entrada inválida");
                me.setMensaje("Se ha ingresado caracteres no permitidos o longitud no adecuada");
                me.setSeverity(FacesMessage.SEVERITY_ERROR);
            }

            new MensajesBean().mostrarMensaje(me);

        } catch (NoSuchAlgorithmException | SQLException | IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void cerrarSesion(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
            String pathApp = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath();
            FacesContext.getCurrentInstance().getExternalContext().redirect(pathApp);
        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.util.Date;


public class UsuarioEntidad {
    
    private int idusuario;
    private String username;
    private String nombrecompletousuario;
    private String correousuario;
    private String claveusuario;
    private Date datetimecreacionusuario;
    private String claveenclaro;    
    private boolean estadousuario;

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombrecompletousuario() {
        return nombrecompletousuario;
    }

    public void setNombrecompletousuario(String nombrecompletousuario) {
        this.nombrecompletousuario = nombrecompletousuario;
    }

    public String getCorreousuario() {
        return correousuario;
    }

    public void setCorreousuario(String correousuario) {
        this.correousuario = correousuario;
    }

    public String getClaveusuario() {
        return claveusuario;
    }

    public void setClaveusuario(String claveusuario) {
        this.claveusuario = claveusuario;
    }

    public Date getDatetimecreacionusuario() {
        return datetimecreacionusuario;
    }

    public void setDatetimecreacionusuario(Date datetimecreacionusuario) {
        this.datetimecreacionusuario = datetimecreacionusuario;
    }

    public String getClaveenclaro() {
        return claveenclaro;
    }

    public void setClaveenclaro(String claveenclaro) {
        this.claveenclaro = claveenclaro;
    }

    public boolean isEstadousuario() {
        return estadousuario;
    }

    public void setEstadousuario(boolean estadousuario) {
        this.estadousuario = estadousuario;
    }
    
   
    
}

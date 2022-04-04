/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;


import clasesgenerales.ListaDatos;
import java.util.List;
import javax.faces.application.FacesMessage;


public class MensajesEntidad {
    private FacesMessage.Severity severity;
    private String titulo;
    private String mensaje;
    private Integer id;
    private boolean estado;
    private String logoperacion;
    private List<ListaDatos> listalog;
    private UsuarioEntidad usuarioentidad;

    public FacesMessage.Severity getSeverity() {
        return severity;
    }

    public void setSeverity(FacesMessage.Severity severity) {
        this.severity = severity;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    
    public String getLogoperacion() {
        return logoperacion;
    }

    public void setLogoperacion(String logoperacion) {
        this.logoperacion = logoperacion;
    }

    public List<ListaDatos> getListalog() {
        return listalog;
    }

    public void setListalog(List<ListaDatos> listalog) {
        this.listalog = listalog;
    }

    public UsuarioEntidad getUsuarioentidad() {
        return usuarioentidad;
    }

    public void setUsuarioentidad(UsuarioEntidad usuarioentidad) {
        this.usuarioentidad = usuarioentidad;
    }
    
}

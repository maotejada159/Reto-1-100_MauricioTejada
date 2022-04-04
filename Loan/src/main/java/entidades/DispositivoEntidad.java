/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;


public class DispositivoEntidad {
    
    private int idregistro;
    private String nombredispositivo;
    private boolean estadodispositivo;
    private boolean disponibledispositivo;
    private String codigoactivo;

    public int getIdregistro() {
        return idregistro;
    }

    public void setIdregistro(int idregistro) {
        this.idregistro = idregistro;
    }

    public String getNombredispositivo() {
        return nombredispositivo;
    }

    public void setNombredispositivo(String nombredispositivo) {
        this.nombredispositivo = nombredispositivo;
    }

    public boolean isEstadodispositivo() {
        return estadodispositivo;
    }

    public void setEstadodispositivo(boolean estadodispositivo) {
        this.estadodispositivo = estadodispositivo;
    }

    public boolean isDisponibledispositivo() {
        return disponibledispositivo;
    }

    public void setDisponibledispositivo(boolean disponibledispositivo) {
        this.disponibledispositivo = disponibledispositivo;
    }

    public String getCodigoactivo() {
        return codigoactivo;
    }

    public void setCodigoactivo(String codigoactivo) {
        this.codigoactivo = codigoactivo;
    }
    
}

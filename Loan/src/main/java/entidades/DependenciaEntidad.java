/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;


public class DependenciaEntidad {
    
    private int idregistro;
    private String nombredependencia;
    private boolean estado;

    public int getIdregistro() {
        return idregistro;
    }

    public void setIdregistro(int idregistro) {
        this.idregistro = idregistro;
    }

    public String getNombredependencia() {
        return nombredependencia;
    }

    public void setNombredependencia(String nombredependencia) {
        this.nombredependencia = nombredependencia;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
}

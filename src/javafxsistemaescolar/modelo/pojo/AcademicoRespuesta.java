/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxsistemaescolar.modelo.pojo;

import java.util.ArrayList;

/**
 *
 * @author ianca
 */
public class AcademicoRespuesta {
    private int codigoRespuesta;
    private ArrayList<Academico>academico;

    public AcademicoRespuesta() {
    }

    public AcademicoRespuesta(int codigoRespuesta, ArrayList<Academico> academico) {
        this.codigoRespuesta = codigoRespuesta;
        this.academico = academico;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Academico> getAcademico() {
        return academico;
    }

    public void setAcademico(ArrayList<Academico> academico) {
        this.academico = academico;
    }
    
    
}

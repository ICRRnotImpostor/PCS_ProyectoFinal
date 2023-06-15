/* 
Autor: Ian Rumayor. 
Creado: 28/05/2023 
Modificado: 14/06/2023  
Descripción: respuesta de CuerpoAcademico
*/
package javafxsistemaescolar.modelo.pojo;

import java.util.ArrayList;

public class CuerpoAcademicoRespuesta {
    private int codigoRespuesta;
    private ArrayList<CuerpoAcademico> cuerpoAcademico;

    public CuerpoAcademicoRespuesta() {
    }

    public CuerpoAcademicoRespuesta(int codigoRespuesta, ArrayList<CuerpoAcademico> cuerpoAcademico) {
        this.codigoRespuesta = codigoRespuesta;
        this.cuerpoAcademico = cuerpoAcademico;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<CuerpoAcademico> getCuerpoAcademico() {
        return cuerpoAcademico;
    }

    public void setCuerpoAcademico(ArrayList<CuerpoAcademico> cuerpoAcademico) {
        this.cuerpoAcademico = cuerpoAcademico;
    }
}

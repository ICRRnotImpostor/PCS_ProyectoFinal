/* 
Autor: Ian Rumayor. 
Creado: 28/05/2023 
Modificado: 14/06/2023  
Descripción: respuesta de Academico
*/
package javafxsistemaescolar.modelo.pojo;

import java.util.ArrayList;

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

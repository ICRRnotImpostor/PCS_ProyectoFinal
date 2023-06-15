/* 
Autor: Ian Rumayor. 
Creado: 28/05/2023 
Modificado: 14/06/2023  
Descripción: respuesta de Anteproyecto
*/

package javafxsistemaescolar.modelo.pojo;

import java.util.ArrayList;

public class AnteproyectoRespuesta {
    private int codigoRespuesta;
    private ArrayList<Anteproyecto> anteproyectos;

    public AnteproyectoRespuesta() {
    }

    public AnteproyectoRespuesta(int codigoRespuesta, ArrayList<Anteproyecto> anteproyectos) {
        this.codigoRespuesta = codigoRespuesta;
        this.anteproyectos = anteproyectos;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Anteproyecto> getAnteproyectos() {
        return anteproyectos;
    }

    public void setAnteproyectos(ArrayList<Anteproyecto> anteproyectos) {
        this.anteproyectos = anteproyectos;
    }

    
}

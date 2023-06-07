package javafxsistemaescolar.modelo.pojo;

import java.util.ArrayList;

public class AnteproyectoRespuesta {
    private int codigoRespuesta;
    private ArrayList<Anteproyecto> anteproyecto;

    public AnteproyectoRespuesta() {
    }

    public AnteproyectoRespuesta(int codigoRespuesta, ArrayList<Anteproyecto> anteproyecto) {
        this.codigoRespuesta = codigoRespuesta;
        this.anteproyecto = anteproyecto;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Anteproyecto> getAnteproyecto() {
        return anteproyecto;
    }

    public void setLgac(ArrayList<Anteproyecto> anteproyecto) {
        this.anteproyecto = anteproyecto;
    }
}

/* 
Autor: Ian Rumayor. 
Creado: 28/05/2023 
Modificado: 14/06/2023 
Descripción: respuesta de Alumno
*/
package javafxsistemaescolar.modelo.pojo;

import java.util.ArrayList;

/**
 *
 * @author ianca
 */
public class AlumnoRespuesta {
    private int codigoRespuesta;
    private ArrayList<Alumno> alumnos;

    public AlumnoRespuesta() {
    }

    public AlumnoRespuesta(int codigoRespuesta, ArrayList<Alumno> alumnos) {
        this.codigoRespuesta = codigoRespuesta;
        this.alumnos = alumnos;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(ArrayList<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    public Alumno[] getAnteproyectos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

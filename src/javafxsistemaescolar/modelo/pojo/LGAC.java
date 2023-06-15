/* 
Autor: Ian Rumayor. 
Creado: 28/05/2023 
Modificado: 15/06/2023  
Descripción: Clase que implementa los objetos de tipo LGAC
*/
package javafxsistemaescolar.modelo.pojo;

public class LGAC {
    private int idLGAC;
    private String nombre;
    private String descripcion;
    private int idCuerpoAcademico;

    public LGAC() {
    }

    public LGAC(int idLGAC, String nombre, String descripcion, int idCuerpoAcademico) {
        this.idLGAC = idLGAC;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idCuerpoAcademico = idCuerpoAcademico;
    }

    public int getIdLGAC() {
        return idLGAC;
    }

    public void setIdLGAC(int idLGAC) {
        this.idLGAC = idLGAC;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdCuerpoAcademico() {
        return idCuerpoAcademico;
    }

    public void setIdCuerpoAcademico(int idCuerpoAcademico) {
        this.idCuerpoAcademico = idCuerpoAcademico;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}

/* 
Autor: Ian Rumayor. 
Creado: 28/05/2023 
Modificado: 15/06/2023  
Descripción: Clase que implementa los objetos de tipo CuerpoAcademico
*/
package javafxsistemaescolar.modelo.pojo;

public class CuerpoAcademico {
    private int idCuerpoAcademico;
    private String nombreCuerpoAcademico;

    public CuerpoAcademico() {
    }

    public CuerpoAcademico(int idCuerpoAcademico, String nombreCuerpoAcademico) {
        this.idCuerpoAcademico = idCuerpoAcademico;
        this.nombreCuerpoAcademico = nombreCuerpoAcademico;
    }

    public int getIdCuerpoAcademico() {
        return idCuerpoAcademico;
    }

    public void setIdCuerpoAcademico(int idCuerpoAcademico) {
        this.idCuerpoAcademico = idCuerpoAcademico;
    }

    public String getNombreCuerpoAcademico() {
        return nombreCuerpoAcademico;
    }

    public void setNombreCuerpoAcademico(String nombreCuerpoAcademico) {
        this.nombreCuerpoAcademico = nombreCuerpoAcademico;
    }
    
    @Override
    public String toString() {
        return nombreCuerpoAcademico;
    }
}

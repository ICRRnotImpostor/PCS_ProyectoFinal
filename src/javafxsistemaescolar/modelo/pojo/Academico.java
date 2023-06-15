/* 
Autor: Ian Rumayor. 
Creado: 28/05/2023 
Modificado: 14/06/2023  
Descripción: Clase que implementa los objetos de tipo Academico
*/
package javafxsistemaescolar.modelo.pojo;

public class Academico {
    private int idAcademico;
    private String nombreAcademico;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private int numeroDePersonal;
    private String correo;

    public Academico() {
    }

    public Academico(int idAcademico, String nombreAcademico, String apellidoPaterno, String apellidoMaterno, int numeroDePersonal, String correo) {
        this.idAcademico = idAcademico;
        this.nombreAcademico = nombreAcademico;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.numeroDePersonal = numeroDePersonal;
        this.correo = correo;
    }

    public int getIdAcademico() {
        return idAcademico;
    }

    public void setIdAcademico(int idAcademico) {
        this.idAcademico = idAcademico;
    }

    public String getNombreAcademico() {
        return nombreAcademico;
    }

    public void setNombreAcademico(String nombreAcademico) {
        this.nombreAcademico = nombreAcademico;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public int getNumeroDePersonal() {
        return numeroDePersonal;
    }

    public void setNumeroDePersonal(int numeroDePersonal) {
        this.numeroDePersonal = numeroDePersonal;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    @Override
    public String toString() {
        return nombreAcademico+" "+apellidoPaterno+" "+apellidoMaterno;
    }
}

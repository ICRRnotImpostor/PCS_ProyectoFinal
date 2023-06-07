/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxsistemaescolar.modelo.pojo;

/**
 *
 * @author ianca
 */
public class Academico {
    private int idAcademico;
    private String nombreAcademico;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correo;

    public Academico() {
    }

    public Academico(int idAcademico, String nombreAcademico, String apellidoPaterno, String apellidoMaterno, String correo) {
        this.idAcademico = idAcademico;
        this.nombreAcademico = nombreAcademico;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
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

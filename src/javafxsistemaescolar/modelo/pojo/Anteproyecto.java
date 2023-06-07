/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxsistemaescolar.modelo.pojo;

/**
 *
 * @author ianca
 */
public class Anteproyecto {
    private int idAnteproyecto;
    private String nombreProyecto;
    private String cuerpoAcademico;
    private String fecha;
    private String lineaDeInvestigacion;
    private String duracionAproximada;
    private String modalidad;
    private String nombreTrabajoRecepcional;
    private String descripcionProyecto;
    private String requisitos;
    private String bibliografiaRecomendada;
    private String resultadosEsperados;
    private String notas;
    private int idDirector;
    private int idCodirector;
    private int idLGAC;

    public Anteproyecto() {
    }

    public Anteproyecto(int idAnteproyecto, String nombreProyecto, String cuerpoAcademico, String fecha, String lineaDeInvestigacion, String duracionAproximada, String modalidad, String nombreTrabajoRecepcional, String descripcionProyecto, String requisitos, String bibliografiaRecomendada, String resultadosEsperados, String notas, int idDirector, int idCodirector, int idLGAC) {
        this.idAnteproyecto = idAnteproyecto;
        this.nombreProyecto = nombreProyecto;
        this.cuerpoAcademico = cuerpoAcademico;
        this.fecha = fecha;
        this.lineaDeInvestigacion = lineaDeInvestigacion;
        this.duracionAproximada = duracionAproximada;
        this.modalidad = modalidad;
        this.nombreTrabajoRecepcional = nombreTrabajoRecepcional;
        this.descripcionProyecto = descripcionProyecto;
        this.requisitos = requisitos;
        this.bibliografiaRecomendada = bibliografiaRecomendada;
        this.resultadosEsperados = resultadosEsperados;
        this.notas = notas;
        this.idDirector = idDirector;
        this.idCodirector = idCodirector;
        this.idLGAC = idLGAC;
    }

    public int getIdAnteproyecto() {
        return idAnteproyecto;
    }

    public void setIdAnteproyecto(int idAnteproyecto) {
        this.idAnteproyecto = idAnteproyecto;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public String getCuerpoAcademico() {
        return cuerpoAcademico;
    }

    public void setCuerpoAcademico(String cuerpoAcademico) {
        this.cuerpoAcademico = cuerpoAcademico;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLineaDeInvestigacion() {
        return lineaDeInvestigacion;
    }

    public void setLineaDeInvestigacion(String lineaDeInvestigacion) {
        this.lineaDeInvestigacion = lineaDeInvestigacion;
    }

    public String getDuracionAproximada() {
        return duracionAproximada;
    }

    public void setDuracionAproximada(String duracionAproximada) {
        this.duracionAproximada = duracionAproximada;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getNombreTrabajoRecepcional() {
        return nombreTrabajoRecepcional;
    }

    public void setNombreTrabajoRecepcional(String nombreTrabajoRecepcional) {
        this.nombreTrabajoRecepcional = nombreTrabajoRecepcional;
    }

    public String getDescripcionProyecto() {
        return descripcionProyecto;
    }

    public void setDescripcionProyecto(String descripcionProyecto) {
        this.descripcionProyecto = descripcionProyecto;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public String getBibliografiaRecomendada() {
        return bibliografiaRecomendada;
    }

    public void setBibliografiaRecomendada(String bibliografiaRecomendada) {
        this.bibliografiaRecomendada = bibliografiaRecomendada;
    }

    public String getResultadosEsperados() {
        return resultadosEsperados;
    }

    public void setResultadosEsperados(String resultadosEsperados) {
        this.resultadosEsperados = resultadosEsperados;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public int getIdDirector() {
        return idDirector;
    }

    public void setIdDirector(int idDirector) {
        this.idDirector = idDirector;
    }

    public int getIdCodirector() {
        return idCodirector;
    }

    public void setIdCodirector(int idCodirector) {
        this.idCodirector = idCodirector;
    }

    public int getIdLGAC() {
        return idLGAC;
    }

    public void setIdLGAC(int idLGAC) {
        this.idLGAC = idLGAC;
    }
    
    
}

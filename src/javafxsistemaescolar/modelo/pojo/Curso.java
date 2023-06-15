/* 
Autor: Miguel Martinez. 
Creado: 21/05/2023 
Modificado: 11/06/2023  
Descripción: Pojo del curso
*/
package javafxsistemaescolar.modelo.pojo;

public class Curso
{
    private int idCurso;
    private String nombreCurso;
    private String periodo;
    private String fechaInicioCurso;
    private String fechaFinCurso;

    public Curso() 
    {
    }

    public Curso(int idCurso, String nombreCurso, String periodo, String fechaInicioCurso, String fechaFinCurso) 
    {
        this.idCurso = idCurso;
        this.nombreCurso = nombreCurso;
        this.periodo = periodo;
        this.fechaInicioCurso = fechaInicioCurso;
        this.fechaFinCurso = fechaFinCurso;
    }

    public int getIdCurso() 
    {
        return idCurso;
    }

    public void setIdCurso(int idCurso)
    {
        this.idCurso = idCurso;
    }

    public String getNombreCurso() 
    {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso)
    {
        this.nombreCurso = nombreCurso;
    }

    public String getPeriodo() 
    {
        return periodo;
    }

    public void setPeriodo(String periodo) 
    {
        this.periodo = periodo;
    }

    public String getFechaInicioCurso() 
    {
        return fechaInicioCurso;
    }

    public void setFechaInicioCurso(String fechaInicioCurso) 
    {
        this.fechaInicioCurso = fechaInicioCurso;
    }

    public String getFechaFinCurso()
    {
        return fechaFinCurso;
    }

    public void setFechaFinCurso(String fechaFinCurso)
    {
        this.fechaFinCurso = fechaFinCurso;
    }        
}


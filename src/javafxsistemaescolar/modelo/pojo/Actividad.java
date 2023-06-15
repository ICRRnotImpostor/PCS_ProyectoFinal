/* 
Autor: Miguel Martinez. 
Creado: 21/05/2023 
Modificado: 11/06/2023  
Descripción: Pojo de la Actividad
*/
package javafxsistemaescolar.modelo.pojo;

public class Actividad {
    private int idActividad;
    private String nombreActividad;
    private String descripcionActividad;    
    private byte[] documento;
    private String fechaInicio;
    private String fechaFin;

    public Actividad()
    {
    }

    public Actividad(int idActividad, String nombreActividad, String descripcionActividad, byte[] documento, String fechaInicio, String fechaFin)
    {
        this.idActividad = idActividad;
        this.nombreActividad = nombreActividad;
        this.descripcionActividad = descripcionActividad;
        this.documento = documento;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public int getIdActividad() 
    {
        return idActividad;
    }

    public void setIdActividad(int idActividad)
    {
        this.idActividad = idActividad;
    }

    public String getNombreActividad() 
    {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) 
    {
        this.nombreActividad = nombreActividad;
    }

    public String getDescripcionActividad()
    {
        return descripcionActividad;
    }

    public void setDescripcionActividad(String descripcionActividad) 
    {
        this.descripcionActividad = descripcionActividad;
    }

    public byte[] getDocumento() 
    {
        return documento;
    }

    public void setDocumento(byte[] documento)
    {
        this.documento = documento;
    }

    public String getFechaInicio() 
    {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio)
    {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() 
    {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin)
    {
        this.fechaFin = fechaFin;
    }
    
}

   



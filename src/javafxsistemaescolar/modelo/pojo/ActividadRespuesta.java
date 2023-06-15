/* 
Autor: Miguel Martinez. 
Creado: 21/05/2023 
Modificado: 11/06/2023  
Descripción: Respuesta que se dará de la actividad
*/
package javafxsistemaescolar.modelo.pojo;

import java.util.ArrayList;

public class ActividadRespuesta 
{
    private int codigoRespuesta;
    private ArrayList<Actividad>actividades;

    public ActividadRespuesta() 
    {
    }

    public ActividadRespuesta(int codigoRespuesta, ArrayList<Actividad> actividades) 
    {
        this.codigoRespuesta = codigoRespuesta;
        this.actividades = actividades;
    }

    public int getCodigoRespuesta() 
    {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta)
    {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Actividad> getActividades() 
    {
        return actividades;
    }

    public void setActividades(ArrayList<Actividad> actividades) 
    {
        this.actividades = actividades;
    }   
}

/* 
Autor: Miguel Martinez. 
Creado: 27/05/2023 
Modificado: 11/06/2023  
Descripción: Interfaz que se usa para notificar si se actualiza o guarda una actividad
*/
package javafxsistemaescolar.interfaz;

public interface INotificacionOperacionActividad 
{    
    public void notificarOperacionActividadGuardar(String nombreActividad);
    public void notificarOperacionActividadActualizar(String nombreActividad);    
    
}

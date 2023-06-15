/* 
Autor: Miguel Martinez. 
Creado: 25/05/2023 
Modificado: 11/06/2023  
Descripción: Interfaz que se usa para notificar si se actualiza o guarda el cuerpo académico
*/
package javafxsistemaescolar.interfaz;

public interface INotificacionOperacionAcademico {
    public void notificarOperacionAcademicoGuardar(String nombreAcademico);
    public void notificarOperacionAcademicoActualizar(String nombreAcademico);
}

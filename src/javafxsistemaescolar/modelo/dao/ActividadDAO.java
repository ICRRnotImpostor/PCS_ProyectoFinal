/* 
Autor: Miguel Martinez. 
Creado: 26/05/2023 
Modificado: 11/06/2023  
Descripción: DAO de la actividad para actualizar los datos de la actividad
*/
package javafxsistemaescolar.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsistemaescolar.modelo.ConexionBD;
import javafxsistemaescolar.modelo.pojo.Actividad;
import javafxsistemaescolar.modelo.pojo.ActividadRespuesta;
import javafxsistemaescolar.utils.Constantes;


public class ActividadDAO 
{
    
    public static ActividadRespuesta obtenerInformacionActividades()
    {
        ActividadRespuesta respuesta =  new ActividadRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        
        if(conexionBD != null)
        {            
            try{
                String consulta = "SELECT idActividad, nombreActividad, descripcionActividad, "+
                        "documento, fechaInicio, fechaFin "+
                "FROM actividad";
                
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();  
                ArrayList<Actividad>actividadesConsulta = new ArrayList();
                while(resultado.next()){
                    Actividad actividad = new Actividad();
                    actividad.setIdActividad(resultado.getInt("idActividad"));
                    actividad.setNombreActividad(resultado.getString("nombreActividad"));
                    actividad.setDescripcionActividad(resultado.getString("descripcionActividad"));
                    actividad.setDocumento(resultado.getBytes("documento"));
                    actividad.setFechaInicio(resultado.getString("fechaInicio"));
                    actividad.setFechaFin(resultado.getString("fechaFin"));
                    actividadesConsulta.add(actividad);

                }
            respuesta.setActividades(actividadesConsulta);
            conexionBD.close();
        
        }catch(SQLException e)
        {
            e.printStackTrace();
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
                }
        }        
        else
        {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }       
    
    public static int guardarActividad(Actividad actividadNueva)
    {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();        
        if(conexionBD != null)
        {
            try{
                String sentencia = "INSERT INTO actividad(nombreActividad, descripcionActividad, "+
                        "documento, fechaInicio, fechaFin) "+
                        "VALUES(?, ?, ?, ?, ?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, actividadNueva.getNombreActividad());
                prepararSentencia.setString(2, actividadNueva.getDescripcionActividad());
                prepararSentencia.setBytes(3, actividadNueva.getDocumento());
                prepararSentencia.setString((4), actividadNueva.getFechaInicio());
                prepararSentencia.setString((5), actividadNueva.getFechaFin()); 
                
                int filasAfectadas = prepararSentencia.executeUpdate();                 
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : 
                        Constantes.ERROR_CONSULTA;
                conexionBD.close();
                
            }catch(SQLException e)
            {
                e.printStackTrace();
                respuesta = Constantes.ERROR_CONSULTA;
                System.err.println("Error al guardar la actividad: " + e.getMessage());
            }
        }else
        {
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;        
    }
    
    public static int modificarActividad(Actividad actividadEdicion)
    {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        
        if(conexionBD != null)
        {
            try{
                String sentencia = "UPDATE actividad "
                        +"SET nombreActividad = ?, descripcionActividad = ?, "
                        +"documento = ?, fechaInicio = ?, fechaFin = ? "
                        +"WHERE idActividad = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1,actividadEdicion.getNombreActividad());
                prepararSentencia.setString(2,actividadEdicion.getDescripcionActividad());
                prepararSentencia.setBytes(3,actividadEdicion.getDocumento());
                prepararSentencia.setString(4,actividadEdicion.getFechaInicio());
                prepararSentencia.setString(5,actividadEdicion.getFechaFin());
                prepararSentencia.setInt(6, actividadEdicion.getIdActividad());
                
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
                conexionBD.close();                
                
            }catch(SQLException e)
            {
                respuesta = Constantes.ERROR_CONSULTA;                
            }
        }else
        {
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }    
       
    public static  int eliminarActividad(int idActividad)
    {
        int respuesta;        
        Connection conexionBD = ConexionBD.abrirConexionBD();
        
        if(conexionBD != null)
        {
            try{
                String sentencia = "DELETE FROM actividad " 
                               +"WHERE idActividad = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idActividad); //1,idAlumno
            
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
                conexionBD.close();
                
            }catch(SQLException e)
            {
                respuesta = Constantes.ERROR_CONSULTA;
            }            
        }else
        {
            respuesta = Constantes.ERROR_CONEXION;
        }       
        return respuesta;
    }     
}

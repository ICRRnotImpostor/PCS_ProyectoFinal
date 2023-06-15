/* 
Autor: Ian Rumayor. 
Creado: 25/05/2023 
Modificado: 15/06/2023  
Descripción: Acceso a los datos de Alumno en la BD
*/
package javafxsistemaescolar.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsistemaescolar.modelo.ConexionBD;
import javafxsistemaescolar.modelo.pojo.Alumno;
import javafxsistemaescolar.modelo.pojo.AlumnoRespuesta;
import javafxsistemaescolar.utils.Constantes;

public class AlumnoDAO {
    public static AlumnoRespuesta buscarPorMatricula(){
        AlumnoRespuesta respuesta = new AlumnoRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        ArrayList<Alumno> alumno = new ArrayList<>();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "SELECT idAlumno, nombre, apellidoPaterno, "
                        +"apellidoMaterno, correo , matricula "
                        +"FROM Alumno WHERE idAnteproyecto is null";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                while(resultado.next()){
                    Alumno AlumnoResult = new Alumno();
                    AlumnoResult.setIdAlumno(resultado.getInt("idAlumno"));
                    AlumnoResult.setNombre(resultado.getString("nombre"));
                    AlumnoResult.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    AlumnoResult.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    AlumnoResult.setMatricula(resultado.getString("matricula"));
                    AlumnoResult.setCorreo(resultado.getString("correo"));
                    alumno.add(AlumnoResult);
                }    
                conexionBD.close();
                respuesta.setAlumnos(alumno);
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
                System.err.println("Error consulta: "+e.getMessage());
            }    
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        } 
        return respuesta;
    }
    
    public static AlumnoRespuesta alumnoPorAnteproyecto(int idAnteproyecto){
        AlumnoRespuesta respuesta = new AlumnoRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        ArrayList<Alumno> alumno = new ArrayList<>();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "SELECT idAlumno, nombre, apellidoPaterno, "
                        +"apellidoMaterno, matricula, correo, idAnteproyecto "
                        +"FROM alumno WHERE idAnteproyecto = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idAnteproyecto);
                ResultSet resultado = prepararSentencia.executeQuery();
                while(resultado.next()){
                    Alumno AlumnoResult = new Alumno();
                    
                    if(resultado.getInt("idAnteproyecto") == idAnteproyecto){
                    AlumnoResult.setIdAlumno(resultado.getInt("idAlumno"));
                    AlumnoResult.setNombre(resultado.getString("nombre"));
                    AlumnoResult.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    AlumnoResult.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    AlumnoResult.setMatricula(resultado.getString("matricula"));
                    AlumnoResult.setCorreo(resultado.getString("correo"));
                    alumno.add(AlumnoResult);
                    }   
                }    
                conexionBD.close();
                respuesta.setAlumnos(alumno);
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
                System.err.println("Error consulta: "+e.getMessage());
            }    
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        } 
        return respuesta;
    }
    
    public static int eliminarAlumnoAsignacion(Alumno alumnoEliminar){
        int respuesta;
         Connection conexionBD = ConexionBD.abrirConexionBD();  
        
         if(conexionBD != null)
         {
             try{
                 String sentencia = "UPDATE alumno SET idAnteproyecto = null WHERE idAlumno = ?";
                 
                 PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                 prepararSentencia.setInt(1, alumnoEliminar.getIdAlumno());
                 
                 int filasAfectadas = prepararSentencia.executeUpdate();
                 respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA :
                         Constantes.ERROR_CONSULTA;
                 conexionBD.close();
                 
             }catch(SQLException e)
             {
                 e.printStackTrace();
                respuesta = Constantes.ERROR_CONSULTA;
             }
         }else
         {
             respuesta = Constantes.ERROR_CONEXION;
         }
         return respuesta;
    }
    
    public static int alumnoAsignacion(Alumno alumnoAsignar){
        int respuesta;
         Connection conexionBD = ConexionBD.abrirConexionBD();  
        
         if(conexionBD != null)
         {
             try{
                 String sentencia = "UPDATE alumno SET idAnteproyecto = ? WHERE idAlumno = ?";
                 
                 PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                 prepararSentencia.setInt(1, alumnoAsignar.getIdAnteproyecto());
                 prepararSentencia.setInt(2, alumnoAsignar.getIdAlumno());
                 
                 int filasAfectadas = prepararSentencia.executeUpdate();
                 respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA :
                         Constantes.ERROR_CONSULTA;
                 conexionBD.close();
                 
             }catch(SQLException e)
             {
                 e.printStackTrace();
                respuesta = Constantes.ERROR_CONSULTA;
             }
         }else
         {
             respuesta = Constantes.ERROR_CONEXION;
         }
         return respuesta;
    }
}

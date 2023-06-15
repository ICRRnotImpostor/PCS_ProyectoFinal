/* 
Autor: Miguel Martinez. 
Creado: 28/05/2023 
Modificado: 11/06/2023  
Descripción: DAO para actualizar los datos del curso
*/
package javafxsistemaescolar.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsistemaescolar.modelo.ConexionBD;
import javafxsistemaescolar.modelo.pojo.Curso;
import javafxsistemaescolar.modelo.pojo.CursoRespuesta;
import javafxsistemaescolar.utils.Constantes;

public class CursoDAO
{    
    public static CursoRespuesta obtenerInformacionCursos()
    {
        CursoRespuesta respuesta = new CursoRespuesta();        
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        
        if(conexionBD != null)
        {
            try{
                String consulta = "SELECT idCurso, nombreCurso, "
                    +"periodo, fechaInicio, fechaFin "
                    +"FROM curso";
            
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();  
                ArrayList<Curso>cursoConsulta = new ArrayList();
                
                while(resultado.next()){
                    Curso curso = new Curso();
                    curso.setIdCurso(resultado.getInt("idCurso"));
                    curso.setNombreCurso(resultado.getString("nombreCurso"));
                    curso.setPeriodo(resultado.getString("periodo"));
                    curso.setFechaInicioCurso(resultado.getString("fechaInicio"));
                    curso.setFechaFinCurso(resultado.getString("fechaFin"));
                    cursoConsulta.add(curso);
                }
                respuesta.setCursos(cursoConsulta);
                conexionBD.close();
                
            }catch(SQLException e)
            {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);

            }        
            
        }else
        {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static int guardarCurso(Curso cursoNuevo)
    {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        
        if(conexionBD != null)
        {
            try{
                String sentencia = "INSERT INTO curso(nombreCurso, periodo, "
                        +"fechaInicio, fechaFin) "
                        +"VALUES(?, ?, ?, ?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, cursoNuevo.getNombreCurso());
                prepararSentencia.setString(2, cursoNuevo.getPeriodo());
                prepararSentencia.setString(3, cursoNuevo.getFechaInicioCurso());
                prepararSentencia.setString(4, cursoNuevo.getFechaFinCurso());
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

/* 
Autor: Ian Rumayor. 
Creado: 25/05/2023 
Modificado: 15/06/2023  
Descripción: Acceso a los datos de academico en la BD
*/
package javafxsistemaescolar.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsistemaescolar.modelo.ConexionBD;
import javafxsistemaescolar.modelo.pojo.Academico;
import javafxsistemaescolar.modelo.pojo.AcademicoRespuesta;
import javafxsistemaescolar.utils.Constantes;


public class AcademicoDAO {
    public static AcademicoRespuesta obtenerInformacionAcademico(){
        AcademicoRespuesta respuesta = new AcademicoRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        ArrayList<Academico> academico = new ArrayList<>();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "SELECT idAcademico, nombreAcademico, apellidoPaterno, "
                        +"apellidoMaterno, numeroDePersonal, correo "
                        +"FROM Academico ";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                while(resultado.next()){
                    Academico AcademicoResult = new Academico();
                    AcademicoResult.setIdAcademico(resultado.getInt("idAcademico"));
                    AcademicoResult.setNombreAcademico(resultado.getString("nombreAcademico"));
                    AcademicoResult.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    AcademicoResult.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    AcademicoResult.setNumeroDePersonal(resultado.getInt("numeroDePersonal"));
                    AcademicoResult.setCorreo(resultado.getString("correo"));
                    academico.add(AcademicoResult);
                }    
                conexionBD.close();
                respuesta.setAcademico(academico);
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
                System.err.println("Error consulta: "+e.getMessage());
            }    
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        } 
        return respuesta;
    }
    
    public static int guardarCuerpoAcademico(Academico AcademicoNuevo)
    {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        
         if(conexionBD != null)
         {
             try{
                 String sentencia = "INSERT INTO academico(nombreAcademico, apellidoPaterno, apellidoMaterno, "
                         +"numeroDePersonal, correo) "
                         +"VALUES(?, ?, ?, ?, ?)";
                 
                 PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                 prepararSentencia.setString(1, AcademicoNuevo.getNombreAcademico());
                 prepararSentencia.setString(2, AcademicoNuevo.getApellidoPaterno());
                 prepararSentencia.setString(3, AcademicoNuevo.getApellidoMaterno());
                 prepararSentencia.setInt(4, AcademicoNuevo.getNumeroDePersonal());
                 prepararSentencia.setString(5, AcademicoNuevo.getCorreo());
                 
                 int filasAfectadas = prepararSentencia.executeUpdate();                 
                 respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : 
                        Constantes.ERROR_CONSULTA;
                conexionBD.close();
                
             }catch(SQLException e)
             {
                 respuesta = Constantes.ERROR_CONSULTA;
             }
         }else{
            respuesta = Constantes.ERROR_CONEXION;
         }
         return respuesta;
    }
    
    public static int modificarCuerpoAcademico(Academico cuerpoAcademicoEdicion){
        int respuesta;
         Connection conexionBD = ConexionBD.abrirConexionBD();  
        
         if(conexionBD != null)
         {
             try{
                 String sentencia = "UPDATE academico "
                         +"SET nombreAcademico = ?, apellidoPaterno = ?, apellidoMaterno = ?, "
                         +"numeroDePersonal = ?, correo = ? "
                         +"WHERE idAcademico = ?";
                 
                 PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                 prepararSentencia.setString(1, cuerpoAcademicoEdicion.getNombreAcademico());
                 prepararSentencia.setString(2, cuerpoAcademicoEdicion.getApellidoPaterno());
                 prepararSentencia.setString(3, cuerpoAcademicoEdicion.getApellidoMaterno());
                 prepararSentencia.setInt(4, cuerpoAcademicoEdicion.getNumeroDePersonal());
                 prepararSentencia.setString(5, cuerpoAcademicoEdicion.getCorreo());
                 prepararSentencia.setInt(6, cuerpoAcademicoEdicion.getIdAcademico());
                 
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

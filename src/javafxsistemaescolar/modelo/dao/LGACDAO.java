/* 
Autor: Ian Rumayor. 
Creado: 28/05/2023 
Modificado: 15/06/2023  
Descripción: Acceso a los datos de LGAC en la BD
*/
package javafxsistemaescolar.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsistemaescolar.modelo.ConexionBD;
import javafxsistemaescolar.modelo.pojo.LGAC;
import javafxsistemaescolar.modelo.pojo.LGACRespuesta;
import javafxsistemaescolar.utils.Constantes;

public class LGACDAO {
    public static LGACRespuesta obtenerInformacionLGAC(){
        LGACRespuesta respuesta = new LGACRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        ArrayList<LGAC> lgac = new ArrayList<>();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "SELECT * FROM lgac ";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                while(resultado.next()){
                    LGAC lgacResult = new LGAC();
                    lgacResult.setIdLGAC(resultado.getInt("idLGAC"));
                    lgacResult.setNombre(resultado.getString("nombre"));
                    lgacResult.setDescripcion(resultado.getString("descripcion"));
                    lgac.add(lgacResult);
                }    
                conexionBD.close();
                respuesta.setLgac(lgac);
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
                System.err.println("Error consulta: "+e.getMessage());
            }    
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        } 
        return respuesta;
    }
    
    public static int guardarLGAC(LGAC nuevaLGAC){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String sentencia = "INSERT INTO lgac (nombre, descripcion, idCuerpoAcademico) "+
                                    "VALUES (?, ?, ?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, nuevaLGAC.getNombre());
                prepararSentencia.setString(2, nuevaLGAC.getDescripcion());
                prepararSentencia.setInt(3, nuevaLGAC.getIdCuerpoAcademico());
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
                conexionBD.close();
            } catch (SQLException e) {
                respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
}

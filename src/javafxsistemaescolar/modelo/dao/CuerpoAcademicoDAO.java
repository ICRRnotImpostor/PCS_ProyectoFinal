/* 
Autor: Ian Rumayor. 
Creado: 28/05/2023 
Modificado: 15/06/2023  
Descripción: Acceso a los datos de CuerpoAcademico en la BD
*/
package javafxsistemaescolar.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsistemaescolar.modelo.ConexionBD;
import javafxsistemaescolar.modelo.pojo.CuerpoAcademico;
import javafxsistemaescolar.modelo.pojo.CuerpoAcademicoRespuesta;
import javafxsistemaescolar.utils.Constantes;

public class CuerpoAcademicoDAO {
    public static CuerpoAcademicoRespuesta obtenerInformacionCuerpoAcademico(){
        CuerpoAcademicoRespuesta respuesta = new CuerpoAcademicoRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        ArrayList<CuerpoAcademico> cuerpoAcademico = new ArrayList<>();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "SELECT * FROM cuerpoAcademico ";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                while(resultado.next()){
                    CuerpoAcademico cuerpoAcademicoResult = new CuerpoAcademico();
                    cuerpoAcademicoResult.setIdCuerpoAcademico(resultado.getInt("idCuerpoAcademico"));
                    cuerpoAcademicoResult.setNombreCuerpoAcademico(resultado.getString("nombreCuerpoAcademico"));
                    cuerpoAcademico.add(cuerpoAcademicoResult);
                }    
                conexionBD.close();
                respuesta.setCuerpoAcademico(cuerpoAcademico);
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
                System.err.println("Error consulta: "+e.getMessage());
            }    
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        } 
        return respuesta;
    }
}

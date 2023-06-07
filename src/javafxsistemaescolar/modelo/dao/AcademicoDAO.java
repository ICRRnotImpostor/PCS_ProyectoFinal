/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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

/**
 *
 * @author ianca
 */
public class AcademicoDAO {
    public static AcademicoRespuesta obtenerInformacionAcademico(){
        AcademicoRespuesta respuesta = new AcademicoRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        ArrayList<Academico> academico = new ArrayList<>();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "SELECT * FROM Academico ";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                while(resultado.next()){
                    Academico AcademicoResult = new Academico();
                    AcademicoResult.setIdAcademico(resultado.getInt("idAcademico"));
                    AcademicoResult.setNombreAcademico(resultado.getString("nombreAcademico"));
                    AcademicoResult.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    AcademicoResult.setApellidoMaterno(resultado.getString("apellidoMaterno"));
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
}

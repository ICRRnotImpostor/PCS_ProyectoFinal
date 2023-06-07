/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxsistemaescolar.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafxsistemaescolar.modelo.ConexionBD;
import javafxsistemaescolar.modelo.pojo.Anteproyecto;
import javafxsistemaescolar.utils.Constantes;

/**
 *
 * @author ianca   15 p
 */
public class AnteproyectoDAO {
    public static int guardarAnteproyecto(Anteproyecto nuevoAnteproyecto){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String sentencia = "INSERT INTO anteproyecto (nombreProyecto, cuerpoAcademico, fecha, lineaDeInvestigacion, "
                                    + "duracionAproximada, modalidad, nombreTrabajoRecepcional, descripcionProyecto, requisitos, "
                                    + "bibliografiaRecomendada, resultadosEsperados, notas, idDirector, idCodirector, idLGAC) "+
                                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, nuevoAnteproyecto.getNombreProyecto());
                prepararSentencia.setString(2, nuevoAnteproyecto.getCuerpoAcademico());
                prepararSentencia.setString(3, nuevoAnteproyecto.getFecha());
                prepararSentencia.setString(4, nuevoAnteproyecto.getLineaDeInvestigacion());
                prepararSentencia.setString(5, nuevoAnteproyecto.getDuracionAproximada());
                prepararSentencia.setString(6, nuevoAnteproyecto.getModalidad());
                prepararSentencia.setString(7, nuevoAnteproyecto.getNombreTrabajoRecepcional());
                prepararSentencia.setString(8, nuevoAnteproyecto.getDescripcionProyecto());
                prepararSentencia.setString(9, nuevoAnteproyecto.getRequisitos());
                prepararSentencia.setString(10, nuevoAnteproyecto.getBibliografiaRecomendada());
                prepararSentencia.setString(11, nuevoAnteproyecto.getResultadosEsperados());
                prepararSentencia.setString(12, nuevoAnteproyecto.getNotas());
                prepararSentencia.setInt(13, nuevoAnteproyecto.getIdLGAC());
                prepararSentencia.setInt(14, nuevoAnteproyecto.getIdDirector());
                prepararSentencia.setInt(15, nuevoAnteproyecto.getIdCodirector());
                
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

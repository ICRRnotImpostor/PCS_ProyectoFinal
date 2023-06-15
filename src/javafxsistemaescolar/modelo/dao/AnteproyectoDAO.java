/* 
Autor: Ian Rumayor. 
Creado: 28/05/2023 
Modificado: 15/06/2023  
Descripción: Acceso a los datos de Anteproyecto en la BD
*/
package javafxsistemaescolar.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsistemaescolar.modelo.ConexionBD;
import javafxsistemaescolar.modelo.pojo.Anteproyecto;
import javafxsistemaescolar.modelo.pojo.AnteproyectoRespuesta;
import javafxsistemaescolar.utils.Constantes;

public class AnteproyectoDAO {
    
    public static AnteproyectoRespuesta recuperarAnteproyectosValidados(){
        AnteproyectoRespuesta respuesta = new AnteproyectoRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT idAnteproyecto, nombreProyecto, anteproyecto.idCuerpoAcademico, cuerpoacademico.nombreCuerpoAcademico AS cuerpoAcademico, fecha, " +
                        "lineaDeInvestigacion, duracionAproximada, modalidad, nombreTrabajoRecepcional," +
                        "descripcionProyecto, requisitos, bibliografiaRecomendada, resultadosEsperados, notas, idDirector, concat(director.nombreAcademico, ' ', " +
                        "director.apellidoPaterno, ' ', director.apellidoMaterno) AS director, idCodirector, anteproyecto.idLGAC, estado, observaciones FROM anteproyecto " +
                        "INNER JOIN academico AS director ON anteproyecto.idDirector = director.idAcademico " +
                        "INNER JOIN lgac ON anteproyecto.idLGAC = lgac.idLGAC " +
                        "INNER JOIN cuerpoacademico ON anteproyecto.idCuerpoAcademico = cuerpoacademico.idCuerpoAcademico WHERE estado = 'validado';";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Anteproyecto> anteproyectoConsulta = new ArrayList();
                while(resultado.next()){
                    Anteproyecto anteproyecto = new Anteproyecto();  
                                      
                    anteproyecto.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                    anteproyecto.setNombreProyecto(resultado.getString("nombreProyecto"));
                    anteproyecto.setIdCuerpoAcademico(resultado.getInt("idCuerpoAcademico"));
                    anteproyecto.setCuerpoAcademico(resultado.getString("cuerpoAcademico"));
                    anteproyecto.setFecha(resultado.getString("fecha"));
                    anteproyecto.setLineaDeInvestigacion(resultado.getString("lineaDeInvestigacion"));
                    anteproyecto.setDuracionAproximada(resultado.getString("duracionAproximada"));
                    anteproyecto.setModalidad(resultado.getString("modalidad"));
                    anteproyecto.setNombreTrabajoRecepcional(resultado.getString("nombreTrabajoRecepcional"));
                    anteproyecto.setDescripcionProyecto(resultado.getString("descripcionProyecto"));
                    anteproyecto.setRequisitos(resultado.getString("requisitos"));
                    anteproyecto.setBibliografiaRecomendada(resultado.getString("bibliografiaRecomendada"));
                    anteproyecto.setResultadosEsperados(resultado.getString("resultadosEsperados"));
                    anteproyecto.setNotas(resultado.getString("notas"));
                    anteproyecto.setIdDirector(resultado.getInt("idDirector"));
                    anteproyecto.setDirector(resultado.getString("director"));
                    anteproyecto.setIdCodirector(resultado.getInt("idCodirector"));
                    anteproyecto.setIdLGAC(resultado.getInt("idLGAC"));
                    anteproyecto.setEstado(resultado.getString("estado"));
                    anteproyecto.setObservaciones(resultado.getString("observaciones"));
                    
                    anteproyectoConsulta.add(anteproyecto);
                }
                respuesta.setAnteproyectos(anteproyectoConsulta);
                conexionBD.close();
            }catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            } 
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static AnteproyectoRespuesta recuperarAnteproyectosPendientes(){
        AnteproyectoRespuesta respuesta = new AnteproyectoRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT idAnteproyecto, nombreProyecto, anteproyecto.idCuerpoAcademico, cuerpoacademico.nombreCuerpoAcademico AS cuerpoAcademico, fecha, " +
                        "lineaDeInvestigacion, duracionAproximada, modalidad, nombreTrabajoRecepcional," +
                        "descripcionProyecto, requisitos, bibliografiaRecomendada, resultadosEsperados, notas, idDirector, concat(director.nombreAcademico, ' ', " +
                        "director.apellidoPaterno, ' ', director.apellidoMaterno) AS director, idCodirector, anteproyecto.idLGAC, estado, observaciones FROM anteproyecto " +
                        "INNER JOIN academico AS director ON anteproyecto.idDirector = director.idAcademico " +
                        "INNER JOIN lgac ON anteproyecto.idLGAC = lgac.idLGAC " +
                        "INNER JOIN cuerpoacademico ON anteproyecto.idCuerpoAcademico = cuerpoacademico.idCuerpoAcademico WHERE estado = 'pendiente';";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Anteproyecto> anteproyectoConsulta = new ArrayList();
                while(resultado.next()){
                    Anteproyecto anteproyecto = new Anteproyecto();  
                                      
                    anteproyecto.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                    anteproyecto.setNombreProyecto(resultado.getString("nombreProyecto"));
                    anteproyecto.setIdCuerpoAcademico(resultado.getInt("idCuerpoAcademico"));
                    anteproyecto.setCuerpoAcademico(resultado.getString("cuerpoAcademico"));
                    anteproyecto.setFecha(resultado.getString("fecha"));
                    anteproyecto.setLineaDeInvestigacion(resultado.getString("lineaDeInvestigacion"));
                    anteproyecto.setDuracionAproximada(resultado.getString("duracionAproximada"));
                    anteproyecto.setModalidad(resultado.getString("modalidad"));
                    anteproyecto.setNombreTrabajoRecepcional(resultado.getString("nombreTrabajoRecepcional"));
                    anteproyecto.setDescripcionProyecto(resultado.getString("descripcionProyecto"));
                    anteproyecto.setRequisitos(resultado.getString("requisitos"));
                    anteproyecto.setBibliografiaRecomendada(resultado.getString("bibliografiaRecomendada"));
                    anteproyecto.setResultadosEsperados(resultado.getString("resultadosEsperados"));
                    anteproyecto.setNotas(resultado.getString("notas"));
                    anteproyecto.setIdDirector(resultado.getInt("idDirector"));
                    anteproyecto.setDirector(resultado.getString("director"));
                    anteproyecto.setIdCodirector(resultado.getInt("idCodirector"));
                    anteproyecto.setIdLGAC(resultado.getInt("idLGAC"));
                    anteproyecto.setEstado(resultado.getString("estado"));
                    anteproyecto.setObservaciones(resultado.getString("observaciones"));
                    
                    anteproyectoConsulta.add(anteproyecto);
                }
                respuesta.setAnteproyectos(anteproyectoConsulta);
                conexionBD.close();
            }catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            } 
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static AnteproyectoRespuesta recuperarAnteproyectosSinPostular(){
        AnteproyectoRespuesta respuesta = new AnteproyectoRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT idAnteproyecto, nombreProyecto, anteproyecto.idCuerpoAcademico, cuerpoacademico.nombreCuerpoAcademico AS cuerpoAcademico, fecha, " +
                        "lineaDeInvestigacion, duracionAproximada, modalidad, nombreTrabajoRecepcional," +
                        "descripcionProyecto, requisitos, bibliografiaRecomendada, resultadosEsperados, notas, idDirector, concat(director.nombreAcademico, ' ', " +
                        "director.apellidoPaterno, ' ', director.apellidoMaterno) AS director, idCodirector, anteproyecto.idLGAC, estado, observaciones FROM anteproyecto " +
                        "INNER JOIN academico AS director ON anteproyecto.idDirector = director.idAcademico " +
                        "INNER JOIN lgac ON anteproyecto.idLGAC = lgac.idLGAC " +
                        "INNER JOIN cuerpoacademico ON anteproyecto.idCuerpoAcademico = cuerpoacademico.idCuerpoAcademico WHERE estado = 'sin postular';";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Anteproyecto> anteproyectoConsulta = new ArrayList();
                while(resultado.next()){
                    Anteproyecto anteproyecto = new Anteproyecto();  
                                      
                    anteproyecto.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                    anteproyecto.setNombreProyecto(resultado.getString("nombreProyecto"));
                    anteproyecto.setIdCuerpoAcademico(resultado.getInt("idCuerpoAcademico"));
                    anteproyecto.setCuerpoAcademico(resultado.getString("cuerpoAcademico"));
                    anteproyecto.setFecha(resultado.getString("fecha"));
                    anteproyecto.setLineaDeInvestigacion(resultado.getString("lineaDeInvestigacion"));
                    anteproyecto.setDuracionAproximada(resultado.getString("duracionAproximada"));
                    anteproyecto.setModalidad(resultado.getString("modalidad"));
                    anteproyecto.setNombreTrabajoRecepcional(resultado.getString("nombreTrabajoRecepcional"));
                    anteproyecto.setDescripcionProyecto(resultado.getString("descripcionProyecto"));
                    anteproyecto.setRequisitos(resultado.getString("requisitos"));
                    anteproyecto.setBibliografiaRecomendada(resultado.getString("bibliografiaRecomendada"));
                    anteproyecto.setResultadosEsperados(resultado.getString("resultadosEsperados"));
                    anteproyecto.setNotas(resultado.getString("notas"));
                    anteproyecto.setIdDirector(resultado.getInt("idDirector"));
                    anteproyecto.setDirector(resultado.getString("director"));
                    anteproyecto.setIdCodirector(resultado.getInt("idCodirector"));
                    anteproyecto.setIdLGAC(resultado.getInt("idLGAC"));
                    anteproyecto.setEstado(resultado.getString("estado"));
                    anteproyecto.setObservaciones(resultado.getString("observaciones"));
                    
                    anteproyectoConsulta.add(anteproyecto);
                }
                respuesta.setAnteproyectos(anteproyectoConsulta);
                conexionBD.close();
            }catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            } 
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static int modificarAnteproyecto(Anteproyecto anteproyectoEdicion){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String sentencia = "UPDATE anteproyecto SET nombreProyecto = ?, idCuerpoAcademico = ?, fecha = ?, lineaDeInvestigacion = ?, "
                                    + "duracionAproximada = ?, modalidad = ?, nombreTrabajoRecepcional = ?, descripcionProyecto = ?, requisitos = ?, "
                                    + "bibliografiaRecomendada = ?, resultadosEsperados = ?, notas = ?, idDirector = ?, idCodirector = ?, idLGAC = ? "+
                                    "WHERE idAnteproyecto = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                
                prepararSentencia.setString(1, anteproyectoEdicion.getNombreProyecto());
                prepararSentencia.setInt(2, anteproyectoEdicion.getIdCuerpoAcademico());
                prepararSentencia.setString(3, anteproyectoEdicion.getFecha());
                prepararSentencia.setString(4, anteproyectoEdicion.getLineaDeInvestigacion());
                prepararSentencia.setString(5, anteproyectoEdicion.getDuracionAproximada());
                prepararSentencia.setString(6, anteproyectoEdicion.getModalidad());
                prepararSentencia.setString(7, anteproyectoEdicion.getNombreTrabajoRecepcional());
                prepararSentencia.setString(8, anteproyectoEdicion.getDescripcionProyecto());
                prepararSentencia.setString(9, anteproyectoEdicion.getRequisitos());
                prepararSentencia.setString(10, anteproyectoEdicion.getBibliografiaRecomendada());
                prepararSentencia.setString(11, anteproyectoEdicion.getResultadosEsperados());
                prepararSentencia.setString(12, anteproyectoEdicion.getNotas());
                prepararSentencia.setInt(13, anteproyectoEdicion.getIdDirector());
                prepararSentencia.setInt(14, anteproyectoEdicion.getIdCodirector());
                prepararSentencia.setInt(15, anteproyectoEdicion.getIdLGAC());
                prepararSentencia.setInt(16, anteproyectoEdicion.getIdAnteproyecto());
                
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
    
    public static int ingresarObservacionesAnteproyecto(Anteproyecto anteproyectoObservaciones){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();       
        if(conexionBD != null){
            try {
                String sentencia = "UPDATE anteproyecto SET observaciones = ? WHERE idAnteproyecto = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, anteproyectoObservaciones.getObservaciones());
                prepararSentencia.setInt(2, anteproyectoObservaciones.getIdAnteproyecto());
                
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
    
    public static int validarAnteproyecto(Anteproyecto anteproyectoValidado){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();       
        if(conexionBD != null){
            try {
                String sentencia = "UPDATE anteproyecto SET estado = ? WHERE idAnteproyecto = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, anteproyectoValidado.getEstado());
                prepararSentencia.setInt(2, anteproyectoValidado.getIdAnteproyecto());
                
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
    
    public static int ppguardarAnteproyecto(Anteproyecto nuevoAnteproyecto){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String sentencia = "INSERT INTO anteproyecto (nombreProyecto, idCuerpoAcademico, fecha, lineaDeInvestigacion, "
                                    + "duracionAproximada, modalidad, nombreTrabajoRecepcional, descripcionProyecto, requisitos, "
                                    + "bibliografiaRecomendada, resultadosEsperados, notas, idDirector, idCodirector, idLGAC, estado) "+
                                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
                
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, nuevoAnteproyecto.getNombreProyecto());
                prepararSentencia.setInt(2, nuevoAnteproyecto.getIdCuerpoAcademico());
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
                prepararSentencia.setInt(13, nuevoAnteproyecto.getIdDirector());
                prepararSentencia.setInt(14, nuevoAnteproyecto.getIdCodirector());
                prepararSentencia.setInt(15, nuevoAnteproyecto.getIdLGAC());
                
                prepararSentencia.setString(16, nuevoAnteproyecto.getEstado());

                
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
    
    public static int guardarAnteproyecto(Anteproyecto nuevoAnteproyecto){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String sentencia = "INSERT INTO anteproyecto (nombreProyecto, idCuerpoAcademico, fecha, lineaDeInvestigacion, "
                                    + "duracionAproximada, modalidad, nombreTrabajoRecepcional, descripcionProyecto, requisitos, "
                                    + "bibliografiaRecomendada, resultadosEsperados, notas, idDirector, idCodirector, idLGAC, estado) "+
                                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
                
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, nuevoAnteproyecto.getNombreProyecto());
                prepararSentencia.setInt(2, nuevoAnteproyecto.getIdCuerpoAcademico());
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
                prepararSentencia.setInt(13, nuevoAnteproyecto.getIdDirector());
                prepararSentencia.setInt(14, nuevoAnteproyecto.getIdCodirector());
                prepararSentencia.setInt(15, nuevoAnteproyecto.getIdLGAC());
                prepararSentencia.setString(16, nuevoAnteproyecto.getEstado());
                
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


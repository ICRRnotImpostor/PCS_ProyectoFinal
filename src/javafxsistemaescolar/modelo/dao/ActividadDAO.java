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
import javafxsistemaescolar.modelo.pojo.Actividad;
import javafxsistemaescolar.modelo.pojo.ActividadRespuesta;
import javafxsistemaescolar.utils.Constantes;

/**
 *
 * @author migue
 */
public class ActividadDAO {
    
    public static ActividadRespuesta obtenerInformacionActividades(){
        ActividadRespuesta respuesta =  new ActividadRespuesta();

        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);

        if(conexionBD != null){
            try{
                String consulta = "SELECT idActividad, nombre, doc, fechaInicio, fechaFin "+
                "FROM actividad";
                
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();  
                ArrayList<Actividad>actividadesConsulta = new ArrayList();
                while(resultado.next()){
                    Actividad actividad = new Actividad();
                    actividad.setIdActividad(resultado.getInt("idActividad"));
                    actividad.setNombre(resultado.getString("nombre"));
                    actividad.setDoc(resultado.getBytes("doc"));
                    actividad.setFechaInicio(resultado.getString("fechaInicio"));
                    actividad.setFechaFin(resultado.getString("fechaFin"));
                    actividadesConsulta.add(actividad);

                }
            respuesta.setActividades(actividadesConsulta);
            conexionBD.close();
        
        }catch(SQLException e){
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);

                }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);

        }
        return respuesta;
    }
}
    

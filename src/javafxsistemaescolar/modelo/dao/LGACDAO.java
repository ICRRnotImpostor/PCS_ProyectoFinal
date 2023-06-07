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
import javafxsistemaescolar.modelo.pojo.LGAC;
import javafxsistemaescolar.modelo.pojo.LGACRespuesta;
import javafxsistemaescolar.utils.Constantes;

/**
 *
 * @author ianca
 */
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
}

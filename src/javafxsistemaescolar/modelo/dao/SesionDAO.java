/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxsistemaescolar.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafxsistemaescolar.modelo.ConexionBD;
import javafxsistemaescolar.modelo.pojo.Usuario;
import javafxsistemaescolar.utils.Constantes;

/**
 *
 * @author migue
 */
public class SesionDAO {
    
    public static Usuario verificarUsuarioSesion(String usuario, String password){
        
        Usuario usuarioVerificado = new Usuario();        
        Connection conexion = ConexionBD.abrirConexionBD();
        if(conexion != null){
            try {
                
                String consulta = "SELECT * FROM usuario WHERE username = ? AND password = ?"; 
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                
                prepararSentencia.setString(1, usuario);
                prepararSentencia.setString(2, password);
                
                ResultSet resultado = prepararSentencia.executeQuery();
                
                usuarioVerificado.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                                
                if(resultado.next()){
                    usuarioVerificado.setIdUsuario(resultado.getInt("idUsuario"));
                    usuarioVerificado.setNombre(resultado.getString("nombre"));
                    usuarioVerificado.setApellidoPaterno(resultado.getString("ApellidoPaterno"));
                    usuarioVerificado.setApellidoMaterno(resultado.getString("ApellidoMaterno"));
                    usuarioVerificado.setUsername(resultado.getString("Username"));
                    usuarioVerificado.setPassword(resultado.getString("Password"));
                                        
                }
                
                //Cerrando la conexion, 
                conexion.close();
            } catch (SQLException ex) {
                usuarioVerificado.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
          // si no es null 
        }else{
            usuarioVerificado.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        
        return usuarioVerificado;
    }
    
}

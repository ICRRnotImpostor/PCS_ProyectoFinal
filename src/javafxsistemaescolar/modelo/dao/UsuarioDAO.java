/* 
Autor: Miguel Martinez. 
Creado: 28/05/2023 
Modificado: 11/06/2023  
Descripción: DAO para actualizar los datos del usuario
*/
package javafxsistemaescolar.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsistemaescolar.modelo.ConexionBD;
import javafxsistemaescolar.modelo.pojo.Usuario;
import javafxsistemaescolar.modelo.pojo.UsuarioRespuesta;
import javafxsistemaescolar.utils.Constantes;

public class UsuarioDAO 
{    
    public static UsuarioRespuesta obtenerInformacionUsuarios()
    {
        UsuarioRespuesta respuesta = new UsuarioRespuesta();        
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);

        if(conexionBD != null)
        {
            try{
                String consulta = "SELECT nombre, apellidoPaterno, apellidoMaterno, privilegios "+
                        "FROM usuario ";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Usuario>usuarioConsulta = new ArrayList();
                while(resultado.next())
                {
                    Usuario usuario = new Usuario();
                    usuario.setNombre(resultado.getString("nombre"));
                    usuario.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    usuario.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    usuario.setPrivilegios(resultado.getString("privilegios"));
                    usuarioConsulta.add(usuario);                    
                }                
                respuesta.setUsuarios(usuarioConsulta);
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
    
    public static int guardarUsuario(Usuario usuarioNuevo)
    {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        
        if(conexionBD != null)
        {
            try{
                String sentencia = "INSERT INTO usuario (nombre, apellidoPaterno, "
                    +"apellidoMaterno, privilegios, username, password) "
                    +"VALUES(?, ?, ?, ?, ?, ?)";
            
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, usuarioNuevo.getNombre());
                prepararSentencia.setString(2, usuarioNuevo.getApellidoPaterno());
                prepararSentencia.setString(3, usuarioNuevo.getApellidoMaterno());
                prepararSentencia.setString(4, usuarioNuevo.getPrivilegios());
                prepararSentencia.setString(5, usuarioNuevo.getUsername());
                prepararSentencia.setString(6, usuarioNuevo.getPassword());
                
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

/* 
Autor: Miguel Martinez. 
Creado: 21/05/2023 
Modificado: 11/06/2023  
Descripción: Respuesta del usuario
*/
package javafxsistemaescolar.modelo.pojo;

import java.util.ArrayList;

public class UsuarioRespuesta 
{
    private int codigoRespuesta;
    private ArrayList<Usuario> usuarios;

    public UsuarioRespuesta()
    {
    }

    public UsuarioRespuesta(int codigoRespuesta, ArrayList<Usuario> usuarios) 
    {
        this.codigoRespuesta = codigoRespuesta;
        this.usuarios = usuarios;
    }

    public int getCodigoRespuesta() 
    {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) 
    {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Usuario> getUsuarios() 
    {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios)
    {
        this.usuarios = usuarios;
    }
    
    
    
}

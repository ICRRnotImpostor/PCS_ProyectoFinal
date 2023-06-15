/* 
Autor: Ian Rumayor. 
Creado: 28/05/2023 
Modificado: 15/06/2023  
Descripción: Clase que implementa los objetos de tipo Usuario
*/
package javafxsistemaescolar.modelo.pojo;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String username;
    private String password;
    private String privilegios;
    //
    private int codigoRespuesta;

    public Usuario() {
        
    }

    public Usuario(int idUsuario, String nombre, String apellidoPaterno, String apellidoMaterno, String username, String password, String privilegios, int codigoRespuesta) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.username = username;
        this.password = password;
        this.privilegios = privilegios;
        this.codigoRespuesta = codigoRespuesta;
    }

    

    public Usuario(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }
    
    
   

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }    

    public String getPrivilegios() {
        return privilegios;
    }

    public void setPrivilegios(String privilegios) {
        this.privilegios = privilegios;
    }
   
    
    
    @Override
    public String toString(){
        return nombre+" "+apellidoPaterno+" "+apellidoMaterno;
    }
    
    
}

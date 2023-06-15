/* 
Autor: Miguel Martinez. 
Creado: 05/06/2023 
Modificado: 15/06/2023  
Descripción: Este formulario sirve para guardar datos del usuario
             que el administrador guardará.
*/ 
package javafxsistemaescolar.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxsistemaescolar.interfaz.INotificacionOperacionUsuario;
import javafxsistemaescolar.modelo.dao.UsuarioDAO;
import javafxsistemaescolar.modelo.pojo.Usuario;
import javafxsistemaescolar.utils.Constantes;
import javafxsistemaescolar.utils.Utilidades;

public class FXMLFormularioUsuarioController implements Initializable 
{

    @FXML
    private Label lbTitulo;
    @FXML
    private TextField tfNombreUsuario;
    @FXML
    private ComboBox<String> cbTipoUsuario;
    @FXML
    private TextField tfApellidoPaterno;
    @FXML
    private TextField tfApellidoMaterno;
    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfPassword;
    
    private ObservableList<Usuario> usuarios;
    private INotificacionOperacionUsuario interfazNotificacionUsuario;
    private boolean esEdicion;
    private Usuario usuarioEdicion;    
    String estiloError = "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    String estiloNormal = "-fx-border-width: 0;";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        agregarComboBox();
    }    

    public void inicializarInformacionFormulario(boolean esEdicion,
            Usuario usuarioEdicion, INotificacionOperacionUsuario interfazNotificacionUsuario)
    {
        this.esEdicion = esEdicion;
        this.usuarioEdicion = usuarioEdicion;
        this.interfazNotificacionUsuario = interfazNotificacionUsuario;
        
        if(esEdicion)
        {
            lbTitulo.setText("Editar informacion del producto "+usuarioEdicion.getNombre());           
        }else
        {
            lbTitulo.setText("Registrar nuevo usuario");
        } 
    }       

    @FXML
    private void clickBtnGuardar(ActionEvent event) 
    {
        validarCamposRegistro();   
    }
    
    private void validarCamposRegistro()
    {
        establecerEstiloNormal();
        boolean datosValidados = true;        
        String nombre = tfNombreUsuario.getText();
        String apellidoPaterno = tfApellidoPaterno.getText();
        String apellidoMaterno =  tfApellidoMaterno.getText();
        String tipoUsuario = cbTipoUsuario.getValue();
        String username = tfUsername.getText();
        String password = tfPassword.getText();
        
        if(nombre.isEmpty())
        {
            tfNombreUsuario.setStyle(estiloError);
            datosValidados = false;
        }
        if(apellidoPaterno.isEmpty())
        {
            tfApellidoPaterno.setStyle(estiloError);
            datosValidados = false;
        }
        if(apellidoMaterno.isEmpty())
        {
            tfApellidoMaterno.setStyle(estiloError);
            datosValidados = false;
        }
        if(tipoUsuario == null)
        {
            cbTipoUsuario.setStyle(estiloError);
            datosValidados = false;
        }
        if(username.isEmpty())
        {
            tfUsername.setStyle(estiloError);
            datosValidados = false;
        }
        if(password.isEmpty())
        {
            tfPassword.setStyle(estiloError);
            datosValidados = false;
        }             
        
        if(datosValidados)
        {
            Usuario usuarioValidado = new Usuario();
            usuarioValidado.setNombre(nombre);
            usuarioValidado.setApellidoPaterno(apellidoPaterno);
            usuarioValidado.setApellidoMaterno(apellidoMaterno);
            usuarioValidado.setPrivilegios(tipoUsuario);
            usuarioValidado.setUsername(username);
            usuarioValidado.setPassword(password);            
            registrarUsuario(usuarioValidado);
            
        }else
        {
            Utilidades.mostrarDialogoSimple("Campos invalidos",
                    "Hay campos invalidos, por favor corrija los campos solicitados",
                    Alert.AlertType.WARNING);
        }             
    }
    
    private void registrarUsuario(Usuario usuarioRegistro)
    {
        int codigoRespuesta = UsuarioDAO.guardarUsuario(usuarioRegistro);
        switch(codigoRespuesta)
        {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion al registrar el usuario ",
                        "El usuario no pudo ser guardado debido a un error de conexion",
                        Alert.AlertType.ERROR);
                break;
                
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la informacion en registrar al usuario",
                        "La informacion del usuario no puede ser guardada, por favor verifique su conexion",
                        Alert.AlertType.WARNING);
                break;
                
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Usuario registrado",
                        "La informacion del usuario fue guardada correctamente",
                        Alert.AlertType.INFORMATION);
                
                cerrarVentana();     
                interfazNotificacionUsuario.notificarOperacionUsuarioGuardar(usuarioRegistro.getNombre());                
                break;
        }
    }
    
    private void agregarComboBox()
    {
        cbTipoUsuario.setItems(FXCollections.observableArrayList("estudiante", "academico", "administrador"));
    }    
    
    private void establecerEstiloNormal()
    {
        tfNombreUsuario.setStyle(estiloNormal);
        tfApellidoPaterno.setStyle(estiloNormal);
        tfApellidoMaterno.setStyle(estiloNormal);
        cbTipoUsuario.setStyle(estiloNormal);
        tfUsername.setStyle(estiloNormal);
        tfPassword.setStyle(estiloNormal);        
    }
    
     @FXML
    private void clickBtnCancelar(ActionEvent event) 
    {
             cerrarVentana();
    }
    
    private void cerrarVentana()
    {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.close();
    }    
}
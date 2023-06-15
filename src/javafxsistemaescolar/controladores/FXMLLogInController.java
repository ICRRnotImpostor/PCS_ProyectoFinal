
package javafxsistemaescolar.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxsistemaescolar.modelo.dao.SesionDAO;
import javafxsistemaescolar.modelo.pojo.Usuario;
import javafxsistemaescolar.utils.Constantes;
import javafxsistemaescolar.utils.Utilidades;

public class FXMLLogInController implements Initializable {

    @FXML
    private TextField tfUsuario;
    @FXML
    private Label lbErrorUsuario;
    @FXML
    private Label lbErrorPassword;
    @FXML
    private PasswordField tfPassword;

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void clickIniciarSesion(ActionEvent event) {
        lbErrorUsuario.setText("");
        lbErrorPassword.setText("");
        validarCampos();
    }
    
    private void validarCampos(){        
        String usuario = tfUsuario.getText();
        String password = tfPassword.getText();
        boolean sonValidos = true;        
        
        if(usuario.isEmpty()){
            sonValidos = false;
            lbErrorUsuario.setText("El campo de usuario es requerido. ");
        }
        if(password.length() == 0){
            sonValidos = false;
            lbErrorPassword.setText("El campo contrasenia es requerido");
        }
        if(sonValidos){
            validarCredencialesUsuarios(usuario, password);            
        }
    }
    
    private void validarCredencialesUsuarios(String usuario, String password){
        Usuario usuarioRespuesta = SesionDAO.verificarUsuarioSesion(usuario, password);
        
        switch(usuarioRespuesta.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion",
                        "Por el momento no hay conexion, intentalo mas tarde",
                        Alert.AlertType.ERROR);
                break;           
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la solicitud",
                        "Por el momento no se puede procesar la solicitud de verificacion",
                        Alert.AlertType.ERROR);                
                break;        
            case Constantes.OPERACION_EXITOSA:
                if(usuarioRespuesta.getIdUsuario() > 0){
                    String priv = usuarioRespuesta.getPrivilegios();
                        Utilidades.mostrarDialogoSimple("Bienvenido(a)",
                                "Bienvenido "+usuarioRespuesta.toString()+ " al sistema...",
                    Alert.AlertType.INFORMATION);
                        switch(priv){
                            case "academico":
                                irPantallaPrincipalDirector();
                                break;
                            case "responsable":
                                irPantallaGestionAnteproyector();
                                break;
                            case "estudiante":
                                irGestorCronograma();
                                break;
                            case "administrador":
                                irPantallaAdministracion();
                                break;
                        }
                }else{
                    Utilidades.mostrarDialogoSimple("Credenciales incorrectas","El usuario y/o contrasenia no son correctos, validar informacion",Alert.AlertType.WARNING);
                }
                break;  
            default:
                Utilidades.mostrarDialogoSimple("Error de peticion",
                        "El sistema no esta disponible por el momento", Alert.AlertType.ERROR); 
        }
    }
    
    private void irPantallaPrincipalDirector(){        
            Stage Menu = (Stage) tfUsuario.getScene().getWindow();
            
            Menu.setScene(Utilidades.inicializarEscena("vistas/FXMLMenuAcademico.fxml"));
            Menu.setTitle("Inicio");
            Menu.show();
    } 
    
    private void irGestorCronograma(){
        Stage gestorCronograma = (Stage) tfUsuario.getScene().getWindow();
        
        gestorCronograma.setScene(Utilidades.inicializarEscena("vistas/FXMLGestionCronograma.fxml"));
        gestorCronograma.setTitle("Gestor de Cronograma");
        gestorCronograma.show();
    }
    
    private void irPantallaAdministracion(){
        Stage gestorCronograma = (Stage) tfUsuario.getScene().getWindow();
        
        gestorCronograma.setScene(Utilidades.inicializarEscena("vistas/FXMLGestionAdministracion.fxml"));
        gestorCronograma.setTitle("Administracion");
        gestorCronograma.show();
    }
    
    private void irPantallaGestionAnteproyector(){
        Stage MenuRCA = (Stage) tfUsuario.getScene().getWindow();
        
        MenuRCA.setScene(Utilidades.inicializarEscena("vistas/FXMLAnteproyectosAdminRCA.fxml"));
        MenuRCA.setTitle("Inicio");
        MenuRCA.show();
    }
}

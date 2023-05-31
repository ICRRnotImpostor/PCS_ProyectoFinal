/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
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
        // TODO
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
                //0 es el valor default
                if(usuarioRespuesta.getIdUsuario() > 0){
                      Utilidades.mostrarDialogoSimple("Bienvenido(a)",
                        "Bienvenido "+usuarioRespuesta.toString()+ " al sistema...",
                    Alert.AlertType.INFORMATION);
                      irPantallaPrincipal();
                      
                     //Si el idUsuario no es menor a cer 
                }else{
                    Utilidades.mostrarDialogoSimple("Credenciales incorrectas",
                            "El usuario y/o contrasenia no son correctos, validar informacion",
                            Alert.AlertType.WARNING);
                    
                }
                    
          
                break;
            
            default:
                Utilidades.mostrarDialogoSimple("Error de peticion",
                        "El sistema no esta disponible por el momento", Alert.AlertType.ERROR);
                
        }
       //System.out.println("Codigo " +usuarioRespuesta.getCodigoRespuesta());
       
    }
    
    private void irPantallaPrincipal(){        
       
            //no importa cual elemento se traiga, va a direccionar a la escena tfUsuario, se hace un casteo (Stage)
            Stage escenarioBase = (Stage) tfUsuario.getScene().getWindow();
            escenarioBase.setScene(Utilidades.inicializarEscena("vistas/FXMLMenuPrincipal.fxml"));
            //Titulo asociado al escenario no a la escena
            escenarioBase.setTitle("Home");
            escenarioBase.show();
            
        
        
    } 
    
    
}

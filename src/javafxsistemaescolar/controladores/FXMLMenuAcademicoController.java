/* 
Autor: Ian Rumayor. 
Creado: 28/05/2023 
Modificado: 15/06/2023  
Descripción: El controlador solo fue creado para poder introducir dos modulos del actor academico
*/
package javafxsistemaescolar.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsistemaescolar.utils.Utilidades;

public class FXMLMenuAcademicoController implements Initializable {

    @FXML
    private Button btnRegresar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void clickBtnAnteproyecto(ActionEvent event) {
        Stage escenarioAnteproyectos = new Stage();
        escenarioAnteproyectos.setScene(Utilidades.inicializarEscena("vistas/FXMLAnteproyectosAdmin.fxml"));
        escenarioAnteproyectos.setTitle("Administradoción de Anteproyectos");
        escenarioAnteproyectos.initModality(Modality.APPLICATION_MODAL);
        escenarioAnteproyectos.showAndWait();
    }

    @FXML
    private void clicBtnLGAC(ActionEvent event) {
        Stage escenarioLGAC = new Stage();
        escenarioLGAC.setScene(Utilidades.inicializarEscena("vistas/FXMLLGACAdmin.fxml"));
        escenarioLGAC.setTitle("Líneas de Generación y Aplicación del Conocimiento");
        escenarioLGAC.initModality(Modality.APPLICATION_MODAL);
        escenarioLGAC.showAndWait();
    }

    @FXML
    private void clicBtnRegresar(ActionEvent event) {
        cerrarVentana();
        Stage salir = new Stage();
        salir.setScene(Utilidades.inicializarEscena("vistas/FXMLLogIn.fxml"));
        salir.setTitle("Inicio de sesion");
        salir.initModality(Modality.APPLICATION_MODAL);
        salir.showAndWait();
        
    }
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) btnRegresar.getScene().getWindow();
        escenarioBase.close();
    }
}

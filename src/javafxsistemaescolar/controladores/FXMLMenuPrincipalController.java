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
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsistemaescolar.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author migue
 */
public class FXMLMenuPrincipalController implements Initializable {

    @FXML
    private Label label;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clickIrGestionCronograma(ActionEvent event) {
        Stage escenarioAlumnos = new Stage();
        escenarioAlumnos.setScene(Utilidades.inicializarEscena("vistas/FXMLGestionCronograma.fxml"));
        escenarioAlumnos.setTitle("Administracion de cronograma");
        escenarioAlumnos.initModality(Modality.APPLICATION_MODAL);
        escenarioAlumnos.showAndWait();
    }

    @FXML
    private void clickGestionAdministracion(ActionEvent event) {
    }

    @FXML
    private void clickBtnAnteproyecto(ActionEvent event) {
        Stage escenarioAnteproyectos = new Stage();
        escenarioAnteproyectos.setScene(Utilidades.inicializarEscena("vistas/FXMLAnteproyectos.fxml"));
        escenarioAnteproyectos.setTitle("Administradoción de Anteproyectos");
        escenarioAnteproyectos.initModality(Modality.APPLICATION_MODAL);
        escenarioAnteproyectos.showAndWait();
    }
    
}

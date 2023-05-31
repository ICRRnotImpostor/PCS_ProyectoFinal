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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsistemaescolar.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author migue
 */
public class FXMLGestionCronogramaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clickBtnRegistrar(ActionEvent event) {
    }

    @FXML
    private void clickBtnModificar(ActionEvent event) {
    }

    @FXML
    private void clickBtnVer(ActionEvent event) {
        Stage escenarioAlumnos = new Stage();
        escenarioAlumnos.setScene(Utilidades.inicializarEscena("vistas/FXMLVerActividad.fxml"));
        escenarioAlumnos.setTitle("Ver actividad");
        //Especifica como se va a mostrar la modaliddad(comportamiento) del escenario
        escenarioAlumnos.initModality(Modality.APPLICATION_MODAL);
        //Volver a ejecutar cuando se cierra
        escenarioAlumnos.showAndWait();
    }

    @FXML
    private void clickBtnEliminar(ActionEvent event) {
    }
    
}

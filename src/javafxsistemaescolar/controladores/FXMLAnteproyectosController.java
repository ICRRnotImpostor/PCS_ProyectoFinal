/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxsistemaescolar.controladores;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsistemaescolar.modelo.pojo.Anteproyecto;
import javafxsistemaescolar.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author ianca
 */
public class FXMLAnteproyectosController implements Initializable {

    @FXML
    private TextField tfBusqueda;
    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnVerDetalles;
    @FXML
    private TableView<?> tvAnteproyectos;
    @FXML
    private TableColumn<?, ?> colNombreProyecto;
    @FXML
    private TableColumn<?, ?> colCuerpoAcademico;
    @FXML
    private TableColumn<?, ?> colDirector;
    @FXML
    private TableColumn<?, ?> colFecha;
    
    
    private ObservableList<Anteproyecto> anteproyecto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }    

    @FXML
    private void clicBtnRegresar(ActionEvent event) {
        Stage scenarioAnteproyectos = (Stage) this.btnRegresar.getScene().getWindow();
        scenarioAnteproyectos.close();
    }

    @FXML
    private void clicBtnRegistrar(ActionEvent event) {
        Stage escenarioRegistroAnteproyecto = new Stage();
        escenarioRegistroAnteproyecto.setScene(Utilidades.inicializarEscena("vistas/FXMLRegistrarAnteproyecto.fxml"));
        escenarioRegistroAnteproyecto.setTitle("Registro de Anteproyecto");
        escenarioRegistroAnteproyecto.initModality(Modality.APPLICATION_MODAL);
        escenarioRegistroAnteproyecto.showAndWait();
    }

    @FXML
    private void clicBtnModificar(ActionEvent event) {
        Stage escenarioModificarAnteproyecto = new Stage();
        escenarioModificarAnteproyecto.setScene(Utilidades.inicializarEscena("vistas/FXMLModificarAnteproyecto.fxml"));
        escenarioModificarAnteproyecto.setTitle("Modificar Anteproyecto");
        escenarioModificarAnteproyecto.initModality(Modality.APPLICATION_MODAL);
        escenarioModificarAnteproyecto.showAndWait();
    }

    @FXML
    private void clicEliminar(ActionEvent event) {
    }

    @FXML
    private void clicBtnPendientes(ActionEvent event) {
    }

    @FXML
    private void clicBtnVerDetalles(ActionEvent event) {
        Stage escenarioDetallesAnteproyecto = new Stage();
        escenarioDetallesAnteproyecto.setScene(Utilidades.inicializarEscena("vistas/FXMLDetallesAnteproyectos.fxml"));
        escenarioDetallesAnteproyecto.setTitle("Detalles Anteproyecto");
        escenarioDetallesAnteproyecto.initModality(Modality.APPLICATION_MODAL);
        escenarioDetallesAnteproyecto.showAndWait();
    }
    
    private void configurarTabla(){
        colNombreProyecto.setCellValueFactory(new PropertyValueFactory("Nombre del proyecto de investigación"));
        colCuerpoAcademico.setCellValueFactory(new PropertyValueFactory("Cuerpo Academico"));
        colDirector.setCellValueFactory(new PropertyValueFactory("Director"));
        colFecha.setCellValueFactory(new PropertyValueFactory("Fecha"));
        
        tvAnteproyectos.widthProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                TableHeaderRow header = (TableHeaderRow) tvAnteproyectos.lookup("TableHeaderRow");
                header.reorderingProperty().addListener(new ChangeListener<Boolean>(){
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        header.setReordering(false);
                    }
                });
            }
        });
    }
    
    /*private void cargarInformacionTabla(){
        anteproyecto = FXCollections.observableArrayList();
        AlumnoRespuesta respuestaBD = AlumnoDAO.obtenerInformacionAlumnos();
        switch(respuestaBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                  Utilidades.mostrarDialogoSimple("Sin conexión", 
                          "Lo sentimos por el momento no hay conexión para poder cargar la información.", 
                          Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                  Utilidades.mostrarDialogoSimple("Error al cargar los datos", 
                          "Hubo un error al cargar la información por favor inténtelo más tarde.", 
                          Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                  anteproyecto.addAll(respuestaBD.getAlumnos());
                  tvAlumnos.setItems(anteproyecto);
                  configurarBusquedaTabla();
                break;    
        }
    }*/
    
}

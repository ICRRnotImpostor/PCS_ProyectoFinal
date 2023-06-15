/* 
Autor: Ian Rumayor. 
Creado: 02/06/2023 
Modificado: 12/06/2023  
Descripción: Controlador de la ventana de administracion de LGAC, funciones implementadas segun lo solicitado: Visualizar LGAC y añadir LGAC
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsistemaescolar.modelo.dao.LGACDAO;
import javafxsistemaescolar.modelo.pojo.LGAC;
import javafxsistemaescolar.modelo.pojo.LGACRespuesta;
import javafxsistemaescolar.utils.Constantes;
import javafxsistemaescolar.utils.Utilidades;

public class FXMLLGACAdminController implements Initializable {

    @FXML
    private TableView<LGAC> tvLGAC;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private Button btnRegresar;
    
    private ObservableList<LGAC> lgac;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarTabla();
    }    

    @FXML
    private void clicBtnRegresar(ActionEvent event) {
        Stage scenarioMenuAcademicos = (Stage) this.btnRegresar.getScene().getWindow();
        scenarioMenuAcademicos.close();
    }

    @FXML
    private void clicBtnNuevaLGAC(ActionEvent event) {
        Stage escenarioNuevaLGAC = new Stage();
        escenarioNuevaLGAC.setScene(Utilidades.inicializarEscena("vistas/FXMLNuevaLGAC.fxml"));
        escenarioNuevaLGAC.setTitle("Añadir nueva Líneas de Generación y Aplicación del Conocimiento");
        escenarioNuevaLGAC.initModality(Modality.APPLICATION_MODAL);
        escenarioNuevaLGAC.showAndWait();
        cargarTabla();
    }
    
    private void configurarTabla(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));      
        tvLGAC.widthProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                TableHeaderRow header = (TableHeaderRow) tvLGAC.lookup("TableHeaderRow");
                header.reorderingProperty().addListener(new ChangeListener<Boolean>(){
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        header.setReordering(false);
                    }
                });
            }
        });
    }
    
    private void cargarTabla(){
        lgac = FXCollections.observableArrayList();
        LGACRespuesta respuesta = LGACDAO.obtenerInformacionLGAC();
        switch(respuesta.getCodigoRespuesta()){
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
                  lgac.addAll(respuesta.getLgac());
                  tvLGAC.setItems(lgac);
                  //configurarBusquedaTabla();
                break;    
        }
    }    
}

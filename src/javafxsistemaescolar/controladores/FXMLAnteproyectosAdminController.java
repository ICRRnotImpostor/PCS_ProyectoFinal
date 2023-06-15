/* 
Autor: Ian Rumayor. 
Creado: 28/05/2023 
Modificado: 13/06/2023  
Descripción: El controlador muestra unicamente los anteproyectos publicados y los no postulados de parte del lado academico
*/
package javafxsistemaescolar.controladores;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsistemaescolar.JavaFXSistemaEscolar;
import javafxsistemaescolar.modelo.dao.AnteproyectoDAO;
import javafxsistemaescolar.modelo.pojo.Anteproyecto;
import javafxsistemaescolar.modelo.pojo.AnteproyectoRespuesta;
import javafxsistemaescolar.utils.Constantes;
import javafxsistemaescolar.utils.Utilidades;

public class FXMLAnteproyectosAdminController implements Initializable {

    @FXML
    private Button btnRegresar;
    @FXML
    private TableView<Anteproyecto> tvAnteproyectos;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colDirector;
    @FXML
    private TableColumn colFecha;
    @FXML
    private TextField tfBusqueda;
    @FXML
    private Button btnSinPostular;
    
    private ObservableList<Anteproyecto> anteproyectos;    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnSinPostular.setText("Sin postular");
        configurarTabla();
        cargarTablaValidados();   
    }    

    @FXML
    private void clicBtnRegresar(ActionEvent event) {
        Stage scenarioDetallesAnteproyectos = (Stage) this.btnRegresar.getScene().getWindow();
        scenarioDetallesAnteproyectos.close();
    }

    @FXML
    private void clicBtnRegistrar(ActionEvent event) {
        Stage escenarioRegistroAnteproyecto = new Stage();
        escenarioRegistroAnteproyecto.setScene(Utilidades.inicializarEscena("vistas/FXMLFormularioAnteproyectos.fxml"));
        escenarioRegistroAnteproyecto.setTitle("Registro de Anteproyecto");
        escenarioRegistroAnteproyecto.initModality(Modality.APPLICATION_MODAL);
        escenarioRegistroAnteproyecto.showAndWait();
        cargarTablaValidados();
        btnSinPostular.setText("Sin postular");
    }

    @FXML
    private void clicBtnModificar(ActionEvent event) {
        Anteproyecto anteproyectoSeleccionado = tvAnteproyectos.getSelectionModel().getSelectedItem();
       if(anteproyectoSeleccionado != null){
            irFormulario(anteproyectoSeleccionado);
        }else{
            Utilidades.mostrarDialogoSimple("Selecciona un Anteproyecto", 
                    "Selecciona el Anteproyecto de la tabla para su edición", 
                    Alert.AlertType.WARNING);
        }
    }
    
    @FXML
    private void clicBtnVerDetalles(ActionEvent event) {
        Anteproyecto anteproyectoSeleccionada = tvAnteproyectos.getSelectionModel().getSelectedItem();        
        irDetalles(anteproyectoSeleccionada);  
    }
    
    @FXML
    private void clicBtnSinPostular(ActionEvent event) {
        String nombreBotonActualizable = btnSinPostular.getText();
        
        switch(nombreBotonActualizable){
            case "Sin postular":
                cargarTablaSinPostular();
                btnSinPostular.setText("Validados");
                tfBusqueda.setText(null);
                break;
            case "Validados" :
                cargarTablaValidados();
                btnSinPostular.setText("Sin postular");
                tfBusqueda.setText(null);
                break;
        }     
    }

    private void irFormulario(Anteproyecto anteproyectoEdicion){
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSistemaEscolar.class.getResource("vistas/FXMLFormularioAnteproyectosMod.fxml"));
            Parent vista = accesoControlador.load();
            FXMLFormularioAnteproyectosModController modificable = accesoControlador.getController();

            modificable.inicializarInformacion(anteproyectoEdicion);

            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene(vista));
            escenarioFormulario.setTitle("Formulario");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(FXMLAnteproyectosAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void configurarTabla(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombreProyecto"));
        colDirector.setCellValueFactory(new PropertyValueFactory("director"));
        colFecha.setCellValueFactory(new PropertyValueFactory("fecha"));
        
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
    
    private void cargarTablaValidados(){
        anteproyectos = FXCollections.observableArrayList();
        AnteproyectoRespuesta respuesta = AnteproyectoDAO.recuperarAnteproyectosValidados();
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
                  anteproyectos.addAll(respuesta.getAnteproyectos());
                  tvAnteproyectos.setItems(anteproyectos);
                  //configurarBusquedaTabla();
                break;    
        }
        configurarBusquedaTabla();
    }
    
        private void cargarTablaSinPostular(){
        anteproyectos = FXCollections.observableArrayList();
        AnteproyectoRespuesta respuesta = AnteproyectoDAO.recuperarAnteproyectosSinPostular();
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
                  anteproyectos.addAll(respuesta.getAnteproyectos());
                  tvAnteproyectos.setItems(anteproyectos);
                  //configurarBusquedaTabla();
                break;    
        }
        configurarBusquedaTabla();
    }
    
    private void configurarBusquedaTabla(){
        if(anteproyectos.size() > 0){
            FilteredList<Anteproyecto> filtrado = new FilteredList<>(anteproyectos, p -> true);
            tfBusqueda.textProperty().addListener(new ChangeListener<String>(){
                
                @Override
                public void changed(ObservableValue<? extends String> observable, 
                        String oldValue, String newValue) {
                    filtrado.setPredicate(anteproyectoFiltro -> {
                        // Caso default o vacio
                        if(newValue == null || newValue.isEmpty()){
                            return true;
                        }
                        // Criterio de busqueda
                        String lowerNewValue = newValue.toLowerCase();
                        if(anteproyectoFiltro.getNombreProyecto().toLowerCase().contains(lowerNewValue)){
                            return true;
                        }else if(anteproyectoFiltro.getDirector().toLowerCase().contains(lowerNewValue)){
                            return true;
                        }
                        return false;
                    });
                }
            
            });
            SortedList<Anteproyecto> sortedListas = new SortedList<>(filtrado);
            sortedListas.comparatorProperty().bind(tvAnteproyectos.comparatorProperty());
           tvAnteproyectos.setItems(sortedListas);
        }
    }

    private void irDetalles(Anteproyecto anteproyectoDetalles){
        int posicion = tvAnteproyectos.getSelectionModel().getSelectedIndex();
               
        if(posicion != -1)
        {
            String nombreBoton = btnSinPostular.getText();
            
            if(nombreBoton == "Sin postular"){
                try{
                    FXMLLoader accesoControlador =  new FXMLLoader(JavaFXSistemaEscolar.class.getResource("vistas/FXMLDetallesAnteproyecto.fxml"));
                    Parent vista = accesoControlador.load();
                    FXMLDetallesAnteproyectoController detalles = accesoControlador.getController();
                    detalles.inicializarInformacionDetalles(false, anteproyectoDetalles);           
                    Stage escenarioDetalles = new Stage();
                    escenarioDetalles.setScene(new Scene(vista));
                    escenarioDetalles.setTitle("Ver detalles");
                    escenarioDetalles.initModality(Modality.APPLICATION_MODAL);
                    escenarioDetalles.showAndWait();
                }catch (IOException ex){
                    Logger.getLogger(FXMLVerActividadController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else if(nombreBoton == "Validados"){
                try{
                    FXMLLoader accesoControlador =  new FXMLLoader(JavaFXSistemaEscolar.class.getResource("vistas/FXMLRevisionAnteproyecto.fxml"));
                    Parent vista = accesoControlador.load();
                    FXMLRevisionAnteproyectoController detalles = accesoControlador.getController();
                    detalles.inicializarInformacionDetalles(true, anteproyectoDetalles); 
                    Stage escenarioDetalles = new Stage();
                    escenarioDetalles.setScene(new Scene(vista));
                    escenarioDetalles.setTitle("Ver detalles, Revision");
                    escenarioDetalles.initModality(Modality.APPLICATION_MODAL);
                    escenarioDetalles.showAndWait();
                }catch (IOException ex){
                    Logger.getLogger(FXMLVerActividadController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }      
        }else
        {
            Utilidades.mostrarDialogoSimple("Seleciona un Anteproyecto", 
                    "Selecciona el Anteproyecto de la tabla para su consulta", 
                    Alert.AlertType.WARNING);
       }
    }
 
}

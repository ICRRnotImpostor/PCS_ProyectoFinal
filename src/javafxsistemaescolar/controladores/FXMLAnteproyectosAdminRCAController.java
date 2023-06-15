/* 
Autor: Ian Rumayor. 
Creado: 28/05/2023 
Modificado: 13/06/2023  
Descripción: El controlador muestra unicamente los anteproyectos publicados y los
no postulados de parte del lado del responsable del cuerpo academico
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

public class FXMLAnteproyectosAdminRCAController implements Initializable {

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
    private Button btnRegresar;
    @FXML
    private Button btnPendientes;
    
    private ObservableList<Anteproyecto> anteproyectos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarTablaValidados();
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

    @FXML
    private void clicBtnVerDetalles(ActionEvent event) {
        Anteproyecto anteproyectoSeleccionada = tvAnteproyectos.getSelectionModel().getSelectedItem();        
        irDetalles(anteproyectoSeleccionada);  
    }

    @FXML
    private void clicBtnPendientes(ActionEvent event) {
        String nombreBotonActualizable = btnPendientes.getText();
        
        switch(nombreBotonActualizable){
            case "Pendientes":
                cargarTablaPendientes();
                btnPendientes.setText("Validados");
                tfBusqueda.setText(null);
                break;
            case "Validados" :
                cargarTablaValidados();
                btnPendientes.setText("Pendientes");
                tfBusqueda.setText(null);
                break;
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
    
    private void cargarTablaPendientes(){
        anteproyectos = FXCollections.observableArrayList();
        AnteproyectoRespuesta respuesta = AnteproyectoDAO.recuperarAnteproyectosPendientes();
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
                        if(newValue == null || newValue.isEmpty()){
                            return true;
                        }
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
        String nombreBoton = btnPendientes.getText();       
        if(posicion != -1)
        {
            if(nombreBoton == "Pendientes"){
                try{
                    FXMLLoader accesoControlador =  new FXMLLoader(JavaFXSistemaEscolar.class.getResource("vistas/FXMLDetallesAnteproyecto.fxml"));
                    Parent vista = accesoControlador.load();
                    FXMLDetallesAnteproyectoController detalles = accesoControlador.getController();
                    detalles.inicializarInformacionDetalles(true, anteproyectoDetalles);           
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
                    detalles.inicializarInformacionDetalles(false, anteproyectoDetalles);           
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
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) btnRegresar.getScene().getWindow();
        escenarioBase.close();
    }
}

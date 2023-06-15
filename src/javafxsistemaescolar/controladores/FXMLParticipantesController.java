/* 
Autor: Ian Rumayor. 
Creado: 02/06/2023 
Modificado: 15/06/2023  
Descripción: Controlador que se encarga de mostrar los participantes de un proyecto, muestra los particpantes disponibles para
asignar al anteproyecto y permite eliminar a un participante
*/
package javafxsistemaescolar.controladores;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafxsistemaescolar.modelo.dao.AlumnoDAO;
import javafxsistemaescolar.modelo.pojo.Alumno;
import javafxsistemaescolar.modelo.pojo.AlumnoRespuesta;
import javafxsistemaescolar.modelo.pojo.Anteproyecto;
import javafxsistemaescolar.utils.Constantes;
import javafxsistemaescolar.utils.Utilidades;

public class FXMLParticipantesController implements Initializable {

    @FXML
    private TableView<Alumno> tvAlumno;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApellidoP;
    @FXML
    private TableColumn colApellidoM;
    @FXML
    private TableColumn colCorreo;
    @FXML
    private TextField tfMatricula;

    private ObservableList<Alumno> alumnos;
    
    private Anteproyecto anteproyecto;
    @FXML
    private TableColumn colMatricula;
    @FXML
    private Label lbTablaSeleccion;
    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnCambioTabla;
    
    private int idAnteproyecto;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        btnCambioTabla.setText("Alumnos sin asignar");
    }    

    @FXML
    private void clicBtnRegresar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void clicBtnAsignar(ActionEvent event) {
        Alumno alumnoSeleccionado = tvAlumno.getSelectionModel().getSelectedItem();        
        if(alumnoSeleccionado != null){
            alumnoSeleccionado.setIdAnteproyecto(idAnteproyecto);
            asignarAlumno(alumnoSeleccionado);
            inicializarDatos(idAnteproyecto);
            btnCambioTabla.setText("Alumnos sin asignar");
        }else{
            Utilidades.mostrarDialogoSimple("Selecciona un estudiante", 
                    "Selecciona el estudiante de la tabla para su asignacion", 
                    Alert.AlertType.WARNING);
        } 
    }

    @FXML
    private void clicBtnEliminar(ActionEvent event) {
        Alumno alumnoSeleccionado = tvAlumno.getSelectionModel().getSelectedItem();
        if(alumnoSeleccionado != null){
            eliminarAlumno(alumnoSeleccionado);
            inicializarDatos(idAnteproyecto);
            btnCambioTabla.setText("Alumnos sin asignar");
        }else{
            Utilidades.mostrarDialogoSimple("Selecciona un estudiante", 
                    "Selecciona el estudiante de la tabla para su elimininacion", 
                    Alert.AlertType.WARNING);
        } 
    }
    
    @FXML
    private void btnCambiarTabla(ActionEvent event) {
        if(btnCambioTabla.getText() == "Alumnos sin asignar"){
            cargarTablaAsignacion();
            btnCambioTabla.setText("Cancelar");
        }else if(btnCambioTabla.getText() == "Cancelar"){
            inicializarDatos(idAnteproyecto);
            btnCambioTabla.setText("Alumnos sin asignar");
        }
    }
    
    private void eliminarAlumno(Alumno alumnoEliminado)
    {
        int codigoRespuesta = AlumnoDAO.eliminarAlumnoAsignacion(alumnoEliminado);        
        switch(codigoRespuesta)
        {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion en registrarProducto",
                        "El alumno no fue removido del proyecto debido a un error de conexion",
                        Alert.AlertType.ERROR);
                break;
                
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la informacion en registrarProducto",
                        "El alumno no fue removido del proyecto, por favor verifique su conexion",
                        Alert.AlertType.WARNING);
                break;
                
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Producto registrado",
                        "El alumno fue removido correctamente",
                        Alert.AlertType.INFORMATION);                
                break;
        }        
    }
    
    private void asignarAlumno(Alumno alumnoEliminado)
    {
        int codigoRespuesta = AlumnoDAO.alumnoAsignacion(alumnoEliminado);        
        switch(codigoRespuesta)
        {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion en registrarProducto",
                        "El alumno no pudo ser asignado debido a un error de conexion",
                        Alert.AlertType.ERROR);
                break;
                
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la informacion en registrarProducto",
                        "La informacion del alumno no puede ser actualizada, por favor verifique su conexion",
                        Alert.AlertType.WARNING);
                break;
                
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Producto registrado",
                        "El alumno fue asignado correctamente correctamente",
                        Alert.AlertType.INFORMATION);                
                break;
        }        
    }
    
    public void inicializarDatos(int idAnteproyecto){
        this.idAnteproyecto = idAnteproyecto;
        alumnos = FXCollections.observableArrayList();
        AlumnoRespuesta respuesta = AlumnoDAO.alumnoPorAnteproyecto(idAnteproyecto);
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
                  alumnos.addAll(respuesta.getAlumnos());
                  tvAlumno.setItems(alumnos);
                  //configurarBusquedaTabla();
                break;    
        }
    }
    
    private void configurarTabla(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidoP.setCellValueFactory(new PropertyValueFactory("apellidoP"));
        colApellidoM.setCellValueFactory(new PropertyValueFactory("apellidoM"));
        colCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        colMatricula.setCellValueFactory(new PropertyValueFactory("matricula"));

        tvAlumno.widthProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                TableHeaderRow header = (TableHeaderRow) tvAlumno.lookup("TableHeaderRow");
                header.reorderingProperty().addListener(new ChangeListener<Boolean>(){
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        header.setReordering(false);
                    }
                });
            }
        });
        }
    
    private void cargarTablaAsignacion(){
        alumnos = FXCollections.observableArrayList();
        AlumnoRespuesta respuesta = AlumnoDAO.buscarPorMatricula();
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
                  alumnos.addAll(respuesta.getAlumnos());
                  tvAlumno.setItems(alumnos);
                  //configurarBusquedaTabla();
                break;    
        }
    }
    
    private void configurarBusquedaTabla(){
        if(alumnos.size() > 0){
            FilteredList<Alumno> filtrado = new FilteredList<>(alumnos, p -> true);
            tfMatricula.textProperty().addListener(new ChangeListener<String>(){
                
                @Override
                public void changed(ObservableValue<? extends String> observable, 
                        String oldValue, String newValue) {
                    filtrado.setPredicate(anteproyectoFiltro -> {
                        if(newValue == null || newValue.isEmpty()){
                            return true;
                        }
                        String lowerNewValue = newValue.toLowerCase();
                        if(anteproyectoFiltro.getMatricula().toLowerCase().contains(lowerNewValue)){
                            return true;
                        }
                        return false;
                    });
                }
            
            });
            SortedList<Alumno> sortedListas = new SortedList<>(filtrado);
            sortedListas.comparatorProperty().bind(tvAlumno.comparatorProperty());
           tvAlumno.setItems(sortedListas);
        }
    }
    private void cerrarVentana()
    {        
        Stage escenarioBase = (Stage) btnRegresar.getScene().getWindow();
        escenarioBase.close();        
    }    

    
}

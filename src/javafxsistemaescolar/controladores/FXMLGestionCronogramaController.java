/* 
Autor: Miguel Martinez. 
Creado: 27/05/2023 
Modificado: 11/06/2023  
Descripción: Este es el controller de la vista principal donde el alummno
             podrá estár gestionando sus actividades
*/
package javafxsistemaescolar.controladores;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsistemaescolar.JavaFXSistemaEscolar;
import javafxsistemaescolar.modelo.dao.ActividadDAO;
import javafxsistemaescolar.interfaz.INotificacionOperacionActividad;
import javafxsistemaescolar.modelo.pojo.Actividad;
import javafxsistemaescolar.modelo.pojo.ActividadRespuesta;
import javafxsistemaescolar.utils.Constantes;
import javafxsistemaescolar.utils.Utilidades;


public class FXMLGestionCronogramaController implements Initializable, INotificacionOperacionActividad
{
    @FXML
    private TableView<Actividad> tvActividades;
    @FXML
    private TableColumn colNombreAct;
    @FXML
    private TableColumn colEstatus;
    @FXML
    private TableColumn colFechaInicio;
    @FXML
    private TableColumn colFechaFin;
    @FXML
    private TextField tfBusqueda;
    
    private ObservableList<Actividad> actividades;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        configurarTabla();
        cargarInFormacionTabla();
    }    
    
    
    private void configurarTabla()
    {
        colNombreAct.setCellValueFactory(new PropertyValueFactory("nombreActividad"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory("fechaInicio"));
        colFechaFin.setCellValueFactory(new PropertyValueFactory("fechaFin"));              
    }    
    
    private void cargarInFormacionTabla()
    {
        actividades = FXCollections.observableArrayList();
        ActividadRespuesta respuestaBD = ActividadDAO.obtenerInformacionActividades();
        
        switch(respuestaBD.getCodigoRespuesta())
        {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion en cargarInformacionTabla",
                        "Lo sentimos, por el momento no hay conexion para poder cargar la informacion",
                        Alert.AlertType.ERROR);
                break;
            
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos en cargarInformacionTabla",
                        "Hubo un error al cargar la infromacion, por favor intentelo mas tarde",
                        Alert.AlertType.WARNING);
                break;
                
            case Constantes.OPERACION_EXITOSA:                
                    actividades.addAll(respuestaBD.getActividades());
                    tvActividades.setItems(actividades);
                    configurarBusquedaTabla();
                break;
        }        
    }
    
    private void configurarBusquedaTabla()
    {
        if(actividades.size() > 0){
            FilteredList<Actividad> filtradoActividad = new FilteredList<>(actividades, p -> true);
            tfBusqueda.textProperty().addListener(new ChangeListener<String>(){
                
                @Override
                public void changed(ObservableValue<? extends String> observable, 
                        String oldValue, String newValue){                    
                        filtradoActividad.setPredicate(actividadFiltro -> 
                        {
                        if(newValue == null || newValue.isEmpty())
                        {
                            return true;
                        }
                        String lowerNewValue = newValue.toLowerCase();
                        if(actividadFiltro.getNombreActividad().toLowerCase().contains(lowerNewValue))
                        {
                            return true;                            
                        }                     
                       return false;                        
                    });                                                               
                }
            });
                SortedList<Actividad> sortedListaActividades = new SortedList<>(filtradoActividad);
                sortedListaActividades.comparatorProperty().bind(tvActividades.comparatorProperty());
                tvActividades.setItems(sortedListaActividades);
        }
    }
    

    @FXML
    private void clickBtnEliminar(ActionEvent event)
    {
        Actividad actividadSeleccionada = tvActividades.getSelectionModel().getSelectedItem();
        if(actividadSeleccionada != null)
        {
            boolean borrarRegistro = Utilidades.mostrarDialogoConfirmacion("Eliminar registro de la actividad",
                    "¿Estás seguro de que deseas eliminar el registro de la actividad: "
                            +actividadSeleccionada.getNombreActividad());
            if(borrarRegistro)
            {
                int codigoRespuesta = ActividadDAO.eliminarActividad(actividadSeleccionada.getIdActividad());
                switch(codigoRespuesta)
                {
                    case Constantes.ERROR_CONEXION:
                        Utilidades.mostrarDialogoSimple("Error de conexión",
                        "La actividad no pudo ser eliminada debido a un error en su conexión...",
                        Alert.AlertType.ERROR);
                    break;
                    
                    case Constantes.ERROR_CONSULTA:
                        Utilidades.mostrarDialogoSimple("Error al eliminar la actividad",
                        "La información de la actividad no puede ser eliminada, por favor verifica sus datos nuevamente.",
                        Alert.AlertType.WARNING);
                      break;
                      
                    case Constantes.OPERACION_EXITOSA:
                        Utilidades.mostrarDialogoSimple("Actividad eliminado",
                        "La información de la actividad fue eliminada correctamente",
                        Alert.AlertType.INFORMATION);
                        cargarInFormacionTabla();
                    break;
                }
            }                                           
        }else
        {
            Utilidades.mostrarDialogoSimple("Selecciona una actividad",
                    "Para eliminar una actividad debes seleccionarlo previamente de la tabla",
                    Alert.AlertType.WARNING);
        }
        
    }

    @FXML
    private void clickBtnAgregar(ActionEvent event)
    {
        irFormulario(false, null);
    }
    
    @FXML
    private void clickBtnVer(ActionEvent event) 
    {
        Actividad actividadSeleccionada = tvActividades.getSelectionModel().getSelectedItem();        
        irConsulta(actividadSeleccionada);        
    }
    
    private void irConsulta(Actividad actividadConsutla)
    {
       int posicion = tvActividades.getSelectionModel().getSelectedIndex();
       if(posicion != -1)
       {
           try{
            FXMLLoader accesoControlador =  new FXMLLoader(JavaFXSistemaEscolar.class.getResource("vistas/FXMLVerActividad.fxml"));
            Parent vista = accesoControlador.load();
            FXMLVerActividadController consulta = accesoControlador.getController();
            consulta.inicializarInformacionConsulta(actividadConsutla);           
            Stage escenarioActividades = new Stage();
            escenarioActividades.setScene(new Scene(vista));
            escenarioActividades.setTitle("Ver actividad");
            escenarioActividades.initModality(Modality.APPLICATION_MODAL);
            escenarioActividades.showAndWait();
       }catch (IOException ex)
       {
            Logger.getLogger(FXMLVerActividadController.class.getName()).log(Level.SEVERE, null, ex);
        }
       }else
       {
           Utilidades.mostrarDialogoSimple("Seleciona una actividad", 
                    "Selecciona el registro en la tabla de la activiad para ser visualizada", 
                    Alert.AlertType.WARNING);
       }
    }    
        
    @FXML
    private void clickBtnModificar(ActionEvent event) 
    {
        int posicion = tvActividades.getSelectionModel().getSelectedIndex();
        if(posicion != -1)
        {
            irFormulario(true, actividades.get(posicion));
        }else
        {
            Utilidades.mostrarDialogoSimple("Selecciona una actividad",
                    "Selecciona el registro en la tabla de la actividad para su edicion",
                    Alert.AlertType.WARNING);
        }
    }    
    
    private void irFormulario(boolean esEdicion, Actividad actividadEdicion)
    {
        try{
            FXMLLoader accesoControlado = new FXMLLoader(JavaFXSistemaEscolar.class.getResource("vistas/FXMLFormularioActividad.fxml"));
            Parent vista = accesoControlado.load();
            FXMLFormularioActividadController formulario = accesoControlado.getController();            
            formulario.inicializarInformacionFormulario(esEdicion, actividadEdicion, this);            
            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene(vista));
            escenarioFormulario.setTitle("Formulario");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();            
        }catch(IOException ex)
        {
            Logger.getLogger(FXMLGestionCronogramaController.class.getName()).log(Level.SEVERE, null, ex);
        }                
    }    

    @Override
    public void notificarOperacionActividadGuardar(String nombreActividad)
    {
        cargarInFormacionTabla();
    }

    @Override
    public void notificarOperacionActividadActualizar(String nombreActividad) 
    {
        cargarInFormacionTabla();
    }        
}

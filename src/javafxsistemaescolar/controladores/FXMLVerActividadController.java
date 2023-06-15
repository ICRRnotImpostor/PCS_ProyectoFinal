/* 
Autor: Miguel Martinez. 
Creado: 29/05/2023 
Modificado: 11/06/2023  
Descripción: Controller de la vista donde se mostrará la actividad guardada.
*/
package javafxsistemaescolar.controladores;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxsistemaescolar.modelo.dao.ActividadDAO;
import javafxsistemaescolar.modelo.pojo.Actividad;
import javafxsistemaescolar.modelo.pojo.ActividadRespuesta;
import javafxsistemaescolar.utils.Constantes;
import javafxsistemaescolar.utils.Utilidades;

public class FXMLVerActividadController implements Initializable 
{
    @FXML
    private Label lbTitulo;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaFIn;
    @FXML
    private TextField tfNombreActividad;
    @FXML
    private TextArea taDescripcionAct;

    private ObservableList<Actividad> actividades;
    private Actividad actividadConsulta;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        tfNombreActividad.setEditable(false);
        taDescripcionAct.setEditable(false);
        dpFechaInicio.setEditable(false);
        dpFechaFIn.setEditable(false);
    }    
    
    public void inicializarConsultaActividad(Actividad actividadConsulta)
    {
        this.actividadConsulta = actividadConsulta;
    }
    
    public void inicializarInformacionConsulta(Actividad actividadConsulta)
    {
        this.actividadConsulta = actividadConsulta;        
        lbTitulo.setText("Consultar la informacion de la actividad: "+actividadConsulta.getIdActividad());
        cargarInformacionConsulta();        
    }
    
    public void cargarInformacionConsulta()
    {
        tfNombreActividad.setText(actividadConsulta.getNombreActividad());
        taDescripcionAct.setText(actividadConsulta.getDescripcionActividad());
        dpFechaInicio.setValue(LocalDate.parse(actividadConsulta.getFechaInicio()));
        dpFechaFIn.setValue(LocalDate.parse(actividadConsulta.getFechaFin()));       
    }
    
    public void cargarInformacionActividades()
    {
        actividades = FXCollections.observableArrayList();
        ActividadRespuesta actividadesBD = ActividadDAO.obtenerInformacionActividades();
        
        switch(actividadesBD.getCodigoRespuesta())
        {
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error de conexión", 
                            "Error de conexion con la base de datos.", 
                            Alert.AlertType.ERROR);
                break;
                
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error de consulta", 
                            "Por el momento no se puede obtener la información.", 
                            Alert.AlertType.WARNING);
                break;
                
            case Constantes.OPERACION_EXITOSA:
                    actividades.addAll(actividadesBD.getActividades());
                break;
        }
    }

    @FXML
    private void clickBtnCerrar(ActionEvent event) 
    {
        Stage escenarioPrincipal = (Stage) lbTitulo.getScene().getWindow();
        escenarioPrincipal.close();
    }
    
}

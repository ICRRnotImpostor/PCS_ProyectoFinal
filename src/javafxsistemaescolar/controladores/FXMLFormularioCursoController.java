/* 
Autor: Miguel Martinez. 
Creado: 30/05/2023 
Modificado: 11/06/2023  
Descripción: Este formulario sirve para guardar datos del curso
             que el administrador guardará.
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxsistemaescolar.interfaz.INotificacionOperacionCurso;
import javafxsistemaescolar.modelo.dao.CursoDAO;
import javafxsistemaescolar.modelo.pojo.Curso;
import javafxsistemaescolar.utils.Constantes;
import javafxsistemaescolar.utils.Utilidades;


public class FXMLFormularioCursoController implements Initializable
{
    @FXML
    private Label lbTitulo;
    @FXML
    private TextField tfNombreCurso;
    @FXML
    private ComboBox<String> cbPeriodo;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaFin;
    
    private ObservableList<Curso> cursos;
    private INotificacionOperacionCurso interfazNotificacionCurso;
    private Curso cursoEdicion;
    private boolean esEdicionCurso;    
    String estiloError = "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    String estiloNormal = "-fx-border-width: 0;";
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        agregarComboBox();
        
    }    
    
    public void inicializarInformacionCurso(boolean esEdicionCurso,
            Curso cursoEdicion, INotificacionOperacionCurso interfazNotificacionCurso)
    {
        this.esEdicionCurso = esEdicionCurso;
        this.cursoEdicion = cursoEdicion;
        this.interfazNotificacionCurso = interfazNotificacionCurso;        
        if(esEdicionCurso)
        {
            lbTitulo.setText("Editar informacion del curso "+cursoEdicion.getNombreCurso());
        }else
        {
            lbTitulo.setText("Registrar nuevo curso");
        } 
    }
    
     @FXML
    private void clickBtnGuardar(ActionEvent event) 
    {
        validarCamposRegistro();
    }
    
    private void validarCamposRegistro()
    {
        establecerEstiloNormal();
        boolean datosValidos = true;        
        String nombreCurso = tfNombreCurso.getText();
        String periodo = cbPeriodo.getValue();
        LocalDate fechaInicioCurso = dpFechaInicio.getValue();
        LocalDate fechaFinCurso = dpFechaFin.getValue();
        
        if(nombreCurso.isEmpty())
        {
            tfNombreCurso.setStyle(estiloError);
            datosValidos =  false;
        }
        if(periodo == null || periodo.isEmpty())
        {
            cbPeriodo.setStyle(estiloError);  
            datosValidos = false;
        }
        if(fechaInicioCurso == null)
        {
            dpFechaInicio.setStyle(estiloError);  
            datosValidos = false;
        }
        if(fechaFinCurso == null)
        {
            dpFechaFin.setStyle(estiloError);  
            datosValidos = false;
        }
        if(dpFechaFin.getValue() != null && dpFechaInicio.getValue() != null)
        {   
            //Se valida que la fecha del fin no sea antes de la fecha de inicio
            if(dpFechaFin.getValue().isBefore(dpFechaInicio.getValue()))
            {
                Utilidades.mostrarDialogoSimple("Fecha no valida",
                "La fecha del fin del curso no puede ser una fecha anterior a la fecha de inicio",
                Alert.AlertType.WARNING);                
                dpFechaInicio.setStyle(estiloError);
                dpFechaFin.setStyle(estiloError);
                datosValidos = false;
            }
            
        }
        
        if(datosValidos)
        {
            Curso cursoValidado = new Curso();
            cursoValidado.setNombreCurso(nombreCurso);
            cursoValidado.setPeriodo(periodo);
            cursoValidado.setFechaInicioCurso(fechaInicioCurso.toString());
            cursoValidado.setFechaFinCurso(fechaFinCurso.toString());                           
            if(esEdicionCurso)
            {
                cursoValidado.setIdCurso(cursoEdicion.getIdCurso());
            }else
            {
                registrarCurso(cursoValidado);
            }
           
        }
        
    }
    
    private void registrarCurso(Curso cursoResgistro)
    {
        int codigoRespuesta = CursoDAO.guardarCurso(cursoResgistro);        
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error en la informacion al registrar el curso",
                        "La informacion del curso no puede ser guardada, por favor verifique su conexion",
                        Alert.AlertType.WARNING);
                break;
                
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la informacion al registrar el curso",
                        "La informacion del curso no puede ser guardada, por favor verifique su conexion",
                        Alert.AlertType.WARNING);
                break;
                
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Curso registrado",
                        "La informacion del curso fue guardada correctamente",
                        Alert.AlertType.INFORMATION);
                
                cerrarVentana();
                interfazNotificacionCurso.notificarOperacionCursoGuardar(cursoResgistro.getNombreCurso());
        }        
    }
    
    //Combo box para agregar el periodo a escoger
    private void agregarComboBox()
    {
                cbPeriodo.setItems(FXCollections.observableArrayList("febrero-julio 2023", "agosto 2023-enero 2024"));
                
    }
    
    private void establecerEstiloNormal()
    {
        tfNombreCurso.setStyle(estiloNormal);
        cbPeriodo.setStyle(estiloNormal);
        dpFechaInicio.setStyle(estiloNormal);
        dpFechaFin.setStyle(estiloNormal);
    }

    
    @FXML
    private void clickBtnCancelar(ActionEvent event) 
    {
        cerrarVentana();
    }
    
    private void cerrarVentana()
    {        
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.close();        
    }    
}

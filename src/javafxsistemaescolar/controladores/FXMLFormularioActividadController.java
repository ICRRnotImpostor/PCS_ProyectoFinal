/* 
Autor: Miguel Martinez. 
Creado: 25/05/2023 
Modificado: 11/06/2023  
Descripción: Este formulario sirve para guardar datos de la actividad
             que el estudiante guardará en su cronograma.
*/ 
package javafxsistemaescolar.controladores;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafxsistemaescolar.interfaz.INotificacionOperacionActividad;
import javafxsistemaescolar.modelo.dao.ActividadDAO;
import javafxsistemaescolar.modelo.pojo.Actividad;
import javafxsistemaescolar.utils.Constantes;
import javafxsistemaescolar.utils.Utilidades;

public class FXMLFormularioActividadController implements Initializable 
{
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaFIn;
    @FXML
    private TextField tfNombreActividad;       
    @FXML
    private Label lbTitulo;
    @FXML
    private TextArea taDescripcionAct;       
    @FXML
    private Button btnSeleccionarDocumento;    
    @FXML
    private Pane paneContenedor;
    @FXML
    private ImageView ivDocumento;
    
    private File archivoDoc;
    private Actividad documento;
    private Actividad actividadEdicion;
    private boolean esEdicion;
    private INotificacionOperacionActividad interfazNotificacionActividad;
    String estiloError = "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    String estiloNormal = "-fx-border-width: 0;";
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
    } 
    
    
    public void inicializarInformacionFormulario(boolean esEdicion,
        Actividad actividadEdicion,INotificacionOperacionActividad interfazNotificacionActividad)
    {      
        this.esEdicion = esEdicion;
        this.actividadEdicion = actividadEdicion;
        this.interfazNotificacionActividad = interfazNotificacionActividad;        
        if(esEdicion){
            lbTitulo.setText("Editar informacion de la actividad : "+actividadEdicion.getNombreActividad());       
            cargarInformacionEdicion();            
        }else{
            lbTitulo.setText("Registrar nueva actividad");
        }        
    }     
    
    private void cargarInformacionEdicion()
    {
        tfNombreActividad.setText(actividadEdicion.getNombreActividad());
        taDescripcionAct.setText(actividadEdicion.getDescripcionActividad());
        dpFechaInicio.setValue(LocalDate.parse(actividadEdicion.getFechaInicio()));
        dpFechaFIn.setValue(LocalDate.parse(actividadEdicion.getFechaFin()));        
    }
   
    @FXML
    private void clickBtnGuardar(ActionEvent event)
    {
        validarCamposRegistro();
        
    }
    
    //Se deben validar los datos ingresados antes de que puedan ser guardados
    private void validarCamposRegistro()
    {
        establecerEstiloNormal();
        boolean datosValidos = true;        
        String nombreActividad = tfNombreActividad.getText();
        String descripcionActividad = taDescripcionAct.getText();        
        LocalDate fechaInicio = dpFechaInicio.getValue();
        LocalDate fechaFin = dpFechaFIn.getValue();    
        
        if(nombreActividad.isEmpty())
        {
            tfNombreActividad.setStyle(estiloError);
            datosValidos =  false;
        }
        if(descripcionActividad.isEmpty())
        {
            taDescripcionAct.setStyle(estiloError);
            datosValidos =  false;
        }
        if(fechaInicio == null)
        {
            dpFechaInicio.setStyle(estiloError);
            datosValidos = false;
        }
        if(fechaFin == null)
        {
            dpFechaFIn.setStyle(estiloError);
            datosValidos = false;
        }
        if(dpFechaFIn.getValue() != null && dpFechaInicio.getValue() != null) 
        {   
            //Se valida que la fecha del fin no sea antes de la fecha de inicio
            if(dpFechaFIn.getValue().isBefore(dpFechaInicio.getValue()))
            {
                Utilidades.mostrarDialogoSimple("Fecha no valida",
                "La fecha del fin de la actividad no puede ser una fecha anterior a la fecha de inicio",
                Alert.AlertType.WARNING);
                
                dpFechaInicio.setStyle(estiloError);
                dpFechaFIn.setStyle(estiloError);
                datosValidos = false;
            }
            
        }         
        
        if(datosValidos)
        {
            Actividad actividadValidada = new Actividad();
            actividadValidada.setNombreActividad(nombreActividad);
            actividadValidada.setDescripcionActividad(descripcionActividad);
            actividadValidada.setFechaInicio(fechaInicio.toString());
            actividadValidada.setFechaFin(fechaFin.toString());
                       
       
            if(esEdicion){
                actividadValidada.setIdActividad(actividadEdicion.getIdActividad());
                actualizarActividad(actividadValidada);
            }else{
                    
                registrarActividad(actividadValidada);
            }
           
        }
    }
    
  
    private void registrarActividad(Actividad actividadRegistro)
    {
        int codigoRespuesta = ActividadDAO.guardarActividad(actividadRegistro);
        
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error en la informacion al registrar la actividad",
                        "La informacion del alumno no puede ser guardada, por favor verifique su conexion",
                        Alert.AlertType.WARNING);
                break;
                
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la informacion al registrar la actividad",
                        "La informacion de la actividad no puede ser guardada, por favor verifique su conexion",
                        Alert.AlertType.WARNING);
                break;
                
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Alumno registrado",
                        "La informacion del alumno fue guardada correctamente",
                        Alert.AlertType.INFORMATION);
                
                cerrarVentana();                
                interfazNotificacionActividad.notificarOperacionActividadGuardar(actividadRegistro.getNombreActividad());      
                 break;
        }
    }       
        
    private void actualizarActividad(Actividad actividadActualizar)
    {
        int codigoRespuesta = ActividadDAO.modificarActividad(actividadActualizar);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion al actualizar la actividad",
                        "La actividad no pudo ser actualizado debido a un error de conexion",
                        Alert.AlertType.ERROR);
                break;
                
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la informacion al actualizar la actividad",
                        "La informacion de la actividad no puede ser actualizada, por favor verifique su conexion",
                        Alert.AlertType.WARNING);
                break;
                
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Actividad registrada",
                        "La informacion de la actividad fue guardada correctamente",
                        Alert.AlertType.INFORMATION);
                
                cerrarVentana();                
                interfazNotificacionActividad.notificarOperacionActividadActualizar(actividadActualizar.getNombreActividad());                
                break;
        }
    }
        
    
    private void establecerEstiloNormal()
    {
        tfNombreActividad.setStyle(estiloNormal);
        taDescripcionAct.setStyle(estiloNormal);
        dpFechaInicio.setStyle(estiloNormal);
        dpFechaFIn.setStyle(estiloNormal);
    }
    
    private void cerrarVentana()
    {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.close();
    }


    @FXML
    private void clickBtnCancelar(ActionEvent event)
    {
        cerrarVentana();
    }
    
    


    @FXML
    private void clickSeleccionarDocumento(ActionEvent event)
    {
       
    }
    
    
    
    
    
    /*FileChooser dialogoDocumento = new FileChooser();
        dialogoDocumento.setTitle("Selecciona un PDF");
        FileChooser.ExtensionFilter filtroDocumento = new FileChooser.ExtensionFilter("Archivos PDF (*.pdf)", "*.pdf");
        dialogoDocumento.getExtensionFilters().add(filtroDocumento);
        Stage escenarioActual = (Stage) lbTitulo.getScene().getWindow();
        File nuevoDocumento = dialogoDocumento.showOpenDialog(escenarioActual);

        if (nuevoDocumento != null && nuevoDocumento.exists()) {
            // Eliminar el archivo PDF anterior si existe
            if (archivoDoc != null && archivoDoc.exists()) {
            archivoDoc.delete();
            }

            // Asignar el nuevo archivo PDF adjuntado
            archivoDoc = nuevoDocumento;

            try {
                // Limpiar el paneContenedor antes de mostrar el nuevo archivo adjuntado
                paneContenedor.getChildren().clear();

                // Mostrar el icono del pdf y el nombre del archivo
                String rutaImagen = "/javafxsistemaescolar/recursos/documentoLogo.png"; // Reemplaza con la ruta correcta del icono del PDF
                ImageView vistaImagen = new ImageView(rutaImagen);
                vistaImagen.setFitWidth(100);//   /javafxsistemaescolar/recursos/documentoLogo.png
                vistaImagen.setFitHeight(100);

                Label labelNombreArchivo = new Label(archivoDoc.getName());
                labelNombreArchivo.setGraphic(vistaImagen);

                paneContenedor.getChildren().addAll(vistaImagen, labelNombreArchivo);
            } catch (Exception e) {
            e.printStackTrace();
            }
        }*/
    
    
    /*
    private int guardarDocumento(Actividad documentoGuardado) throws SQLException {
        int idActividad = -1; // 
        try {
            ResultadoOperacion resultadoPostularAnteproyecto = ActividadDAO.guardarActividad(documentoGuardado);
            if (!resultadoPostularAnteproyecto.isError()) {
                idActividad = resultadoPostularAnteproyecto.getFilasAfectadas();
                Utilidades.mostrarDialogoSimple("Mensaje", "Documento guardado"
                        , Alert.AlertType.INFORMATION);
            } else {
                Utilidades.mostrarDialogoSimple("Error", "Campos faltantes"
                        , Alert.AlertType.ERROR);
        }
        } catch (SQLException e) {
            Utilidades.mostrarDialogoSimple("Error"
                    , "Error de conexión", Alert.AlertType.ERROR);
        }
        return idActividad;
    }
    */
  
    
    
}


  
/* 
Autor: Miguel Martinez. 
Creado: 25/05/2023 
Modificado: 15/06/2023  
Descripción: Este formulario sirve para guardar datos del cuerpo académico
             que el administrador guardará.
*/ 
package javafxsistemaescolar.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxsistemaescolar.interfaz.INotificacionOperacionAcademico;
import javafxsistemaescolar.modelo.dao.AcademicoDAO;
import javafxsistemaescolar.modelo.pojo.Academico;
import javafxsistemaescolar.utils.Constantes;
import javafxsistemaescolar.utils.Utilidades;

public class FXMLFormularioCuerpoAcademicoController implements Initializable 
{
        
    @FXML
    private Label lbTitulo;
    @FXML
    private TextField tfNombreCuerpoAcademico;
    @FXML
    private TextField tfApellidoMaterno;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfApellidoPaterno;
    @FXML
    private TextField tfNumeroPersonal;
    
    private Academico AcademicoEdicion;
    private boolean esEdicionCuerpoAcademico;
    private INotificacionOperacionAcademico interfazNotificacionCuerpoAcademico;
    String estiloError = "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    String estiloNormal = "-fx-border-width: 0;";

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    public void inicializarInformacionFormularioCuerpoAcademico(boolean esEdicionCuerpoAcademico,
            Academico AcademicoEdicion, INotificacionOperacionAcademico interfazNotificacionCuerpoAcademico)
    {
        this.esEdicionCuerpoAcademico = esEdicionCuerpoAcademico;
        this.AcademicoEdicion = AcademicoEdicion;
        this.interfazNotificacionCuerpoAcademico = interfazNotificacionCuerpoAcademico;
        
        if(esEdicionCuerpoAcademico)
        {
            lbTitulo.setText("Editar informacion del academico : "+AcademicoEdicion.getNombreAcademico());
            cargarInformacionEdicion();
        }else
        {
            lbTitulo.setText("Registrar nuevo integrante del Cuerpo Academico");
        }
    }
    
    private void cargarInformacionEdicion()
    {
        tfNombreCuerpoAcademico.setText(AcademicoEdicion.getNombreAcademico());
        tfApellidoPaterno.setText(AcademicoEdicion.getApellidoPaterno());
        tfApellidoMaterno.setText(AcademicoEdicion.getApellidoMaterno());
        int numeroDePersonal = AcademicoEdicion.getNumeroDePersonal();
        String numeroDePersonalTexto = String.valueOf(numeroDePersonal);
        tfNumeroPersonal.setText(numeroDePersonalTexto);
        tfNumeroPersonal.setEditable(false);
        tfCorreo.setText(AcademicoEdicion.getCorreo());      
        tfCorreo.setEditable(false);
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
        boolean datosValidados = true;
        
        String nombre = tfNombreCuerpoAcademico.getText();
        String apellidoPaterno = tfApellidoPaterno.getText();
        String apellidoMaterno = tfApellidoMaterno.getText();
        String correo = tfCorreo.getText();
        int numeroDePersonal = 0;
        
        try{
            numeroDePersonal = Integer.parseInt(tfNumeroPersonal.getText());
            
            if(nombre.isEmpty())
            {
                tfNombreCuerpoAcademico.setStyle(estiloError);
                datosValidados = false;
            }
            if(apellidoPaterno.isEmpty())
            {
                tfApellidoPaterno.setStyle(estiloError);
                datosValidados = false;
            }
            if(apellidoMaterno.isEmpty())
            {
                tfApellidoMaterno.setStyle(estiloError);
                datosValidados = false;
            }
            if(!Utilidades.correoValido(correo))
            {
                tfCorreo.setStyle(estiloError);  
                datosValidados = false;
            }
        }catch(NumberFormatException e)
        {
            tfNumeroPersonal.setStyle(estiloError);            
            datosValidados = false;
        }
        
        if(!datosValidados){
            Utilidades.mostrarDialogoSimple("Campos invalidos",
                    "Hay campos invalidos, por favor corrija los campos solicitados",
                    Alert.AlertType.WARNING);
        }
        
        if(datosValidados)
        {
            Academico cuerpoAcademicoValidado = new Academico();
            cuerpoAcademicoValidado.setNombreAcademico(nombre);
            cuerpoAcademicoValidado.setApellidoPaterno(apellidoPaterno);
            cuerpoAcademicoValidado.setApellidoMaterno(apellidoMaterno);
            cuerpoAcademicoValidado.setNumeroDePersonal(numeroDePersonal);
            cuerpoAcademicoValidado.setCorreo(correo);
            
            
                if (esEdicionCuerpoAcademico) 
                {
                    cuerpoAcademicoValidado.setIdAcademico(AcademicoEdicion.getIdAcademico());                
                               
                    actualizarCuerpoAcademico(cuerpoAcademicoValidado);
                } else
                {
                    registrarCuerpoAcademico(cuerpoAcademicoValidado);
                }                                                                                   
        }        
        
    }
    
    private void registrarCuerpoAcademico(Academico AcademicoRegistro)
    {
        int codigoRespuesta = AcademicoDAO.guardarCuerpoAcademico(AcademicoRegistro);        
        switch(codigoRespuesta)
        {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion en el formulario del academico",
                        "El formulario no pudo ser guardado debido a un error de conexion",
                        Alert.AlertType.ERROR);
                break;
                
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la informacion en el formulario",
                        "La informacion del academico no puede ser guardada, por favor verifique su conexion",
                        Alert.AlertType.WARNING);
                break;
                
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Academico resgitrado",
                        "La informacion del academico fue guardada correctamente",
                        Alert.AlertType.INFORMATION);
                
                cerrarVentana();     
                interfazNotificacionCuerpoAcademico.notificarOperacionAcademicoGuardar(AcademicoRegistro.getNombreAcademico());                
                break;
        }        
    }
    
    private void actualizarCuerpoAcademico(Academico AcademicoActualizar)
    {
        int codigoRespuesta = AcademicoDAO.modificarCuerpoAcademico(AcademicoActualizar);
         switch(codigoRespuesta)
         {
                    case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error de conexion al actualizar el academico",
                        "El academico no pudo ser actualizado debido a un error de conexion",
                        Alert.AlertType.ERROR);
                     break;
                
                case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error en la informacion al actualizar el academico",
                        "La informacion del academico no puede ser actualizada, por favor verifique su conexion",
                        Alert.AlertType.WARNING);
                    break;
                
                case Constantes.OPERACION_EXITOSA:
                    Utilidades.mostrarDialogoSimple("Academico actualizado",
                        "La informacion del academico fue guardada correctamente",
                        Alert.AlertType.INFORMATION);
                
                    cerrarVentana();
                    interfazNotificacionCuerpoAcademico.notificarOperacionAcademicoActualizar(AcademicoActualizar.getNombreAcademico());
                
                    break;
        }            
    }     
    
    private void establecerEstiloNormal()
    {
        tfNombreCuerpoAcademico.setStyle(estiloNormal);
        tfApellidoPaterno.setStyle(estiloNormal);     
        tfApellidoMaterno.setStyle(estiloNormal);        
        tfNumeroPersonal.setStyle(estiloNormal);   
        tfCorreo.setStyle(estiloNormal);
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
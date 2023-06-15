/* 
Autor: Ian Rumayor. 
Creado: 28/05/2023 
Modificado: 12/06/2023  
Descripción: Controlador de registro de LGAC
*/
package javafxsistemaescolar.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxsistemaescolar.modelo.dao.CuerpoAcademicoDAO;
import javafxsistemaescolar.modelo.dao.LGACDAO;
import javafxsistemaescolar.modelo.pojo.CuerpoAcademico;
import javafxsistemaescolar.modelo.pojo.CuerpoAcademicoRespuesta;
import javafxsistemaescolar.modelo.pojo.LGAC;
import javafxsistemaescolar.utils.Constantes;
import javafxsistemaescolar.utils.Utilidades;

public class FXMLNuevaLGACController implements Initializable {

    @FXML
    private TextField tfNombre;
    @FXML
    private TextArea taDescripcion;
    @FXML
    private Button btnRegresar;      
    @FXML
    private ComboBox<CuerpoAcademico> cbCuerpoAcademico;
    
    private ObservableList<CuerpoAcademico> cuerpoAcademico;
    String estiloError = "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    String estiloNormal = "-fx-border-width: 0;";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformacionCuerpoAcademico();
    }    

    @FXML
    private void clicBtnRegistrar(ActionEvent event) {
        validarCampos();
    }
    
    @FXML
    private void clicBtnRegresar(ActionEvent event) {
        cerrarVentana();
    }
    
    private void establecerEstiloNormal(){
        tfNombre.setStyle(estiloNormal);
        taDescripcion.setStyle(estiloNormal);
        cbCuerpoAcademico.setStyle(estiloNormal);
    }
    
    private void validarCampos(){
        establecerEstiloNormal();
        boolean datosValidos = true;
        String nombre = tfNombre.getText();
        String descripcion = taDescripcion.getText();
        int posicionCuerpoAcademico = cbCuerpoAcademico.getSelectionModel().getSelectedIndex();
        if(nombre.isEmpty()){
            tfNombre.setStyle(estiloError);
            datosValidos = false;
        }
        if(descripcion.isEmpty()){
            taDescripcion.setStyle(estiloError);
            datosValidos = false;
        }
        if(posicionCuerpoAcademico == -1){
            cbCuerpoAcademico.setStyle(estiloError);
            datosValidos = false;
        } 
        if(datosValidos){
            LGAC ingresarLGAC = new LGAC();
            ingresarLGAC.setNombre(nombre);
            ingresarLGAC.setDescripcion(descripcion);
            ingresarLGAC.setIdCuerpoAcademico(cuerpoAcademico.get(posicionCuerpoAcademico).getIdCuerpoAcademico());
            registrarLGAC(ingresarLGAC);
        }
    }
    
    private void registrarLGAC(LGAC nuevaLGAC){
        int codigoRespuesta = LGACDAO.guardarLGAC(nuevaLGAC);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión", 
                        "La LGAC no pudo ser guardado debido a un error en su conexión...", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la información", 
                        "La información de la LGAC no puede ser guardada, por favor verifique su información e intente más tarde.", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("LGAC registrada", 
                        "La información de la LGAC fue guardada correctamente", 
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
                break;
        }
    }
    
    private void cargarInformacionCuerpoAcademico(){
        cuerpoAcademico = FXCollections.observableArrayList();
        CuerpoAcademicoRespuesta cuerpoAcademicoCB = CuerpoAcademicoDAO.obtenerInformacionCuerpoAcademico();
        switch(cuerpoAcademicoCB.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                  Utilidades.mostrarDialogoSimple("Error de conexión", 
                            "Error en la conexión con la base de datos.", 
                            Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                  Utilidades.mostrarDialogoSimple("Error de consulta", 
                          "Por el momento no se puede obtener la información", 
                          Alert.AlertType.INFORMATION);
                break;
            case Constantes.OPERACION_EXITOSA:
                  cuerpoAcademico.addAll(cuerpoAcademicoCB.getCuerpoAcademico());
                  cbCuerpoAcademico.setItems(cuerpoAcademico);
                break;
        }
    }
    
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) btnRegresar.getScene().getWindow();
        escenarioBase.close();
    }
}

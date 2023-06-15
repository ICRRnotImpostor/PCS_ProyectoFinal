/* 
Autor: Ian Rumayor. 
Creado: 06/05/2023 
Modificado: 15/06/2023  
Descripción: El controlador se creo para poder separar las operaciones de UPDATE del primer formulario
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxsistemaescolar.modelo.dao.AcademicoDAO;
import javafxsistemaescolar.modelo.dao.AnteproyectoDAO;
import javafxsistemaescolar.modelo.dao.CuerpoAcademicoDAO;
import javafxsistemaescolar.modelo.dao.LGACDAO;
import javafxsistemaescolar.modelo.pojo.Academico;
import javafxsistemaescolar.modelo.pojo.AcademicoRespuesta;
import javafxsistemaescolar.modelo.pojo.Anteproyecto;
import javafxsistemaescolar.modelo.pojo.CuerpoAcademico;
import javafxsistemaescolar.modelo.pojo.CuerpoAcademicoRespuesta;
import javafxsistemaescolar.modelo.pojo.LGAC;
import javafxsistemaescolar.modelo.pojo.LGACRespuesta;
import javafxsistemaescolar.utils.Constantes;
import javafxsistemaescolar.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author ianca
 */
public class FXMLFormularioAnteproyectosModController implements Initializable {

    @FXML
    private TextField tfNombreProyecto;
    @FXML
    private DatePicker dpFecha;
    @FXML
    private TextField tfLineaInvestigacion;
    @FXML
    private TextField tfDuracion;
    @FXML
    private TextField tfmodalidad;
    @FXML
    private TextField tfTrabajoRecepcional;
    @FXML
    private TextArea taRequisitos;
    @FXML
    private TextArea taResultados;
    @FXML
    private TextArea taDescripcion;
    @FXML
    private TextArea taBibliografia;
    @FXML
    private TextArea taNotas;
    @FXML
    private ComboBox<Academico> cbDirector;
    @FXML
    private ComboBox<Academico> cbCodirector;
    @FXML
    private ComboBox<LGAC> cbLGAC;
    @FXML
    private Button btnRegresar;
    @FXML
    private ComboBox<CuerpoAcademico> cbCuerpoAcademico;
    
    private ObservableList<LGAC> ligas;
    private ObservableList<Academico> perAcademico;
    private ObservableList<CuerpoAcademico> cuerpoAcademico;
    
    String estiloError = "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    String estiloNormal = "-fx-border-width: 0;";
    
    private Anteproyecto anteproyectoModificable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarLGAC();
        cargarInformacionAcademico();
        cargarInformacionCuerpoAcademico();
    }    

    @FXML
    private void clicBtnModificar(ActionEvent event) {
        validarCampos();
    }

    @FXML
    private void clicBtnRegresar(ActionEvent event) {
        cerrarVentana();
    }
    
    public void inicializarInformacion(Anteproyecto anteproyectoModificable){
        this.anteproyectoModificable = anteproyectoModificable;     
        cargarInformacionEdicion();
    }
    
    private void cargarInformacionEdicion(){
        tfNombreProyecto.setText(anteproyectoModificable.getNombreProyecto());
        dpFecha.setValue(LocalDate.parse(anteproyectoModificable.getFecha()));
        tfLineaInvestigacion.setText(anteproyectoModificable.getLineaDeInvestigacion());
        tfDuracion.setText(anteproyectoModificable.getDuracionAproximada());
        tfmodalidad.setText(anteproyectoModificable.getModalidad());
        tfTrabajoRecepcional.setText(anteproyectoModificable.getNombreTrabajoRecepcional());
        taRequisitos.setText(anteproyectoModificable.getRequisitos());
        taResultados.setText(anteproyectoModificable.getResultadosEsperados());
        taDescripcion.setText(anteproyectoModificable.getDescripcionProyecto());
        taBibliografia.setText(anteproyectoModificable.getBibliografiaRecomendada());
        taNotas.setText(anteproyectoModificable.getNotas());
        int posicionLGAC = obtenerPosicionLGAC(anteproyectoModificable.getIdLGAC());
        cbLGAC.getSelectionModel().select(posicionLGAC);
        int posicionDirector = obtenerPosicionDirector(anteproyectoModificable.getIdDirector());
        cbDirector.getSelectionModel().select(posicionDirector);
        int posicionCodirector = obtenerPosicionCodirector(anteproyectoModificable.getIdCodirector());
        cbCodirector.getSelectionModel().select(posicionCodirector);
        int posicionCuerpoAcademico = obtenerPosicionCuerpoAcademico(anteproyectoModificable.getIdCuerpoAcademico());
        cbCuerpoAcademico.getSelectionModel().select(posicionCuerpoAcademico);  
    }
    
    private void establecerEstiloNormal(){
        tfNombreProyecto.setStyle(estiloNormal);
        dpFecha.setStyle(estiloNormal);
        tfLineaInvestigacion.setStyle(estiloNormal);
        tfDuracion.setStyle(estiloNormal);
        tfmodalidad.setStyle(estiloNormal);
        tfTrabajoRecepcional.setStyle(estiloNormal);
        taRequisitos.setStyle(estiloNormal);
        taResultados.setStyle(estiloNormal);
        taDescripcion.setStyle(estiloNormal);
        taBibliografia.setStyle(estiloNormal);
        taNotas.setStyle(estiloNormal);
        cbLGAC.setStyle(estiloNormal);
        cbDirector.setStyle(estiloNormal);
        cbCodirector.setStyle(estiloNormal);
        cbCuerpoAcademico.setStyle(estiloNormal);
    }
    
    private void validarCampos(){
        establecerEstiloNormal();
        boolean datosValidos = true;
        String nombreProyecto = tfNombreProyecto.getText();
        String lineaInvestigacion = tfLineaInvestigacion.getText();
        String duracion = tfDuracion.getText();
        String modalidad = tfmodalidad.getText();
        String trabajoRecepcional = tfTrabajoRecepcional.getText();
        String requisitos = taRequisitos.getText();
        String resultados = taResultados.getText();
        String descripcion = taDescripcion.getText();
        String bibliografia = taBibliografia.getText();
        String notas = taNotas.getText();
        LocalDate fecha = dpFecha.getValue();
        int posicionLGAC = cbLGAC.getSelectionModel().getSelectedIndex();
        int posicionDirector = cbDirector.getSelectionModel().getSelectedIndex();
        int posicionCodirector = cbCodirector.getSelectionModel().getSelectedIndex();
        int posicionCuerpoAcademico = cbCuerpoAcademico.getSelectionModel().getSelectedIndex();

        if(nombreProyecto.isEmpty()){
            tfNombreProyecto.setStyle(estiloError);
            datosValidos = false;
        }
        if(posicionCuerpoAcademico == -1){
            cbCuerpoAcademico.setStyle(estiloError);
            datosValidos = false;
        } 
        if (fecha == null) {
            dpFecha.setStyle(estiloError);
            datosValidos = false;
        }   
        if(lineaInvestigacion.isEmpty()){
            tfLineaInvestigacion.setStyle(estiloError);
            datosValidos = false;
        }   
        if(posicionLGAC == -1){
            cbLGAC.setStyle(estiloError);
            datosValidos = false;
        }   
        if(posicionDirector == -1){
            cbDirector.setStyle(estiloError);
            datosValidos = false;
        }     
        if(posicionCodirector == -1){
            cbCodirector.setStyle(estiloError);
            datosValidos = false;
        }
        if(duracion.isEmpty()){
            tfDuracion.setStyle(estiloError);
            datosValidos = false;
        }
        if(modalidad.isEmpty()){
            tfmodalidad.setStyle(estiloError);
            datosValidos = false;
        }
        if(trabajoRecepcional.isEmpty()){
            tfTrabajoRecepcional.setStyle(estiloError);
            datosValidos = false;
        }
        if(requisitos.isEmpty()){
            taRequisitos.setStyle(estiloError);
            datosValidos = false;
        }
        if(resultados.isEmpty()){
            taResultados.setStyle(estiloError);
            datosValidos = false;
        }
        if(descripcion.isEmpty()){
            taDescripcion.setStyle(estiloError);
            datosValidos = false;
        }
        if(bibliografia.isEmpty()){
            taBibliografia.setStyle(estiloError);
            datosValidos = false;
        }
        if(notas.isEmpty()){
            taNotas.setStyle(estiloError);
            datosValidos = false;
        }        
        if(datosValidos){
            Anteproyecto ingresarAnteproyecto = new Anteproyecto();
            ingresarAnteproyecto.setNombreProyecto(nombreProyecto);
            ingresarAnteproyecto.setIdCuerpoAcademico(cuerpoAcademico.get(posicionCuerpoAcademico).getIdCuerpoAcademico());
            ingresarAnteproyecto.setFecha(fecha.toString());
            ingresarAnteproyecto.setLineaDeInvestigacion(lineaInvestigacion);
            ingresarAnteproyecto.setDuracionAproximada(duracion);
            ingresarAnteproyecto.setModalidad(modalidad);
            ingresarAnteproyecto.setNombreTrabajoRecepcional(trabajoRecepcional);
            ingresarAnteproyecto.setRequisitos(requisitos);
            ingresarAnteproyecto.setDescripcionProyecto(descripcion);
            ingresarAnteproyecto.setBibliografiaRecomendada(bibliografia);
            ingresarAnteproyecto.setNotas(notas);
            ingresarAnteproyecto.setIdLGAC(ligas.get(posicionLGAC).getIdLGAC());
            ingresarAnteproyecto.setIdDirector(perAcademico.get(posicionDirector).getIdAcademico());
            ingresarAnteproyecto.setIdCodirector(perAcademico.get(posicionCodirector).getIdAcademico());
            ingresarAnteproyecto.setResultadosEsperados(resultados);  
            ingresarAnteproyecto.setIdAnteproyecto(anteproyectoModificable.getIdAnteproyecto());
            modificarAnteproyecto(ingresarAnteproyecto);
        } 
    }
  
    private void modificarAnteproyecto(Anteproyecto anteproyectoMod){
        int codigoRespuesta = AnteproyectoDAO.modificarAnteproyecto(anteproyectoMod);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión", 
                        "El Anteproyecto no pudo ser actualizado debido a un error en su conexión...", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la información", 
                        "La información del Anteproyecto no puede ser actualizada, por favor verifique su información e intente más tarde.", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Anteproyecto actualizado", 
                        "La información del Anteproyecto fue actualizada correctamente", 
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
                break;
        }
    }
    
    private void cargarLGAC(){
        ligas = FXCollections.observableArrayList();
        LGACRespuesta lgacCB = LGACDAO.obtenerInformacionLGAC();
        switch(lgacCB.getCodigoRespuesta()){
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
                  ligas.addAll(lgacCB.getLgac());
                  cbLGAC.setItems(ligas);
                break;
        }
    }
    
    private void cargarInformacionAcademico(){
        perAcademico = FXCollections.observableArrayList();
        AcademicoRespuesta AcademicoCB = AcademicoDAO.obtenerInformacionAcademico();
        switch(AcademicoCB.getCodigoRespuesta()){
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
                  perAcademico.addAll(AcademicoCB.getAcademico());
                  cbDirector.setItems(perAcademico);
                  cbCodirector.setItems(perAcademico);
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
    
    private int obtenerPosicionLGAC(int idLGAC){
        for (int i = 0; i < ligas.size(); i++) {
            if(ligas.get(i).getIdLGAC() == idLGAC)
                return i;
        }
        return 0;
    }
    
    private int obtenerPosicionDirector(int idDirector){
        for (int i = 0; i < perAcademico.size(); i++) {
            if(perAcademico.get(i).getIdAcademico() == idDirector)
                return i;
        }
        return 0;
    }
    
    private int obtenerPosicionCodirector(int idCodirector){
        for (int i = 0; i < perAcademico.size(); i++) {
            if(perAcademico.get(i).getIdAcademico() == idCodirector)
                return i;
        }
        return 0;
    }
    
    private int obtenerPosicionCuerpoAcademico(int idCuerpoAcademico){
        for (int i = 0; i < cuerpoAcademico.size(); i++) {
            if(cuerpoAcademico.get(i).getIdCuerpoAcademico() == idCuerpoAcademico)
                return i;
        }
        return 0;
    }
    
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) btnRegresar.getScene().getWindow();
        escenarioBase.close();
    }
}

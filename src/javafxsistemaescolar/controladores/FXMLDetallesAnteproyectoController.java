/* 
Autor: Ian Rumayor. 
Creado: 28/05/2023 
Modificado: 15/06/2023  
Descripción: El controlador ademas de mostrar la informacion del anteproyecto muestra alterna entre 
asignar o no a un estudiantes dependiendo el privilegio
*/
package javafxsistemaescolar.controladores;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsistemaescolar.JavaFXSistemaEscolar;
import javafxsistemaescolar.modelo.dao.AcademicoDAO;
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

public class FXMLDetallesAnteproyectoController implements Initializable {

    @FXML
    private TextField tfNombreProyecto;
    @FXML
    private DatePicker dpFecha;
    @FXML
    private TextField tfLineaInvestigacion;
    @FXML
    private TextField tfDuracion;
    @FXML
    private TextField tfModalidad;
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
    private ComboBox<LGAC> cbLgac;
    @FXML
    private Button btnRegresar;
    @FXML
    private ComboBox<CuerpoAcademico> cbCuerpoAcademico;
    @FXML
    private Button btnParticipantes;
    
    private Anteproyecto anteproyectoDetalles;
    private ObservableList<LGAC> ligas;
    private ObservableList<Academico> perAcademico;
    private ObservableList<CuerpoAcademico> cuerpoAcademico;
    private boolean rCA;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarLGAC();
        cargarInformacionAcademico();
        cargarInformacionCuerpoAcademico();
    }    

    @FXML
    private void clicBtnRegresar(ActionEvent event) {
        Stage scenarioDetallesAnteproyectos = (Stage) this.btnRegresar.getScene().getWindow();
        scenarioDetallesAnteproyectos.close();
    }
    
    @FXML
    private void clicBtnParticipantes(ActionEvent event) {       
        cargarParticipantes();
    }
    
    public void inicializarInformacionDetalles(boolean rCA, Anteproyecto anteproyectoDetalles){
        this.rCA = rCA;
        if(rCA){
            btnParticipantes.setDisable(true);
        }
        this.anteproyectoDetalles = anteproyectoDetalles;
        cargarInformacionEdicion(); 
    }
    
    private void cargarInformacionEdicion(){
        tfNombreProyecto.setText(anteproyectoDetalles.getNombreProyecto());
        dpFecha.setValue(LocalDate.parse(anteproyectoDetalles.getFecha()));
        tfLineaInvestigacion.setText(anteproyectoDetalles.getLineaDeInvestigacion());
        tfDuracion.setText(anteproyectoDetalles.getDuracionAproximada());
        tfModalidad.setText(anteproyectoDetalles.getModalidad());
        tfTrabajoRecepcional.setText(anteproyectoDetalles.getNombreTrabajoRecepcional());
        taRequisitos.setText(anteproyectoDetalles.getRequisitos());
        taResultados.setText(anteproyectoDetalles.getResultadosEsperados());
        taDescripcion.setText(anteproyectoDetalles.getDescripcionProyecto());
        taBibliografia.setText(anteproyectoDetalles.getBibliografiaRecomendada());
        taNotas.setText(anteproyectoDetalles.getNotas());
        int posicionLGAC = obtenerPosicionLGAC(anteproyectoDetalles.getIdLGAC());
        cbLgac.getSelectionModel().select(posicionLGAC);
        int posicionDirector = obtenerPosicionDirector(anteproyectoDetalles.getIdDirector());
        cbDirector.getSelectionModel().select(posicionDirector);
        int posicionCodirector = obtenerPosicionCodirector(anteproyectoDetalles.getIdCodirector());
        cbCodirector.getSelectionModel().select(posicionCodirector);
        int posicionCuerpoAcademico = obtenerPosicionCuerpoAcademico(anteproyectoDetalles.getIdCuerpoAcademico());
        cbCuerpoAcademico.getSelectionModel().select(posicionCuerpoAcademico); 
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
                  cbLgac.setItems(ligas);
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

    private void cargarParticipantes(){
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSistemaEscolar.class.getResource("vistas/FXMLParticipantes.fxml"));
            Parent vista = accesoControlador.load();
            FXMLParticipantesController modificable = accesoControlador.getController();

            modificable.inicializarDatos(anteproyectoDetalles.getIdAnteproyecto());
            System.out.println(anteproyectoDetalles.getIdAnteproyecto());
            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene(vista));
            escenarioFormulario.setTitle("Formulario");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(FXMLAnteproyectosAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

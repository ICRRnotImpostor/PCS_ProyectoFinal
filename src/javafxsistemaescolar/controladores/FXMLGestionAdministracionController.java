/* 
Autor: Miguel Martinez. 
Creado: 25/05/2023 
Modificado: 11/06/2023  
Descripción: Este es el controller de la vista principal donde el administrador
             podrá estár gestionando a los usuarios, cursos y cuerpo académico.
*/
package javafxsistemaescolar.controladores;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsistemaescolar.JavaFXSistemaEscolar;
import javafxsistemaescolar.interfaz.INotificacionOperacionAcademico;
import javafxsistemaescolar.interfaz.INotificacionOperacionCurso;
import javafxsistemaescolar.interfaz.INotificacionOperacionUsuario;
import javafxsistemaescolar.modelo.dao.AcademicoDAO;
import javafxsistemaescolar.modelo.dao.CursoDAO;
import javafxsistemaescolar.modelo.dao.UsuarioDAO;
import javafxsistemaescolar.modelo.pojo.Academico;
import javafxsistemaescolar.modelo.pojo.AcademicoRespuesta;
import javafxsistemaescolar.modelo.pojo.Curso;
import javafxsistemaescolar.modelo.pojo.CursoRespuesta;
import javafxsistemaescolar.modelo.pojo.Usuario;
import javafxsistemaescolar.modelo.pojo.UsuarioRespuesta;
import javafxsistemaescolar.utils.Constantes;
import javafxsistemaescolar.utils.Utilidades;

public class FXMLGestionAdministracionController implements Initializable, INotificacionOperacionUsuario, 
        INotificacionOperacionCurso,  INotificacionOperacionAcademico
{
    //Se separan los componentes para diferenciar para qué se usan
    @FXML
    private TableView<Usuario> tvUsuarios;   
    @FXML
    private TableColumn colNombreUsr;
    @FXML
    private TableColumn colApellidoPat;
    @FXML
    private TableColumn colApellidoMat;
    @FXML
    private TableColumn colTipoUsuario;    
  
    private ObservableList<Usuario> usuarios;
    private INotificacionOperacionUsuario interfazNotificacionUsuario;
     
    @FXML
    private TableView<Curso> tvCursos;
    @FXML
    private TableColumn colNombreCurso;
    @FXML
    private TableColumn colPeriodoCurso;
    @FXML
    private TableColumn colFechaInicioCurso;
    @FXML
    private TableColumn colFechaFinCurso;
    
    private ObservableList<Curso> cursos;
    private INotificacionOperacionCurso interfazNotificacionCurso;
   
    
    @FXML
    private TableView<Academico> tvCuerpoAcademico;
    @FXML
    private TableColumn colNombreCA;
    @FXML
    private TableColumn colApellidoPatCA;
    @FXML
    private TableColumn colApellidoMatCA;
    @FXML
    private TableColumn colNumeroPersonalCA;
    @FXML
    private TableColumn colCorreoCA;
    
     private ObservableList<Academico> cuerposAcademicos;
     private INotificacionOperacionAcademico interfazNotificacionCuerpoAcademico;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        configurarTablaUsuarios();
        cargarInformacionTablaUsuarios();
        
        configurarTablaCurso();
        cargarInformacionTablaCurso();
        
        configurarTablaCuerpoAcademico();
        cargarInformacionTablaCuerpoAcademico();
    }    

    //Componentes para los usuarios
    private void configurarTablaUsuarios()
    {
        colNombreUsr.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidoPat.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colApellidoMat.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        colTipoUsuario.setCellValueFactory(new PropertyValueFactory("privilegios"));
        
    }
    
    private void cargarInformacionTablaUsuarios()
    {
        usuarios = FXCollections.observableArrayList();
        UsuarioRespuesta respuestaBD = UsuarioDAO.obtenerInformacionUsuarios();
        
        switch(respuestaBD.getCodigoRespuesta())
        {
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Sin conexión",
                            "Los sentimos por el momento no hay conexión para poder cargar la información", 
                            Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error al cargar los datos", 
                            "Hubo un error al cargar la información por favor inténtelo más tarde", 
                            Alert.AlertType.WARNING);
                break;
                
            case Constantes.OPERACION_EXITOSA:
                    usuarios.addAll(respuestaBD.getUsuarios());
                    tvUsuarios.setItems(usuarios);                    
                break;
        }        
    }
    
    @FXML
    private void clickBtnAñadirUsuario(ActionEvent event)
    {
        irFormulario(false, null);
                
    }
    
    private void irFormulario(boolean esEdicion, Usuario usuarioEdicion)
    {
        try{
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSistemaEscolar.class.getResource("vistas/FXMLFormularioUsuario.fxml"));
            Parent vista = accesoControlador.load();
            FXMLFormularioUsuarioController formulario = accesoControlador.getController();
            formulario.inicializarInformacionFormulario(esEdicion, usuarioEdicion, this);      
                        
            Stage escenarioUsuario = new Stage();
            escenarioUsuario.setScene(new Scene(vista));
            escenarioUsuario.setTitle("Formulario");
            escenarioUsuario.initModality(Modality.APPLICATION_MODAL);
            escenarioUsuario.showAndWait();
        }catch(IOException ex)
        {
             Logger.getLogger(FXMLGestionAdministracionController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    } 
    
    //Componenetes para el curso
    private void configurarTablaCurso()
    {
        colNombreCurso.setCellValueFactory(new PropertyValueFactory("nombreCurso"));
        colPeriodoCurso.setCellValueFactory(new PropertyValueFactory("periodo"));
        colFechaInicioCurso.setCellValueFactory(new PropertyValueFactory("fechaInicioCurso"));
        colFechaFinCurso.setCellValueFactory(new PropertyValueFactory("fechaFinCurso"));        
    }
    
    private void cargarInformacionTablaCurso()
    {
        cursos = FXCollections.observableArrayList();
        CursoRespuesta respuestaBD = CursoDAO.obtenerInformacionCursos(); 
        
        switch(respuestaBD.getCodigoRespuesta())
        {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion en cargar informacion tabla de curso",
                        "Lo sentimos, por el momento no hay conexion para poder cargar la informacion",
                        Alert.AlertType.ERROR);
                break;
            
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos de los cursos",
                        "Hubo un error al cargar la informacion, por favor intentelo mas tarde",
                        Alert.AlertType.WARNING);
                break;
                
            case Constantes.OPERACION_EXITOSA:              
                    cursos.addAll(respuestaBD.getCursos());
                    tvCursos.setItems(cursos);
                break;
        }
    }

    @FXML
    private void clickBtnAñadirCurso(ActionEvent event) 
    {
        irFormularioCurso(false, null);    
        
    }
    
    private void irFormularioCurso(boolean esEdicionCurso, Curso cursoEdicion)
    {
        try{
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSistemaEscolar.class.getResource("vistas/FXMLFormularioCurso.fxml"));
            Parent vista = accesoControlador.load();
            FXMLFormularioCursoController formulario = accesoControlador.getController();
            formulario.inicializarInformacionCurso(esEdicionCurso, cursoEdicion, this);      
                        
            Stage escenarioCurso = new Stage();
            escenarioCurso.setScene(new Scene(vista));
            escenarioCurso.setTitle("Formulario");
            escenarioCurso.initModality(Modality.APPLICATION_MODAL);
            escenarioCurso.showAndWait();            
        }catch(IOException ex)
        {
             Logger.getLogger(FXMLGestionAdministracionController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
        
    private void configurarTablaCuerpoAcademico()
    {
        colNombreCA.setCellValueFactory(new PropertyValueFactory("nombreAcademico"));
        colApellidoPatCA.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colApellidoMatCA.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        colNumeroPersonalCA.setCellValueFactory(new PropertyValueFactory("numeroDePersonal")); 
        colCorreoCA.setCellValueFactory(new PropertyValueFactory("correo"));        
    }
    
    private void cargarInformacionTablaCuerpoAcademico()
    {
        cuerposAcademicos = FXCollections.observableArrayList();
        AcademicoRespuesta respuestaBD = AcademicoDAO.obtenerInformacionAcademico();
        
        switch(respuestaBD.getCodigoRespuesta())
        {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion al cargar informacion de la tabla del cuerpo academico",
                        "Lo sentimos, por el momento no hay conexion para poder cargar la informacion",
                        Alert.AlertType.ERROR);
                break;
            
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos",
                        "Hubo un error al cargar la informacion, por favor intentelo mas tarde",
                        Alert.AlertType.WARNING);
                break;
                
            case Constantes.OPERACION_EXITOSA:
                    cuerposAcademicos.addAll(respuestaBD.getAcademico());
                    tvCuerpoAcademico.setItems(cuerposAcademicos);
                break;
        }
    }
    
    
    @FXML
    private void clickBtnAniadirCA(ActionEvent event)
    {
        irFormularioCuerpoAcademico(false, null);
    }

    
    private void irFormularioCuerpoAcademico(boolean esEdicionCuerpoAcademico,Academico cuerpoAcademicoEdicion)
    {
        try{
            FXMLLoader accesoControlado = new FXMLLoader(JavaFXSistemaEscolar.class.getResource("vistas/FXMLFormularioCuerpoAcademico.fxml"));
            Parent vista = accesoControlado.load();
            FXMLFormularioCuerpoAcademicoController formulario = accesoControlado.getController();            
            formulario.inicializarInformacionFormularioCuerpoAcademico(esEdicionCuerpoAcademico, cuerpoAcademicoEdicion, this);            
            Stage escenarioFormularioCuerpoAcademico = new Stage();
            escenarioFormularioCuerpoAcademico.setScene(new Scene(vista));
            escenarioFormularioCuerpoAcademico.setTitle("Formulario");
            escenarioFormularioCuerpoAcademico.initModality(Modality.APPLICATION_MODAL);
            escenarioFormularioCuerpoAcademico.showAndWait();            
        }catch(IOException ex)
        {
            Logger.getLogger(FXMLGestionAdministracionController.class.getName()).log(Level.SEVERE, null, ex);
        }       
        
    }

    @FXML
    private void clickBtnMoificarCA(ActionEvent event) 
    {
        int posicion = tvCuerpoAcademico.getSelectionModel().getSelectedIndex();
        if(posicion != -1)
        {
            irFormularioCuerpoAcademico(true, cuerposAcademicos.get(posicion));
        }else
        {
            Utilidades.mostrarDialogoSimple("Selecciona un registro",
                    "Selecciona el registro en la tabla de academicos para su edicion",
                    Alert.AlertType.WARNING);
        }
    }   
    
    @Override
    public void notificarOperacionUsuarioGuardar(String nombreUsuario)
    {
        cargarInformacionTablaUsuarios();
    }    
    
    @Override
    public void notificarOperacionCursoGuardar(String nombreCurso) 
    {
        cargarInformacionTablaCurso();
    }

    @Override
    public void notificarOperacionAcademicoGuardar(String nombreCuerpoAcademico) 
    {
        cargarInformacionTablaCuerpoAcademico();
    }

    @Override
    public void notificarOperacionAcademicoActualizar(String nombreCuerpoAcademico) 
    {
        cargarInformacionTablaCuerpoAcademico();
    }
}

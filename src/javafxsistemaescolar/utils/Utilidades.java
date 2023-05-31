/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxsistemaescolar.utils;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafxsistemaescolar.JavaFXSistemaEscolar;


/**
 *
 * @author migue
 */
public class Utilidades {
     public static void mostrarDialogoSimple(String titulo, String mensaje, Alert.AlertType tipo){
        Alert alertaSimple = new Alert(tipo);
        alertaSimple.setTitle(titulo);
        alertaSimple.setContentText(mensaje);
        alertaSimple.setHeaderText(null);
        alertaSimple.showAndWait();
     }
     
     public static boolean mostrarDialogoConfirmacion(String titulo, String mensaje){
         Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
         alertaConfirmacion.setTitle(titulo);
         alertaConfirmacion.setContentText(mensaje);
         alertaConfirmacion.setHeaderText(null);
         Optional<ButtonType> botonClick = alertaConfirmacion.showAndWait();
         return (botonClick.get() == ButtonType.OK);
     }
     
     public static Scene inicializarEscena(String ruta){
         Scene escena = null;
         
         try{
            Parent vista = FXMLLoader.load(JavaFXSistemaEscolar.class.getResource(ruta));
            escena = new  Scene(vista);
         } catch(IOException ex){
           System.err.println("Error "+ex.getMessage());
         }
         return escena;         
     }
     
     public static boolean correoValido(String correo){    
         //if(correo != null && !correo.isEmpty())
         if(correo != null && correo.length() > 0){
             Pattern patronCorreo = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+");
         //Que carge el patron a un elemento de match (evaluacion del patron). COnfiguracion de math. Segundo elemento, qué encntraste
         //Se configura el patron con el (correo)
         Matcher matchPatron = patronCorreo.matcher(correo);
         //si se encuentra o no, regresa un boolean
         return matchPatron.find();
         
             
        }else{
         return false;
        }
         
     }
     
}

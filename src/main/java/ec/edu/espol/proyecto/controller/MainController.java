package ec.edu.espol.proyecto.controller;

import ec.edu.espol.proyecto.utils.Util;
import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public final class MainController {
    
    private Stage             stage;
    private final String[] opcModoJuego={"Jugador vs Computador", "Computador vs Computador", "Jugador vs Jugador"};
    private final String[] opcMarca={"X", "O"};
    private final String[] opcInicio={"Jugador 1", "Jugador 2"};
    
    @FXML
    private ChoiceBox<String> cbModoJuego;
    
    @FXML
    private ChoiceBox<String> cbMarca;
    
    @FXML
    private ChoiceBox<String> cbInicio;
    
     @FXML
    private TextField tfJugador1;
      @FXML
    private TextField tfJugador2;
      
    private Button btnEmpezar;
    
    @FXML
    private void onBtnEmpezarClick(final ActionEvent ae) throws IOException {
        
        final FXMLLoader fxmlLoader = Util.getFXMLLoader("second");
        final Parent root = fxmlLoader.load();
        final SecondController Controller = fxmlLoader.getController();
        
        //Controller.setNombre1(tfJugador1.getText());   
        //Controller.setNombre2(tfJugador2.getText());
        //Controller.setMarca1(cbMarca.getValue());
        
        stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    @FXML
    private void onSalirBtnClick() {
        Platform.exit();
    }

    public void initialize() {
        cbModoJuego.getItems().addAll(opcModoJuego);
        cbMarca.getItems().addAll(opcMarca);  
        cbInicio.getItems().addAll(opcInicio);    
        cbModoJuego.setValue(opcModoJuego[0]);
        cbMarca.setValue(opcMarca[0]);
        cbInicio.setValue(opcInicio[0]);
        
    }
}
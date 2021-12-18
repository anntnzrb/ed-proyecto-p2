package ec.edu.espol.proyecto.controller;

import ec.edu.espol.proyecto.juego.Player;
import ec.edu.espol.proyecto.utils.Util;
import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

final public class SecondController {

    private Stage stage;
    private Player player1;
    private Player player2;

    @FXML
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btnRegresar;
    private Button btnMostrar;
    private Button btnIniciar;
    private Label lblJugador1;
    private Label lblJugador2;
    private Label lblMarca1;
    private Label lblMarca2;

    public void setNombre1(final String nombre1) {
        lblJugador1.setText(nombre1);
    }

    public void setNombre2(final String nombre2) {
        lblJugador2.setText(nombre2);
    }

    public void setMarca1(final String marca) {
        lblMarca1.setText(marca);
    }

    public void setMarca2(final String marca) {
        lblMarca2.setText(marca);
    }

    private void crearJugador() {
        player1 = new Player(lblJugador1.getText());
        lblJugador1.setText(String.format(player1.getNickname()));
        
        player2 = new Player(lblJugador2.getText());
        lblJugador2.setText(String.format(player2.getNickname()));
        
    }

    @FXML
    private void onRegresarBtnClick(final ActionEvent ae) throws IOException {
        stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
        stage.setScene(Util.getNewScene("main"));
        stage.show();
    }

    @FXML
    private void onSalirBtnClick() {
        Platform.exit();
    }
    
//    
//    private void onIniciarBtnClick() {
//    
//        crearJugador();
//       
//    }

    @FXML
    public void initialize() {
        
    }

}

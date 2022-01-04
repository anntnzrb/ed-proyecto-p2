package ec.edu.espol.proyecto.controller;

import ec.edu.espol.proyecto.utils.Util;
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

import java.io.IOException;

import static ec.edu.espol.proyecto.game.Game.O_MARK;
import static ec.edu.espol.proyecto.game.Game.X_MARK;

public final class MainController {
    private       Stage    stage;
    private final String[] gameModes = {"Jugador vs Computador", "Computador vs Computador", "Jugador vs Jugador"};
    private final String[] marks     = {Character.toString(X_MARK), Character.toString(O_MARK)};
    private final String[] players   = {"Jugador 1", "Jugador 2"};

    @FXML
    private Button            btnStart;
    @FXML
    private ChoiceBox<String> cbGameMode;
    @FXML
    private ChoiceBox<String> cbMark;
    @FXML
    private ChoiceBox<String> cbInitPlayer;
    @FXML
    private TextField         txtPlayer1;
    @FXML
    private TextField         txtPlayer2;

    /* constructores */
    private MainController() { }

    @FXML
    private void onBtnEmpezarClick(final ActionEvent ae) throws IOException {
        final FXMLLoader fxmlLoader = Util.getFXMLLoader("second");
        final Parent root = fxmlLoader.load();
        final SecondController secondController = fxmlLoader.getController();

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
        cbGameMode.getItems().addAll(gameModes);
        cbMark.getItems().addAll(marks);
        cbInitPlayer.getItems().addAll(players);
        cbGameMode.setValue(gameModes[0]);
        cbMark.setValue(marks[0]);
        cbInitPlayer.setValue(players[0]);
    }
}
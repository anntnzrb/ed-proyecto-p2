package ec.edu.espol.proyecto.controller;

import ec.edu.espol.proyecto.game.Board;
import ec.edu.espol.proyecto.game.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import static ec.edu.espol.proyecto.game.Game.O_MARK;
import static ec.edu.espol.proyecto.game.Game.X_MARK;

public final class SecondController {
    private Stage stage;

    /* juego */
    private Board  board;
    private Player p1;
    private Player p2;

    @FXML
    private GridPane tableroGP;
    @FXML
    private Button   btnStart;
    @FXML
    private Button   btnReturn;
    @FXML
    private Button   btnShowTree;
    @FXML
    private Label    lblNamePlayer1;
    @FXML
    private Label    lblNamePlayer2;
    @FXML
    private Label    lblMarkPlayer1;
    @FXML
    private Label    lblMarkPlayer2;

    @FXML
    private void onStartBtnClick() {
        createPlayer();
        System.out.println(p1.getNickname());
        System.out.println(p1.getMark());
        System.out.println(p2.getNickname());
        System.out.println(p2.getMark());
    }

    @FXML
    private void onExitBtnClick() {
        Platform.exit();
    }

    @FXML
    private void onReturnBtnClick() {
        Platform.exit();
    }

    void setNames(final String name1, final String name2) {
        lblNamePlayer1.setText(name1);
        lblNamePlayer2.setText(name2);
    }

    /**
     * Método encargado de crear & actualizar el {@link Board}.
     */
    private void updateBoard() {
        tableroGP.getChildren().clear();
    }

    /**
     * Arma el tablero a partir de parámetros específicos.
     */
    private void buildBoard() {
        board = new Board(p1, p2);
    }

    /**
     * Método encargado de crear el {@link Player} del {@link Board}.
     */
    private void createPlayer() {
        p1 = new Player(lblNamePlayer1.getText(), lblMarkPlayer1.getText().charAt(0));
        p2 = new Player(lblNamePlayer2.getText(), lblMarkPlayer2.getText().charAt(0));
    }

    @FXML
    public void initialize() {

    }

    void setPrimaryMark(final char mark) {
        if (mark == X_MARK) {
            lblMarkPlayer1.setText(Character.toString(X_MARK));
            lblMarkPlayer2.setText(Character.toString(O_MARK));
        } else {
            lblMarkPlayer1.setText(Character.toString(O_MARK));
            lblMarkPlayer2.setText(Character.toString(X_MARK));
        }
    }
}

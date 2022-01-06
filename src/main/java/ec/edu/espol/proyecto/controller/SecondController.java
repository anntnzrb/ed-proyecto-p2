package ec.edu.espol.proyecto.controller;

import ec.edu.espol.proyecto.game.Board;
import ec.edu.espol.proyecto.game.Player;
import ec.edu.espol.proyecto.game.Tile;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static ec.edu.espol.proyecto.game.Game.*;

public final class SecondController {
    private Stage stage;

    /* juego */
    private Board    board;
    private Tile[][] tbl;
    private Player   p1;
    private Player   p2;
    private int      roundNum;

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
        buildBoard();

        /* debug */
        System.out.printf("El jugador #1 (%s) usa la marca '%s'\n",
                          p1.getNickname(), p1.getMark());
        System.out.printf("El jugador #2 (%s) usa la marca '%s'\n",
                          p2.getNickname(), p2.getMark());
        logRound();
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

    void setPrimaryMark(final char mark) {
        if (mark == X_MARK) {
            lblMarkPlayer1.setText(Character.toString(X_MARK));
            lblMarkPlayer2.setText(Character.toString(O_MARK));
        } else {
            lblMarkPlayer1.setText(Character.toString(O_MARK));
            lblMarkPlayer2.setText(Character.toString(X_MARK));
        }
    }

    /**
     * Método encargado de crear & actualizar el {@link Board}.
     */
    private void updateBoard() {
        tableroGP.getChildren().clear();
        for (int i = 0, row = tbl.length; i < row; ++i) {
            for (int j = 0, col = tbl[i].length; j < col; ++j) {
                final Tile tile = board.get(i, j);
                final StackPane stackPane = new StackPane(new Text(Character.toString(tile.getMark())));
                stackPane.setAlignment(Pos.CENTER);

                stackPane.setOnMouseClicked(ev -> updateTile(tile));

                /* agregar letras al GridPane */
                GridPane.setMargin(stackPane, new Insets(0, 4, 0, 4));
                tableroGP.add(stackPane, j, i);
            }
        }
    }

    private void updateTile(final Tile tile) {
        if (tile.getMark() == NULL_CHAR) {
            tile.setMark('M');
            updateBoard();
            //board.checkWin();
            ++roundNum;
            logRound();
        }
    }

    /**
     * Arma el tablero a partir de parámetros específicos.
     */
    private void buildBoard() {
        board = new Board(p1, p2);
        tbl = board.getBoard();
        updateBoard();
    }

    /**
     * Método encargado de crear el {@link Player} del {@link Board}.
     */
    private void createPlayer() {
        p1 = new Player(lblNamePlayer1.getText(), lblMarkPlayer1.getText().charAt(0));
        p2 = new Player(lblNamePlayer2.getText(), lblMarkPlayer2.getText().charAt(0));
    }

    private int logRound() {
        System.out.printf(roundNum == 0
                          ? "\nEmpezando juego... (ronda #%d)\n"
                          : "\nAvanzando a la siguiente ronda... (ronda #%d)\n", roundNum);

        return roundNum;
    }

    @FXML
    public void initialize() {
        /* se inicializa el juego desde la ronda 0 */
        roundNum = 0;
    }
}

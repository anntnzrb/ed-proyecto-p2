package ec.edu.espol.proyecto.controller;

import ec.edu.espol.proyecto.ds.Tree;
import ec.edu.espol.proyecto.game.*;
import ec.edu.espol.proyecto.utils.Util;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static ec.edu.espol.proyecto.game.Game.*;

public final class SecondController {
    private Stage       stage;
    private Tree<Board> gameBoardTree;

    /* juego */
    private Board    board;
    private Tile[][] tbl;
    private Player   p1;
    private Player   p2;
    private int      roundNum;
    private GameMode gameMode;
    private char     currentMark;

    @FXML
    private GridPane boardGP;
    @FXML
    private Button   btnStart;
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
    private Label    lblTurn;
    @FXML
    private Label    lblRound;

    @FXML
    public void initialize() {
        /* se inicializa el juego desde la ronda 1 */
        roundNum = 1;

        /* árbol de tableros */
        board=new Board();
        gameBoardTree = new Tree<>(new Board());  
    }

    @FXML
    private void onStartBtnClick() {
 
       
        /* al iniciar la partida se crea el jugador (humano) */
        p1 = new Player(lblNamePlayer1.getText(), lblMarkPlayer1.getText().charAt(0));
        

        /*
         * si se juega contra la máquina, crear un objeto AI, caso contrario,
         *  otro humano.
         */
        if (gameMode == GameMode.AI) {
            p2 = new Player("PC", lblMarkPlayer2.getText().charAt(0));

        } else {
            p2 = new Player(lblNamePlayer2.getText(), lblMarkPlayer2.getText().charAt(0));
        }

        updateGameInfo();
        buildBoard();

        /* debug */
        System.out.printf("\nSe ha inicializado un juego juego con la modalidad: '%s'\n",
                          gameMode);
        System.out.printf("El jugador #1 (%s) usa la marca '%s'\n",
                          p1.getNickname(), p1.getMark());
        if (gameMode == GameMode.AI) {
            System.out.printf("El computador usa la marca '%s'\n", p2.getMark());

        } else {
            System.out.printf("El jugador #2 (%s) usa la marca '%s'\n",
                              p2.getNickname(), p2.getMark());
        }
        logGameInfo();

        /* deshabilitar botón */
        btnStart.setDisable(true);
    }

    @FXML
    public void onTreeShowBtnClick() {
        btnShowTree.setOnAction(ev -> {
            final VBox panel = new VBox();
            panel.setSpacing(10);
            final ObservableList<Node> fondo = panel.getChildren();
            panel.setStyle("-fx-background-color: BEIGE;");
            stage.setScene(new Scene(panel, 500, 500));
            stage.show();
            btnShowTree.setVisible(false);
        });
    }

    private Tile aiMoveMark() {
        final Tile tile = null;
        final List<Board> listTables = gameBoardTree.getUtilidadMax(board);
        final List<Board> listBestTables =
                listTables.stream()
                          .filter(e -> (e.utilityBoard(p2.getMark()) == listTables.get(listTables.size() - 1)
                                                                                  .utilityBoard(p2.getMark())))
                          .collect(Collectors.toCollection(LinkedList::new));

        Collections.shuffle(listBestTables);
        return listBestTables.isEmpty() ? tile : board.obtenerCasilla(listBestTables.get(0));
    }
    
     private void generateTree(){     //cambiar nombre
        Player player;
        char marca1, marca2;
        if(p1.getMark()==currentMark){
            player = new Player(p1.getNickname(),p1.getMark());
            marca1 = p1.getMark();
            marca2 = p2.getMark();
        }            
        else{
            player = new Player(p2.getNickname(),p2.getMark());
            marca1 = p2.getMark();
            marca2 = p1.getMark();
        }
        Board tabla = new  Board();
        tabla.copyBoard(this.board);
        for (int i = 0; i < board.getBOARD_SIZE(); i++) {
            for (int j = 0; j < board.getBOARD_SIZE(); j++) {
                if(board.getBoard()[i][j].getMark()=='*'){
                    Board firstBoard  = new Board();
                    firstBoard.copyBoard(tabla);
                    firstBoard.modifyBoard(i, j, marca1);
                    gameBoardTree.add(firstBoard, tabla);
                    gameBoardTree.findNode(firstBoard).getData().setUtility(10);
                    
                    for (int k = 0; k < firstBoard.getBOARD_SIZE(); k++) {
                        for (int l = 0; l < firstBoard.getBOARD_SIZE(); l++) {
                            if(firstBoard.getBoard()[k][l].getMark()=='*'){
                                Board secondBoard = new Board(p1,p2);
                                secondBoard.copyBoard(firstBoard);
                                secondBoard.modifyBoard(k, l, marca2);
                                secondBoard.setUtility(secondBoard.utilityBoard(player.getMark()));
                                if(secondBoard.utilityBoard(player.getMark())<firstBoard.getUtility())
                                    gameBoardTree.findNode(firstBoard).getData().setUtility(secondBoard.getUtility());                                  
                                    gameBoardTree.add(secondBoard, firstBoard);
                            }
                        }
                    }
                }
            }
        }
       
    }

    private void wait(final int mill) {
        try {
            Thread.sleep(mill * 100L);
        } catch (final InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void onExitBtnClick() {
        Platform.exit();
    }

    @FXML
    private void onReturnBtnClick(final ActionEvent ae) throws IOException {
        stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
        stage.setScene(Util.getNewScene("main"));
        stage.show();
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

    void setInitMark(final String mark) {
        currentMark = mark.charAt(0);
    }

    void setGameMode(final GameMode gameMode) {
        this.gameMode = gameMode;
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
     * Método encargado de crear & actualizar el tablero {@link Board}.
     */
    private void updateBoard() {
        boardGP.getChildren().clear();
        for (int i = 0, row = tbl.length; i < row; ++i) {
            for (int j = 0, col = tbl[i].length; j < col; ++j) {
                final Tile tile = board.getTile(i, j);
                final StackPane stackPane = new StackPane(new Text(Character.toString(tile.getMark())));
                stackPane.setAlignment(Pos.CENTER);

                stackPane.setOnMouseClicked(ev -> {

                    if (gameMode == GameMode.AI) {
                        generateTree();
                        updateTile(aiMoveMark());
                    } else {
                        updateTile(tile);
                    }
                });

                /* agregar letras al GridPane */
                GridPane.setMargin(stackPane, new Insets(0, 4, 0, 4));
                boardGP.add(stackPane, j, i);
            }
        }
    }

    /**
     * Actualiza (modifica) una celda (@{@link Tile}) del tablero (@{@link Board}).
     *
     * @param tile celda a modificar
     */
    private void updateTile(final Tile tile) {
        if (tile.getMark() == NULL_CHAR) {
            tile.setMark(currentMark);
            updateBoard();

            if (checkWin(board.checkWin())) {
                boardGP.setDisable(true);
                Util.alert("El juego ha terminado, para jugar denuevo regrese al menú anterior",
                           true);
            }

            /* actualizar */
            updateGame();
        } else {
            Util.alert("No puedes colocar una marca en una celda que ya esté marcada previamente.",
                       true);
        }
    }

    /* ************************************************************************
     * métodos de actualización del juego
     * ********************************************************************* */

    private void updateGame() {
        ++roundNum;
        updateNextMark();
        updateGameInfo();
        logGameInfo();
    }

    private void updateGameInfo() {
        lblRound.setText(String.valueOf(roundNum));
        lblTurn.setText(String.valueOf(currentMark));
    }

    private void updateNextMark() {
        if (currentMark == X_MARK) {
            currentMark = O_MARK;
        } else {
            currentMark = X_MARK;
        }
    }

    private boolean checkWin(final char mark) {
        if (board.isFull()) {
            Util.alert("Este juego ha terminado en empate", true);
            return true;
        }

        final boolean isAI = gameMode == GameMode.AI;
        if (mark == X_MARK) {
            if (isAI) {
                Util.alert(p2.getMark() == X_MARK
                           ? "Ha ganado la marca 'X' (Computador)"
                           : String.format("Ha ganado la marca 'X' (%s)", p1.getNickname()), true);
            } else {
                Util.alert(p1.getMark() == X_MARK
                           ? String.format("Ha ganado la marca 'X' (%s)", p1.getNickname())
                           : String.format("Ha ganado la marca 'X' (%s)", p2.getNickname()), true);
            }

            return true;
        }
        if (mark == O_MARK) {
            if (isAI) {
                Util.alert(p2.getMark() == O_MARK
                           ? "Ha ganado la marca 'O' (Computador)"
                           : String.format("Ha ganado la marca 'O' (%s)", p1.getNickname()), true);
            } else {
                Util.alert(p1.getMark() == O_MARK
                           ? String.format("Ha ganado la marca 'O' (%s)", p1.getNickname())
                           : String.format("Ha ganado la marca 'O' (%s)", p2.getNickname()), true);
            }

            return true;
        }

        return false;
    }

    /**
     * Muestra en pantalla el estado actual del juego.
     */
    private void logGameInfo() {
        System.out.printf(roundNum == 0
                          ? "\nEmpezando juego... (ronda #%d) [%s]\n"
                          : "\nAvanzando a la siguiente ronda... (ronda #%d) [%s]\n", roundNum, currentMark);
    }
}
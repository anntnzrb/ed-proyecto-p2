package ec.edu.espol.proyecto.controller;

import ec.edu.espol.proyecto.ds.Tree;
import ec.edu.espol.proyecto.game.*;
import ec.edu.espol.proyecto.utils.Util;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import static ec.edu.espol.proyecto.game.Game.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public final class SecondController {
    private Stage stage;
    private Tree<Board> arbolTablaJuego;
    private Board tableroActual;
    private BorderPane rootJuego;

    /* juego */
    private Board    board;
    private Tile[][] tbl;
    private Player   p1;
    private Player   p2;
    private AI       ai;
    private int      roundNum;
    private GameMode gameMode;
    private char     currentMark;

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
    private Label    lblTurn;
    @FXML
    private Label    lblRound;
    
    @FXML
    public void initialize() {
        /* se inicializa el juego desde la ronda 1 */
        roundNum = 1;
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
    
////////////////////////////////////////////////////////////////////////////////   
    @FXML
    public void onTreeShowBtnClick() {
        btnShowTree.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {              
                VBox panel = new VBox();
                panel.setSpacing(10);
                ObservableList fondo = panel.getChildren();
                Stage sta = new Stage();
                panel.setStyle("-fx-background-color: BEIGE;");
                Scene escena = new Scene(panel, 500, 500);
                sta.setScene(escena);
                sta.show();
                btnShowTree.setVisible(false);
            }
        });
    }
    

    private Tile moveMachine(){
        Tile c = null;
        List<Board> listRecomendado = this.arbolTablaJuego.getUtilidadMax(board);
        List<Board> opciones = new LinkedList<>();
//        listRecomendado.forEach(e->{
//            e.showBoard();
//        });
        listRecomendado.stream().filter((e) -> 
                (e.utilityBoard(p2.getMark())==listRecomendado.get(listRecomendado.size()-1).utilityBoard(p2.getMark()))).forEachOrdered((e) -> {
            opciones.add(e);   
        });
        Collections.shuffle(opciones);
        if(!opciones.isEmpty())
            return this.board.obtenerCasilla(opciones.get(0));        
       
        return c;
    }
      
       
    private void PC() {    
            while (!checkWin(p2.getMark())) {
                if (p2.getMark() == currentMark) {           
                    Tile c = moveMachine();
                    if (c != null) {
                        updateTile(c);
                    }
                }
              
            }
        ;
       
    }

    private void esperar(int mill) {
        try {
            Thread.sleep(mill * 100);
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }

       
////////////////////////////////////////////////////////////////////////////////
       
    
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
        tableroGP.getChildren().clear();
        for (int i = 0, row = tbl.length; i < row; ++i) {
            for (int j = 0, col = tbl[i].length; j < col; ++j) {
                final Tile tile = board.getTile(i, j);
                final StackPane stackPane = new StackPane(new Text(Character.toString(tile.getMark())));
                stackPane.setAlignment(Pos.CENTER);

                stackPane.setOnMouseClicked(ev ->{
                    
                      if (gameMode==GameMode.AI){
                          updateTile(moveMachine());
                      }else {
                          updateTile(tile);
                      }
                        });

                /* agregar letras al GridPane */
                GridPane.setMargin(stackPane, new Insets(0, 4, 0, 4));
                tableroGP.add(stackPane, j, i);
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
                tableroGP.setDisable(true);
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
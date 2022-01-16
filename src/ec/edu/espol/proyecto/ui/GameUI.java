package ec.edu.espol.proyecto.ui;

import ec.edu.espol.proyecto.ds.Tree;
import ec.edu.espol.proyecto.game.Board;
import ec.edu.espol.proyecto.game.Player;
import ec.edu.espol.proyecto.game.Tile;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ec.edu.espol.proyecto.util.Util;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static ec.edu.espol.proyecto.game.Game.NULL_MARK;

public final class GameUI extends Application {

    private static final int         AI_PLAY_DELAY = 750;
    /* JavaFX */
    private final        SplitPane   root;
    private final        BorderPane  gameBoardBP;
    private final        TextField   turno;
    private final        Button      exitBtn;
    private final        Button      hintBtn;
    /* juego */
    private final        Board       currentBoard;
    private final        Tree<Board> gameBoardTree;
    private final        Player      p1;
    private final        Player      p2;
    private final        String      gameMode;
    private              boolean     isFinished;
    private              Tile        tile;
    private              boolean     isTie;

    GameUI(final Player p1, final Player p2, final String gameMode) {
        /* juego */
        currentBoard = new Board();
        gameBoardTree = new Tree<>(new Board());
        this.p1 = p1;
        this.p2 = p2;
        this.gameMode = gameMode;
        isFinished = false;
        isTie = false;

        /* FX */
        gameBoardBP = new BorderPane();
        turno = new TextField();

        /* botones */
        hintBtn = new Button();
        hintBtn.setOnAction(value -> showHint());

        exitBtn = new Button();
        final ImageView exitBtnImgView = new ImageView(Util.getFileFromResources("exit-button.png"));
        exitBtnImgView.setFitHeight(30);
        exitBtnImgView.setPreserveRatio(true);
        exitBtn.setGraphic(exitBtnImgView);
        exitBtn.setOnAction(e -> exit());

        /* root*/
        root = new SplitPane();
        setupRoot();

        /* init */
        playGame();
        updateTurnInfo();
    }

    /**
     * Configura el root de la escena.
     */
    private void setupRoot() {
        final ImageView hintImgView = new ImageView(Util.getFileFromResources("hint.png"));

        hintImgView.setFitHeight(40);
        hintImgView.setFitWidth(40);
        hintBtn.setGraphic(hintImgView);
        root.getItems()
            .addAll(gameBoardBP);
        gameBoardBP.getStylesheets()
                   .add(Util.getFileFromResources("style.css"));
        turno.setOpacity(1);
        turno.setAlignment(Pos.CENTER);

        /* borders & configs */
        topBorder();
        centerBorder();
        setRight();
        setLeft();
        setBottom();
    }

    /**
     * Configura el panel superior de la escena (Turno y jugadores).
     */
    private void topBorder() {

        final HBox vbplayer = new HBox();
        vbplayer.getChildren().addAll(new Label("Jugadores: "),
                                      new Label(String.format("%s (%s)", p1.getName(), p1.getMark()) + " vs "
                                                + String.format("%s (%s)", p2.getName(), p2.getMark())), new Label(""));

        vbplayer.setPadding(new Insets(40));
        vbplayer.setAlignment(Pos.CENTER);
        final HBox hbTurno = new HBox(5);
        hbTurno.setPadding(new Insets(10));
        hbTurno.getChildren().addAll(new Label("Turno: "), turno, vbplayer);
        hbTurno.setId("turno");
        turno.setEditable(false);
        turno.setDisable(true);
        hbTurno.setAlignment(Pos.TOP_LEFT);
        gameBoardBP.setTop(hbTurno);
    }

    /**
     * Configura el panel central de la escena (tablero y operaciones).
     *
     * @return el panel central del root
     */
    private Parent centerBorder() {
        final VBox centerBorder = new VBox();
        centerBorder.setPadding(new Insets(0));
        centerBorder.setAlignment(Pos.CENTER);

        final int boardSize = currentBoard.getBoardSize();
        for (int i = 0; i < boardSize; ++i) {
            final HBox rowHbox = new HBox();
            rowHbox.setAlignment(Pos.CENTER);

            for (int j = 0; j < boardSize; ++j) {
                final StackPane tileStackPane = new StackPane();
                final ImageView tileImgView = new ImageView(Util.getFileFromResources("cuadrado.png"));
                tileImgView.setFitWidth(140);
                tileImgView.setFitHeight(140);
                tileStackPane.getChildren()
                             .add(new Label(i + "," + j));

                final String[] tileVals = ((Labeled) tileStackPane.getChildren().get(0)).getText().split(",");
                final int x = Integer.parseInt(tileVals[0]);
                final int y = Integer.parseInt(tileVals[1]);
                tileStackPane.setOnMouseClicked(e -> {
                    (tileStackPane.getChildren().get(1)).setEffect(null);
                    final ImageView logoImgView;
                    if (p1.isTurn()) {
                        logoImgView = new ImageView(Util.getFileFromResources(p1.getMark() + ".png"));
                        tile = new Tile(x, y, p1.getMark());
                    } else {
                        logoImgView = new ImageView(Util.getFileFromResources(p2.getMark() + ".png"));
                        tile = new Tile(x, y, p2.getMark());
                    }

                    /* deshabilitar para re-hacer click */
                    tileStackPane.setDisable(true);

                    /* actualizaar tablero posterior a hacer click */
                    updateBoard();

                    /* obtener mejor posible celda */
                    final Tile bestTile = getBestTile();

                    /* si no hay mejor celda es porque es un empate */
                    if (bestTile == null) {
                        isTie = true;
                    }

                    /* verificar estado del juego */
                    if (currentBoard.checkWin()) {
                        isFinished = true;
                        if (p1.isTurn()) {
                            showWinner(p1);
                        } else {
                            showWinner(p2);
                        }
                    }

                    /* actualizar turnos */
                    updateTurns();

                    /* finalmente establecer la marca correspondiente */
                    logoImgView.setFitWidth(120);
                    logoImgView.setFitHeight(120);
                    getMinMax();
                    tileStackPane.getChildren()
                                 .add(logoImgView);
                });

                tileStackPane.getChildren().add(tileImgView);
                rowHbox.getChildren().add(tileStackPane);
            }

            centerBorder.getChildren().addAll(rowHbox);
        }

        centerBorder.setPadding(new Insets(0));
        gameBoardBP.setCenter(centerBorder);
        // centerBorder.getChildren().addAll(VBN);
        return centerBorder;
    }

    /**
     * Configura el panel inferior (Regresar).
     */
    private void setBottom() {
        final VBox vbBottom = new VBox(10);
        vbBottom.setPadding(new Insets(10));
        vbBottom.setAlignment(Pos.TOP_LEFT);
        vbBottom.getChildren().addAll(exitBtn);
        gameBoardBP.setBottom(vbBottom);
    }

    /**
     * Configura el panel derecho (Pista).
     */
    private void setRight() {
        final VBox vbRight = new VBox(10);
        vbRight.setPadding(new Insets(20));
        vbRight.setAlignment(Pos.CENTER_LEFT);
        vbRight.getChildren().addAll(hintBtn);
        gameBoardBP.setRight(vbRight);
    }

    /**
     * Configura el panel izquierdo.
     */
    private void setLeft() {
        final VBox vbRight = new VBox(30);
        vbRight.setPadding(new Insets(40));
        gameBoardBP.setLeft(vbRight);
    }


    /**
     * Algoritmo minmax.
     */
    private void getMinMax() {
        final Player currentPlayer;
        final char thisMark;
        final char otherMark;

        if (p1.isTurn()) {
            currentPlayer = new Player(p1.getName(), p1.getMark());
            thisMark = p1.getMark();
            otherMark = p2.getMark();
        } else {
            currentPlayer = new Player(p2.getName(), p2.getMark());
            thisMark = p2.getMark();
            otherMark = p1.getMark();
        }

        final Board boardCopy = new Board();
        boardCopy.copyBoard(currentBoard);
        final int boardSize = currentBoard.getBoardSize();
        for (int i = 0; i < boardSize; ++i) {
            for (int j = 0; j < boardSize; ++j) {
                if (currentBoard.getTile(i, j).getMark() == NULL_MARK) {
                    final Board firstGentBoard = new Board();
                    firstGentBoard.copyBoard(boardCopy);
                    firstGentBoard.modBoard(i, j, thisMark);
                    gameBoardTree.add(firstGentBoard, boardCopy);
                    gameBoardTree.findNode(firstGentBoard).getData().setUtility(10);
                    for (int k = 0; k < boardSize; ++k) {
                        for (int l = 0; l < boardSize; ++l) {
                            if (firstGentBoard.getTile(k, l).getMark() == NULL_MARK) {
                                final Board secondGenBoard = new Board(currentPlayer);
                                secondGenBoard.copyBoard(firstGentBoard);
                                secondGenBoard.modBoard(k, l, otherMark);
                                secondGenBoard.setUtility(secondGenBoard.getMinUtility());
                                if (secondGenBoard.getMinUtility() < firstGentBoard.getUtility()) {
                                    gameBoardTree.findNode(firstGentBoard)
                                                 .getData()
                                                 .setUtility(secondGenBoard.getUtility());
                                }

                                /* finalmente agregar al tablero pade e hijo */
                                gameBoardTree.add(secondGenBoard, firstGentBoard);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * General el hilo del jugador.
     *
     * @param p jugador del hilo
     */
    private void playerThread(final Player p) {
        final Thread pThread = new Thread(() -> {
            while (!isFinished) {
                System.out.print("");
                if (isFinished && !isTie) {
                    showWinner(p);
                }
            }
        });

        pThread.setDaemon(true);
        pThread.start();
    }

    /**
     * Genera el hilo del AI.
     *
     * @param p jugar al cual la AI emula
     */
    private void aiThread(final Player p) {
        final Thread aiThread = new Thread(() -> {
            while (!isFinished) {
                System.out.print("");
                if (!isFinished && p.isTurn()) {
                    waitMS(AI_PLAY_DELAY);
                    if (!currentBoard.checkWin() && !isTie) {
                        final Tile bestTile = getBestTile();
                        if (bestTile != null) {
                            final HBox rowHB = ((HBox) ((Pane) gameBoardBP.getCenter()).getChildren()
                                                                                       .get(bestTile.getX()));
                            final StackPane stackPane = (StackPane) rowHB.getChildren().get(bestTile.getY());
                            Platform.runLater(() -> stackPane.fireEvent(new MouseEvent(MouseEvent.MOUSE_CLICKED,
                                                                                       stackPane.getLayoutX(),
                                                                                       stackPane.getLayoutY(),
                                                                                       stackPane.getLayoutX(),
                                                                                       stackPane.getLayoutY(),
                                                                                       MouseButton.PRIMARY,
                                                                                       1,
                                                                                       true,
                                                                                       true,
                                                                                       true,
                                                                                       true,
                                                                                       true,
                                                                                       true,
                                                                                       true,
                                                                                       true,
                                                                                       true,
                                                                                       true,
                                                                                       null)));
                        }
                    }
                    waitMS(AI_PLAY_DELAY);
                }
            }
        });

        aiThread.setDaemon(true);
        aiThread.start();
    }

    /**
     * Retorna la mejor posible celda a marcar.
     *
     * @return mejor posible celda a marcar
     */
    private Tile getBestTile() {
        final List<Board> listBoards = gameBoardTree.getMaxUtility(currentBoard);
        final List<Board> listBestBoards = new LinkedList<>();
        Util.printHeader("Tableros de la ronda actual del juego:");

        /* mostrar cada uno de los posibles tableros*/
        Objects.requireNonNull(listBoards).forEach(Board::showBoard);

        /* Filtrar los mejores tableros */
        listBoards.stream()
                  .filter(e -> (e.getUtility() == listBoards.get(listBoards.size() - 1).getUtility()))
                  .forEachOrdered(listBestBoards::add);

        /* si la lista de mejores tableros NO está vacía, escoger cualquiera */
        if (!listBestBoards.isEmpty()) {
            return currentBoard.getTile(listBestBoards.get(Util.getRandomInt(listBestBoards.size())));
        }

        /* si no hay tanleros significa que no hay un posible ganador => empate*/
        isFinished = true;
        isTie = true;

        /* empate */
        Platform.runLater(() -> {
            Util.alert("Nadie gana, es un empate.", true);
            hintBtn.setDisable(true);
            gameBoardBP.getCenter()
                       .setDisable(true);
        });

        return null;
    }

    /**
     * Wrapper de {@link #wait()}.
     */
    private void waitMS(final int ms) {
        try {
            Thread.sleep(ms);
        } catch (final InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Actualiza el turno de cada jugador en cada ronda.
     */
    private void updateTurns() {
        if (p1.isTurn()) {
            p1.setTurn(false);
            p2.setTurn(true);
        } else {
            p2.setTurn(false);
            p1.setTurn(true);
        }

        updateTurnInfo();
    }

    /**
     * Actualiza la información del turno de cada jugador en cada ronda.
     */
    private void updateTurnInfo() {
        if (p1.isTurn()) {
            turno.setText(p1.getName());
            currentBoard.setPlayer(p1);
        } else {
            turno.setText(p2.getName());
            currentBoard.setPlayer(p2);
        }
    }

    /**
     * Muestra un mensaje con el jugador ganador.
     *
     * @param p jugador ganador
     */
    private void showWinner(final Player p) {
        Platform.runLater(() -> {
            if (!p.isTurn()) {
                Util.alert("Ganador: " + p.getName(), true);
            }
            hintBtn.setDisable(true);
            gameBoardBP.getCenter().setDisable(true);
        });
    }

    /**
     * Muestra la celda de la pista.
     *
     * @return la celda de la pista dada
     */
    private Tile showHint() {
        final Tile hintedTile = getBestTile();
        if (hintedTile == null) {
            return null;
        }

        final HBox rowHbox = ((HBox) ((Pane) gameBoardBP.getCenter()).getChildren().get(hintedTile.getX()));
        final StackPane stackPane = (StackPane) rowHbox.getChildren().get(hintedTile.getY());
        final Shadow hintShadow = new Shadow();
        final int shadowSize = 5;
        hintShadow.setColor(Color.HOTPINK);
        hintShadow.setHeight(shadowSize);
        hintShadow.setWidth(shadowSize);
        hintShadow.setRadius(shadowSize);
        Platform.runLater(() -> (stackPane.getChildren().get(1)).setEffect(hintShadow));

        return hintedTile;
    }

    /**
     * Empezar el juego acorde a las preferencias especificadas.
     */
    private void playGame() {
        getMinMax();

        if ("Jugador vs Computador".equals(gameMode)) {
            playerThread(p1);
            aiThread(p2);
        } else if ("Computador vs Computador".equals(gameMode)) {
            aiThread(p1);
            aiThread(p2);
            hintBtn.setDisable(true);
        } else {
            playerThread(p1);
            playerThread(p2);
        }
    }

    /**
     * Actualiza el tablero.
     */
    private void updateBoard() {
        currentBoard.getBoard()[tile.getX()][tile.getY()].setMark(tile.getMark());
    }

    /**
     * Termina la ejecución del programa.
     */
    private void exit() {
        Platform.exit();
    }


    @Override
    public void start(final Stage stage) {
        final Scene scene = new Scene(root, 800, 650);
        scene.getStylesheets()
             .add(Util.getFileFromResources("style.css"));

        stage.getIcons().add(new Image(Util.getFileFromResources("icon.png")));
        stage.setTitle("3 en Raya (G7)");
        stage.setScene(scene);
        stage.show();
    }
}

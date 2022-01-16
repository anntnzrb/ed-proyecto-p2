package ec.edu.espol.proyecto.ui;

import ec.edu.espol.proyecto.game.Player;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ec.edu.espol.proyecto.util.Util;

import static ec.edu.espol.proyecto.game.Game.O_MARK;
import static ec.edu.espol.proyecto.game.Game.X_MARK;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;

public final class MainUI extends Application {
    /* JavaFX */
    private final BorderPane  root;
    private final ToggleGroup gameModeGroup;
    private final ToggleGroup markGroup;
    private final ToggleGroup turnGroup;
    private final TextField   p1txt;
    private final TextField   p2txt;
    private final Button      playBtn;

    /* juego */
    private String gameMode;
    private char   mark;
    private Player p1;
    private Player p2;

    /* constructores */
    public MainUI() {
        /* grupos */
        gameModeGroup = new ToggleGroup();
        markGroup = new ToggleGroup();
        turnGroup = new ToggleGroup();

        /* botones */
        playBtn = new Button();
        playBtn.setOnAction(e -> playGame());
        final ImageView playBtnImgView = new ImageView(Util.getFileFromResources("play-button.png"));
        playBtnImgView.setFitHeight(50);
        playBtnImgView.setPreserveRatio(true);
        playBtn.setGraphic(playBtnImgView);

        /* Text Fields */
        p1txt = new TextField();
        p2txt = new TextField();

        /* root */
        root = new BorderPane();
        rootSetUp();
    }

    /**
     * Configura el root de esta escena.
     *
     * @return el root de esta escena
     */
    private Parent rootSetUp() {
        topBorder();
        centerBorderA();
        bottomPane();
        root.getStylesheets()
            .add(Util.getFileFromResources("style.css"));

        return root;
    }

    /**
     * Configura la sección superior (logo) de esta escena.
     *
     * @return el panel superior de esta escena
     */
    private Parent topBorder() {
        final HBox topHbox = new HBox(5);
        final ImageView logoImgView = new ImageView(Util.getFileFromResources("logo.png"));
        logoImgView.setFitHeight(150);
        logoImgView.setFitWidth(500);
        topHbox.getChildren()
               .add(logoImgView);
        root.setTop(topHbox);

        return topHbox;
    }

    /**
     * Configura el panel central de esta escena (modo de juego, etc).
     *
     * @return el panel central de esta escena
     */
    private Parent centerBorderA() {
        final VBox centerVbox = new VBox(15);
        centerVbox.setAlignment(Pos.CENTER);

        final HBox hbModoJuego = new HBox(10);
        final Label lblmodoJuego = new Label("Modo de juego: ");
        final VBox gameModeVbox = new VBox(5);
        setupGameMode(gameModeVbox);
        hbModoJuego.getChildren()
                   .addAll(lblmodoJuego, gameModeVbox);

        final HBox markHBox = new HBox(10);
        final VBox chosenVbox = new VBox(5);
        setupMarks(chosenVbox);
        final Label lbl = new Label("Marca Jugador #1: ");
        markHBox.getChildren()
                .addAll(lbl, chosenVbox);
        centerBorderB(centerVbox);
       

        centerVbox.getChildren()
                  .add(hbModoJuego);
         centerVbox.getChildren()
                  .addAll(markHBox);
        centerVbox.getStylesheets()
                  .add(Util.getFileFromResources("style.css"));
        centerBorderC(centerVbox);
        centerVbox.setPadding(new Insets(10));
        root.setCenter(centerVbox);
        return centerVbox;
    }

    /**
     * Configura el panel central de esta escena (nombres jugadores, etc).
     *
     * @return el panel central de esta escena
     */
    private Parent centerBorderB(final VBox vbox) {
        final HBox centerHbox = new HBox(10);
        final Label p1Lbl = new Label("Jugador #1: ");
        p1Lbl.setTextFill(Color.DARKVIOLET);
        centerHbox.getChildren().addAll(p1Lbl, p1txt);
        final HBox p2Hbox = new HBox(10);
        final Label p2Lbl = new Label("Jugador #2: ");
        p2Lbl.setTextFill(Color.DARKVIOLET);
        p2Hbox.getChildren().addAll(p2Lbl, p2txt);
        vbox.getChildren().addAll(centerHbox, p2Hbox);
        centerHbox.setAlignment(Pos.CENTER);
        p2Hbox.setAlignment(Pos.CENTER);

        return centerHbox;
    }

    /**
     * Configura el panel central de esta escena (quien inicia, etc).
     *
     * @return el panel central de esta escena
     */
    private Parent centerBorderC(final VBox vbox) {
        final HBox initPlayerVbox = new HBox(15);
      
        final Label lblInitPlayer = new Label("Jugador que inicia: ");
        final VBox opciones = new VBox(15);
        turnGroup.getToggles()
                 .addAll(new RadioButton("Jugador 1"), new RadioButton("Jugador 2"));
        turnGroup.getToggles()
                 .forEach(gr -> opciones.getChildren().add((Node) gr));

        ((ButtonBase) turnGroup.getToggles().get(0)).fire();
        initPlayerVbox.getChildren()
                      .addAll(lblInitPlayer, opciones);
        vbox.getChildren()
            .add(initPlayerVbox);
        return initPlayerVbox;
    }

    /**
     * Configura la sección de botones en la parte inferior.
     *
     * @return el panel inferior de esta escena
     */
    private Parent bottomPane() {
        final HBox bottomHbox = new HBox(10);
        bottomHbox.setAlignment(Pos.CENTER);
        bottomHbox.getChildren()
                  .addAll(playBtn);
        bottomHbox.setPadding(new Insets(20));
        root.setBottom(bottomHbox);

        return bottomHbox;
    }

    /**
     * Configura el modo de juego acorde a lo propuesto en UI.
     */
    private void setupGameMode(final VBox vbox) {
        gameModeGroup.getToggles()
                     .addAll(new RadioButton("Jugador vs Computador"),
                             new RadioButton("Computador vs Computador"),
                             new RadioButton("Jugador vs Jugador"));
        gameModeGroup.getToggles().forEach(gr -> {
            ((ButtonBase) gr).setOnAction(e -> {
                gameMode = ((Labeled) gr).getText();
                if ("Jugador vs Computador".equals(gameMode)) {
                    p1txt.setText("Mel");
                    p2txt.setText("AI");
                    p1txt.setEditable(true);
                    p2txt.setEditable(false);
                    p1txt.setDisable(false);
                    p2txt.setDisable(true);
                    p1txt.requestFocus();
                } else if ("Computador vs Computador".equals(gameMode)) {
                    p1txt.setText("AI 1");
                    p2txt.setText("AI 2");
                    p1txt.setEditable(false);
                    p2txt.setEditable(false);
                    p1txt.setDisable(true);
                    p2txt.setDisable(true);
                } else {
                    p1txt.setText("Mel");
                    p2txt.setText("Zoe");
                    p2txt.setEditable(true);
                    p1txt.setEditable(true);
                    p1txt.setDisable(false);
                    p2txt.setDisable(false);
                    p1txt.requestFocus();
                }
            });

            vbox.getChildren().add((Node) gr);
        });

        ((ButtonBase) gameModeGroup.getToggles().get(0)).fire();
    }

    /**
     * Configura las opciones de las marcas.
     */
    private void setupMarks(final VBox vbox) {
        markGroup.getToggles()
                 .addAll(new RadioButton(String.valueOf(X_MARK)), new RadioButton(String.valueOf(O_MARK)));
        markGroup.getToggles()
                 .forEach(gr -> {
                     ((ButtonBase) gr).setOnAction(e -> mark = ((Labeled) gr).getText().charAt(0));
                     vbox.getChildren().add((Node) gr);
                 });

        ((ButtonBase) markGroup.getToggles().get(0)).fire();
    }

    /**
     * Inicializa el juego acorde a los parámetros establecidos en UI.
     */
    private void playGame() {
        if (p1txt.getText().isEmpty() || p2txt.getText().isEmpty()) {
            Util.alert("Faltan datos, ingresar casillas corresponientes.",
                       true);
        } else {
            p1 = new Player(p1txt.getText().trim(), mark);
            p2 = mark == X_MARK
                 ? new Player(p2txt.getText().trim(), O_MARK)
                 : new Player(p2txt.getText().trim(), X_MARK);
            turnGroup.getToggles().forEach(gr -> {
                if ("Jugador 1".equalsIgnoreCase(((Labeled) gr).getText()) && gr.isSelected()) {
                    p1.setTurn(true);
                    p2.setTurn(false);
                } else if ("Jugador 2".equalsIgnoreCase(((Labeled) gr).getText())
                           && gr.isSelected()) {
                    p1.setTurn(false);
                    p2.setTurn(true);
                }
            });

            final GameUI gameUI = new GameUI(p1, p2, gameMode);
            gameUI.start(new Stage());
            closeWindow();
        }
    }

    /**
     * Cierra el UI actual para así abrir otro.
     */
    private void closeWindow() {
        final Stage stage = (Stage) playBtn.getScene().getWindow();
        stage.close();
    }

    @Override
    public void start(final Stage stage) {
        final Scene scene = new Scene(root, 480, 550);
        scene.getStylesheets()
             .add(Util.getFileFromResources("style.css"));

        stage.getIcons().add(new Image(Util.getFileFromResources("icon.png")));
        stage.setTitle("3 en Raya (G7)");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
package ec.edu.espol.proyecto;

import ec.edu.espol.proyecto.utils.Util;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

final public class MainApp extends Application {

    private static Scene scene;

    public static void main(final String... argv) {
        launch(argv);
    }

    @Override
    public void init() {
        System.out.println("Inicializando aplicación...");
    }

    @Override
    public void stop() {
        System.out.println("Cerrando aplicación...");
    }

    @Override
    public void start(final Stage primaryStage) throws IOException {
        scene = new Scene(Util.loadFXML("main"));

        /* ********************************************************************
         * Stage
         * ***************************************************************** */
        primaryStage.setResizable(false); // no resize
        primaryStage.setTitle("3 en Raya (G7)");
        primaryStage.getIcons()
                    .add(new Image(Objects.requireNonNull(
                            MainApp.class
                                    .getResourceAsStream("icon/icon.png"))));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
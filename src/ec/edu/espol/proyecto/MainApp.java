package ec.edu.espol.proyecto;

import javafx.application.Application;
import javafx.stage.Stage;
import ec.edu.espol.proyecto.ui.MainUI;

public final class MainApp extends Application {
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
    public void start(final Stage stage) {
        final MainUI mainUI = new MainUI();
        mainUI.start(stage);
    }
}
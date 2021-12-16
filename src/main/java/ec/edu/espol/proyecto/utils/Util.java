package ec.edu.espol.proyecto.utils;

import ec.edu.espol.proyecto.MainApp;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

import java.io.IOException;

/**
 * Enum Util.
 * Provee métodos (estáticos exclusivamente) para el funcionamiento del
 * proyecto.
 */
public enum Util {
    ;

    /**
     * Retorna un objeto @{@link FXMLLoader} para posteriormente cargarlo.
     *
     * @param fxml archivo de tipo FXML de la escena
     * @return @{@link FXMLLoader}
     */
    public static FXMLLoader getFXMLLoader(String fxml) {
        return new FXMLLoader(MainApp.class.getResource(fxml + ".fxml"));
    }

    /**
     * Busca y carga el archivo FXML.
     *
     * @param fxml archivo de tipo FXML de la escena
     * @return nodo de tipo Parent
     * @throws IOException arroja error si no se encuentra el archivo FXML
     */
    public static Parent loadFXML(String fxml) throws IOException {
        return Util.getFXMLLoader(fxml).load();
    }

    /* *************************************************************************
     * JFX
     * *********************************************************************** */

    /**
     * Método para mostrar información por consola.
     *
     * @param msg mensaje a mostrar en pantalla
     */
    public static void log(String msg) {
        System.out.println(msg);
    }

    /**
     * Método que muestra una alerta de error.
     *
     * @param msg   mensaje a mostrar
     * @param isLog @{@code true} si se desea mostrar en consola
     */
    public static void err(String msg, boolean isLog,
                           boolean isExit) {
        new Alert(Alert.AlertType.ERROR, msg).showAndWait();

        if (isLog) {
            Util.log(msg);
        }

        if (isExit) {
            Platform.exit();
        }
    }

    /**
     * Wrapper de {@link #err(String, boolean, boolean)}
     */
    public static void err(String msg, boolean isLog) {
        Util.err(msg, isLog, false);
    }

    /**
     * Método que muestra una alerta de información.
     *
     * @param msg   mensaje a mostrar
     * @param isLog @{@code true} si se desea mostrar en consola
     */
    public static void alert(String msg, boolean isLog) {
        new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
        if (isLog) {
            Util.log(msg);
        }
    }
}

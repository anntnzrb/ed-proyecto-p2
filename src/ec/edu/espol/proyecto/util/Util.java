package ec.edu.espol.proyecto.util;

import javafx.scene.control.Alert;

import java.util.concurrent.ThreadLocalRandom;

public enum Util {
    ;

    private static void printHeader(final String txt, final String delim) {
        System.out.printf("\n%s\n%s\n%s\n", delim, txt, delim);
    }

    public static void printHeader(final String txt) {
        printHeader(txt, "-------------------------------------------------------------------------------");
    }

    /**
     * Retorna un String to la ruta absoluta de un archivo dado.
     *
     * @param file archivo a buscar
     * @return String con la ruta completa del archivo
     */
    public static String getFileFromResources(final String file) {
        return "./resources/" + file;
    }

    /**
     * Retorna un número aleatorio del rango especificado.
     *
     * @param minv valor mínimo
     * @param maxv valor máximo
     * @return entero aleatorio dentro del rango
     */
    public static int getRandomInt(final int minv, final int maxv) {
        return ThreadLocalRandom.current().nextInt(minv, maxv);
    }

    /**
     * Wrapper de {@link #getRandomInt(int, int)}
     */
    public static int getRandomInt(final int maxv) {
        return getRandomInt(0, maxv);
    }

    /**
     * Método para mostrar información por consola.
     *
     * @param msg mensaje a mostrar en pantalla
     */
    public static void log(final String msg) {
        System.out.println(msg);
    }

    /**
     * Método que muestra una alerta de información.
     *
     * @param msg   mensaje a mostrar
     * @param isLog @{@code true} si se desea mostrar en consola
     */
    public static void alert(final String msg, final boolean isLog) {
        new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
        if (isLog) { log(msg); }
    }
}

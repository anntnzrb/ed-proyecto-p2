package ec.edu.espol.proyecto;

import ec.edu.espol.proyecto.juego.Board;

public final class TestApp {
    public static void main(final String... argv) {
        final var tbl = new Board();
        tbl.showBoard();
    }
}

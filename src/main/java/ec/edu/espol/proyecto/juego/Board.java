package ec.edu.espol.proyecto.juego;

import java.util.Arrays;

public final class Board {
    /**
     * Tamaño del tablero (cuadrado)
     */
    private static final int BOARD_SIZE = 3;

    private final Tile[][] board;

    /* constructores */
    public Board() {
        this.board = new Tile[Board.BOARD_SIZE][Board.BOARD_SIZE];
        this.fillBoard();
    }

    private void fillBoard() {
        for (int i = 0; i < BOARD_SIZE; ++i) {
            for (int j = 0; j < BOARD_SIZE; ++j) {
                board[i][j] = new Tile(i, j);
            }
        }
    }

    public Board mostrarTablero() {
        for (int i = 0; i < BOARD_SIZE; ++i) {
            for (int j = 0; j < BOARD_SIZE; ++j) {
                System.out.print(" | " + board[i][j]);
            }
            System.out.println(" |");
        }

        return this;
    }

    @Override
    public String toString() {
        return Arrays.toString(board);
    }
}

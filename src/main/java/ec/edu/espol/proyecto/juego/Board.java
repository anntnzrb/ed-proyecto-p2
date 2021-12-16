package ec.edu.espol.proyecto.juego;

public final class Board {
    /**
     * Tamaño del tablero (cuadrado)
     */
    private static final int BOARD_SIZE = 3;

    private final Tile[][] board;

    /* constructores */
    public Board() {
        board = new Tile[BOARD_SIZE][BOARD_SIZE];
        fillBoard();
    }

    /**
     * Llena el tablero, inicialmente lo llena con el caracter que respresenta
     * casillas vacías (especificado en {@link Tile}.
     *
     * @return copia del tablero ({@link Tile})
     */
    private Tile[][] fillBoard() {
        for (int i = 0; i < BOARD_SIZE; ++i) {
            for (int j = 0; j < BOARD_SIZE; ++j) {
                board[i][j] = new Tile(i, j);
            }
        }

        return board.clone();
    }

    /**
     * Muestra el contenido del tablero.
     *
     * @return copia del tablero ({@link Tile})
     */
    public Tile[][] showBoard() {
        for (int i = 0; i < BOARD_SIZE; ++i) {
            for (int j = 0; j < BOARD_SIZE; ++j) {
                System.out.print(" | " + board[i][j]);
            }
            System.out.println(" |");
        }

        return board.clone();
    }
}

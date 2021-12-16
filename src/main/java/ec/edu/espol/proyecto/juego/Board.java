package ec.edu.espol.proyecto.juego;

public final class Board {
    /**
     * Tamaño del tablero (cuadrado).
     */
    private static final int BOARD_SIZE = 3;

    /**
     * Tablero constituido por casillas ({@link Tile}).
     */
    private Tile[][] board;

    /**
     * Jugadores del juego.
     * <p>
     * Es necesaria que 2 jugadores existan para jugar (así sea computadora).
     */
    private Player p1;
    private Player p2;

    /* constructores */
    private Board() {}

    public Board(final Player p1, final Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        board = new Tile[BOARD_SIZE][BOARD_SIZE];
        fillBoard();
    }

    /**
     * Llena el tablero, inicialmente lo llena con el caracter que respresenta
     * casillas vacías (especificado en {@link Tile}).
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

    public boolean checkWin() {

        return false;
    }

    /* métodos auxiliares */
    public Tile[][] modBoard(final int x, final int y, final char mark) {
        board[x][y].setMark(mark);

        return board.clone();
    }

    public char checkDiagonals() {
        boolean isDiagonalValid = true;

        // check diagonal principal
        for (int i = 0; i < BOARD_SIZE - 1; ++i) {
            if (board[i][i].getMark() != board[i + 1][i + 1].getMark()) {
                isDiagonalValid = false;
                break;
            }
        }

        // check diagonal secundaria (solo si la principal es válida)
        if (isDiagonalValid) {
            for (int i = BOARD_SIZE - 1; i > 0; --i) {
                if (board[i][i].getMark() != board[i - 1][i - 1].getMark()) {
                    isDiagonalValid = false;
                    break;
                }
            }
        }

        return isDiagonalValid ? board[0][0].getMark() : '*';
    }

    public char checkRows() {
        // check primera fila
        final char fr00 = board[0][0].getMark();
        final char fr01 = board[0][1].getMark();
        final char fr02 = board[0][2].getMark();
        final boolean frCheck = fr00 == fr01 && fr01 == fr02;

        // check segunda fila
        final char sr00 = board[1][0].getMark();
        final char sr01 = board[1][1].getMark();
        final char sr02 = board[1][2].getMark();
        final boolean srCheck = sr00 == sr01 && sr01 == sr02;

        // check tercera fila
        final char tr00 = board[2][0].getMark();
        final char tr01 = board[2][1].getMark();
        final char tr02 = board[2][2].getMark();
        final boolean trCheck = tr00 == tr01 && tr01 == sr02;

        final boolean isWin = frCheck && srCheck && trCheck;

        return isWin ? board[0][0].getMark() : '*';
    }
}

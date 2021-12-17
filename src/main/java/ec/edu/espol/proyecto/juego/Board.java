package ec.edu.espol.proyecto.juego;

import java.util.stream.IntStream;

import static ec.edu.espol.proyecto.juego.Game.NULL_CHAR;

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

    /* métodos auxiliares */
    public Tile[][] modBoard(final int x, final int y, final char mark) {
        board[x][y].setMark(mark);

        return board.clone();
    }

    /**
     * Verifica si el juego se ha ganada y retorna la marca de quién ganó.
     *
     * @return marca ganadora (NULL_CHAR si empate)
     */
    public char checkWin() {
        final char winRow = checkRows();
        final char winCol = checkCols();
        final char winDiag = checkDiagonals();

        if (winRow != NULL_CHAR) {
            return winRow;
        }
        if (winCol != NULL_CHAR) {
            return winCol;
        }
        if (winDiag != NULL_CHAR) {
            return winDiag;
        }

        // retorna (0) si es empate
        return 0;
    }

    /**
     * Verifica si todos los elementos de alguna de las 2 diagonales
     * son iguales.
     *
     * @return marca ganadora (NULL_CHAR si nadie gana)
     */
    public char checkDiagonals() {
        // check diagonal principal
        boolean isDiagonalValid = IntStream.range(0, BOARD_SIZE - 1)
                                           .noneMatch(i -> board[i + 1][i + 1].getMark() == NULL_CHAR
                                                           || board[i][i].getMark() != board[i + 1][i + 1].getMark());

        // check diagonal secundaria (solo si la principal es válida)
        if (!isDiagonalValid) {
            for (int i = BOARD_SIZE - 1; i > 0; --i) {
                final var x = board[i][i].getMark();
                final var y = board[i - 1][i - 1].getMark();

                /* si se encuentra el NULL_CHAR, no es válida */
                if (x == NULL_CHAR || y == NULL_CHAR) {
                    isDiagonalValid = false;
                    break;
                }

                // hacer la condición válida si es que son iguales
                if (x == y) {
                    isDiagonalValid = true;
                } else {
                    isDiagonalValid = false;
                    break;
                }
            }
        }

        /*
         * si alguna de las diagonales es válida, entonces la marca ganadora
         * pasará por el centro del tablero.
         */
        return isDiagonalValid ? board[1][1].getMark() : NULL_CHAR;
    }

    /**
     * Verifica si todos los elementos de alguna fila son iguales.
     *
     * @return marca ganadora (NULL_CHAR si nadie gana)
     */
    public char checkRows() {
        // check primera fila
        final boolean frCheck = IntStream.range(0, BOARD_SIZE - 1)
                                         .noneMatch(i -> board[0][i].getMark() == NULL_CHAR
                                                         || board[0][i].getMark() != board[0][i + 1].getMark());

        // check segunda fila
        final boolean srCheck = IntStream.range(0, BOARD_SIZE - 1)
                                         .noneMatch(i -> board[1][i].getMark() == NULL_CHAR
                                                         || board[1][i].getMark() != board[1][i + 1].getMark());

        // check tercera fila
        final boolean trCheck = IntStream.range(0, BOARD_SIZE - 1)
                                         .noneMatch(i -> board[2][i].getMark() == NULL_CHAR
                                                         || board[2][i].getMark() != board[2][i + 1].getMark());

        /* si se gana, retornar la marca ganadora */
        if (frCheck || srCheck || trCheck) {
            if (frCheck) {
                return board[0][0].getMark();
            } else if (srCheck) {
                return board[1][1].getMark();
            } else {
                return board[2][2].getMark();
            }
        }

        return NULL_CHAR;
    }

    /**
     * Verifica si todos los elementos de alguna columna son iguales.
     *
     * @return marca ganadora (NULL_CHAR si nadie gana)
     */
    public char checkCols() {
        // check primera columna
        final boolean fcCheck = IntStream.range(0, BOARD_SIZE - 1)
                                         .noneMatch(i -> board[i][0].getMark() == NULL_CHAR
                                                         || board[i][0].getMark() != board[i + 1][0].getMark());

        // check segunda columna
        final boolean scCheck = IntStream.range(0, BOARD_SIZE - 1)
                                         .noneMatch(i -> board[i][1].getMark() == NULL_CHAR
                                                         || board[i][1].getMark() != board[i + 1][1].getMark());

        // check tercera columna
        final boolean tcCheck = IntStream.range(0, BOARD_SIZE - 1)
                                         .noneMatch(i -> board[i][2].getMark() == NULL_CHAR
                                                         || board[i][2].getMark() != board[i + 1][2].getMark());

        if (fcCheck || scCheck || tcCheck) {
            if (fcCheck) {
                return board[0][0].getMark();
            } else if (scCheck) {
                return board[1][1].getMark();
            } else {
                return board[2][2].getMark();
            }
        }

        return NULL_CHAR;
    }
}

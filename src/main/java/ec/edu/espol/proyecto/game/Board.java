package ec.edu.espol.proyecto.game;

import java.util.Arrays;
import java.util.stream.IntStream;

import static ec.edu.espol.proyecto.game.Game.*;

public final class Board {

    /**
     * Tamaño del tablero (cuadrado).
     */
    private static final int BOARD_SIZE = 3;
    private int utility;
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
    public Board() {
        board = new Tile[BOARD_SIZE][BOARD_SIZE];
        utility = 10;
        fillBoard();
    }

    public Board(final Player p1, final Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        board = new Tile[BOARD_SIZE][BOARD_SIZE];
        utility = 10;
        fillBoard();
    }

    public int getUtility() {
        return utility;
    }

    public void setUtility(int utility) {
        this.utility = utility;
    }

    /**
     * Retorna una celda (@{@link Tile}) a partir de coordenadas dadas.
     *
     * @param x coordenada x de la celda
     * @param y coordenada y de la celda
     * @return la celda solicitada
     */
    public Tile getTile(final int x, final int y) {
        return board[x][y];
    }

    public int getBOARD_SIZE() {
        return BOARD_SIZE;
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

    public void copyBoard(final Board tabla) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new Tile(i, j, tabla.board[i][j].getMark());
            }
        }
    }

    public void modifyBoard(int x, int y, char marca) {
        this.board[x][y] = new Tile(x, y, marca);

    }

    /**
     * Modifica la marca del tablero a partir de las coordenadas x e y pasadas
     * por parámetro.
     *
     * @param x coordenada x del tablero
     * @param y coordenada y del tablero
     * @param mark marca a modificar
     * @return copia del tablero ({@link Tile})
     */
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
        return winDiag;
    }

    /**
     * Calcula el valor de utilidad de una marca en el tablero (con respecto a
     * la otra marca).
     *
     * @param mark marca a analizar
     * @return valor de utilidad de la marca
     */
    public int utilityBoard(final char mark) {
        return mark == X_MARK
                ? utilityMark(X_MARK) - utilityMark(O_MARK)
                : utilityMark(O_MARK) - utilityMark(X_MARK);
    }

    /**
     * Calcula la utilidad de una marca en específico.
     *
     * @param mark marca a analizar
     * @return valor de utilidad de la marca
     */
    private int utilityMark(final char mark) {
        char otherMark = mark;
        if (mark == X_MARK) {
            otherMark = O_MARK;
        } else if (mark == O_MARK) {
            otherMark = X_MARK;
        }

        int fil = 3;
        int col = 3;
        int diag = 2;

        // check filas
        if (isInRow(0, otherMark)) {
            --fil;
        }
        if (isInRow(1, otherMark)) {
            --fil;
        }
        if (isInRow(2, otherMark)) {
            --fil;
        }

        // check columnas
        if (isInCol(0, otherMark)) {
            --col;
        }
        if (isInCol(1, otherMark)) {
            --col;
        }
        if (isInCol(2, otherMark)) {
            --col;
        }

        // check diagonales
        if (isInDiag(0, otherMark)) {
            --diag;
        }
        if (isInDiag(1, otherMark)) {
            --diag;
        }

        return fil + col + diag;
    }

    /* ************************************************************************
     * métodos auxiliares
     * ********************************************************************* */
    /**
     * Busca la marca en la fila pasada por parámetro.
     *
     * @param row fila a analizar
     * @param mark marca a buscar
     * @return {@code true} si la marca se encuentra, {@code false} caso
     * contrario
     */
    private boolean isInRow(final int row, final char mark) {
        return IntStream.range(0, BOARD_SIZE)
                .anyMatch(i -> board[row][i].getMark() == mark);
    }

    /**
     * Busca la marca en la columna pasada por parámetro.
     *
     * @param col columna a analizar
     * @param mark marca a buscar
     * @return {@code true} si la marca se encuentra, {@code false} caso
     * contrario
     */
    private boolean isInCol(final int col, final char mark) {
        return IntStream.range(0, BOARD_SIZE)
                .anyMatch(i -> board[i][col].getMark() == mark);
    }

    /**
     * Busca la marca dada en alguna de las 2 diagonales. Si el valor de diag es
     * 0, busca en la diagonal principal, caso contrario busca en la diagonal
     * secundaria.
     *
     * @param diag diagonal a analizar
     * @param mark marca a buscar
     * @return {@code true} si la marca se encuentra, {@code false} caso
     * contrario
     */
    private boolean isInDiag(final int diag, final char mark) {
        if (diag == 0) {
            return IntStream.range(0, BOARD_SIZE - 1).anyMatch(i -> board[i][i].getMark() == mark);
        } else {
            return IntStream.iterate(BOARD_SIZE - 1, i -> i > 0, i -> i - 1)
                    .anyMatch(i -> board[i][i].getMark() == mark);
        }
    }

    /**
     * Verifica si todos los elementos de alguna de las 2 diagonales son
     * iguales.
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
            for (int i = BOARD_SIZE - 1, j = 0; i > 0; --i, ++j) {
                final var x = board[j][i].getMark();
                final var y = board[j + 1][i - 1].getMark();

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

    /**
     * Verifica si el tablero está lleno.
     * <p>
     * El tablero estará completo cuando éste no tenga mas marcas de tipo '*'.
     *
     * @return {@code true} si el tablero está lleno {@code false} caso
     * contrario
     */
    public boolean isFull() {
        return Arrays.stream(board)
                .flatMap(Arrays::stream)
                .noneMatch(tile -> tile.getMark() == NULL_CHAR);
    }

    /* getters & setters */
    public Tile[][] getBoard() {
        return board.clone();
    }

    public Tile obtenerCasilla(final Board tabla) {
        Tile c = null;
        if (tabla != null) {
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if (tabla.board[i][j].getMark() != board[i][j].getMark()) {
                        c = new Tile(i, j, tabla.board[i][j].getMark());
                    }
                }
            }
        }
        return c;
    }
}

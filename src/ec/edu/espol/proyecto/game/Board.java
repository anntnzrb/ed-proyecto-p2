package ec.edu.espol.proyecto.game;

import java.io.Serializable;
import java.util.stream.IntStream;

import static ec.edu.espol.proyecto.game.Game.*;

public final class Board implements Comparable<Board>, Serializable {
    private static final int      BOARD_SIZE = 3;
    private              Tile[][] board;
    private              Player   player;
    private              int      utility;

    /* constructores */
    public Board() {
        board = new Tile[BOARD_SIZE][BOARD_SIZE];
        utility = 10;
        fillMatrix();
    }

    public Board(final Player player) {
        this.player = player;
        board = new Tile[BOARD_SIZE][BOARD_SIZE];
        utility = 10;
    }

    /**
     * Copia el tablero actual en el tablero pasado por parámetro.
     *
     * @param board tablero el cual será copiado
     */
    public void copyBoard(final Board board) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                this.board[i][j] = new Tile(i, j, board.getBoard()[i][j].getMark());
            }
        }
    }

    /**
     * Retorna la utilidad mínima del tablero.
     *
     * @return la utilidad mínima del tablero
     */
    public int getMinUtility() {
        final int pThis = calculateProb(this, player.getMark());
        final int pOponente = player.getMark() == X_MARK ? calculateProb(this, O_MARK) : calculateProb(this, X_MARK);

        return pThis - pOponente;
    }

    /**
     * Calcula la probabilidad de una marca a partir de un tablero especificado.
     *
     * @param board tablero a analizar
     * @param mark  marca a analizar
     * @return la probalidad de dicha marca en dicho tablero
     */
    private int calculateProb(final Board board, final char mark) {
        int rows = 3;
        int cols = 3;
        int diags = 2;
        int diag1 = 0, diag2 = 0;

        /* filas */
        for (int i = 0; i < BOARD_SIZE; i++) {
            int f = 0;
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board.getBoard()[i][j].getMark() == mark || board.getBoard()[i][j].getMark() == NULL_MARK) {
                    f++;
                }
            }

            if (f != BOARD_SIZE) { rows--; }
            final int rev = BOARD_SIZE - i - 1;
            if (board.getBoard()[i][rev].getMark() == mark || board.getBoard()[i][rev].getMark() == NULL_MARK) {
                diag2++;
            }
            if (board.getBoard()[i][i].getMark() == mark || board.getBoard()[i][i].getMark() == NULL_MARK) {
                diag1++;
            }
        }

        /* diagonales */
        if (diag1 != 3) { diags--; }
        if (diag2 != 3) { diags--; }

        /* cols */
        for (int i = 0; i < BOARD_SIZE; i++) {
            int c = 0;
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board.getBoard()[j][i].getMark() == mark || board.getBoard()[j][i].getMark() == NULL_MARK) {
                    c++;
                }
            }

            if (c != BOARD_SIZE) { cols--; }
        }

        return rows + cols + diags;
    }

    /**
     * Rellena la matriz (inicialmente empleado para iniciarla vacía)
     */
    private void fillMatrix() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new Tile(i, j, NULL_MARK);
            }
        }
    }

    /**
     * Muestra el contenido del tablero.
     *
     * @return copia del tablero ({@link Tile})
     */
    public Tile[][] showBoard() {
        System.out.println("\nUtilidad: " + getUtility());
        for (int i = 0; i < BOARD_SIZE; ++i) {
            for (int j = 0; j < BOARD_SIZE; ++j) {
                System.out.print(" | " + board[i][j].getMark());
            }
            System.out.println(" |");
        }

        return board.clone();
    }

    /**
     * Modifica el tablero a partir de coordenadas de la matriz y una marca.
     *
     * @param x    posición x del tablero
     * @param y    posición y del tablero
     * @param mark marca a analizar
     */
    public void modBoard(final int x, final int y, final char mark) {
        board[x][y] = new Tile(x, y, mark);
    }

    /**
     * Verifica si el juego se ha ganado.
     *
     * @return {@code true} si se ha ganado la partida
     */
    public boolean checkWin() {
        int rows;
        int diag1 = 0;
        int diag2 = 0;
        int cols;
        for (int i = 0; i < BOARD_SIZE; ++i) {
            rows = 0;
            cols = 0;
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j].getMark() == player.getMark()) {
                    rows++;
                }
                if (i == j && board[i][j].getMark() == player.getMark()) {
                    diag1++;
                }
                if (getBoard()[j][i].getMark() == player.getMark()) {
                    cols++;
                }
            }

            if (rows == BOARD_SIZE || cols == BOARD_SIZE || diag1 == BOARD_SIZE) {
                return true;
            }

            final int rev = BOARD_SIZE - i - 1;
            if (getBoard()[i][rev].getMark() == player.getMark()) {
                diag2++;
            }
        }

        return diag2 == BOARD_SIZE;
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

    /**
     * Retorna una celda del tablero
     *
     * @param board tablero a analizar
     * @return una celda del tablero
     */
    public Tile getTile(final Board board) {
        if (board == null) { return null; }

        Tile tile = null;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board.getBoard()[i][j].getMark() != this.board[i][j].getMark()) {
                    tile = new Tile(i, j, board.getBoard()[i][j].getMark());
                }
            }
        }

        return tile;
    }

    @Override
    public String toString() {
        final StringBuilder strBld = new StringBuilder();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                strBld.append(board[i][j].getMark()).append("   ");
            }

            strBld.append("\n");
        }

        return strBld.toString();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) { return true; }
        if (obj == null || getClass() != obj.getClass()) { return false; }

        final Board other = (Board) obj;

        return IntStream.range(0, BOARD_SIZE)
                        .noneMatch(i -> IntStream.range(0, BOARD_SIZE)
                                                 .anyMatch(j -> !board[i][j].equals(other.getBoard()[i][j])));
    }

    @Override
    public int compareTo(final Board board) {
        return utility - board.getUtility();
    }

    /* getters & setters */
    public Tile[][] getBoard() {
        return board;
    }

    public void setBoard(final Tile[][] board) {
        this.board = board;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(final Player player) {
        this.player = player;
    }

    public int getBoardSize() {
        return BOARD_SIZE;
    }

    public int getUtility() {
        return utility;
    }

    public void setUtility(final int utility) {
        this.utility = utility;
    }
}
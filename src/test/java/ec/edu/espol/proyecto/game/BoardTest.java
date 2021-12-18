package ec.edu.espol.proyecto.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ec.edu.espol.proyecto.game.Game.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings({"ClassWithoutConstructor", "AssertWithoutMessage"})
final class BoardTest {
    private Board tblEmpate;
    private Board tblFil1;
    private Board tblFil2;
    private Board tblFil3;
    private Board tblCol1;
    private Board tblCol2;
    private Board tblCol3;
    private Board tblDiagonal0;
    private Board tblDiagonal1;
    private Board tblUtilidad;

    private Player p1;
    private Player p2;

    @BeforeEach
    void setUp() {
        // jugadores
        p1 = new Player("Mel", 'X');
        p2 = new Player("Zo", 'O');


        /* *********************************************************************
         * Tablero empatado (completo)
         * ****************************************************************** */
        tblEmpate = new Board(p1, p2);
        tblEmpate.modBoard(0, 1, X_MARK);
        tblEmpate.modBoard(1, 1, O_MARK);
        tblEmpate.modBoard(0, 2, X_MARK);
        tblEmpate.modBoard(0, 0, O_MARK);
        tblEmpate.modBoard(2, 2, X_MARK);
        tblEmpate.modBoard(1, 2, O_MARK);
        tblEmpate.modBoard(1, 0, X_MARK);
        tblEmpate.modBoard(2, 0, O_MARK);
        tblEmpate.modBoard(2, 1, X_MARK);

        /* *********************************************************************
         * Tablero fila #1
         * ****************************************************************** */
        tblFil1 = new Board(p1, p2);
        tblFil1.modBoard(0, 0, X_MARK);
        tblFil1.modBoard(1, 2, O_MARK);
        tblFil1.modBoard(0, 1, X_MARK);
        tblFil1.modBoard(2, 2, O_MARK);
        tblFil1.modBoard(0, 2, X_MARK);

        /* *********************************************************************
         * Tablero fila #2
         * ****************************************************************** */
        tblFil2 = new Board(p1, p2);
        tblFil2.modBoard(0, 2, X_MARK);
        tblFil2.modBoard(1, 0, O_MARK);
        tblFil2.modBoard(0, 1, X_MARK);
        tblFil2.modBoard(1, 1, O_MARK);
        tblFil2.modBoard(2, 1, X_MARK);
        tblFil2.modBoard(1, 2, O_MARK);
        tblFil2.modBoard(2, 0, X_MARK);
        tblFil2.modBoard(0, 0, O_MARK);
        tblFil2.modBoard(2, 2, O_MARK);

        /* *********************************************************************
         * Tablero fila #3
         * ****************************************************************** */
        tblFil3 = new Board(p1, p2);
        tblFil3.modBoard(2, 0, O_MARK);
        tblFil3.modBoard(0, 0, X_MARK);
        tblFil3.modBoard(2, 1, O_MARK);
        tblFil3.modBoard(1, 1, X_MARK);
        tblFil3.modBoard(2, 2, O_MARK);

        /* *********************************************************************
         * Tablero columna #1
         * ****************************************************************** */
        tblCol1 = new Board(p1, p2);
        tblCol1.modBoard(0, 0, X_MARK);
        tblCol1.modBoard(0, 1, O_MARK);
        tblCol1.modBoard(1, 0, X_MARK);
        tblCol1.modBoard(0, 2, O_MARK);
        tblCol1.modBoard(2, 0, X_MARK);

        /* *********************************************************************
         * Tablero columna #2
         * ****************************************************************** */
        tblCol2 = new Board(p1, p2);
        tblCol2.modBoard(0, 1, O_MARK);
        tblCol2.modBoard(0, 0, X_MARK);
        tblCol2.modBoard(1, 1, O_MARK);
        tblCol2.modBoard(2, 2, X_MARK);
        tblCol2.modBoard(2, 1, O_MARK);

        /* *********************************************************************
         * Tablero columna #3
         * ****************************************************************** */
        tblCol3 = new Board(p1, p2);
        tblCol3.modBoard(0, 2, X_MARK);
        tblCol3.modBoard(2, 0, O_MARK);
        tblCol3.modBoard(1, 2, X_MARK);
        tblCol3.modBoard(0, 0, O_MARK);
        tblCol3.modBoard(2, 2, X_MARK);

        /* *********************************************************************
         * Tablero con diagonal primaria
         * ****************************************************************** */
        tblDiagonal0 = new Board(p1, p2);
        tblDiagonal0.modBoard(0, 0, X_MARK);
        tblDiagonal0.modBoard(0, 2, O_MARK);
        tblDiagonal0.modBoard(1, 1, X_MARK);
        tblDiagonal0.modBoard(2, 1, O_MARK);
        tblDiagonal0.modBoard(2, 2, X_MARK);

        /* *********************************************************************
         * Tablero con diagonal secundaria
         * ****************************************************************** */
        tblDiagonal1 = new Board(p1, p2);
        tblDiagonal1.modBoard(0, 0, O_MARK);
        tblDiagonal1.modBoard(0, 1, X_MARK);
        tblDiagonal1.modBoard(1, 1, O_MARK);
        tblDiagonal1.modBoard(0, 2, X_MARK);
        tblDiagonal1.modBoard(2, 2, O_MARK);

        /* *********************************************************************
         * Tablero utilidad
         * ****************************************************************** */
        tblUtilidad = new Board(p1, p2);
        tblUtilidad.modBoard(0, 0, X_MARK);
        tblUtilidad.modBoard(1, 0, O_MARK);
    }

    @Test
    void checkRows() {
        System.out.println("\nPrimera fila válida, gana 'X'");
        tblFil1.showBoard();
        assertEquals(X_MARK, tblFil1.checkRows());

        System.out.println("\nSegunda fila válida, gana 'O'");
        tblFil2.showBoard();
        assertEquals(O_MARK, tblFil2.checkRows());

        System.out.println("\nTercera fila inválida, gana 'O'");
        tblFil3.showBoard();
        assertEquals(O_MARK, tblFil3.checkRows());

    }

    @Test
    void checkCols() {
        System.out.println("\nPrimera columna válida, gana 'X'");
        tblCol1.showBoard();
        assertEquals(X_MARK, tblCol1.checkCols());

        System.out.println("\nSegunda columna válida, gana 'O'");
        tblCol2.showBoard();
        assertEquals(O_MARK, tblCol2.checkCols());

        System.out.println("\nTercera columna válida, gana 'X'");
        tblCol3.showBoard();
        assertEquals(X_MARK, tblCol3.checkCols());
    }

    @Test
    void checkDiagonals() {
        System.out.println("\nDiagonal principal válida, gana 'X'");
        tblDiagonal0.showBoard();
        assertEquals(X_MARK, tblDiagonal0.checkDiagonals());

        System.out.println("\nDiagonal secundaria válida, gana 'O'");
        tblDiagonal1.showBoard();
        assertEquals(O_MARK, tblDiagonal1.checkDiagonals());

        System.out.println("\nTablero empate retorna NULL_CHAR ya que no es válido");
        tblEmpate.showBoard();
        assertEquals(NULL_CHAR, tblEmpate.checkDiagonals());
    }

    @Test
    void checkWin() {
        assertEquals(0, tblEmpate.checkWin());
        assertEquals(X_MARK, tblDiagonal0.checkWin());
    }

    @Test
    void utilityBoard() {
        System.out.println("\nUtilidad de 'X' = 1 | 'O' = -1");
        tblUtilidad.showBoard();
        assertEquals(1, tblUtilidad.utilityBoard(X_MARK));
        assertEquals(-1, tblUtilidad.utilityBoard(O_MARK));
    }
}

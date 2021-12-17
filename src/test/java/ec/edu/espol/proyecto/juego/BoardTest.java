package ec.edu.espol.proyecto.juego;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        tblEmpate.modBoard(0, 1, 'X');
        tblEmpate.modBoard(1, 1, 'O');
        tblEmpate.modBoard(0, 2, 'X');
        tblEmpate.modBoard(0, 0, 'O');
        tblEmpate.modBoard(2, 2, 'X');
        tblEmpate.modBoard(1, 2, 'O');
        tblEmpate.modBoard(1, 0, 'X');
        tblEmpate.modBoard(2, 0, 'O');
        tblEmpate.modBoard(2, 1, 'X');

        /* *********************************************************************
         * Tablero fila #1
         * ****************************************************************** */
        tblFil1 = new Board(p1, p2);
        tblFil1.modBoard(0, 0, 'X');
        tblFil1.modBoard(1, 2, 'O');
        tblFil1.modBoard(0, 1, 'X');
        tblFil1.modBoard(2, 2, 'O');
        tblFil1.modBoard(0, 2, 'X');

        /* *********************************************************************
         * Tablero fila #2
         * ****************************************************************** */
        tblFil2 = new Board(p1, p2);
        tblFil2.modBoard(0, 2, 'X');
        tblFil2.modBoard(1, 0, 'O');
        tblFil2.modBoard(0, 1, 'X');
        tblFil2.modBoard(1, 1, 'O');
        tblFil2.modBoard(2, 1, 'X');
        tblFil2.modBoard(1, 2, 'O');
        tblFil2.modBoard(2, 0, 'X');
        tblFil2.modBoard(0, 0, 'O');
        tblFil2.modBoard(2, 2, 'O');

        /* *********************************************************************
         * Tablero fila #3
         * ****************************************************************** */
        tblFil3 = new Board(p1, p2);
        tblFil3.modBoard(2, 0, 'O');
        tblFil3.modBoard(0, 0, 'X');
        tblFil3.modBoard(2, 1, 'O');
        tblFil3.modBoard(1, 1, 'X');
        tblFil3.modBoard(2, 2, 'O');

        /* *********************************************************************
         * Tablero columna #1
         * ****************************************************************** */
        tblCol1 = new Board(p1, p2);
        tblCol1.modBoard(0,0,'X');
        tblCol1.modBoard(0,1,'O');
        tblCol1.modBoard(1,0,'X');
        tblCol1.modBoard(0,2,'O');
        tblCol1.modBoard(2,0,'X');

        /* *********************************************************************
         * Tablero columna #2
         * ****************************************************************** */
        tblCol2 = new Board(p1, p2);
        tblCol2.modBoard(0,1,'O');
        tblCol2.modBoard(0,0,'X');
        tblCol2.modBoard(1,1,'O');
        tblCol2.modBoard(2,2,'X');
        tblCol2.modBoard(2,1,'O');

        /* *********************************************************************
         * Tablero columna #3
         * ****************************************************************** */
        tblCol3 = new Board(p1, p2);
        tblCol3.modBoard(0,2,'X');
        tblCol3.modBoard(2,0,'O');
        tblCol3.modBoard(1,2,'X');
        tblCol3.modBoard(0,0,'O');
        tblCol3.modBoard(2,2,'X');

        /* *********************************************************************
         * Tablero con diagonal primaria
         * ****************************************************************** */
        tblDiagonal0 = new Board(p1, p2);
        tblDiagonal0.modBoard(0, 0, 'X');
        tblDiagonal0.modBoard(0, 2, 'O');
        tblDiagonal0.modBoard(1, 1, 'X');
        tblDiagonal0.modBoard(2, 1, 'O');
        tblDiagonal0.modBoard(2, 2, 'X');

        /* *********************************************************************
         * Tablero con diagonal secundaria
         * ****************************************************************** */
        tblDiagonal1 = new Board(p1, p2);
        tblDiagonal1.modBoard(0, 0, 'O');
        tblDiagonal1.modBoard(0, 1, 'X');
        tblDiagonal1.modBoard(1, 1, 'O');
        tblDiagonal1.modBoard(0, 2, 'X');
        tblDiagonal1.modBoard(2, 2, 'O');
    }

    @Test
    void checkRows() {
        System.out.println("\nPrimera fila válida, gana 'X'");
        tblFil1.showBoard();
        assertEquals('X', tblFil1.checkRows());

        System.out.println("\nSegunda fila válida, gana 'O'");
        tblFil2.showBoard();
        assertEquals('O', tblFil2.checkRows());

        System.out.println("\nTercera fila inválida, gana 'O'");
        tblFil3.showBoard();
        assertEquals('O', tblFil3.checkRows());

    }

    @Test
    void checkCols() {
        System.out.println("\nPrimera columna válida, gana 'X'");
        tblCol1.showBoard();
        assertEquals('X', tblCol1.checkCols());

        System.out.println("\nSegunda columna válida, gana 'O'");
        tblCol2.showBoard();
        assertEquals('O', tblCol2.checkCols());

        System.out.println("\nTercera columna válida, gana 'X'");
        tblCol3.showBoard();
        assertEquals('X', tblCol3.checkCols());
    }

    @Test
    void checkDiagonals() {
        System.out.println("\nDiagonal principal válida, gana 'X'");
        tblDiagonal0.showBoard();
        assertEquals('X', tblDiagonal0.checkDiagonals());

        System.out.println("\nDiagonal secundaria válida, gana 'O'");
        tblDiagonal1.showBoard();
        assertEquals('O', tblDiagonal1.checkDiagonals());

        System.out.println("\nTablero empate retorna '*' ya que no es válido");
        tblEmpate.showBoard();
        assertEquals('*', tblEmpate.checkDiagonals());
    }
}

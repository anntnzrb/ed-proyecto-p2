package ec.edu.espol.proyecto.juego;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings({"ClassWithoutConstructor", "AssertWithoutMessage"})
final class BoardTest {
    private Board tblEmpate;
    private Board tblFila1;
    private Board tblFila2;
    private Board tblFila3;
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
        tblEmpate.modBoard(0,1, 'X');
        tblEmpate.modBoard(1,1, 'O');
        tblEmpate.modBoard(0,2, 'X');
        tblEmpate.modBoard(0,0, 'O');
        tblEmpate.modBoard(2,2, 'X');
        tblEmpate.modBoard(1,2, 'O');
        tblEmpate.modBoard(1,0, 'X');
        tblEmpate.modBoard(2,0, 'O');
        tblEmpate.modBoard(2,1, 'X');

        /* *********************************************************************
         * Tablero fila #1
         * ****************************************************************** */
        tblFila1 = new Board(p1, p2);
        tblFila1.modBoard(0,0,'X');
        tblFila1.modBoard(1,0,'O');
        tblFila1.modBoard(0,2,'X');
        tblFila1.modBoard(1,1,'O');
        tblFila1.modBoard(0,1,'X');
        tblFila1.modBoard(2,0,'O');
        tblFila1.modBoard(1,2,'X');
        tblFila1.modBoard(2,2,'O');
        tblFila1.modBoard(2,1,'X');

        /* *********************************************************************
         * Tablero fila #2
         * ****************************************************************** */
        tblFila2 = new Board(p1, p2);
        tblFila2.modBoard(2,0,'O');
        tblFila2.modBoard(0,2,'X');
        tblFila2.modBoard(1,0,'O');
        tblFila2.modBoard(0,1,'X');
        tblFila2.modBoard(1,1,'O');
        tblFila2.modBoard(2,1,'X');
        tblFila2.modBoard(1,2,'O');
        tblFila2.modBoard(0,0,'O');
        tblFila2.modBoard(2,2,'O');

        /* *********************************************************************
         * Tablero fila #3
         * ****************************************************************** */
        tblFila3 = new Board(p1, p2);
        tblFila3.modBoard(2,0,'O');
        tblFila3.modBoard(0,2,'X');
        tblFila3.modBoard(1,1,'O');
        tblFila3.modBoard(2,1,'O');
        tblFila3.modBoard(0,1,'X');
        tblFila3.modBoard(1,2,'O');
        tblFila3.modBoard(1,0,'X');
        tblFila3.modBoard(0,0,'O');
        tblFila3.modBoard(2,2,'O');

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
        tblDiagonal1.modBoard(1, 0, 'X');
        tblDiagonal1.modBoard(2, 0, 'O');
        tblDiagonal1.modBoard(2, 2, 'O');
        tblDiagonal1.modBoard(0, 2, 'O');
    }

    @Test
    void checkRows() {
        System.out.println("\nPrimera fila válida, gana 'X'");
        tblFila1.showBoard();
        assertEquals('X', tblFila1.checkRows());

        System.out.println("\nSegunda fila válida, gana 'O'");
        tblFila2.showBoard();
        assertEquals('O', tblFila2.checkRows());

        System.out.println("\nTercera fila inválida, gana 'O'");
        tblFila3.showBoard();
        assertEquals('O', tblFila3.checkRows());

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

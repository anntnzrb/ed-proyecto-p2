package ec.edu.espol.proyecto.game;

import ec.edu.espol.proyecto.ds.Tree;

import java.util.Comparator;

import static ec.edu.espol.proyecto.game.Game.NULL_CHAR;

public class AI {
    public static Tree<Board> generateBoards(final Board board, final char mark) {
        /* crear un árbol ordenado por la utilidad de cada tablero ??? */
        final Tree<Board> boardTree = new Tree<>(board, Comparator.comparingInt(Board::getUtility));

        final Tile[][] tbl = board.getBoard();
        for (int i = 0, tblLength = tbl.length; i < tblLength; ++i) {
            final Tile[] row = tbl[i];
            for (int j = 0, rowLength = tbl[i].length; j < rowLength; ++j) {
                final Tile t = row[j];
                if (t.getMark() == NULL_CHAR) {
                    final Board subBoard = new Board();
                    subBoard.setBoard(board.cloneBoard());
                    subBoard.modBoard(i, j, mark);
                    boardTree.add(subBoard);
                }

            }
        }

        return boardTree;
    }
}

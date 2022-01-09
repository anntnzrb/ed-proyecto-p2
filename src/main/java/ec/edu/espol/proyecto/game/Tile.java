package ec.edu.espol.proyecto.game;

import static ec.edu.espol.proyecto.game.Game.NULL_CHAR;

/**
 * Casilla del tablero.
 */
public final class Tile {
    private int  x;
    private int  y;
    private char mark;

    /* constructores */
    private Tile() { }

    Tile(final int x, final int y, final char mark) {
        this.x = x;
        this.y = y;
        this.mark = mark;
    }

    /**
     * Constructor empleado únicamente para crear un tablero vacío
     */
    Tile(final int x, final int y) {
        this(x, y, NULL_CHAR);
    }


    /* getters & setters */
    public int getX() {
        return x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(final int y) {
        this.y = y;
    }

    public char getMark() {
        return mark;
    }

    public void setMark(final char mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return Character.toString(mark);
    }
}
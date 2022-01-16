package ec.edu.espol.proyecto.game;

import java.io.Serializable;

public final class Tile implements Serializable {
    private final int  y;
    private       int  x;
    private       char mark;

    /* constructores */
    public Tile(final int x, final int y, final char mark) {
        this.x = x;
        this.y = y;
        this.mark = mark;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) { return true; }
        if (obj == null || getClass() != obj.getClass()) { return false; }

        final Tile other = (Tile) obj;
        return x == other.getX() && y == other.getY() && mark == other.getMark();
    }

    @Override
    public String toString() {
        return String.format("Tile: %s (%d, %d)", mark, x, y);
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

    public char getMark() {
        return mark;
    }

    public void setMark(final char mark) {
        this.mark = mark;
    }
}
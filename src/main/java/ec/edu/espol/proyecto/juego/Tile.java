package ec.edu.espol.proyecto.juego;

/**
 * Casilla del tablero.
 */
public class Tile {
    private int  x;
    private int  y;
    private char data;


    /* constructores */
    private Tile() {}

    public Tile(int x, int y, char data) {
        this.x = x;
        this.y = y;
        this.data = data;
    }

    /**
     * Constructor empleado únicamente para crear un tablero vacío
     */
    protected Tile(int x, int y) {
        this(x, y, '*');
    }

    /* getters & setters */
    public int getX() {
        return this.x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(final int y) {
        this.y = y;
    }

    public char getData() {
        return this.data;
    }

    public void setData(final char data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return Character.toString(data);
    }
}
package ec.edu.espol.proyecto.game;

import java.io.Serializable;

public final class Player implements Serializable {
    private String  name;
    private char    mark;
    private boolean turn;

    /* constructores */
    public Player(final String name, final char mark) {
        this.name = name;
        this.mark = mark;
        turn = false;
    }

    /* getters & setters */
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public char getMark() {
        return mark;
    }

    public void setMark(final char mark) {
        this.mark = mark;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(final boolean turn) {
        this.turn = turn;
    }
}
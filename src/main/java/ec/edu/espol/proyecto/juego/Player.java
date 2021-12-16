package ec.edu.espol.proyecto.juego;

public final class Player {
    private String nickname;
    private char mark;

    /* constructores */
    private Player() {}

    public Player(final String nickname, final char mark) {
        this.nickname = nickname;
        this.mark = mark;
    }

    /* getters & setters */
    public String getNickname() {
        return nickname;
    }

    public char getMark() {
        return mark;
    }
}

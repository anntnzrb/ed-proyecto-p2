package ec.edu.espol.proyecto.game;

import static ec.edu.espol.proyecto.game.Game.NULL_CHAR;

public final class Player {
    private final char   mark;
    private       String nickname;

    /* constructores */
    public Player(final String nickname, final char mark) {
        this.nickname = nickname;
        this.mark = mark;
    }

    public Player() {
        this("none", NULL_CHAR);
    }

    /* getters & setters */
    public String getNickname() {
        return nickname;
    }

    public void setNickname(final String nickname) {
        this.nickname = nickname;
    }

    public char getMark() {
        return mark;
    }
}

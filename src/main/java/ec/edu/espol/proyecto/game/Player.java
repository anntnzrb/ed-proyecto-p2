package ec.edu.espol.proyecto.game;

import static ec.edu.espol.proyecto.game.Game.NULL_CHAR;

public final class Player {
    private       String nickname;
    private final char   mark;

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

    public char getMark() {
        return mark;
    }

    public void setNickname(final String nickname) {
        this.nickname = nickname;
    }
}

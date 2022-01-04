package ec.edu.espol.proyecto.game;

public final class Player {
    private String nickname;
    private char   mark;

    /* constructor */

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

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    
}

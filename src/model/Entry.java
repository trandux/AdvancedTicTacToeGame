package model;

/**
 * Represents entries of the {@link Board}
 *
 * @author Tobias Thirolf
 */
public enum Entry {

    EMPTY('-'),
    X('x'),
    O('o');

    private final char token;

    Entry(char token) {
        this.token = token;
    }

    public char getToken() {
        return token;
    }

}

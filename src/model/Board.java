package model;

import java.util.Arrays;
import java.util.StringJoiner;

/**
 * This class represents the board on which the tokens are placed. The board is indexed from 0 to 8, where 0
 * represents the upper left corner and 8 the lower right one.
 *
 * @author Tobias Thirolf
 */
public class Board {

    private static final int SIZE = 3;
    private static final int DIVISOR = 2;

    private final Entry[] entries = new Entry[SIZE * SIZE];

    /**
     * Creates a new instance of a board. All entries are {@link Entry#EMPTY} by default
     */
    public Board() {
        Arrays.fill(entries, Entry.EMPTY);
    }

    private Board(Board board) {
        System.arraycopy(board.entries, 0, entries, 0, SIZE * SIZE);
    }

    /**
     * Changes the entry on the board on a specific position
     * @param pos the position on the board
     * @param entry the entry that will be set on the position
     */
    public void set(int pos, Entry entry) {
        entries[pos] = entry;
    }

    /**
     * Gets the current entry on a specific position
     * @param pos the position on the board
     * @return the entry on the given position
     */
    public Entry get(int pos) {
        return entries[pos];
    }

    /**
     * Creates a copy of the current board
     * @return a copy of the current board
     */
    public Board copy() {
        return new Board(this);
    }

    /**
     * Returns whether {@link Entry#EMPTY} are currently present on the board
     * @return whether the board currently contains {@link Entry#EMPTY}
     */
    public boolean hasEmpty() {
        return Arrays.asList(entries).contains(Entry.EMPTY);
    }

    /**
     * Returns the entry if it covers a whole line either diagonally, vertically, or horizontally. Never returns
     * {@link Entry#EMPTY}. Note that if multiple {@link Entry} cover whole lines, only the first one found will be
     * returned.
     * @return the entry if it covers a whole line on the board, {@code null} otherwise
     */
    public Entry getSameInLine() {
        Entry middle = getSameFromMiddle();
        if (middle != null) return middle;

        return getSameFromCorners();
    }

    /*
    Checks the upper left and lower right corner as starting points vertically and horizontally simultaniously.
    Returns the first entry value to be found which covers a whole line
     */
    private Entry getSameFromCorners() {
        Entry topLeft = entries[0];
        Entry topVertical = topLeft;
        Entry topHorizontal = topLeft;
        Entry downRight = entries[SIZE * SIZE - 1];
        Entry downVertical = downRight;
        Entry downHorizontal = downRight;
        if (!topLeft.equals(Entry.EMPTY) || !downRight.equals(Entry.EMPTY)) {

            //checks and updates temp values to iteratively assure continuing presence of the same token
            for (int i = 1; i < SIZE; i++) {
                topVertical = getIfSame(topVertical, i * SIZE);

                topHorizontal = getIfSame(topHorizontal, i);

                downVertical = getIfSame(downVertical, SIZE * SIZE - 1 - i * SIZE);

                downHorizontal = getIfSame(downHorizontal, SIZE * SIZE - 1 - i);
            }

            //checks all temp values and returns their non trivial value, representing a token that covers a whole line
            if (topVertical != null && !topVertical.equals(Entry.EMPTY)) return topVertical;
            if (topHorizontal != null && !topHorizontal.equals(Entry.EMPTY)) return topHorizontal;
            if (downVertical != null && !downVertical.equals(Entry.EMPTY)) return downVertical;
            if (downHorizontal != null && !downHorizontal.equals(Entry.EMPTY)) return downHorizontal;
        }
        return null;
    }

    private Entry getIfSame(Entry entry, int pos) {
        return entries[pos].equals(entry) ? entry : null;
    }

    private Entry getSameFromMiddle() {
        Entry middle = entries[SIZE * SIZE / DIVISOR];
        if (!middle.equals(Entry.EMPTY)) {
            for (int i = 0; i < SIZE + 1; i++) {
                if (middle.equals(entries[i]) && middle.equals(entries[SIZE * SIZE - 1 - i])) {
                    return middle;
                }
            }
        }
        return null;
    }

    /**
     * Calculates the index of the cell the given token has to place to win the game in
     * the next move. If more than one cell meets the requirement, the one with the lowest
     * index will be returned. If no cell meets the requirement, {@code -1} is returned.
     *
     * @param token the token
     * @return the index of the cell {@code token} has to play to win the game or {@code -1}
     */
    public int getWinningIndex(Entry token) {
        for (int i = 0; i < this.entries.length; i++) {
            if (!this.entries[i].equals(Entry.EMPTY)) {
                continue;
            }

            this.entries[i] = token;
            boolean hasWon = hasWon(token);
            this.entries[i] = Entry.EMPTY;

            if (hasWon) {
                return i;
            }
        }

        return -1;
    }

    private boolean hasWon(Entry token) {
        return token.equals(getSameInLine());
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(System.lineSeparator());
        for (int row = 0; row < SIZE; row++) {
            StringBuilder builder = new StringBuilder();
            for (int column = 0; column < SIZE; column++) {
                builder.append(entries[row * SIZE + column].getToken());
            }
            joiner.add(builder.toString());
        }
        return joiner.toString();
    }
}

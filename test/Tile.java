package test;


public final class Tile {
    public final char letter;
    public final int score;

    private Tile(char letter, int score) {
        this.letter = letter;
        this.score = score;
    }

    @Override
    public int hashCode() {
        return 31 * Character.hashCode(letter) + Integer.hashCode(score);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Tile other = (Tile) obj;
        return letter == other.letter && score == other.score;
    }

    public char getLetter() {
        return letter;
    }

    public int getValue() {
        return score;
    }
}
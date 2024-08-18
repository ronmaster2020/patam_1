package test;


import java.util.ArrayList;

public class Board {
    private Tile[][] tiles = new Tile[15][15];
    private static Board INSTANCE = null;
    private boolean isEmpty = true;
    private static final int[][] DOUBLE_WORD_SCORES = {
            {1, 1}, {2, 2}, {3, 3}, {4, 4},
            {1, 13}, {2, 12}, {3, 11}, {4, 10},
            {13, 1}, {12, 2}, {11, 3}, {10, 4},
            {13, 13}, {12, 12}, {11, 11}, {10, 10}
    };
    private static final int[][] TRIPLE_WORD_SCORES = {
            {0, 0}, {0, 7}, {0, 14},
            {7, 0}, {7, 14},
            {14, 0}, {14, 7}, {14, 14}
    };
    private static final int[][] DOUBLE_LETTER_SCORES = {
            {0, 3}, {0, 11},
            {2, 6}, {2, 8},
            {3, 0}, {3, 7}, {3, 14},
            {6, 2}, {6, 6}, {6, 8}, {6, 12},
            {7, 3}, {7, 11},
            {8, 2}, {8, 6}, {8, 8}, {8, 12},
            {11, 0}, {11, 7}, {11, 14},
            {12, 6}, {12, 8},
            {14, 3}, {14, 11}
    };
    private static final int[][] TRIPLE_LETTER_SCORES = {
            {1, 5}, {1, 9},
            {5, 1}, {5, 5}, {5, 9}, {5, 13},
            {9, 1}, {9, 5}, {9, 9}, {9, 13},
            {13, 5}, {13, 9}
    };

    public static Board getBoard() {
        if (INSTANCE == null) {
            INSTANCE = new Board();
        }
        return INSTANCE;
    }
    public Tile[][] getTiles() {
        return tiles;
    }
    public void setTile(Tile t, int row, int col) {
        tiles[row][col] = t;
    }
    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }
    public boolean boardLegal(Word w) {
        if (w == null || w.getTiles().length == 0) {
            return false;
        }
        if (w.getRow() < 0 || w.getRow() >= 15 || w.getCol() < 0 || w.getCol() >= 15) {
            return false;
        }
        Word fullWord = new Word(w.getTiles(), w.getRow(), w.getCol(), w.isVertical());
        if (fullWord == null) {
            return false;
        }
        if (w.isVertical()) {
            int row = w.getRow();
            if (row + w.getTiles().length > 15) {
                return false;
            }
            if (this.isEmpty) {
                if (w.getCol() != 7) {
                    return false;
                }
                if (!(w.getRow() <= 7 && w.getRow() + w.getTiles().length >= 7)) {
                    return false;
                }
            }
            int col = w.getCol();
            Tile[] wTiles = w.getTiles();
            for (int i = 0; i < w.getTiles().length; i++) {
                Tile current = tiles[row + i][col];
                fullWord.getTiles()[i] = wTiles[i];
                if (wTiles[i] == null || wTiles[i].getLetter() == '_'){
                    if (current == null) {
                        return false;
                    }
                } else {
                    if (current != null && current != wTiles[i]) {
                        return false;
                    }
                }
            }
        } else {
            int col = w.getCol();
            if (col + w.getTiles().length > 15) {
                return false;
            }
            if (this.isEmpty) {
                if (w.getRow() != 7) {
                    return false;
                }
                if (!(w.getCol() <= 7 && w.getCol() + w.getTiles().length >= 7)) {
                    return false;
                }
            }
            int row = w.getRow();
            Tile[] wTiles = w.getTiles();
            for (int i = 0; i < w.getTiles().length; i++) {
                Tile current = tiles[row][col + i];
                fullWord.getTiles()[i] = wTiles[i];
                if (wTiles[i] == null || wTiles[i].getLetter() == '_') {
                    if (current == null) {
                        return false;
                    }
                } else {
                    if (current != null && current != wTiles[i]) {
                        return false;
                    }
                }
            }
        }
        if (!dictionaryLegal(fullWord)) {
            return false;
        }
        return true;
    }
    public boolean dictionaryLegal(Word w) {
        return true;
    }
    public int tryPlaceWord(Word w) {
        if (!boardLegal(w)) {
            return -1;
        }
        int col = w.getCol();
        int row = w.getRow();
        int score = getScore(w);
        Tile[] tiles = w.getTiles();
        if (w.isVertical()) {
            for (int i = 0; i < w.getTiles().length; i++) {
                if (tiles[i] != null && tiles[i].getLetter() != '_') {
                    this.tiles[row + i][col] = tiles[i];
                    Word word = getWord(row + i, col, false);
                    if (word != null) {
                        score += getScore(word);
                    }
                }
            }
        } else {
            for (int i = 0; i < w.getTiles().length; i++) {
                if (tiles[i] != null && tiles[i].getLetter() != '_') {
                    this.tiles[row][col + i] = tiles[i];
                    Word word = getWord(row, col + i, true);
                    if (word != null) {
                        score += getScore(word);
                    }
                }
            }
        }
        this.isEmpty = false;
        return score;
    }
    public ArrayList<Word> getWords(Word w) {
        if (!boardLegal(w)) {
            return null;
        }
        ArrayList<Word> words = new ArrayList<Word>();
        if (w.isVertical()) {
            int row = w.getRow();
            int col = w.getCol();
            for (int i = 0; i < w.getTiles().length; i++) {
                Word word = getWord(row + i, col, false);
                if (word != null) {
                    words.add(word);
                }
                row++;
            }
        } else {
            int row = w.getRow();
            int col = w.getCol();
            for (int i = 0; i < w.getTiles().length; i++) {
                Word word = getWord(row, col + i, true);
                if (word != null) {
                    words.add(word);
                }
                col++;
            }
        }
        return words;
    }
    private Word getWord(int row, int col, boolean vertical) {
        if (this.tiles[row][col] == null) {
            return null;
        }
        if (!vertical) {
            while (col > 0 && tiles[row][col - 1] != null) {
                col--;
            }
            int len = 0;
            while (col + len < 14 && tiles[row][col + len] != null) {
                len++;
            }
            if (len <= 1) {
                return null;
            }
            Tile[] word = new Tile[len];
            for (int k = 0; k < len; k++) {
                word[k] = tiles[row][col + k];
            }
            return new Word(word, row, col, vertical);
        } else {
            while (row > 0 && tiles[row - 1][col] != null) {
                row--;
            }
            int len = 0;
            while (row + len < 14 && tiles[row + len][col] != null) {
                len++;
            }
            if (len <= 1) {
                return null;
            }
            Tile[] word = new Tile[len];
            for (int k = 0; k < len; k++) {
                word[k] = tiles[row + k][col];
            }
            return new Word(word, row, col, vertical);
        }
    }
    public int getScore(Word w) {
        if (!boardLegal(w)) {
            return -1;
        }
        int score = 0;
        int row = w.getRow();
        int col = w.getCol();
        int wordMultiplier = 1;
        if (this.isEmpty) {
            wordMultiplier = 2;
        }
        if (w.isVertical()) {
            for (int i = 0; i < w.getTiles().length; i++) {
                if (tiles[row + i][col] == null) {
                    score += getLetterWithBonusScore(row + i, col, w.getTiles()[i]);
                } else {
                    score += getLetterWithBonusScore(row + i, col, tiles[row + i][col]);
                }

                if (isDoubleWordScore(row + i, col)) {
                    wordMultiplier *= 2;
                }
                if (isTripleWordScore(row + i, col)) {
                    wordMultiplier *= 3;
                }
            }
        } else {
            for (int i = 0; i < w.getTiles().length; i++) {
                if (tiles[row][col + i] == null) {
                    score += getLetterWithBonusScore(row, col + i, w.getTiles()[i]);
                } else {
                    score += getLetterWithBonusScore(row, col + i, tiles[row][col + i]);
                }

                if (isDoubleWordScore(row + i, col)) {
                    wordMultiplier *= 2;
                }
                if (isTripleWordScore(row + i, col)) {
                    wordMultiplier *= 3;
                }
            }
        }
        return score * wordMultiplier;
    }
    private int getLetterWithBonusScore(int row, int col, Tile t) {
        if (t == null) {
            return 0;
        }
        int score = 0;
        if (isDoubleLetterScore(row, col)) {
            score += t.getValue() * 2;
        } else if (isTripleLetterScore(row, col)) {
            score += t.getValue() * 3;
        } else {
            score += t.getValue();
        }
        return score;
    }
    private boolean isDoubleWordScore(int row, int col) {
        for (int[] position : DOUBLE_WORD_SCORES) {
            if (position[0] == row && position[1] == col) {
                return true;
            }
        }
        return false;
    }
    private boolean isTripleWordScore(int row, int col) {
        for (int[] position : TRIPLE_WORD_SCORES) {
            if (position[0] == row && position[1] == col) {
                return true;
            }
        }
        return false;
    }
    private boolean isDoubleLetterScore(int row, int col) {
        for (int[] position : DOUBLE_LETTER_SCORES) {
            if (position[0] == row && position[1] == col) {
                return true;
            }
        }
        return false;
    }
    private boolean isTripleLetterScore(int row, int col) {
        for (int[] position : TRIPLE_LETTER_SCORES) {
            if (position[0] == row && position[1] == col) {
                return true;
            }
        }
        return false;
    }
}

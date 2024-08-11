package test;


import java.util.ArrayList;

public class Board {
    private Tile[][] tiles = new Tile[15][15];
    private static Board INSTANCE = null;
    private boolean isEmpty = true;

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
                    } else {
                        fullWord.getTiles()[i] = current;
                        continue;
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
                    } else {
                        fullWord.getTiles()[i] = current;
                        continue;
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
//    public int tryPlaceWord(Word w) {
//        if (!boardLegal(w)) {
//            return -1;
//        }
//        Tile[] word = w.getTiles();
//        if (w.isVertical()) {
//            for (int i = 0; i < word.length; i++) {
//                tiles[w.getRow() + i][w.getCol()] = word[i];
//            }
//        } else {
//            for (int i = 0; i < word.length; i++) {
//                tiles[w.getRow()][w.getCol() + i] = word[i];
//            }
//        }
//        return 14;
//    }
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
        if (vertical) {
            int j = row;
            while (j < 14 && tiles[j + 1][col] != null) {
                j++;
            }
            if (j - row < 1) {
                return null;
            }
            Tile[] word = new Tile[j - row + 1];
            for (int k = row; k <= j; k++) {
                word[k - row] = tiles[k][col];
            }
            return new Word(word, row, col, vertical);
        } else {
            int j = col;
            while (j < 14 && tiles[row][j + 1] != null) {
                j++;
            }
            if (j - col < 1) {
                return null;
            }
            Tile[] word = new Tile[j - col + 1];
            for (int k = col; k <= j; k++) {
                word[k - col] = tiles[row][k];
            }
            return new Word(word, row, col, vertical);
        }
    }
}

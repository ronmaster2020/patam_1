package test;


public class Word {
//    private Tile[] tiles;
//    private int score;
//
//    public Word(Tile[] tiles) {
//        this.tiles = tiles;
//        this.score = 0;
//        for (Tile t : tiles) {
//            this.score += t.getScore();
//        }
//    }
//
//    public int getScore() {
//        return this.score;
//    }
//
//    public Tile[] getTiles() {
//        return this.tiles;
//    }
//
//    public String toString() {
//        String s = "";
//        for (Tile t : this.tiles) {
//            s += t.getLetter();
//        }
//        return s;
//    }
//
//    public boolean equals(Word w) {
//        if (this.tiles.length != w.getTiles().length) {
//            return false;
//        }
//        for (int i = 0; i < this.tiles.length; i++) {
//            if (!this.tiles[i].equals(w.getTiles()[i])) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public boolean contains(Tile t) {
//        for (Tile tile : this.tiles) {
//            if (tile.equals(t)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public boolean contains(Word w) {
//        for (Tile tile : w.getTiles()) {
//            if (!this.contains(tile)) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public boolean isSubWord(Word w) {
//        if (this.tiles.length < w.getTiles().length) {
//            return false;
//        }
//        for (int i = 0; i < this.tiles.length - w.getTiles().length + 1; i++) {
//            boolean isSubWord = true;
//            for (int j = 0; j < w.getTiles().length; j++) {
//                if (!this.tiles[i + j].equals(w.getTiles()[j])) {
//                    isSubWord = false;
//                    break;
//                }
//            }
//            if (isSubWord) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public boolean isSuperWord(Word w) {
//        return w.isSubWord(this);
//    }
//
//    public boolean isAnagram(Word w) {
//        if (this.tiles.length != w.getTiles().length) {
//            return false;
//        }
//        int[] quantities = new int[26];
//        for (Tile tile : this.tiles) {
//            quantities[tile.getLetter() - 'A']++;
//        }
//        for (Tile tile : w.getTiles()) {
//            quantities[tile.getLetter() - 'A']--;
//        }
//        for (int i = 0; i < 26; i++) {
//            if (quantities[i] != 0) {
//                return false;
//            }
//        }
//        return true;
//    }
}

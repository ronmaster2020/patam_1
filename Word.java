package test;


public class Word {
    private Tile[] tiles;
    private int row, col;
    private boolean vertical;

    public Word(Tile[] tiles, int row, int col, boolean vertical) {
        this.tiles = tiles;
        this.row = row;
        this.col = col;
        this.vertical = vertical;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public boolean isVertical() {
        return this.vertical;
    }

    public Tile[] getTiles() {
        return this.tiles;
    }

    public void setTiles(Tile[] tiles) {
        this.tiles = tiles;
    }

    @Override
    public String toString() {
        String s = "";
        for (Tile t : this.tiles) {
            s += t.getLetter();
        }
        return s;
    }

    @Override
    public boolean equals(Object w) {
        if (this == w) {
            return true;
        }
        if (w == null || w.getClass() != getClass()) {
            return false;
        }
        Word other = (Word) w;
        if (this.row != other.row || this.col != other.col || this.vertical != other.vertical) {
            return false;
        }
        for (int i = 0; i < this.tiles.length; i++) {
            if (!this.tiles[i].equals(other.tiles[i])) {
                return false;
            }
        }
        return true;
    }
}

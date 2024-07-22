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

    public static class Bag {
        private final static Bag INSTANCE = new Bag();

        private final static Tile[] TILES = new Tile[] {
            new Tile('A', 1),
            new Tile('B', 3),
            new Tile('C', 3),
            new Tile('D', 2),
            new Tile('E', 1),
            new Tile('F', 4),
            new Tile('G', 2),
            new Tile('H', 4),
            new Tile('I', 1),
            new Tile('J', 8),
            new Tile('K', 5),
            new Tile('L', 1),
            new Tile('M', 3),
            new Tile('N', 1),
            new Tile('O', 1),
            new Tile('P', 3),
            new Tile('Q', 10),
            new Tile('R', 1),
            new Tile('S', 1),
            new Tile('T', 1),
            new Tile('U', 1),
            new Tile('V', 4),
            new Tile('W', 4),
            new Tile('X', 8),
            new Tile('Y', 4),
            new Tile('Z', 10)};

        private final static int[] quantities = new int[] {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
        private final int[] dynamicQuantities = new int[] {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};

        public Tile getRand() {
            int[] check = new int[26];
            for (int i = 0; i < 26; i++) {
                check[i] = 0;
            }
            int count = 0;
            int index = (int) (Math.random() * TILES.length);
            while (quantities[index] == 0) {
                if(check[index] == 0) {
                    if(++count == 26) {
                        return null;
                    }
                    check[index] = 1;
                }
                index = (int) (Math.random() * TILES.length);
            }
            quantities[index]--;
            return TILES[index];
        }

        public Tile getTile(char c) {
            int i = c - 'A';
            if (i < 0 || i >= 26) {
                return null;
            }
            if (dynamicQuantities[i] == 0) {
                return null;
            }
            dynamicQuantities[i]--;
            return TILES[i];
        }

        public void put(Tile t) {
            if (t == null || t.letter < 'A' || t.letter > 'Z') {
                return;
            }
            int i = t.letter - 'A';
            if (dynamicQuantities[i] + 1 > quantities[i]) {
                throw new IllegalStateException("No more tiles of this type left");
            }
            dynamicQuantities[i]++;
        }

        public int size() {
            int count = 0;
            for (int i = 0; i < 26; i++) {
                count += dynamicQuantities[i];
            }
            return count;
        }

        public int[] getQuantities() {
            return dynamicQuantities.clone();
        }

        public static Bag getBag() {
            return INSTANCE;
        }
    }
}
// File: MapManager.java
package monopoly;

public class MapManager {
    // 1) Full space names (40 entries)
    private String[] names = new String[] {
        "Go","Mediterranean Avenue","Community Chest","Baltic Avenue","Income Tax",
        "Reading Railroad","Oriental Avenue","Chance","Vermont Avenue","Connecticut Avenue",
        "Just Visiting","St. Charles Place","Electric Company","States Avenue","Virginia Avenue",
        "Pennsylvania Railroad","St. James Place","Community Chest","Tennessee Avenue","New York Avenue",
        "Free Parking","Kentucky Avenue","Chance","Indiana Avenue","Illinois Avenue",
        "B. & O. Railroad","Atlantic Avenue","Ventnor Avenue","Water Works","Marvin Gardens",
        "Go To Jail","Pacific Avenue","North Carolina Avenue","Community Chest","Pennsylvania Railroad",
        "Short Line Railroad","Chance","Park Place","Luxury Tax","Boardwalk"
    };

    // 2) Parallel array of Property objects (null where no property)
    private Property[] spaces = new Property[40];

    // 3) Tiles for calling landOn(...)
    private Tile[] tiles = new Tile[40];

    public MapManager() {
        // Populate ownable spaces exactly at these indices:

        // Brown
        spaces[1]  = new Property(1,  "Mediterranean Avenue","property",  60,  2);
        spaces[3]  = new Property(3,  "Baltic Avenue",        "property",  60,  4);
        // Light Blue
        spaces[6]  = new Property(6,  "Oriental Avenue",      "property", 100,  6);
        spaces[8]  = new Property(8,  "Vermont Avenue",       "property", 100,  6);
        spaces[9]  = new Property(9,  "Connecticut Avenue",   "property", 120,  8);
        // Pink
        spaces[11] = new Property(11, "St. Charles Place",    "property", 140, 10);
        spaces[13] = new Property(13, "States Avenue",        "property", 140, 10);
        spaces[14] = new Property(14, "Virginia Avenue",      "property", 160, 12);
        // Orange
        spaces[16] = new Property(16, "St. James Place",      "property", 180, 14);
        spaces[18] = new Property(18, "Tennessee Avenue",     "property", 180, 14);
        spaces[19] = new Property(19, "New York Avenue",      "property", 200, 16);
        // Red
        spaces[21] = new Property(21, "Kentucky Avenue",      "property", 220, 18);
        spaces[23] = new Property(23, "Indiana Avenue",       "property", 220, 18);
        spaces[24] = new Property(24, "Illinois Avenue",      "property", 240, 20);
        // Yellow
        spaces[26] = new Property(26, "Atlantic Avenue",      "property", 260, 22);
        spaces[27] = new Property(27, "Ventnor Avenue",       "property", 260, 22);
        spaces[29] = new Property(29, "Marvin Gardens",       "property", 280, 24);
        // Green
        spaces[31] = new Property(31, "Pacific Avenue",       "property", 300, 26);
        spaces[32] = new Property(32, "North Carolina Avenue","property", 300, 26);
        spaces[34] = new Property(34, "Pennsylvania Avenue",  "property", 320, 28);
        // Dark Blue
        spaces[37] = new Property(37, "Park Place",           "property", 350, 35);
        spaces[39] = new Property(39, "Boardwalk",            "property", 400, 50);
        // Railroads
        spaces[5]  = new Property(5,  "Reading Railroad",     "railroad", 200, 25);
        spaces[15] = new Property(15, "Pennsylvania Railroad","railroad", 200, 25);
        spaces[25] = new Property(25, "B. & O. Railroad",     "railroad", 200, 25);
        spaces[35] = new Property(35, "Short Line Railroad",  "railroad", 200, 25);
        // Utilities
        spaces[12] = new Property(12, "Electric Company",     "utility", 150,  0);
        spaces[28] = new Property(28, "Water Works",          "utility", 150,  0);

        // Build Tile objects for each space name
        for (int i = 0; i < 40; i++) {
            tiles[i] = new Tile(names[i]);
        }
    }

    /** Return the full space name for labeling or card logic */
    public String getName(int idx) {
        return (idx >= 0 && idx < names.length) ? names[idx] : "";
    }

    /** Return the Property at idx, or null if no buyable property there */
    public Property getProperty(int idx) {
        return (idx >= 0 && idx < spaces.length) ? spaces[idx] : null;
    }

    /** Return the Tile object to call landOn(...) */
    public Tile getTile(int idx) {
        return (idx >= 0 && idx < tiles.length) ? tiles[idx] : null;
    }

    /**
     * Render a 10×4 ring with fixed slots and player initials.
     * LABELS array used for concise display.
     */
    public void render(Participant[] players) {
        String[] LABELS = {
          "GO","ME","CC","BA","IT","RR","OR","CH","VE","CO",
          "JV","SC","EC","ST","VI","RR","SJ","CC","TE","NY",
          "FP","KY","CH","IN","IL","RR","AT","WW","MG","GJ",
          "GJ","PA","NC","CC","PR","SL","CH","PP","LT","BW"
        };

        // Top row: 0→9
        for (int i = 0; i < 10; i++) {
            printSlot(i, LABELS, players);
        }
        System.out.println();

        // Middle rows: left 39→31, blanks, right 10→18
        for (int r = 1; r <= 8; r++) {
            printSlot(40 - r, LABELS, players);
            for (int j = 0; j < 8; j++) System.out.print("      ");
            printSlot(r + 9, LABELS, players);
            System.out.println();
        }

        // Bottom row: 30→39
        for (int i = 30; i < 40; i++) {
            printSlot(i, LABELS, players);
        }
        System.out.println("\n");
    }

    // Helper: prints "[XXI] " where XX=LABELS[idx], I=player initial or space
    private void printSlot(int idx, String[] lbl, Participant[] players) {
        char mark = ' ';
        for (Participant p : players) {
            if (!p.bankrupt && p.position == idx) {
                mark = p.getName().charAt(0);
                break;
            }
        }
        System.out.print("[" + lbl[idx] + mark + "] ");
    }
}

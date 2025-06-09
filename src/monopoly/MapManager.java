// File: MapManager.java
package monopoly;

public class MapManager {
    // Full names for each board space (0–39)
    private static final String[] NAMES = {
        "Go","Mediterranean Avenue","Community Chest","Baltic Avenue","Income Tax",
        "Reading Railroad","Oriental Avenue","Chance","Vermont Avenue","Connecticut Avenue",
        "Just Visiting","St. Charles Place","Electric Company","States Avenue","Virginia Avenue",
        "Pennsylvania Railroad","St. James Place","Community Chest","Tennessee Avenue","New York Avenue",
        "Free Parking","Kentucky Avenue","Chance","Indiana Avenue","Illinois Avenue",
        "B. & O. Railroad","Atlantic Avenue","Ventnor Avenue","Water Works","Marvin Gardens",
        "Go To Jail","Pacific Avenue","North Carolina Avenue","Community Chest","Pennsylvania Railroad",
        "Short Line Railroad","Chance","Park Place","Luxury Tax","Boardwalk"
    };

    private Property[] spaces = new Property[40];
    private Tile[]      tiles  = new Tile[40];

    public MapManager() {
        // 1) Initialize all ownable Properties
        // Brown
        spaces[1]  = new Property(1,  "Mediterranean Avenue", "COLOR",  60,   2);
        spaces[3]  = new Property(3,  "Baltic Avenue",         "COLOR",  60,   4);
        // Light Blue
        spaces[6]  = new Property(6,  "Oriental Avenue",       "COLOR", 100,   6);
        spaces[8]  = new Property(8,  "Vermont Avenue",        "COLOR", 100,   6);
        spaces[9]  = new Property(9,  "Connecticut Avenue",    "COLOR", 120,   8);
        // Pink
        spaces[11] = new Property(11, "St. Charles Place",     "COLOR", 140,  10);
        spaces[13] = new Property(13, "States Avenue",         "COLOR", 140,  10);
        spaces[14] = new Property(14, "Virginia Avenue",       "COLOR", 160,  12);
        // Orange
        spaces[16] = new Property(16, "St. James Place",       "COLOR", 180,  14);
        spaces[18] = new Property(18, "Tennessee Avenue",      "COLOR", 180,  14);
        spaces[19] = new Property(19, "New York Avenue",       "COLOR", 200,  16);
        // Red
        spaces[21] = new Property(21, "Kentucky Avenue",       "COLOR", 220,  18);
        spaces[23] = new Property(23, "Indiana Avenue",        "COLOR", 220,  18);
        spaces[24] = new Property(24, "Illinois Avenue",       "COLOR", 240,  20);
        // Yellow
        spaces[26] = new Property(26, "Atlantic Avenue",       "COLOR", 260,  22);
        spaces[27] = new Property(27, "Ventnor Avenue",        "COLOR", 260,  22);
        spaces[29] = new Property(29, "Marvin Gardens",        "COLOR", 280,  24);
        // Green
        spaces[31] = new Property(31, "Pacific Avenue",        "COLOR", 300,  26);
        spaces[32] = new Property(32, "North Carolina Avenue", "COLOR", 300,  26);
        spaces[34] = new Property(34, "Pennsylvania Avenue",   "COLOR", 320,  28);
        // Dark Blue
        spaces[37] = new Property(37, "Park Place",            "COLOR", 350,  35);
        spaces[39] = new Property(39, "Boardwalk",             "COLOR", 400,  50);
        // Railroads
        spaces[5]  = new Property(5,  "Reading Railroad",      "RAILROAD", 200, 25);
        spaces[15] = new Property(15, "Pennsylvania Railroad", "RAILROAD", 200, 25);
        spaces[25] = new Property(25, "B. & O. Railroad",      "RAILROAD", 200, 25);
        spaces[35] = new Property(35, "Short Line Railroad",   "RAILROAD", 200, 25);
        // Utilities
        spaces[12] = new Property(12, "Electric Company",      "UTILITY", 150,  0);
        spaces[28] = new Property(28, "Water Works",           "UTILITY", 150,  0);

        // 2) Initialize Tiles for every space
        for (int i = 0; i < 40; i++) {
            tiles[i] = new Tile(NAMES[i]);
        }
    }

    /** Returns the full name of the space at idx. */
    public String getName(int idx) {
        if (idx < 0 || idx >= NAMES.length) return "";
        return NAMES[idx];
    }

    /** Returns the Property at idx, or null if none ownable. */
    public Property getProperty(int idx) {
        if (idx < 0 || idx >= spaces.length) return null;
        return spaces[idx];
    }

    /** Returns the Tile object at idx (for Tile.landOn calls). */
    public Tile getTile(int idx) {
        if (idx < 0 || idx >= tiles.length) return null;
        return tiles[idx];
    }

    /** Renders a simple text board with player initials. */
    public void render(Participant[] players) {
        String[] lbl = {
          "GO","ME","CC","BA","IT","RR","OR","CH","VE","CO",
          "JV","SC","EC","ST","VI","RR","SJ","CC","TE","NY",
          "FP","KY","CH","IN","IL","RR","AT","WW","MG","GJ",
          "GJ","PA","PC","NC","CC","PA","SL","CH","PP","LT","BW"
        };

        // Top row 0→9
        for (int i = 0; i < 10; i++) printSlot(i, lbl, players);
        System.out.println();

        // Middle rows (1→8 on right, 39→31 on left)
        for (int r = 1; r <= 8; r++) {
            printSlot(40 - r, lbl, players);
            for (int j = 0; j < 8; j++) System.out.print("    ");
            printSlot(r + 9, lbl, players);
            System.out.println();
        }

        // Bottom row 30→39
        for (int i = 30; i < 40; i++) printSlot(i, lbl, players);
        System.out.println("\n");
    }

    /** Helper for render: prints [XXI] or [XXInitial]. */
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

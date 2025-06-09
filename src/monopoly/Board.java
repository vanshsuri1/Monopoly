// File: Board.java
package monopoly;

public class Board {
    // Official monopoly space names for rendering
    public static final String[] NAMES = {
        "Go","Mediterranean","Community Chest","Baltic","Income Tax",
        "Reading Railroad","Oriental","Chance","Vermont","Connecticut",
        "Just Visiting","St. Charles Place","Electric Company","States Avenue","Virginia Avenue",
        "Pennsylvania Railroad","St. James Place","Community Chest","Tennessee","New York Avenue",
        "Free Parking","Kentucky","Chance","Indiana","Illinois",
        "B. & O. Railroad","Atlantic","Ventnor","Water Works","MarvinGardens",
        "Go To Jail","Pacific","North Carolina","Community Chest","Pennsylvania",
        "Short Line","Chance","Park Place","Luxury Tax","Boardwalk"
    };

    // Parallel array of Property objects (null where no property)
    public static final Property[] PROPS = new Property[40];
    static {
        // Brown
        PROPS[1]  = new Property("Mediterranean",  60,  50, new int[]{2,10,30,90,160,250},  30, "property");
        PROPS[3]  = new Property("Baltic",         60,  50, new int[]{4,20,60,180,320,450},  30, "property");
        // Light Blue
        PROPS[6]  = new Property("Oriental",      100,  50, new int[]{6,30,90,270,400,550},  50, "property");
        PROPS[8]  = new Property("Vermont",       100,  50, new int[]{6,30,90,270,400,550},  50, "property");
        PROPS[9]  = new Property("Connecticut",   120,  50, new int[]{8,40,100,300,450,600},  60, "property");
        // Pink
        PROPS[11] = new Property("St. Charles",   140, 100, new int[]{10,50,150,450,625,750}, 70, "property");
        PROPS[13] = new Property("States",        140, 100, new int[]{10,50,150,450,625,750}, 70, "property");
        PROPS[14] = new Property("Virginia",      160, 100, new int[]{12,60,180,500,700,900}, 80, "property");
        // Orange
        PROPS[16] = new Property("St. James",     180, 100, new int[]{14,70,200,550,750,950}, 90, "property");
        PROPS[18] = new Property("Tennessee",     180, 100, new int[]{14,70,200,550,750,950}, 90, "property");
        PROPS[19] = new Property("New York",      200, 100, new int[]{16,80,220,600,800,1000},100,"property");
        // Red
        PROPS[21] = new Property("Kentucky",      220, 150, new int[]{18,90,250,700,875,1050},110,"property");
        PROPS[23] = new Property("Indiana",       220, 150, new int[]{18,90,250,700,875,1050},110,"property");
        PROPS[24] = new Property("Illinois",      240, 150, new int[]{20,100,300,750,925,1100},120,"property");
        // Yellow
        PROPS[26] = new Property("Atlantic",      260, 150, new int[]{22,110,330,800,975,1150},130,"property");
        PROPS[27] = new Property("Ventnor",       260, 150, new int[]{22,110,330,800,975,1150},130,"property");
        PROPS[29] = new Property("MarvinGardens", 280, 150, new int[]{24,120,360,850,1025,1200},140,"property");
        // Green
        PROPS[31] = new Property("Pacific",       300, 200, new int[]{26,130,390,900,1100,1275},150,"property");
        PROPS[32] = new Property("NorthCarolina", 300, 200, new int[]{26,130,390,900,1100,1275},150,"property");
        PROPS[34] = new Property("Pennsylvania",  320, 200, new int[]{28,150,450,1000,1200,1400},160,"property");
        // Dark Blue
        PROPS[37] = new Property("ParkPlace",     350, 200, new int[]{35,175,500,1100,1300,1500},175,"property");
        PROPS[39] = new Property("Boardwalk",     400, 200, new int[]{50,200,600,1400,1700,2000},200,"property");
        // Railroads
        PROPS[5]  = new Property("ReadingRR",     200,   0, new int[]{25,50,100,200}, 100,"railroad");
        PROPS[15] = new Property("PennsylvaniaRR",200,   0, new int[]{25,50,100,200}, 100,"railroad");
        PROPS[25] = new Property("BORailroad",    200,   0, new int[]{25,50,100,200}, 100,"railroad");
        PROPS[35] = new Property("ShortLineRR",   200,   0, new int[]{25,50,100,200}, 100,"railroad");
        // Utilities
        PROPS[12] = new Property("ElectricCompany",150, 0, new int[]{4,10}, 75,"utility");
        PROPS[28] = new Property("WaterWorks",     150, 0, new int[]{4,10}, 75,"utility");
    }

    private Tile[] tiles;

    public Board() {
        tiles = new Tile[40];
        for (int i = 0; i < 40; i++) {
            tiles[i] = new Tile(NAMES[i]);
        }
    }

    public void render(Participant[] players) {
        // your existing display logic here…
    }

    public Tile getTile(int position) {
        return tiles[position];
    }

    /** returns the Property or null if no ownable object on that space */
    public Property getProperty(int position) {
        return PROPS[position];
    }
}

// File: Property.java
package monopoly;

/**
 * Simple Monopoly property record that works with both constructor styles:
 *   1) Property(int location, String name, String type, int price, int rent)
 *   2) Property(String name, int price, int houseCost, int[] rents, int mortgage, String type)
 */
public class Property {

    /* -------- Core data -------- */
    private int     location   = -1;       // board index (optional)
    private String  name;                  // e.g. "Baltic Avenue"
    private String  type;                  // "property", "railroad", "utility"
    private int     price;                 // purchase price
    private int     baseRent;              // base rent (no houses)
    private int     houseCost;             // cost per house (for colored sets)
    private int[]   rents;                 // rents[0]=base, rents[1..5]=house/hotel
    private int     mortgageValue;         // mortgage value
    private boolean mortgaged   = false;   // current mortgage status

    /* -------- 5-arg constructor (location version) -------- */
    public Property(int location, String name, String type, int price, int rent) {
        this.location  = location;
        this.name      = name;
        this.type      = type;
        this.price     = price;
        this.baseRent  = rent;
        this.houseCost = 0;
        this.rents     = new int[]{rent};
        this.mortgageValue = price / 2;
    }

    /* -------- 6-arg constructor used by Board -------- */
    public Property(String name, int price, int houseCost,
                    int[] rents, int mortgage, String type) {
        this.name          = name;
        this.price         = price;
        this.houseCost     = houseCost;
        this.rents         = rents.clone();
        this.baseRent      = rents.length > 0 ? rents[0] : 0;
        this.mortgageValue = mortgage;
        this.type          = type;
    }

    /* -------- Getters / setters -------- */
    public int     getLocation()      { return location; }
    public String  getName()          { return name; }
    public String  getType()          { return type; }
    public int     getPrice()         { return price; }
    public int     getBaseRent()      { return baseRent; }
    public int     getRent()          { return baseRent; }    // used by BuyingManager
    public int     getHouseCost()     { return houseCost; }
    public int     getMortgageValue() { return mortgageValue; }
    public boolean isMortgaged()      { return mortgaged; }
    public void    setMortgaged(boolean m) { mortgaged = m; }

    /** Debug print */
    public void print() {
        System.out.printf("[%s] $%d rent $%d (%s)%s%n",
                name, price, baseRent, type, mortgaged ? " [M]" : "");
    }
}
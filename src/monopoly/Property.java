// File: Property.java
package monopoly;

/**
 * Monopoly property class with two constructor styles.
 * No use of clone() or the ?: operator.
 */
public class Property {

    /* ---- Core fields ---- */
    private int     location = -1;      // board index, -1 if not supplied
    private String  name;
    private String  type;               // "property", "railroad", "utility"
    private int     price;
    private int     baseRent;
    private int     houseCost;
    private int[]   rents;              // rent table (length 1-6)
    private int     mortgageValue;
    private boolean mortgaged = false;

    /* ---- 5-arg constructor (includes location) ---- */
    public Property(int location,
                    String name,
                    String type,
                    int price,
                    int rent) {

        this.location = location;
        this.name     = name;
        this.type     = type;
        this.price    = price;
        this.baseRent = rent;
        this.houseCost = 0;

        rents = new int[1];
        rents[0] = rent;

        mortgageValue = price / 2;
    }

    /* ---- 6-arg constructor (used by Board) ---- */
    public Property(String name,
                    int price,
                    int houseCost,
                    int[] rents,
                    int mortgage,
                    String type) {

        this.name      = name;
        this.price     = price;
        this.houseCost = houseCost;
        this.type      = type;
        this.mortgageValue = mortgage;

        /* Manual copy of rents[] */
        this.rents = new int[rents.length];
        for (int i = 0; i < rents.length; i++) {
            this.rents[i] = rents[i];
        }

        /* Set baseRent safely */
        if (rents.length > 0) {
            this.baseRent = rents[0];
        } else {
            this.baseRent = 0;
        }
    }

    /* ---- Getters / setters ---- */
    public int     getLocation()      { return location; }
    public String  getName()          { return name; }
    public String  getType()          { return type; }
    public int     getPrice()         { return price; }
    public int     getBaseRent()      { return baseRent; }
    public int     getRent()          { return baseRent; }
    public int     getHouseCost()     { return houseCost; }
    public int     getMortgageValue() { return mortgageValue; }
    public boolean isMortgaged()      { return mortgaged; }
    public void    setMortgaged(boolean m) { mortgaged = m; }

    /* ---- Debug print ---- */
    public void print() {
        System.out.print("[" + name + "] $" + price +
                         " rent $" + baseRent +
                         " (" + type + ")");
        if (mortgaged) {
            System.out.print(" [M]");
        }
        System.out.println();
    }
}
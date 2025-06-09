// File: Property.java
package monopoly;

public class Property {
    private int    location;   // board index 0–39
    private String name;       // e.g. "Mediterranean Avenue"
    private String type;       // "property", "railroad", or "utility"
    private int    price;      // purchase cost
    private int    rent;       // base rent

    /**
     * 5-arg constructor matching MapManager calls:
     * new Property(location, name, type, price, rent)
     */
    public Property(int location, String name, String type, int price, int rent) {
        this.location = location;
        this.name     = name;
        this.type     = type;
        this.price    = price;
        this.rent     = rent;
    }

    // Basic getters:
    public int    getLocation()  { return location; }
    public String getName()      { return name; }
    public String getType()      { return type; }
    public int    getPrice()     { return price; }
    public int    getRent()      { return rent; }

    // A simple print for debugging
    public void print() {
        System.out.printf("[%d] %s (%s): $%d rent $%d%n",
                          location, name, type, price, rent);
    }
}

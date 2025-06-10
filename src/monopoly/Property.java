// File: Property.java
package monopoly;

/**
 * Monopoly property that supports
 * • Two constructor styles (5-arg with location, 6-arg without)
 * • Houses / hotel
 * • Mortgage flag
 * No clone() or ?: operator anywhere.
 */
public class Property {

    /* ---------- Core fields ---------- */
    private int     location  = -1;     // board index; −1 if not supplied
    private String  name;
    private String  type;               // "property", "railroad", "utility"
    private int     price;
    private int     baseRent;
    private int     houseCost;
    private int[]   rents;              // rent table (length 1–6)
    private int     mortgageValue;
    private boolean mortgaged = false;

    /* ---------- House / hotel state ---------- */
    private int     houseCount = 0;     // 0–4 houses
    private boolean hasHotel   = false; // true after hotel built

    /* ---------- 5-arg constructor (includes location) ---------- */
    public Property(
            int    location,
            String name,
            String type,
            int    price,
            int    rent)
    {
        this.location = location;
        this.name     = name;
        this.type     = type;
        this.price    = price;
        this.baseRent = rent;
        this.houseCost = 0;

        // rents[] only contains base rent for this constructor
        this.rents = new int[1];
        this.rents[0] = rent;

        this.mortgageValue = price / 2;
    }

    /* ---------- 6-arg constructor (used by Board) ---------- */
    public Property(
            String name,
            int    price,
            int    houseCost,
            int[]  rentTable,
            int    mortgage,
            String type)
    {
        this.name          = name;
        this.price         = price;
        this.houseCost     = houseCost;
        this.type          = type;
        this.mortgageValue = mortgage;

        // Manual copy of rentTable (no clone)
        this.rents = new int[rentTable.length];
        for (int i = 0; i < rentTable.length; i++)
        {
            this.rents[i] = rentTable[i];
        }

        // Determine base rent
        if (rentTable.length > 0)
        {
            this.baseRent = rentTable[0];
        }
        else
        {
            this.baseRent = 0;
        }
    }

    /* ---------- Getters ---------- */
    public int getLocation()
    {
        return location;
    }

    public String getName()
    {
        return name;
    }

    public String getType()
    {
        return type;
    }

    public int getPrice()
    {
        return price;
    }

    public int getBaseRent()
    {
        return baseRent;
    }

    public int getHouseCost()
    {
        return houseCost;
    }

    public int getMortgageValue()
    {
        return mortgageValue;
    }

    public boolean isMortgaged()
    {
        return mortgaged;
    }

    /* ---------- Setter ---------- */
    public void setMortgaged(boolean value)
    {
        mortgaged = value;
    }

    /* ---------- House / hotel utilities ---------- */
    public boolean buildHouse()
    {
        if (!type.equals("property"))
        {
            return false;                 // railroads / utilities cannot build
        }

        if (hasHotel)
        {
            return false;                 // already has hotel
        }

        if (houseCount < 4)
        {
            houseCount = houseCount + 1;  // add a house
            return true;
        }

        // Convert 4 houses → hotel
        houseCount = 4;
        hasHotel   = true;
        return true;
    }

    /** Rent depends on current houseCount or hotel flag */
    public int getRent()
    {
        int index = 0;

        if (hasHotel)
        {
            index = rents.length - 1;     // last slot = hotel rent
        }
        else
        {
            if (houseCount < rents.length)
            {
                index = houseCount;
            }
            else
            {
                index = rents.length - 1; // safety
            }
        }
        return rents[index];
    }

    /* ---------- Debug print ---------- */
    public void print()
    {
        System.out.print("[" + name + "]  $" + price);

        System.out.print("  rent $" + getRent());

        System.out.print("  (" + type + ")");

        if (houseCount > 0)
        {
            System.out.print("  houses=" + houseCount);
        }

        if (hasHotel)
        {
            System.out.print("  [HOTEL]");
        }

        if (mortgaged)
        {
            System.out.print("  [MORTGAGED]");
        }

        System.out.println();
    }
}
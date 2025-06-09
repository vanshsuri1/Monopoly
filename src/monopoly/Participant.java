// File: Participant.java
package monopoly;

/**
 * Composition wrapper to give each Player game fields:
 * money, position, bankrupt flag, move() + getNetWorth()
 */
public class Participant {
    public Player core;    // your linked-list backed Player
    public int    money;
    public int    position;
    public boolean bankrupt;

    public Participant(String name) {
        core     = new Player(name);
        money    = 1500;
        position = 0;
        bankrupt = false;
    }

    public String getName() {
        return core.getName();
    }

    // Move forward steps, wrap, collect 200 if passing GO
    public void move(int steps) {
        int old = position;
        position = (position + steps) % 40;
        if (position < old) {
            money += 200;
            System.out.println(getName() + " passed GO and collects $200.");
        }
    }

    // Sum cash + purchase prices of all owned properties
    public int getNetWorth() {
        int total = money;
        for (int loc = 0; loc < 40; loc++) {
            Property p = core.getOwnedProperties().searchByLocation(loc);
            if (p != null) {
                total += (int)p.getPrice();
            }
        }
        return total;
    }

    // Convenience
    public void buy(Property p) {
        core.buyProperty(p);
    }
}

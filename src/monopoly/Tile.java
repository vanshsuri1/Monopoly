// File: Tile.java
package monopoly;

public class Tile {
    public String name;

    public Tile(String name) {
        this.name = name;
    }

    /** Called when a player lands here. */
    public void landOn(Participant p, GameController controller) {

        /* ---------- Chance ---------- */
        if (name.equals("Chance")) {
            Card c = Card.drawChance();
            System.out.println("Chance: " + c.text);
            c.applyEffect(p, controller);
            return;
        }

        /* ---------- Community Chest ---------- */
        if (name.equals("Community Chest")) {
            Card c = Card.drawChest();
            System.out.println("Community Chest: " + c.text);
            c.applyEffect(p, controller);
            return;
        }

        /* ---------- Property / Railroad / Utility ---------- */
        Property prop = controller.getMapManager().getProperty(p.position);
        if (prop != null) {
            controller.getBuyingManager()
                      .handleSpace(p,
                                   controller.getPlayers(),
                                   controller.getMapManager());
            return;
        }

        /* ---------- Special board squares (no switch/break) ---------- */
        if (name.equals("Go")) {
            p.money += 200;
            System.out.println(p.getName() + " collects $200 for landing on GO.");
        }
        else if (name.equals("Income Tax")) {
            System.out.println(p.getName() + " pays Income Tax. (not implemented)");
        }
        else if (name.equals("Luxury Tax")) {
            p.money -= 75;
            System.out.println(p.getName() + " pays $75 Luxury Tax.");
        }
        else if (name.equals("Just Visiting") || name.equals("Jail")) {
            System.out.println(p.getName() + " is just visiting Jail.");
        }
        else if (name.equals("Free Parking")) {
            System.out.println(p.getName() + " landed on Free Parking (no action).");
        }
        else if (name.equals("Go To Jail")) {
            p.position = 10;     // Jail index
            p.inJail   = true;
            System.out.println(p.getName() + " goes directly to Jail!");
        }
        /* Any other label → no action */
    }
}
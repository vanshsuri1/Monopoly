// File: Tile.java
package monopoly;

public class Tile {
    public String name;

    public Tile(String name) {
        this.name = name;
    }

    /**
     * Called when a player lands on this square.
     * Assumes GameController has getters for the pieces we need.
     */
    public void landOn(Participant p, GameController controller) {
        // 1) Chance
        if (name.equals("Chance")) {
            Card c = Card.drawChance();
            System.out.println("Chance: " + c.text);
            c.applyEffect(p, controller);
        }
        // 2) Community Chest
        else if (name.equals("Community Chest")) {
            Card c = Card.drawChest();
            System.out.println("Community Chest: " + c.text);
            c.applyEffect(p, controller);
        }
        // 3) Property / Railroad / Utility
        else {
            // fetch the Property object (or null if none on this space)
            Property prop = controller.getMapManager().getProperty(p.position);

            if (prop != null) {
                // hand off to your BuyingManager
                controller.getBuyingManager()
                          .handleSpace(p,
                                       controller.getPlayers(),
                                       controller.getMapManager());
            }
            // 4) Special board squares
            else {
                switch (name) {
                  case "Go":
                    p.money += 200;
                    System.out.println(p.getName() + 
                        " collects $200 for landing on Go.");
                    break;
                  case "Income Tax":
                    // TODO: deduct 200 or 10% etc.
                    System.out.println(p.getName() + 
                        " pays Income Tax.");
                    break;
                  case "Luxury Tax":
                    p.money -= 75;
                    System.out.println(p.getName() + 
                        " pays $75 Luxury Tax.");
                    break;
                  case "Just Visiting":
                  case "Jail":
                    System.out.println(p.getName() + 
                        " is just visiting Jail.");
                    break;
                  case "Free Parking":
                    System.out.println(p.getName() + 
                        " landed on Free Parking (no action).");
                    break;
                  case "Go To Jail":
                    p.position = 10;    // Jail index
                    System.out.println(p.getName() + 
                        " goes directly to Jail!");
                    break;
                  default:
                    // nothing
                }
            }
        }
    }
}

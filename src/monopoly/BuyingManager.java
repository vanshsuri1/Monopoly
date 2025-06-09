// File: BuyingManager.java
package monopoly;

import java.util.Scanner;

public class BuyingManager {
    private Scanner in = new Scanner(System.in);

    /** 
     * Offer buy/rent logic for ownable spaces.
     */
    public void handleSpace(
        Participant p,
        Participant[] all,
        MapManager map
    ) {
        int pos = p.position;
        Property prop = map.getProperty(pos);
        if (prop == null) return;

        // find an owner
        Participant owner = null;
        for (Participant q : all) {
            if (q.core.getOwnedProperties().searchByLocation(pos) != null) {
                owner = q;
                break;
            }
        }

        if (owner == null) {
            // unowned: offer purchase
            System.out.println("Buy " + prop.getName() 
                + " for $" + (int)prop.getPrice() + "? (yes/no)");
            if (in.nextLine().equalsIgnoreCase("yes") 
             && p.money >= (int)prop.getPrice()) {
                p.money -= (int)prop.getPrice();
                p.buy(prop);
                System.out.println(p.getName() 
                    + " bought " + prop.getName());
            }
        }
        else if (owner != p) {
            // owned by someone else: pay rent
            int rent = prop.getRent();
            System.out.println("Pay rent $" + rent 
                + " to " + owner.getName());
            if (p.money >= rent) {
                p.money -= rent;
                owner.money += rent;
            } else {
                p.bankrupt = true;
                System.out.println(p.getName() 
                    + " cannot pay rent and is bankrupt!");
            }
        }
        // owned by you: nothing
    }
}

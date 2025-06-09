// File: AuctionManager.java
package monopoly;

import java.util.Scanner;

public class AuctionManager {
    private Scanner in = new Scanner(System.in);

    /**
     * Auction for an unbought property.
     */
    public void auction(Property prop, Participant[] players) {
        System.out.println("\n=== Auction: " + prop.getName() + " ===");
        int high = 0;
        Participant winner = null;
        boolean anyBid;
        do {
            anyBid = false;
            for (Participant p : players) {
                if (p.bankrupt) continue;
                System.out.println(p.getName() 
                    + ", high bid $" + high 
                    + ", your bid (0=pass)?");
                int bid = Integer.parseInt(in.nextLine());
                if (bid > high && bid <= p.money) {
                    high = bid;
                    winner = p;
                    anyBid = true;
                }
            }
        } while (anyBid);

        if (winner != null && high > 0) {
            winner.money -= high;
            winner.buy(prop);
            System.out.println(winner.getName() 
                + " wins at $" + high);
        } else {
            System.out.println("No bids. Auction over.");
        }
        System.out.println("========================\n");
    }
}

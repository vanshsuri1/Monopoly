// File: TradeManager.java
package monopoly;

import java.util.Scanner;

public class TradeManager {
    private Scanner in = new Scanner(System.in);

    /**
     * One-way trade offer: A offers B a property and/or cash.
     */
    public void trade(Participant[] players) {
        System.out.println("\n--- Trade Phase ---");
        System.out.print("Propose trade? (yes/no): ");
        if (!in.nextLine().equalsIgnoreCase("yes")) {
            System.out.println("Skipping trades.\n");
            return;
        }
        // List players
        for (int i=0; i<players.length; i++) {
            System.out.println((i+1) + ": " 
                + players[i].getName());
        }
        System.out.print("Proposer #: ");
        Participant a = players[Integer.parseInt(in.nextLine())-1];
        System.out.print("Receiver #: ");
        Participant b = players[Integer.parseInt(in.nextLine())-1];

        // Show A's props
        System.out.println(a.getName() + "'s properties:");
        a.core.getOwnedProperties().printAll();
        System.out.print("Offer property location (0=none): ");
        int loc = Integer.parseInt(in.nextLine());
        Property offered = loc>0 
            ? a.core.getOwnedProperties().searchByLocation(loc) 
            : null;

        System.out.print("Offer cash: $");
        int cash = Integer.parseInt(in.nextLine());
        if (cash > a.money) cash = a.money;

        System.out.print(b.getName() + ", accept " 
            + (offered!=null?offered.getName():"no property") 
            + " and $" + cash + "? (yes/no): ");
        if (in.nextLine().equalsIgnoreCase("yes")) {
            if (offered != null) {
                a.core.sellProperty(offered.getLocation());
                b.buy(offered);
            }
            a.money -= cash;
            b.money += cash;
            System.out.println("Trade completed.\n");
        } else {
            System.out.println("Trade declined.\n");
        }
    }
}

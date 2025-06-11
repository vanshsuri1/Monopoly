// File: TradeManager.java
package monopoly;

import java.util.Scanner;

public class TradeManager {
	private Scanner in = new Scanner(System.in);

	/** Interactive two-way trade between two participants. */
    public void trade(Participant[] players) {

        System.out.println("\n--- Trade Phase ---");
        System.out.print("Start a trade? (yes/no): ");
        if (!in.nextLine().equalsIgnoreCase("yes")) {
            System.out.println("No trades proposed.\n");
            return;
        }

        /* ---------- Select the two players ---------- */
        for (int i = 0; i < players.length; i++)
            System.out.println((i + 1) + ": " + players[i].getName());

        System.out.print("Trader A #: ");
        Participant a = players[Integer.parseInt(in.nextLine()) - 1];
        System.out.print("Trader B #: ");
        Participant b = players[Integer.parseInt(in.nextLine()) - 1];

        /* ---------- A's offer ---------- */
        TradeOffer offerA = buildOffer(a, "A");

        /* ---------- B's offer ---------- */
        TradeOffer offerB = buildOffer(b, "B");

        /* ---------- Confirm trade ---------- */
        System.out.println("\n--- Proposed Trade ---");
        printOffer(a, offerA);
        printOffer(b, offerB);

        System.out.print(a.getName() + ", do you accept? (yes/no): ");
        boolean aAccept = in.nextLine().equalsIgnoreCase("yes");
        System.out.print(b.getName() + ", do you accept? (yes/no): ");
        boolean bAccept = in.nextLine().equalsIgnoreCase("yes");

        if (aAccept && bAccept) {
            executeTrade(a, b, offerA, offerB);
            System.out.println("Trade completed.\n");
        } else {
            System.out.println("Trade cancelled.\n");
        }
    }

	/* ---------- Helper classes & methods ---------- */

	/** Represents the cash + (optional) property a player is offering. */
	private static class TradeOffer {
		int cash;
		Property prop; // may be null
	}

	/** Build an offer interactively. */
	private TradeOffer buildOffer(Participant p, String tag) {
		TradeOffer off = new TradeOffer();

		// List properties
		System.out.println("\n" + p.getName() + "'s properties:");
		p.core.getOwnedProperties().printAll();
		System.out.print("Player " + tag + " - offer property location (0 for none): ");
		int loc = Integer.parseInt(in.nextLine());
		if (loc > 0)
			off.prop = p.core.getOwnedProperties().searchByLocation(loc);

		System.out.print("Player " + tag + " - offer cash $: ");
		int cash = Integer.parseInt(in.nextLine());
		if (cash > p.money)
			cash = p.money;
		if (cash < 0)
			cash = 0;
		off.cash = cash;

		return off;
	}

	/** Print a participant's offer. */
	private void printOffer(Participant p, TradeOffer off) {
		System.out.print(p.getName() + " offers ");
		if (off.prop != null)
			System.out.print(off.prop.getName());
		else
			System.out.print("no property");
		System.out.println(" and $" + off.cash);
	}

	/** Execute the agreed trade: transfer property and cash. */
	private void executeTrade(Participant a, Participant b, TradeOffer offA, TradeOffer offB) {

		// Transfer property A → B
		if (offA.prop != null) {
			a.core.sellProperty(offA.prop.getLocation());
			b.buy(offA.prop);
		}
		// Transfer property B → A
		if (offB.prop != null) {
			b.core.sellProperty(offB.prop.getLocation());
			a.buy(offB.prop);
		}
		// Transfer cash
		a.money -= offA.cash;
		b.money += offA.cash;
		b.money -= offB.cash;
		a.money += offB.cash;
	}
}
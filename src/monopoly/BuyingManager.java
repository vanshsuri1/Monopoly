// File: BuyingManager.java
package monopoly;

import java.util.Scanner;

public class BuyingManager {

	private Scanner in = new Scanner(System.in);
	private AuctionManager auction = new AuctionManager();

	public void handleSpace(Participant p, Participant[] all, MapManager map) {
		Property prop = map.getProperty(p.position);
		if (prop == null)
			return;

		/* ---------- find owner ---------- */
		Participant owner = null;
		for (Participant q : all) {
			if (q.core.getOwnedProperties().searchByLocation(p.position) != null) {
				owner = q;
				break;
			}
		}

		/* ---------- unowned: prompt then auction ---------- */
		if (owner == null) {
			System.out.print("Buy " + prop.getName() + " for $" + prop.getPrice() + " (y/n): ");
			if (in.nextLine().equalsIgnoreCase("y") && p.money >= prop.getPrice()) {
				p.money -= prop.getPrice();
				p.buy(prop);
				System.out.println(p.getName() + " bought " + prop.getName());
			} else {
				System.out.println("Property goes to auction.");
				auction.auction(prop, all);
			}
			return;
		}

		/* ---------- rent ---------- */
		if (owner != p) {
			int rent = prop.getRent();
			System.out.println("Pay rent $" + rent + " to " + owner.getName());
			if (p.money >= rent) {
				p.money -= rent;
				owner.money += rent;
			} else {
				p.bankrupt = true;
				System.out.println(p.getName() + " is bankrupt!");
			}
			return;
		}

		/* ---------- owned by self: build ---------- */
		if (prop.getType().equals("property")) {
			if (ownsColourSet(p, prop) && !prop.isMortgaged()) {
				System.out.print("Build on " + prop.getName() + " for $" + prop.getHouseCost() + " (y/n): ");
				if (in.nextLine().equalsIgnoreCase("y") && p.money >= prop.getHouseCost()) {
					if (prop.buildHouse()) {
						p.money -= prop.getHouseCost();
						System.out.println("Built on " + prop.getName() + ". Rent now $" + prop.getRent());
					} else {
						System.out.println("Cannot build further.");
					}
				}
			}
		}
	}

	/**
	 * Very simple colour-set test: same first 3 letters, player owns ≥2. Made
	 * public static so GameController can call it.
	 */
	public boolean ownsColourSet(Participant p, Property prop) {
		String key = prop.getName();
		if (key.length() > 3) {
			key = key.substring(0, 3);
		}

		int owned = 0;
		for (int loc = 0; loc < 40; loc++) {
			Property pr = p.core.getOwnedProperties().searchByLocation(loc);
			if (pr != null) {
				String k = pr.getName();
				if (k.length() > 3) {
					k = k.substring(0, 3);
				}
				if (k.equals(key)) {
					owned += 1;
				}
			}
		}
		return owned >= 2; // simplistic rule
	}
}
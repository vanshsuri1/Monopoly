// File: Participant.java
package monopoly;

/**
 * A thin wrapper that adds cash, board position, jail flags, and helper methods
 * around your original linked-list–based Player class.
 */
public class Participant {

	/* ---------- Wrapped original Player ------------ */
	public Player core; // your untouched Player (with linked list)

	/* ---------- Game-specific fields --------------- */
	public int money; // liquid cash
	public int position; // 0–39 board index
	public boolean bankrupt; // true once net worth / cash < 0
	public boolean inJail; // true if currently in Jail
	public boolean hasGetOutOfJailCard; // holds a free-jail card

	/* ---------- Constructor ------------------------ */
	public Participant(String name) {
		this.core = new Player(name); // uses your existing Player ctor
		this.money = 1500; // Monopoly starting cash
		this.position = 0; // Start on GO
		this.bankrupt = false;
		this.inJail = false;
		this.hasGetOutOfJailCard = false;
	}

	/* ---------- Convenience getters ---------------- */
	public String getName() {
		return core.getName();
	}

	/* ---------- Movement logic --------------------- */
	public void move(int steps) {
		int oldPos = position;
		position = (position + steps) % 40;
		if (position < oldPos) { // passed GO
			money += 200;
			System.out.println(getName() + " passed GO and collects $200.");
		}
	}

	/* ---------- Net-worth calculation -------------- */
	public int getNetWorth() {
		int total = money;
		for (int loc = 0; loc < 40; loc++) {
			Property prop = core.getOwnedProperties().searchByLocation(loc);
			if (prop != null)
				total += prop.getPrice();
		}
		return total;
	}

	/* ---------- Wrapper to original buy logic ------ */
	public void buy(Property prop) {
		core.buyProperty(prop); // linked-list insert
	}
}
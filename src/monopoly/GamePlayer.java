package monopoly;

// A thin wrapper around your pasted Player that adds money, position, and bankrupt flags.
public class GamePlayer {
	public Player core; // your original Player
	public int money; // liquid cash
	public int position; // 0–39 on board
	public boolean bankrupt;

	public GamePlayer(String name) {
		this.core = new Player(name);
		this.money = 1500;
		this.position = 0;
		this.bankrupt = false;
	}

	public String getName() {
		return core.getName();
	}

	// Delegate to your pasted methods:
	public void buyProperty(Property p) {
		core.buyProperty(p);
	}

	public void sellProperty(int loc) {
		core.sellProperty(loc);
	}

	public void showProperties() {
		core.showProperties();
	}
	// …and so on for search/mortgage if you need them…

}

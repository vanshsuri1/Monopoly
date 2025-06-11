// File: GameController.java
package monopoly;

import java.util.Scanner;

public class GameController {

	/* ---------- Singleton references (used by SaveLoadManager) ---------- */
	private static GameController instance;
	private static MapManager boardRef;

	/* ---------- Managers ---------- */
	private MapManager mapManager;
	private BuyingManager buyingManager;
	private AuctionManager auctionManager;
	private TradeManager tradeManager;
	private SaveLoadManager saveLoad;

	/* ---------- Players ---------- */
	private Participant[] players;

	/* ---------- Console ---------- */
	private Scanner input = new Scanner(System.in);

	/* ---------- Main entry ---------- */
	public static void main(String[] args) {
		new GameController().start();
	}

	/* ---------- Accessors for other classes ---------- */
	public static MapManager getBoard() {
		return boardRef;
	}

	public MapManager getMapManager() {
		return mapManager;
	}

	public BuyingManager getBuyingManager() {
		return buyingManager;
	}

	public Participant[] getPlayers() {
		return players;
	}

	/* ---------- Game start ---------- */
	public void start() {
		instance = this; // record singleton
		mapManager = new MapManager();
		boardRef = mapManager;

		buyingManager = new BuyingManager();
		auctionManager = new AuctionManager();
		tradeManager = new TradeManager();
		saveLoad = new SaveLoadManager();

		/* -------- read players -------- */
		System.out.print("Enter number of players (2-4): ");
		int n = Integer.parseInt(input.nextLine());

		players = new Participant[n];

		for (int i = 0; i < n; i++) {
			System.out.print("Name for Player " + (i + 1) + ": ");
			players[i] = new Participant(input.nextLine());
		}

		/* -------- optional load -------- */
		System.out.print("Load previous game? (y / n): ");
		if (input.nextLine().equalsIgnoreCase("y")) {
			try {
				GameState gs = saveLoad.load();
				players = gs.players;
			} catch (Exception e) {
				System.out.println("No save file found. Starting new game.");
			}
		}

		gameLoop();
	}

	/* ---------- Main game loop ---------- */
	private void gameLoop() {
		boolean gameOver = false;

		while (!gameOver) {
			/* ===== Each player’s turn ===== */
			for (int i = 0; i < players.length; i++) {
				Participant p = players[i];

				if (p.bankrupt)
					continue;

				/* ------- Optional build menu before rolling ------- */
				System.out.print(p.getName() + " – build on a property before rolling (y / n)? ");

				if (input.nextLine().equalsIgnoreCase("y")) {
					listBuildOptions(p);
					System.out.print("Enter board location to build (-1 to cancel): ");

					int loc = Integer.parseInt(input.nextLine());

					if (loc >= 0) {
						attemptBuild(p, loc);
					}
				}

				/* ------- Render board ------- */
				mapManager.render(players);

				/* ------- Roll dice ------- */
				System.out.println(p.getName() + " – press [Enter] to roll");
				input.nextLine();

				int roll = DiceRoll.roll();
				System.out.println(p.getName() + " rolled " + roll);

				p.move(roll);

				/* ------- Land on tile ------- */
				Tile tile = mapManager.getTile(p.position);
				tile.landOn(p, this);

				/* ------- Bankruptcy check ------- */
				if (p.money < 0 || p.getNetWorth() <= 0) {
					p.bankrupt = true;
					System.out.println(p.getName() + " is bankrupt!");
				}

				/* ------- Status summary ------- */
				System.out.println("\n-- Status --");
				for (int j = 0; j < players.length; j++) {
					Participant x = players[j];

					System.out.println(x.getName() + " cash $" + x.money + " net $" + x.getNetWorth()
							+ (x.bankrupt ? " [BANKRUPT]" : ""));
				}
				System.out.println();
			}

			/* ===== Trade phase ===== */
			tradeManager.trade(players);

			/* ===== Auto-save ===== */
			try {
				saveLoad.save(new GameState(players));
				System.out.println("Game auto-saved.");
			} catch (Exception e) {
				System.out.println("Auto-save failed.");
			}

			/* ===== Win check ===== */
			int alive = 0;
			Participant last = null;

			for (int i = 0; i < players.length; i++) {
				if (!players[i].bankrupt) {
					alive = alive + 1;
					last = players[i];
				}
			}

			if (alive <= 1) {
				gameOver = true;

				if (last != null) {
					System.out.println(last.getName() + " wins the game!");
				}
			}
		}
	}

	/* ---------- Helper: list buildable properties ---------- */
	private void listBuildOptions(Participant p) {
		System.out.println("\nBuild-eligible properties:");

		for (int loc = 0; loc < 40; loc++) {
			Property pr = p.core.getOwnedProperties().searchByLocation(loc);

			if (pr != null && pr.getType().equals("property") && !pr.isMortgaged()
					&& new BuyingManager().ownsColourSet(p, pr)) {
				System.out.println(
						loc + " : " + pr.getName() + "  cost $" + pr.getHouseCost() + "  houses " + pr.getHouseCost());
			}
		}
	}

	/* ---------- Helper: attempt to build ---------- */
	private void attemptBuild(Participant p, int loc) {
		Property prop = p.core.getOwnedProperties().searchByLocation(loc);

		if (prop == null) {
			System.out.println("You don’t own a property at " + loc);
			return;
		}

		if (prop.isMortgaged()) {
			System.out.println("Property is mortgaged.");
			return;
		}

		if (!new BuyingManager().ownsColourSet(p, prop)) {
			System.out.println("You do not own the full set.");
			return;
		}

		int cost = prop.getHouseCost();

		if (p.money < cost) {
			System.out.println("Not enough cash.");
			return;
		}

		boolean ok = prop.buildHouse();

		if (ok) {
			p.money = p.money - cost;
			System.out.println("Built on " + prop.getName() + ". New rent $" + prop.getRent());
		} else {
			System.out.println("Cannot build further on that property.");
		}
	}
}
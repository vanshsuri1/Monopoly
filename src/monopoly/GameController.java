// File: GameController.java
package monopoly;

import java.util.Scanner;

public class GameController {

	// Singleton reference (used by SaveLoadManager)
	private static MapManager boardRef;

	// Managers
	private MapManager mapManager;
	private BuyingManager buyingManager;
	private AuctionManager auctionManager;
	private TradeManager tradeManager;
	private SaveLoadManager saveLoad;

	// Players
	private Participant[] players;

	// Console
	private Scanner input = new Scanner(System.in);

	// Main Entry
	public static void main(String[] args) {
		new GameController().start();
	}

	// To print to Console slower than Default and at a specific speed
	public static void printSlow(String s) {
		for (int i = 0; i < s.length(); i++) {
			System.out.print("" + s.charAt(i));
			// sleep for a bit after printing a letter
			try {
				Thread.sleep(100);
			} catch (InterruptedException m) {
				;
			}
		}
	}

	// To print to Console slower than Default and at variable speed
	public static void printSlow(String s, int t) {
		for (int i = 0; i < s.length(); i++) {
			System.out.print("" + s.charAt(i));
			// sleep for a bit after printing a letter
			try {
				Thread.sleep(t);
			} catch (InterruptedException m) {
				;
			}
		}
	}

	// To print to Console slower than Default and at variable speed with println()
	public static void printSlowln(String s, int t) {
		for (int i = 0; i < s.length(); i++) {
			System.out.print("" + s.charAt(i));
			// sleep for a bit after printing a letter
			try {
				Thread.sleep(t);
			} catch (InterruptedException m) {
				;
			}
		}
		System.out.println();
	}

	// To print to Console slower than Default and at a specific speed with
	// println()
	public static void printSlowln(String s) {
		for (int i = 0; i < s.length(); i++) {
			System.out.print("" + s.charAt(i));
			// sleep for a bit after printing a letter
			try {
				Thread.sleep(100);
			} catch (InterruptedException m) {
				;
			}
		}
		System.out.println();
	}

	// Accessors for other classes
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

	// Game Start
	public void start() {
		mapManager = new MapManager();
		boardRef = mapManager;

		buyingManager = new BuyingManager();
		auctionManager = new AuctionManager();
		tradeManager = new TradeManager();
		saveLoad = new SaveLoadManager();
		String file = null;

		// Optional Load Game
		System.out.print("Load previous game? (y / n): ");
		if ((input.next().toLowerCase()).equals("y")) {
			System.out.print("\nEnter the location of the saved game file: ");
			file = input.next();
			try {
				players = saveLoad.load(file);
			} catch (Exception e) {
				System.out.println("\nNo save file found. Starting new game.");
			}
		}

		if (players == null) {
			// Read Players from save File
			System.out.print("\nEnter the Number of Players (2-4): ");
			int n = input.nextInt();

			while (2 > n || n > 4) {
				System.out.print("\nEnter the Number of Players (2-4): ");
				n = input.nextInt();
			}
			players = new Participant[n];
			for (int i = 0; i < n; i++) {
				printSlow("\nName of Player " + (i + 1) + " (No Spaces): ", 50);
				players[i] = new Participant(input.next());
			}

			System.out.print("\nEnter the location of the file to save the game to: ");
			file = input.next();
		}

		saveLoad.setFile(file);

		gameLoop();
	}

	// Main game loop
	private void gameLoop() {
		boolean gameOver = false;

		while (!gameOver) {
			// Each player’s turn
			for (int i = 0; i < players.length; i++) {
				Participant p = players[i];

				if (p.bankrupt)
					continue;

				System.out.println();
				// Optional build menu before rolling, if a previous game was loaded
				System.out.print(p.getName() + " – build on a property before rolling (y / n)? ");
				String in = input.next().toLowerCase();
				if (in.equals("y")) {
					listBuildOptions(p);
					System.out.println();
					System.out.print("Enter board location to build (-1 to cancel): ");
					int loc = input.nextInt();

					if (loc >= 0) {
						attemptBuild(p, loc);
					}
				}

				// Render Board
				mapManager.render(players);

				// Roll Dice
				System.out.println(p.getName() + " – press [Enter] to roll");
				input.next();

				int roll = DiceRoll.roll();
				System.out.println(p.getName() + " rolled " + roll);

				p.move(roll);

				// Land on Tile
				Tile tile = mapManager.getTile(p.position);
				tile.landOn(p, this);

				// Bankruptcy Check
				if (p.money < 0 || p.getNetWorth() <= 0) {
					p.bankrupt = true;
					System.out.println(p.getName() + " is bankrupt!");
				}

				// Status Summary
				System.out.println("\n-- Status --");
				for (int j = 0; j < players.length; j++) {
					Participant x = players[j];

					System.out.println(x.getName() + " cash $" + x.money + " net $" + x.getNetWorth()
							+ (x.bankrupt ? " [BANKRUPT]" : ""));
				}
				System.out.println();
			}

			// Trade Phase
			tradeManager.trade(players);

			// Auto Save
			try {
				saveLoad.save(players);
				System.out.println("Game auto-saved.");
			} catch (Exception e) {
				System.out.println("Auto-save failed.");
			}

			// Win Check
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

	// Helper: list buildable properties
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

	// Helper: Attempt to Build
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
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

	// Cool Printer
	PrintSlow ps = new PrintSlow();
	
	// Players
	private Participant[] players;

	// Scanner
	private Scanner input = new Scanner(System.in);

	// Main Entry
	public static void main(String[] args) {
		new GameController().start();
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
				System.out.print("\nInvalid Number of Players. Enter again (2-4): ");
				n = input.nextInt();
			}
			players = new Participant[n];
			for (int i = 0; i < n; i++) {
				ps.printSlow("\nName of Player " + (i + 1) + " (No Spaces): ", 30);
				players[i] = new Participant(input.next());
			}

			ps.printSlow("\nEnter the location of the file to save the game to: ", 30);
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
				ps.printSlow(p.getName() + " – build on a property before rolling (y / n)? ", 30);
				String in = input.next().toLowerCase();
				if (in.equals("y")) {
					listBuildOptions(p);
					System.out.println();
					ps.printSlow("Enter board location to build (-1 to cancel): ", 30);
					int loc = input.nextInt();

					if (loc >= 0) {
						attemptBuild(p, loc);
					}
				}

				// Render Board
				mapManager.render(players);

				// Roll Dice
				ps.printSlow(p.getName() + " – Rolling Dices", 75);
				ps.printSlowln("...", 750);

				int roll1 = 0;
				int roll2 = 0;
				int roll = 0;

				while (roll1 == roll2) {
					roll1 = roll();
					roll2 = roll();
					roll = roll1 + roll2;
					if (roll1 == roll2)
						ps.printSlowln(p.getName() + " rolled a Double: " + roll, 50);
					else
						ps.printSlowln(p.getName() + " rolled: " + roll, 50);

					p.move(roll);

					// Land on Tile
					Tile tile = mapManager.getTile(p.position);
					tile.landOn(p, this);

					// Bankruptcy Check
					if (p.money < 0 || p.getNetWorth() <= 0) {
						p.bankrupt = true;
						ps.printSlowln(p.getName() + " is bankrupt!");
					}

					// Status Summary
					ps.printSlowln("\n-- Status --", 25);
					for (int j = 0; j < players.length; j++) {
						Participant x = players[j];

						ps.printSlow(x.getName() + " cash $" + x.money + " net $" + x.getNetWorth(), 25);
						if (x.bankrupt)
							ps.printSlowln(" [BANKRUPT]", 25);
						else
							System.out.println();
					}
					System.out.println();
				}
			}

			// Trade Phase
			tradeManager.trade(players);

			// Auto Save
			try {
				saveLoad.save(players);
				ps.printSlowln("Game auto-saved.", 50);
			} catch (Exception e) {
				ps.printSlowln("Auto-save failed. Enter a new Save Location: ", 50);
				saveLoad.setFile(input.next());
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
					ps.printSlowln(last.getName() + " Wins the Game!", 150);
				}
			}
		}
	}

	// Helper: list buildable properties
	private void listBuildOptions(Participant p) {
		ps.printSlowln("\nBuild-eligible properties:", 25);

		for (int loc = 0; loc < 40; loc++) {
			Property pr = p.core.getOwnedProperties().searchByLocation(loc);

			if (pr != null && pr.getType().equals("property") && !pr.isMortgaged()
					&& new BuyingManager().ownsColourSet(p, pr)) {
				ps.printSlowln(
						loc + " : " + pr.getName() + "  cost $" + pr.getHouseCost() + "  houses " + pr.getHouseCost(), 30);
			}
		}
	}

	// Helper: Attempt to Build
	private void attemptBuild(Participant p, int loc) {
		Property prop = p.core.getOwnedProperties().searchByLocation(loc);

		if (prop == null) {
			ps.printSlowln("You don’t own a property at " + loc, 50);
			return;
		}

		if (prop.isMortgaged()) {
			ps.printSlowln("Property is mortgaged.", 50);
			return;
		}

		if (!new BuyingManager().ownsColourSet(p, prop)) {
			ps.printSlowln("You do not own the full set.", 50);
			return;
		}

		int cost = prop.getHouseCost();

		if (p.money < cost) {
			ps.printSlowln("Not enough cash.", 50);
			return;
		}

		boolean ok = prop.buildHouse();

		if (ok) {
			p.money = p.money - cost;
			ps.printSlowln("Built on " + prop.getName() + ". New rent $" + prop.getRent(), 50);
		} else {
			ps.printSlowln("Cannot build further on that property.");
		}
	}

	public static int roll() {
		int d = (int) (Math.random() * 6) + 1;
		return d;
	}

}
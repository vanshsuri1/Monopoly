// File: GameController.java
package monopoly;

import java.util.Scanner;

public class GameController {
    private MapManager     mapManager;
    private BuyingManager  buyingManager;
    private AuctionManager auctionManager;
    private TradeManager   tradeManager;
    private Participant[]  players;
    private Scanner        input = new Scanner(System.in);

    public static void main(String[] args) {
        new GameController().start();
    }

    /** Entry point: build managers, read player names, then enter gameLoop */
    public void start() {
        mapManager     = new MapManager();
        buyingManager  = new BuyingManager();
        auctionManager = new AuctionManager();
        tradeManager   = new TradeManager();

        System.out.print("Enter number of players (2-4): ");
        int n = Integer.parseInt(input.nextLine());
        players = new Participant[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Enter name for Player " + (i+1) + ": ");
            players[i] = new Participant(input.nextLine());
        }

        gameLoop();
    }

    /** Main turn-by-turn game loop */
    private void gameLoop() {
        boolean gameOver = false;

        while (!gameOver) {
            for (int i = 0; i < players.length; i++) {
                Participant p = players[i];
                if (p.bankrupt) continue;

                // show board
                mapManager.render(players);

                // roll
                System.out.println(p.getName() + "'s turn. [Enter] to roll");
                input.nextLine();
                int roll = DiceRoll.roll();
                System.out.println(p.getName() + " rolled: " + roll);

                // move & pass GO
                p.move(roll);

                // landing
                int pos = p.position;
                String label = mapManager.getName(pos);
                System.out.println(p.getName() +
                                   " landed on " + label +
                                   " (space " + pos + ")");

                // defer to Tile.landOn
                Tile tile = mapManager.getTile(pos);
                tile.landOn(p, this);

                // check bankrupt
                if (p.money < 0 || p.getNetWorth() <= 0) {
                    p.bankrupt = true;
                    System.out.println(p.getName() + " is bankrupt!");
                }

                // status
                System.out.println("\n-- Status --");
                for (int j = 0; j < players.length; j++) {
                    Participant x = players[j];
                    System.out.printf("%s: Cash=$%d  NW=$%d %s\n",
                        x.getName(), x.money, x.getNetWorth(),
                        x.bankrupt ? "(Bankrupt)" : "");
                }
                System.out.println("----------------\n");
            }

            // after all players have moved, allow a trade round
            tradeManager.trade(players);

            // check for single winner
            int alive = 0;
            Participant last = null;
            for (int i = 0; i < players.length; i++) {
                Participant p = players[i];
                if (!p.bankrupt) {
                    alive++;
                    last = p;
                }
            }

            if (alive <= 1) {
                gameOver = true;
                if (last != null) {
                    System.out.println(last.getName() + " wins!");
                }
            }
        }
    }

    // ---------------------------------------------------
    // Getter methods required by Tile.landOn(...):
    // ---------------------------------------------------

    /** Expose the MapManager to Tile.landOn */
    public MapManager getMapManager() {
        return mapManager;
    }

    /** Expose the BuyingManager to Tile.landOn */
    public BuyingManager getBuyingManager() {
        return buyingManager;
    }

    /** Expose the player array to Tile.landOn (and others) */
    public Participant[] getPlayers() {
        return players;
    }
}

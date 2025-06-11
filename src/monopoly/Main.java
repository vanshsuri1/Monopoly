package monopoly;

//File: Main.java

import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		// Create the game controller, which holds the board, decks, players, etc.
		GameController controller = new GameController();
		// Kick off the game (initialization + main loop)
		controller.start();

	}
}

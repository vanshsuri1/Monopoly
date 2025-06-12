// File: SaveLoadManager.java
package monopoly;

import java.io.*;

public class SaveLoadManager {

	private static String FILE;

	public void setFile(String f) {
		FILE = f;
	}

	public void save(Participant[] state) throws IOException {
		PrintWriter pw = new PrintWriter(new FileWriter(FILE));

		pw.println(state.length); // Prints the number of players on the very first line

		// Save each player to one line of text
		for (int i = 0; i < state.length; i++) {
			Participant p = state[i];
			// Write core stats
			pw.print(p.getName() + "," + p.money + "," + p.position + "," + p.inJail + "," + p.bankrupt + ","
					+ p.hasGetOutOfJailCard + "|");

			// Write property locations that player owns
			String commaSeparated = "";
			for (int loc = 0; loc < 40; loc++) {
				Property prop = p.core.getOwnedProperties().searchByLocation(loc);
				if (prop != null) {
					if (commaSeparated.length() == 0) {
						commaSeparated = commaSeparated + loc;
					} else {
						commaSeparated = commaSeparated + ";" + loc;
					}
				}
			}
			pw.println(commaSeparated);
		}
		pw.close();
		System.out.println("Game saved to " + FILE);
	}

	/** Load game from file and rebuild Player data. */
	public Participant[] load(String f) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(f));
		Participant[] loaded = new Participant[Integer.parseInt(br.readLine())];
		int count = 0;
		String line;

		while ((line = br.readLine()) != null && count < 4) {
			// Split stats and property list
			int bar = line.indexOf('|');
			String statsPart = line.substring(0, bar);
			String propPart = line.substring(bar + 1);

			String[] stats = statsPart.split(",");
			Participant p = new Participant(stats[0]);
			p.money = Integer.parseInt(stats[1]);
			p.position = Integer.parseInt(stats[2]);
			p.inJail = Boolean.parseBoolean(stats[3]);
			p.bankrupt = Boolean.parseBoolean(stats[4]);
			p.hasGetOutOfJailCard = Boolean.parseBoolean(stats[5]);

			// Re-attach properties
			if (propPart.length() > 0) {
				String[] locs = propPart.split(";");
				for (int i = 0; i < locs.length; i++) {
					int loc = Integer.parseInt(locs[i]);
					Property prop = GameController.getBoard().getProperty(loc);
					if (prop != null) {
						p.buy(prop);
					}
				}
			}
			loaded[count] = p;
			count = count + 1;
		}
		br.close();

		// Trim to actual size
		Participant[] result = new Participant[count];
		for (int i = 0; i < count; i++) {
			result[i] = loaded[i];
		}
		System.out.println("Game loaded from " + FILE);
		return result;
	}

}
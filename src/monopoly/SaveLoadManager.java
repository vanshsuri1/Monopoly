// File: SaveLoadManager.java
package monopoly;

import java.io.*;

public class SaveLoadManager {

    private static final String FILE = "/Users/vanshsuri/Desktop/monopoly_save.txt";

    /** Save each player to one line of text. */
    public void save(GameState state) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter(FILE));

        for (Participant p : state.players) {
            // Write core stats
            pw.print(p.getName() + "," + p.money + "," + p.position + ","
                     + p.inJail + "," + p.bankrupt + "|");

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
    public GameState load() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(FILE));
        Participant[] loaded = new Participant[4]; // supports up to 4 players
        int count = 0;
        String line;

        while ((line = br.readLine()) != null && count < 4) {
            // Split stats and property list
            int bar = line.indexOf('|');
            String statsPart = line.substring(0, bar);
            String propPart  = line.substring(bar + 1);

            String[] stats = statsPart.split(",");
            Participant p = new Participant(stats[0]);
            p.money    = Integer.parseInt(stats[1]);
            p.position = Integer.parseInt(stats[2]);
            p.inJail   = Boolean.parseBoolean(stats[3]);
            p.bankrupt = Boolean.parseBoolean(stats[4]);

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
        return new GameState(result);
    }
}
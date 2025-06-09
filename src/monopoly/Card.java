// File: Card.java
package monopoly;

public class Card {
    public String text;
    public String type;

    private static final String[] CHANCE_TEXTS = {
        "Advance to Boardwalk.",
        "Advance to Go (Collect $200).",
        "Advance to Illinois Avenue. If you pass Go, collect $200.",
        "Advance to St. Charles Place. If you pass Go, collect $200.",
        "Advance to the nearest Railroad. If unowned, you may buy it. If owned, pay double rent.",
        "Advance to the nearest Railroad. If unowned, you may buy it. If owned, pay double rent.",
        "Advance token to nearest Utility. If unowned, you may buy it. If owned, throw dice and pay 10× dice roll.",
        "Bank pays you dividend of $50.",
        "Get Out of Jail Free.",
        "Go Back 3 Spaces.",
        "Go to Jail. Go directly to Jail, do not pass Go, do not collect $200.",
        "Make general repairs on all your property. For each house pay $25. For each hotel pay $100.",
        "Speeding fine $15.",
        "Take a trip to Reading Railroad. If you pass Go, collect $200.",
        "You have been elected Chairman of the Board. Pay each player $50.",
        "Your building loan matures. Collect $150."
    };
    private static final String[] CHEST_TEXTS = {
        "Advance to Go (Collect $200)",
        "Bank error in your favor. Collect $200",
        "Doctor’s fee. Pay $50",
        "From sale of stock you get $50",
        "Get Out of Jail Free",
        "Go to Jail. Go directly to jail, do not pass Go, do not collect $200",
        "Holiday fund matures. Receive $100",
        "Income tax refund. Collect $20",
        "It is your birthday. Collect $10 from every player",
        "Life insurance matures. Collect $100",
        "Pay hospital fees of $100",
        "Pay school fees of $50",
        "Receive $25 consultancy fee",
        "You are assessed for street repair. $40 per house. $115 per hotel",
        "You have won second prize in a beauty contest. Collect $10",
        "You inherit $100"
    };

    public static final Card[] chanceDeck;
    public static final Card[] chestDeck;
    private static int chanceIndex=0, chestIndex=0;

    static {
        chanceDeck = new Card[CHANCE_TEXTS.length];
        for (int i=0; i<CHANCE_TEXTS.length; i++) {
            chanceDeck[i] = new Card(CHANCE_TEXTS[i], "Chance");
        }
        shuffle(chanceDeck);

        chestDeck = new Card[CHEST_TEXTS.length];
        for (int i=0; i<CHEST_TEXTS.length; i++) {
            chestDeck[i] = new Card(CHEST_TEXTS[i], "Community Chest");
        }
        shuffle(chestDeck);
    }

    // Private for deck building
    private Card(String t, String ty) {
        text = t; 
        type = ty;
    }
    // Public fallback
    public Card(String t) {
        text = t; type = "";
    }

    private static void shuffle(Card[] deck) {
        for (int i=deck.length-1; i>0; i--) {
            int j = (int)(Math.random()*(i+1));
            Card tmp = deck[i];
            deck[i] = deck[j];
            deck[j] = tmp;
        }
    }

    public static Card drawChance() {
        Card c = chanceDeck[chanceIndex++];
        if (chanceIndex >= chanceDeck.length) chanceIndex=0;
        return c;
    }
    public static Card drawChest() {
        Card c = chestDeck[chestIndex++];
        if (chestIndex >= chestDeck.length) chestIndex=0;
        return c;
    }

    // Updated signature to Participant
    public void applyEffect(Participant p, GameController ctrl) {
        // TODO: implement each card's rules here
    }
}

// File: Card.java
package monopoly;

/**
 * Card object for Chance and Community Chest decks.
 * Decks are stored in CardLinkedList structures (head-only, no tail).
 */
public class Card {

    public String text;   // full description
    public String type;   // "Chance" or "Community Chest"

    /* ----- Raw text arrays (16 each) ----- */
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

    /* ----- Linked-list decks ----- */
    private static final CardLinkedList chanceDeck = new CardLinkedList();
    private static final CardLinkedList chestDeck  = new CardLinkedList();

    /* ----- Static initializer: build + shuffle once ----- */
    static {
        for (int i = 0; i < CHANCE_TEXTS.length; i++) {
            chanceDeck.insert(new Card(CHANCE_TEXTS[i], "Chance"));
        }
        for (int i = 0; i < CHEST_TEXTS.length; i++) {
            chestDeck.insert(new Card(CHEST_TEXTS[i], "Community Chest"));
        }
        chanceDeck.shuffle();
        chestDeck.shuffle();
    }

    /* ----- Constructors ----- */
    private Card(String txt, String tp) {
        text = txt;
        type = tp;
    }

    // Public fallback (rarely needed)
    public Card(String txt) {
        this(txt, "");
    }

    /* ----- Draw helpers ----- */
    public static Card drawChance() { return chanceDeck.draw(); }
    public static Card drawChest()  { return chestDeck.draw(); }

    /* ----- Apply effect (stub) ----- */
    


    /* ------------------ Effects ------------------ */
    public void applyEffect(Participant p, GameController ctrl) {

        MapManager     map = ctrl.getMapManager();
        BuyingManager  buy = ctrl.getBuyingManager();
        Participant[]  all = ctrl.getPlayers();

        /* ---- CHANCE ---- */
        if (type.equals("Chance")) {

            if (text.equals("Advance to Boardwalk.")) {
                p.position = 39;
                System.out.println(p.getName()+" advances to Boardwalk.");
                buy.handleSpace(p, all, map);
            }

            else if (text.equals("Advance to Go (Collect $200).")) {
                p.position = 0; p.money += 200;
                System.out.println(p.getName()+" advances to GO and collects $200.");
            }

            else if (text.equals("Advance to Illinois Avenue. If you pass Go, collect $200.")) {
                if (p.position > 24) p.money += 200;
                p.position = 24;
                System.out.println(p.getName()+" advances to Illinois Ave.");
                buy.handleSpace(p, all, map);
            }

            else if (text.equals("Advance to St. Charles Place. If you pass Go, collect $200.")) {
                if (p.position > 11) p.money += 200;
                p.position  = 11;
                System.out.println(p.getName()+" advances to St. Charles Place.");
                buy.handleSpace(p, all, map);
            }

            else if (text.contains("nearest Railroad")) {
                int[] rr = {5,15,25,35};
                for (int r: rr) if (r > p.position) { p.position = r; break; }
                System.out.println(p.getName()+" moves to nearest Railroad.");
                buy.handleSpace(p, all, map); // double rent not implemented
            }

            else if (text.contains("nearest Utility")) {
                int[] ut = {12,28};
                for (int u: ut) if (u > p.position) { p.position = u; break; }
                System.out.println(p.getName()+" moves to nearest Utility.");
                buy.handleSpace(p, all, map); // 10× dice rent not implemented
            }

            else if (text.equals("Bank pays you dividend of $50.")) {
                p.money += 50;
            }

            else if (text.equals("Get Out of Jail Free.")) {
                p.hasGetOutOfJailCard = true;
            }

            else if (text.equals("Go Back 3 Spaces.")) {
                p.position = (p.position + 37) % 40;
                System.out.println(p.getName()+" goes back 3 spaces.");
                buy.handleSpace(p, all, map);
            }

            else if (text.startsWith("Go to Jail")) {
                p.position = 10;
                p.inJail   = true;
                System.out.println(p.getName()+" goes directly to Jail.");
            }

            else if (text.equals("Make general repairs on all your property. For each house pay $25. For each hotel pay $100.")) {
                System.out.println("General repairs not implemented.");
            }

            else if (text.equals("Speeding fine $15.")) {
                p.money -= 15;
            }

            else if (text.equals("Take a trip to Reading Railroad. If you pass Go, collect $200.")) {
                if (p.position > 5) p.money += 200;
                p.position = 5;
                System.out.println(p.getName()+" travels to Reading Railroad.");
                buy.handleSpace(p, all, map);
            }

            else if (text.equals("You have been elected Chairman of the Board. Pay each player $50.")) {
                for (Participant q: all)
                    if (q != p && !q.bankrupt) { q.money += 50; p.money -= 50; }
            }

            else if (text.equals("Your building loan matures. Collect $150.")) {
                p.money += 150;
            }
        }

        /* ---- COMMUNITY CHEST ---- */
        else {

            if (text.equals("Advance to Go (Collect $200)")) {
                p.position = 0; p.money += 200;
            }

            else if (text.equals("Bank error in your favor. Collect $200")) {
                p.money += 200;
            }

            else if (text.equals("Doctor’s fee. Pay $50")) {
                p.money -= 50;
            }

            else if (text.equals("From sale of stock you get $50")) {
                p.money += 50;
            }

            else if (text.equals("Get Out of Jail Free")) {
                p.hasGetOutOfJailCard = true;
            }

            else if (text.startsWith("Go to Jail")) {
                p.position = 10; p.inJail = true;
            }

            else if (text.equals("Holiday fund matures. Receive $100")) {
                p.money += 100;
            }

            else if (text.equals("Income tax refund. Collect $20")) {
                p.money += 20;
            }

            else if (text.equals("It is your birthday. Collect $10 from every player")) {
                for (Participant q: all)
                    if (q != p && !q.bankrupt) { q.money -= 10; p.money += 10; }
            }

            else if (text.equals("Life insurance matures. Collect $100")) {
                p.money += 100;
            }

            else if (text.equals("Pay hospital fees of $100")) {
                p.money -= 100;
            }

            else if (text.equals("Pay school fees of $50")) {
                p.money -= 50;
            }

            else if (text.equals("Receive $25 consultancy fee")) {
                p.money += 25;
            }

            else if (text.equals("You are assessed for street repair. $40 per house. $115 per hotel")) {
                System.out.println("Street repair card not implemented.");
            }

            else if (text.equals("You have won second prize in a beauty contest. Collect $10")) {
                p.money += 10;
            }

            else if (text.equals("You inherit $100")) {
                p.money += 100;
            }
        }
    }
}
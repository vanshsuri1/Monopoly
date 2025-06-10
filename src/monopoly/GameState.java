// File: GameState.java
package monopoly;

/** Holds the array of participants for saving / loading. */
public class GameState {
    public Participant[] players;
    public GameState(Participant[] players) {
        this.players = players;
    }
}
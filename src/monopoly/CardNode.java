// File: CardNode.java
package monopoly;

/** Node for a singly linked list of Card objects. */
class CardNode {
    public Card     card;
    public CardNode next;
    public CardNode(Card c) {
        card = c;
        next = null;
    }
}
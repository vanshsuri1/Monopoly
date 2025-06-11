// File: CardLinkedList.java
package monopoly;

import java.util.Random;

/**
 * A simple singly linked list to act as a deck of Cards. Only a head pointer is
 * used (no tail).
 */
class CardLinkedList {
	private CardNode head;

	public CardLinkedList() {
		head = null;
	}

	/** Insert at end (walk until null, like PropertyLinkedList). */
	public void insert(Card c) {
		CardNode n = new CardNode(c);
		if (head == null) {
			head = n;
		} else {
			CardNode temp = head;
			while (temp.next != null)
				temp = temp.next;
			temp.next = n;
		}
	}

	/** Draw (pop) the top card; returns null if deck empty. */
	public Card draw() {
		if (head == null)
			return null;
		CardNode top = head;
		head = head.next;
		return top.card;
	}

	/**
	 * Shuffle: copy to array, Fisher–Yates, then rebuild list. Keeps only a head
	 * pointer (no tail).
	 */
	public void shuffle() {
		/* 1) count nodes */
		int count = 0;
		CardNode t = head;
		while (t != null) {
			count++;
			t = t.next;
		}

		if (count <= 1)
			return; // nothing to shuffle

		/* 2) copy to array */
		Card[] arr = new Card[count];
		t = head;
		int idx = 0;
		while (t != null) {
			arr[idx] = t.card;
			t = t.next;
			idx++;
		}

		/* 3) Fisher–Yates shuffle */
		Random rnd = new Random();
		for (int i = count - 1; i > 0; i--) {
			int j = rnd.nextInt(i + 1);
			Card temp = arr[i];
			arr[i] = arr[j];
			arr[j] = temp;
		}

		/* 4) rebuild list with new order (head only) */
		head = null;
		for (int i = 0; i < arr.length; i++) {
			insert(arr[i]); // reuses head-only insert
		}
	}
}
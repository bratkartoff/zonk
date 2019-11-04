package com.dhbw.zonk.gameData;

import java.io.Serializable;
import java.util.LinkedList;

public class CardStack implements Serializable {
	private int visible = 0;

	private LinkedList<Card> cards;

	public boolean isEmpty() { return cards.isEmpty(); };
	public Card getTopCard(int i) { return cards.get(i); }
	public void addCard(Card c) { cards.add(c); }
}

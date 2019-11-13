package com.dhbw.zonk.gameData;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
	private String name;
	private ArrayList<Card> cards = new ArrayList<>();

	public Player(String name) {
		this.name = name;
	}

	public String getName() { return name; };

	public int getCardCount() {	return cards.size(); }
	public Card getCard(int i) { return cards.get(i); }
	public void addCard(Card c) { cards.add(c); }
}

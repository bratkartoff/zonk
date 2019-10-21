package com.dhbw.zonk.gameData;


import java.io.Serializable;
import java.util.ArrayList;

// todo: maybe use a CardContainer class to share common methods between card stacks and hands
class Hand implements Serializable {
	private ArrayList<Card> cards = new ArrayList<>();

	public int getCardCount() {	return cards.size(); }
	public Card getCard(int i) { return cards.get(i); }
	public void addCard(Card c) { cards.add(c); }
}

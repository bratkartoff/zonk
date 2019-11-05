package com.dhbw.zonk.gameData;

import android.widget.ImageView;

import java.io.Serializable;
import java.util.LinkedList;

public class CardStack implements Serializable {
	private int visible = 0;



	private ImageView CardStackView;

	private LinkedList<Card> cards;

	public boolean isEmpty() { return cards.isEmpty(); };
	public Card getTopCard(int i) { return cards.get(i); }
	public void addCard(Card c) { cards.add(c); }
	public void setCardStackView(ImageView CardStackView){this.CardStackView = CardStackView;}
	public ImageView getCardStackView() {
		return CardStackView;
	}
}

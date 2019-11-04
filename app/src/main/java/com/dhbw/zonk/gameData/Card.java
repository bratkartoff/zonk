package com.dhbw.zonk.gameData;

class Card {
	enum suit { hearts, diamonds, clubs, spades }

	private final int rank;
	private final suit suit;
	// todo: picture

	Card(int rank, suit suit) {
		this.rank = rank;
		this.suit = suit;
	}

	public Card.suit getSuit() {
		return suit;
	}

	public int getRank() {
		return rank;
	}
}

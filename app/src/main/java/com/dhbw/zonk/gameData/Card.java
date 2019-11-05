package com.dhbw.zonk.gameData;

class Card {
	enum suit { hearts, diamonds, clubs, spades }

	private final int rank;
	private final suit suit;
	private String picture;

	Card(int rank, suit suit, String picture) {
		this.rank = rank;
		this.suit = suit;
		this.picture = picture;
	}

	public Card.suit getSuit() {
		return suit;
	}

	public int getRank() {
		return rank;
	}

	public String getPicture() {return picture;}
}

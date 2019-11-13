package com.dhbw.zonk.gameData;

import android.media.Image;
import android.widget.ImageView;
import java.io.Serializable;
//import android.support.v7.widget.AppCompatImageView;

class Card extends ImageView implements Serializable {
	enum suit { hearts, diamonds, clubs, spades }

	private final int rank;
	private final suit suit;
	private String picture;



	private ImageView CardView;

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

	public void setCardView(ImageView CardView){this.CardView = CardView;}

	public ImageView getCardView() {
		return CardView;
	}
}

package com.dhbw.zonk.gameData;

import com.dhbw.zonk.Message;
import com.dhbw.zonk.Player;
import com.dhbw.zonk.lobbyData.LobbyState;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class GameState implements Serializable {
	private ArrayList<GamePlayer> players;
	private ArrayList<CardStack> stacks;

	// todo: pass lobbyStateObject and extract info (or pass all config options separately)
	GameState(LobbyState lobbyState) {
		// copy player info from lobby
		this.players = new ArrayList<>();
		for (Player plr : lobbyState.getPlayers())
			this.players.add(new GamePlayer(plr));

		// create a card deck
		Card[] cards = new Card[52];
		for (Card.suit suit : Card.suit.values()) {
			for (int rank = 2; rank < 15; rank++) {
				cards[suit.ordinal() * 13 + rank] = new Card(rank, suit);
			}
		}
		// alternatively, hardcode it
		// Card[] cards = new Card[] {new Card(2, Card.suit.hearts), new Card(3, Card.suit.hearts), new Card(4, Card.suit.hearts), new Card(5, Card.suit.hearts), new Card(6, Card.suit.hearts), new Card(7, Card.suit.hearts), new Card(8, Card.suit.hearts), new Card(9, Card.suit.hearts), new Card(10, Card.suit.hearts), new Card(11, Card.suit.hearts), new Card(12, Card.suit.hearts), new Card(13, Card.suit.hearts), new Card(14, Card.suit.hearts), new Card(2, Card.suit.diamonds), new Card(3, Card.suit.diamonds), new Card(4, Card.suit.diamonds), new Card(5, Card.suit.diamonds), new Card(6, Card.suit.diamonds), new Card(7, Card.suit.diamonds), new Card(8, Card.suit.diamonds), new Card(9, Card.suit.diamonds), new Card(10, Card.suit.diamonds), new Card(11, Card.suit.diamonds), new Card(12, Card.suit.diamonds), new Card(13, Card.suit.diamonds), new Card(14, Card.suit.diamonds), new Card(2, Card.suit.clubs), new Card(3, Card.suit.clubs), new Card(4, Card.suit.clubs), new Card(5, Card.suit.clubs), new Card(6, Card.suit.clubs), new Card(7, Card.suit.clubs), new Card(8, Card.suit.clubs), new Card(9, Card.suit.clubs), new Card(10, Card.suit.clubs), new Card(11, Card.suit.clubs), new Card(12, Card.suit.clubs), new Card(13, Card.suit.clubs), new Card(14, Card.suit.clubs), new Card(2, Card.suit.spades), new Card(3, Card.suit.spades), new Card(4, Card.suit.spades), new Card(5, Card.suit.spades), new Card(6, Card.suit.spades), new Card(7, Card.suit.spades), new Card(8, Card.suit.spades), new Card(9, Card.suit.spades), new Card(10, Card.suit.spades), new Card(11, Card.suit.spades), new Card(12, Card.suit.spades), new Card(13, Card.suit.spades), new Card(14, Card.suit.spades)};

		// shuffle it
		Collections.shuffle(Arrays.asList(cards));

		// todo distribute cards on stacks and hands
		// todo get player function
		// todo get stack function
	}
}

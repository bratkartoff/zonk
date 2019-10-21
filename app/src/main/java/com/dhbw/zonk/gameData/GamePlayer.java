package com.dhbw.zonk.gameData;

import com.dhbw.zonk.Player;

class GamePlayer extends Player {
	Hand hand;

	// extract information from Player object and use it to construct a GamePlayer
	GamePlayer(Player player) {
		this(player.getName(), new Hand());
	}

	private GamePlayer(String name, Hand hand) {
		super(name);
		this.hand = hand;
	}
}

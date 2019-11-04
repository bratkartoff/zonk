package com.dhbw.zonk.gameData;

import java.io.Serializable;

public class Player implements Serializable {
	private String name;
	private Hand hand;

	public Player(String name, Hand hand) {
		this.name = name;
		this.hand = hand;
	}

	public String getName() { return name; }
}

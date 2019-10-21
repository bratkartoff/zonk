package com.dhbw.zonk;

import java.io.Serializable;

// player data shared between lobby and game
public class Player implements Serializable {
	String name;

	public Player (String name) {
		this.name = name;
	}

	public String getName() { return name; }
}

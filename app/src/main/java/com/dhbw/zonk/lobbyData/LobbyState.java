package com.dhbw.zonk.lobbyData;

import com.dhbw.zonk.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class LobbyState implements Serializable {
	// todo: this should not be hardcoded
	private int stackNumber = 3;
	private ArrayList<LobbyPlayer> players = new ArrayList<>();

	public int getStackNumber() {
		return stackNumber;
	}

	public ArrayList<LobbyPlayer> getPlayers() {
		return players;
	}
}

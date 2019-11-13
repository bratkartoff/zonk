package com.dhbw.zonk.networking;

import com.dhbw.zonk.gameData.GameState;
import com.dhbw.zonk.gameData.Player;

import java.io.Serializable;

abstract class Message implements Serializable {
	abstract void process();
	boolean isValid(long playerID) { return true; }
}

// first message sent by the client
class ClientHello extends Message {
	private final String username;

	ClientHello(String username) {
		this.username = username;
	}

	// always processed on the server
	void process() {
		GameState.getInstance().addPlayer(new Player(username));
	}

	boolean isValid(long playerID) {
		GameState gs = GameState.getInstance();
		return gs.getPlayerByName(username) == null && !gs.hasStarted();
	}
}

// replace the gameState
class GameStateUpdate extends Message {
	private final GameState gameState;

	GameStateUpdate(GameState gameState) {
		this.gameState = gameState;
	}

	void process() {
		GameState.setInstance(gameState);
	}
}


class Move extends Message {
	public boolean isValid(long playerID) {
		// todo: gameState hashcode check
		return true;
	}

	public void process() {
		// todo change game state
	}
}

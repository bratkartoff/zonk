package com.dhbw.zonk.gameData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GameState implements Serializable {
	private static GameState instance = null;
	private ArrayList<Player> players = new ArrayList<>();
	private ArrayList<CardStack> stacks = new ArrayList<>();
	private boolean started = false;

	private GameState() {}

	public static GameState getInstance() {
		if (instance == null)
			reset();
		return instance;
	}

	public static void setInstance(GameState newInstance) {
		instance = newInstance;
	}

	public static void reset() {
		instance = new GameState();
	}

	public void startGame() {
		// create a card deck
		Card[] cards = new Card[52];
		for (Card.suit suit : Card.suit.values()) {
			for (int rank = 2; rank < 15; rank++) {
				cards[suit.ordinal() * 13 + rank] = new Card(rank, suit);
			}
		}

		// shuffle it
		Collections.shuffle(Arrays.asList(cards));

		// todo distribute cards on stacks and hands
		// todo get player function
		// todo get stack function
		started = true;
	}

	public boolean hasStarted() { return started; }

	public Player getPlayerByName(String name) {
		for (Player p : players) {
			if (p.getName().equals(name))
				return p;
		}
		return null;
	}

	public void addPlayer(Player player) {
		players.add(player);
	}
}

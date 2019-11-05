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

	public GameState() {} //why was the constructor private before?

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
				cards[suit.ordinal() * 13 + rank] = new Card(rank, suit, null);
			}
		}

		// shuffle it
		Collections.shuffle(Arrays.asList(cards));

		// todo distribute cards on stacks and hands
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

	public int get_count_of_players(){
		return players.size();
	}

	public int get_count_of_stacks(){
		return stacks.size();
	}

	public Player get_player(int index){
		if(index < players.size()) {
			return (players.get(index));
		}else{
			return null;
		}
	}

	public CardStack get_cardstack(int index){
		if(index < stacks.size()) {
			return (stacks.get(index));
		}else{
			return null;
		}
	}


}

package com.dhbw.zonk.networking;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends IOThread {
	private final InetAddress remoteHost;
	private final int port;
	private Integer playerID = null;

	public Client (InetAddress remoteHost, int port) {
		this.remoteHost = remoteHost;
		this.port = port;
	}

	public void run() {
		try {
			connect();
		} catch (IOException e) {
			// todo: host does not exists, network unreachable, socket in use, etc.
			Log.d("Client", "Look Dave, I can see you're really upset about this... " + e);
			return;
		}
		if (authenticate())
			mainLoop();
		cleanup();
	}

	private void connect() throws IOException {
		sck = new Socket(remoteHost, port);
		output = new ObjectOutputStream(sck.getOutputStream());
		input =  new ObjectInputStream(sck.getInputStream());
	}

	private boolean authenticate() {
		// send authentication message
		try {
			output.writeObject(new ClientHello("Test")); // todo
			GameStateUpdate gameStateUpdate = (GameStateUpdate) input.readObject();
			gameStateUpdate.process();
			return true;
		} catch (IOException | ClassNotFoundException e) {
			// todo: signal error to user
			return false;
		}
	}

	private void mainLoop() {
		while (!stopRequested()) {
			try {
					Message message = (Message) input.readObject();
					message.process();
				} catch (IOException | ClassNotFoundException e) {
					break; // don't talk to strangers
				}
			}
			// todo: buffer incoming messages and execute them
	}

	private void cleanup() {
		try {
			input.close();
			output.close();
		} catch (IOException e) {
			Log.d("Client", "Error closing something");
		}
	}
}

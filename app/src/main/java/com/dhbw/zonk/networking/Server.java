package com.dhbw.zonk.networking;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import android.util.*;

import com.dhbw.zonk.gameData.GameState;

public class Server extends Thread {
	private static Server instance;
	private int port;
	private ServerSocket srvSck = null;
	private LinkedList<ClientWorker> clients = new LinkedList<>();
	private long clientID = 1; // 0 indicates local client = host
	private ServerMessageHandler messageHandler = null;

	private Server() {}

	public static Server getInstance() {
		if (instance == null)
			reset();
		return instance;
	}

	public static void reset() {
		if (instance != null && instance.isAlive())
			instance.requestStop();
		instance = new Server();
	}

	public void run() {
		try {
			srvSck = new ServerSocket(port);

			// start a message handler
			messageHandler = new ServerMessageHandler();
			messageHandler.start();

			// main server loop
			while (true) {
				log("Listening for client");
				try {
					ClientWorker cw = new ClientWorker(srvSck.accept(), clientID);
					clients.add(cw);
					log("Client connected, id " + clientID);
					clientID++;
					cw.start();
				} catch (IOException e) {
					break;
				}
			}
		} catch (IOException e) {
			Log.e("Server", "Error creating ServerSocket: " + e);
		}
	}

	public void setPort(int port) {
		this.port = port;
	}


	public void requestStop() {
		for (ClientWorker client : clients)
			client.requestStop();
		messageHandler.requestStop();
		try {
			if (srvSck != null)
				srvSck.close();
		} catch (IOException e) {
			Log.e("Server", "Error closing ServerSocket: " + e);
		}
	}


	// send a message to all connected clients
	private void distributeMessage(Message message) {
		for (ClientWorker client : clients)
			client.send(message);
	}

	void queueMessage(Message message, ClientWorker origin) {
		messageHandler.put(message, origin);
	}


	synchronized void removeClient(long id) {
		clients.removeIf(client -> (client.getID() == id));
	}


	private void log(String msg) { Log.v("Server", msg); }


	// separate thread that processes all received messages sequentially
	private class ServerMessageHandler extends Thread {
		private BlockingQueue<MessageInfo> messages = new LinkedBlockingQueue<>();

		public void run() {
			while (true) {
				try {
					// read message. this blocks if no messages are queued
					MessageInfo messageInfo = messages.take();
					if (messageInfo instanceof ShutdownMessage)
						break;

					Message message = messageInfo.message;
					ClientWorker cw = messageInfo.clientWorker;
					if (message.isValid(cw.getID())) {
						Server.getInstance().distributeMessage(message);
						message.process(); // process locally
					}
					else
						cw.requestStop();
					// todo: disconnect message for client instead of kicking when an incorrect message is received
					// (incorrect messages are expected to happen)
				} catch (InterruptedException e) {
					// no code should call Thread.interrupt() because this may stop the thread while a message is being handled
					// therefore this code should never be reached but has to be present because InterruptedException is checked
					throw new RuntimeException(e);
				}
			}
		}

		void put(Message message, ClientWorker cw) {
			try {
				messages.put(new MessageInfo(message, cw));
			} catch (InterruptedException e) { // same as above
				throw new RuntimeException(e);
			}
		}

		void requestStop() {
			try {
				messages.put(new ShutdownMessage());
			} catch (InterruptedException e) { // same as above
				throw new RuntimeException(e);
			}
		}


		// simple data structure that includes the ClientWorker
		private class MessageInfo {
			final Message message;
			final ClientWorker clientWorker;

			MessageInfo(Message message, ClientWorker clientWorker) {
				this.message = message;
				this.clientWorker = clientWorker;
			}
		}

		// poison pill
		private class ShutdownMessage extends MessageInfo {
			ShutdownMessage() {
				super(null,null);
			}
		}
	}
}


// handles IO
// one ClientWorker is created for each client
class ClientWorker extends IOThread {
	private final long id;

	ClientWorker(Socket sck, long id) throws IOException {
		this.sck = sck;
		this.id = id;
		this.output = new ObjectOutputStream(sck.getOutputStream());
		this.input = new ObjectInputStream(sck.getInputStream());
	}

	long getID() { return this.id; }

	public void run() {
		if (authenticate())
			mainLoop();
		cleanup();
	}

	private boolean authenticate() {
		try {
			ClientHello authentication = (ClientHello) input.readObject();
			if (authentication.isValid(id)) {
				authentication.process();
				output.writeObject(new GameStateUpdate(GameState.getInstance()));
				return true;
			} else {
				// send error message
				return false;
			}
		} catch (IOException | ClassNotFoundException e) {
			return false;
		}
	}

	private void mainLoop() {
		Log.d("ClientWorker", "Connection established");
		while (!stopRequested())
		{
			try {
				Message message = (Message) input.readObject();
				Server.getInstance().queueMessage(message, this);
			} catch (IOException | ClassNotFoundException e) {
				break; // don't talk to strangers
			}
		}
	}

	private void cleanup() {
		try {
			input.close();
			output.close();
		} catch (IOException e) {
			log("Error closing something");
		}
		Server.getInstance().removeClient(id); // don't keep dead threads around
	}

	private void log(String msg) { Log.v("Thread " + id, msg); }
}


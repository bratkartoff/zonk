package com.dhbw.zonk;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.*;

class Server extends Thread {
	private final int port;
	private ServerSocket srvSck = null;
	private LinkedList<ClientWorker> clients = new LinkedList<>();
	private long clientID = 1; // 0 indicates local client = host
	private final Lobby context;
	private ServerMessageHandler messageHandler = null;

	Server(Lobby context, int port) {
		this.context = context;
		this.port = port;
	}

	public void run() {
		try {
			srvSck = new ServerSocket(port);

			//todo: determine ip address somewhere else
			//get ip address
			//java is not my favorite language
			WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			int ipInt = wm.getConnectionInfo().getIpAddress();
			String ipAddr;
			if (ipInt != 0)
				ipAddr = String.format("%d.%d.%d.%d", ipInt % 256, (ipInt >> 8) % 256, (ipInt >> 16) % 256, (ipInt >> 24) % 256);
			else
				ipAddr = "Unknown";
			//display ip address
			context.runOnUiThread(() -> {
				//this runs on the main thread (ui thread)
				context.displayText(ipAddr);
			});

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

	void stopListening() {
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

	private synchronized void removeThread(long id) {
		clients.removeIf(client -> (client.getID() == id));
	}

	private ServerMessageHandler getMessageHandler() {
		return messageHandler;
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
					else {
						ClientWorker cw = messageInfo.clientWorker;
						cw.setConnectionState(messageInfo.message.process(cw.getConnectionState(), cw.getID()));
					}
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
				super(null,  null);
			}
		}
	}


	// handles IO
	// one ClientWorker is created for each client
	class ClientWorker extends Thread {
		private Socket sck;
		private final long id;
		private ClientState connectionState; // todo: initialize
		private Boolean stop = false;
		private ObjectOutputStream output;
		private ObjectInputStream input;

		ClientWorker(Socket sck, long id) throws IOException {
			this.sck = sck;
			this.id = id;
			this.input = new ObjectInputStream(sck.getInputStream());
			this.output = new ObjectOutputStream(sck.getOutputStream());
		}

		synchronized void requestStop() {
			log("Received exit signal");
			stop = true;
			// Close socket to cause thread to exit
			try {
				sck.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		long getID() { return this.id; }

		public void run() {
			while (true)
			{
				try {
					Message message = (Message) input.readObject();
					getMessageHandler().put(message, this);
				} catch (IOException | ClassNotFoundException e) {
					break; // don't talk to strangers
				}

				if (stopRequested())
					break;
			}
			try {
				input.close();
				output.close();
			} catch (IOException e) {
				log("Error closing something");
			}
			removeThread(id); // don't keep dead threads around
		}

		void setConnectionState(ClientState connectionState) {
			this.connectionState = connectionState;
		}

		ClientState getConnectionState() {
			return connectionState;
		}

		private void log(String msg) { Log.v("Thread " + id, msg); }
		private synchronized Boolean stopRequested() { return stop; }
	}
}

enum ClientState {
	something; //todo
}

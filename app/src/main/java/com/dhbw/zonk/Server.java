package com.dhbw.zonk;

import java.net.*;
import java.io.*;
import java.util.*;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.*;

class Server extends Thread {
	private final int port;
	private ServerSocket srvSck = null;
	private LinkedList<ClientWorker> clients = new LinkedList<>();
	private long clientID = 0;
	private final Lobby context;

	Server(Lobby context, int port) {
		this.context = context;
		this.port = port;
	}

	public void run() {
		try {
			srvSck = new ServerSocket(port);
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
			while (true) {
				log("Listening for client");
				try {
					ClientWorker cw = new ClientWorker(this, srvSck.accept(), clientID);
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
		try {
			if (srvSck != null)
				srvSck.close();
		} catch (IOException e) {
			Log.e("Server", "Error closing ServerSocket: " + e);
		}
	}

	synchronized void send(String msg, long from) {
		for (ClientWorker client : clients) {
			if (client.getID() != from)
				client.print(from + ": " + msg);
		}
	}

	synchronized void stopThread(long id) {
		log("Requesting thread " + id + " to exit");
		ClientWorker client;
		ListIterator<ClientWorker> iter = clients.listIterator();
		while(iter.hasNext()) {
			client = iter.next();
			if (client.getID() == id) {
				log("Found thread " + id + " in clients list");
				client.requestStop();
				iter.remove();
				break;
			}
		}
	}

	synchronized Boolean isValidThreadID(long id) {
		for (ClientWorker client : clients) {
			if (client != null && client.getID() == id)
				return true;
		}
		return false;
	}

	private void log(String msg) { Log.v("Server", msg); }
}


class ClientWorker extends Thread {
	private Socket sck = null;
	private Server srv = null;
	private final long id;
	private Boolean stop = false;
	private PrintStream outputWriter = null;
	private Scanner inputScanner = null;
	
	ClientWorker(Server srv, Socket sck, long id) throws IOException {
		this.srv = srv;
		this.sck = sck;
		this.id = id;
		this.inputScanner = new Scanner(sck.getInputStream());
		this.outputWriter = new PrintStream(sck.getOutputStream());
	}

	synchronized void requestStop() {
		log("Received exit signal");
		stop = true;
		// Close socket to cause thread to exit
		try {
			sck.close();
		} catch (IOException e) {
			log(Log.getStackTraceString(e));
		}
	}
	long getID() { return this.id; }
	void print(String msg) { outputWriter.println(msg); }
	private void printerr(String msg) { print("Error: " + msg); }

	public void run() {
		print("Connected as client " + id);
		String str;
		while (inputScanner.hasNextLine())
		{
			str = inputScanner.nextLine();

			// check for /kick
			// this is only for testing and should not be implemented here, if at all
			String[] strlst = str.split(" ");
			if (strlst[0].equals("/kick")) {
				if (strlst.length != 2) {
					printerr("Incorrect number of arguments for /kill threadID");
				} else {
					Long threadID = null;
					try {
						threadID = Long.valueOf(strlst[1]);
						if (!srv.isValidThreadID(threadID))	{
							printerr("\"" + threadID + "\" is not a valid ID");
						} else {
							log("Requesting thread " + threadID + " to exit");
							srv.stopThread(threadID);
						}
					} catch (NumberFormatException e) {
						printerr("Could not convert \"" + strlst[1] + "\" to type \"long\"");
					}
				}
				continue;
			}

			if (stopRequested())
				break;
			srv.send(str, id);
		}
		inputScanner.close();
		outputWriter.flush();
		log("Exiting");
	}

	private void log(String msg) { Log.v("Thread " + id, msg); }
	private synchronized Boolean stopRequested() { return stop; }
}

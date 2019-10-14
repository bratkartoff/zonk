package com.dhbw.zonk;

import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

class Client extends Thread {
	private final InetAddress remoteHost;
	private final int port;

	Client (InetAddress remoteHost, int port) {
		this.remoteHost = remoteHost;
		this.port = port;
	}

	@Override
	public void run() {
		super.run();
		// bind client socket
		try {
			Socket sock = new Socket(remoteHost, port);
			// todo: communication (not here)
		} catch (IOException e) {
			Log.d("Client", "Look Dave, I can see you're really upset about this... " + e);
			// todo: host does not exists, network unreachable, socket in use, etc.
		}
	}
}

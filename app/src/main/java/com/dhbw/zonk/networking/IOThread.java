package com.dhbw.zonk.networking;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

abstract class IOThread extends Thread {
	Socket sck;
	Boolean stop = false;
	ObjectOutputStream output;
	ObjectInputStream input;

	synchronized void requestStop() {
		stop = true;
		// Close socket to cause thread to exit from main loop in run method (implemented in subclasses)
		try {
			sck.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	void send(Message message) {
		try{
			output.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace(); // todo: proper error handling
		}
	}

	protected synchronized Boolean stopRequested() { return stop; }
}

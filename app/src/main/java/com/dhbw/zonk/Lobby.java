package com.dhbw.zonk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.net.InetAddress;

public class Lobby extends AppCompatActivity {
	private static final int PORT = 20043;

	private Server srv = null;
	private Client client = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lobby);

		// did we get a remote host address to connect to?
		InetAddress remoteHost = (InetAddress) getIntent().getSerializableExtra("remoteHost");
		if (remoteHost != null) {
			// initialize client
			client = new Client(remoteHost, PORT);
			client.start();
		} else {
			// no? start a server
			srv = new Server(this, PORT);
			srv.start();
		}
	}

	public void onDestroy() {
		super.onDestroy();

		if (isHost())
			srv.stopListening();
	}

	public void displayText(String str) {
		LinearLayout linLayout = this.findViewById(R.id.lobbyLayout);
		TextView text = new TextView(this);
		text.setText(str);
		linLayout.addView(text);
	}

	private boolean isHost() {
		return srv != null;
	}
}

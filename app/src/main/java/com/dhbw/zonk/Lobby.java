package com.dhbw.zonk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dhbw.zonk.networking.Client;
import com.dhbw.zonk.networking.Server;

import java.net.InetAddress;

public class Lobby extends AppCompatActivity {
	private static final int port = 20043;

	private Client client = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lobby);

		// did we get a remote host address to connect to?
		InetAddress remoteHost = (InetAddress) getIntent().getSerializableExtra("remoteHost");
		if (remoteHost != null) {
			// initialize client
			client = new Client(remoteHost, port);
			client.start();
		} else {
			// no? start a server
			Server.getInstance().setPort(port);
			Server.getInstance().start();
		}
	}

	public void onDestroy() {
		super.onDestroy();

		if (isHost())
			Server.reset();
	}

	private boolean isHost() {
		return Server.getInstance().isAlive();
	}
}

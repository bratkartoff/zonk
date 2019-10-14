package com.dhbw.zonk;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	// Switch to lobby activity
	public void buttonLobbyClick(View view) {
		Intent i = new Intent(this, Lobby.class);
		startActivity(i);
	}

	public void buttonConnectClick(View view) {
		// get ip address string from text field
		EditText editTextIp = findViewById(R.id.editTextIp);
		String ipString = editTextIp.getText().toString();
		// try to convert it to InetAddress
		InetAddress remoteHost;
		try {
			remoteHost = Inet4Address.getByName(ipString);
		} catch (UnknownHostException e) {
			// Not a valid ip address: do nothing (for now) todo
			return;
		}

		// start lobby
		Intent i = new Intent(this, Lobby.class);
		i.putExtra("remoteHost", remoteHost);
		startActivity(i);
	}
}

package com.dhbw.zonk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Lobby extends AppCompatActivity {
	private Server srv = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lobby);

		// start the server
		srv = new Server(this, 20043);
		srv.start();
	}

	public void onDestroy() {
		super.onDestroy();
		srv.stopListening();
	}

	public void displayText(String str) {
		LinearLayout linLayout = this.findViewById(R.id.lobbyLayout);
		TextView text = new TextView(this);
		text.setText(str);
		linLayout.addView(text);
	}
}

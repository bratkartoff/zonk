package com.dhbw.zonk;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

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
}

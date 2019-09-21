package com.dhbw.zonk;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
	private Server srv = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		srv = new Server(20043);
		srv.start();
	}

	public void onDestroy() {
		super.onDestroy();
		srv.stopListening();
	}
}

package com.dhbw.zonk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Spiel_beitreten extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spiel_beitreten);
    }

    public void onClickButtonConnect(View v) {
        // get ip address string from text field
        EditText editTextIp = findViewById(R.id.accessCode);
        String ipString = editTextIp.getText().toString();
        // try to convert to InetAddress
        InetAddress remoteHost;
        try {
            remoteHost = Inet4Address.getByName(ipString);
        } catch (UnknownHostException e) {
            // todo: error message
            return;
        }

        // start lobby
        Intent intent = new Intent(this, Lobby.class);
        intent.putExtra("remoteHost", remoteHost);
        startActivity(intent);
    }
}

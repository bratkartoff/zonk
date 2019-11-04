package com.dhbw.zonk;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @SuppressLint("ResourceType")
    public void onClickButton (View view) {
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
        Intent intent = new Intent(this, Hauptmenue.class);
        intent.putExtra("remoteHost", remoteHost);
        startActivity(intent);
    }

}

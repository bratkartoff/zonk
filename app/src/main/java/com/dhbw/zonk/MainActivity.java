package com.dhbw.zonk;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
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
    private static MediaPlayer mediaPlayer;

    public static MediaPlayer getMediaPlayer () {
        return mediaPlayer;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.unomusik);
        mediaPlayer.setLooping(true);
    }

    @SuppressLint("ResourceType")
    public void onClickStart (View view) {
        // start lobby
        Intent intent = new Intent(this, Hauptmenue.class);
        startActivity(intent);
    }

}

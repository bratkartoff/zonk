package com.dhbw.zonk;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

import java.io.IOException;

public class Einstellungen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einstellungen);
    }



    public void onToggleClicked(View view) {
        ToggleButton toggleButton = view.findViewById(R.id.toggleButton);
        MediaPlayer mediaPlayer = MainActivity.getMediaPlayer();

        if(((ToggleButton) view).isChecked()) {
            mediaPlayer.seekTo(0);
            mediaPlayer.start();
        } else {
            mediaPlayer.pause();
        }
    }
}

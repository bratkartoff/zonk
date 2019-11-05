package com.dhbw.zonk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ParameterEinstellen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameter_einstellen);
    }

    public void onClickButtonStart(View v) {
        // todo: check parameters and pass to gameState
        // start lobby
        Intent intent = new Intent(this, Lobby.class);
        startActivity(intent);
    }
}

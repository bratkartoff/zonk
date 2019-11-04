package com.dhbw.zonk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Hauptmenue extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hauptmenue);
    }

    public void onClickButton1 (View view) {
        Intent intent = new Intent(this, Spiel_beitreten.class);
        startActivity(intent);
    }
}

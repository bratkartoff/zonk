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

    public void onClickSpielbeitreten(View view) {
        Intent intent = new Intent(this, Spiel_beitreten.class);
        startActivity(intent);
    }

    public void onClickspielgenerieren(View view) {
        Intent intent = new Intent(this, ParameterEinstellen.class);
        startActivity(intent);
    }

    public void onClickspieltest(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void onClickEinstellungen(View view) {
        Intent intent = new Intent(this, Einstellungen.class);
        startActivity(intent);
    }
}

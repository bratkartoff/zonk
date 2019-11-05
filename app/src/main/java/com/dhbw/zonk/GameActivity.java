package com.example.schlonk;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toolbar;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button test;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        test = (Button) findViewById(R.id.test);

    }
    public void addToHand(View view)
    {
        java.lang.String cardType = "c01";
        int tmp = (int)cardType.charAt(0);
        int cardId = Integer.valueOf(String.valueOf(tmp) + String.valueOf(cardType.charAt(1)) + String.valueOf(cardType.charAt(1)));
        LinearLayout scrollview = (LinearLayout) findViewById(R.id.Karten);
        ImageView newCard = new ImageView(this);
        newCard.setImageResource(getResources().getIdentifier(cardType, "drawable",getPackageName()));
        newCard.setId(cardId);
        newCard.setTag(cardType);
        newCard.setLayoutParams(new LayoutParams(300, LayoutParams.WRAP_CONTENT));
        scrollview.addView(newCard);
    }

    public void deleteFromHand(View view) {
        java.lang.String cardType = "c01";
        int tmp = (int)cardType.charAt(0);
        int cardId = Integer.valueOf(String.valueOf(tmp) + String.valueOf(cardType.charAt(1)) + String.valueOf(cardType.charAt(1)));
        LinearLayout scrollview = (LinearLayout) findViewById(R.id.Karten);
        scrollview.removeView(findViewById(cardId));
    }
}


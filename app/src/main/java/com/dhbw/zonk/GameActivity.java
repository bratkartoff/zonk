package com.dhbw.zonk;

import androidx.appcompat.app.AppCompatActivity;

import java.util.*;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toolbar;

import android.app.Activity;
import android.content.ClipData;
import android.graphics.Typeface;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;

import com.dhbw.zonk.gameData.GameState;

public class GameActivity extends AppCompatActivity {
    private List<ImageView> CardViewList = new ArrayList<ImageView>();
    //private List<ImageView> CardstackViewList = new ArrayList<ImageView>(); todo sinnvoll?
    private GameState gameState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if(gameState == null){ //Prevent Runtimeexceptions
            gameState = new GameState();
        }

        //generation of linked StackViews
        //todo add more cardstacks, maybe even dynamically
        ImageView CardStackView = findViewById(R.id.imageViewStack1);
        for(int i = 0; i < gameState.get_count_of_stacks();i++){
            gameState.get_cardstack(i).setCardStackView(CardStackView);
            gameState.get_cardstack(i).getCardStackView().setOnDragListener(new CardDropListener());
            if(gameState.get_cardstack(i).isEmpty()){
                gameState.get_cardstack(i).getCardStackView().setVisibility(View.INVISIBLE);
            }else{
                gameState.get_cardstack(i).getCardStackView().setVisibility(View.VISIBLE);
            }
        }


    }

    private final class CardPickupListener implements OnTouchListener{
        public boolean onTouch(View view, MotionEvent motionEvent){
            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                //Preparing the Drag, no idea what the code does actually...
                ClipData data = ClipData.newPlainText("","");
                DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                //start dragging el cardino
                view.startDragAndDrop(data, shadowBuilder, view, 0); //view.startDrag() is deprecated, maybe it wont work with the new version...
                return true;
            }else{
                return false;
            }
        }
    }

    private class CardDropListener implements OnDragListener{
        public boolean onDrag(View view, DragEvent dragEvent){
            //handle drag events
            switch (dragEvent.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DROP:
                    View droppedView = (View) dragEvent.getLocalState();
                    View destinationView = (View) view;
                    //todo Implement "Movement of card" between Hands and Stacks

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //no action necessary
                    break;
                default:
                    break;
            }
            return true;
        }
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
        newCard.setOnTouchListener(new CardPickupListener());
        scrollview.addView(newCard);
        CardViewList.add(newCard);
    }

    public void deleteFromHand(View view) {
        java.lang.String cardType = "c01";
        int tmp = (int)cardType.charAt(0);
        int cardId = Integer.valueOf(String.valueOf(tmp) + String.valueOf(cardType.charAt(1)) + String.valueOf(cardType.charAt(1)));
        LinearLayout scrollview = (LinearLayout) findViewById(R.id.Karten);
        ImageView Card_to_remove = findViewById(cardId);
        scrollview.removeView(Card_to_remove);
        CardViewList.remove(Card_to_remove);
    }
}


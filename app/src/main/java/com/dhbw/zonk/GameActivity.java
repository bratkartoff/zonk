package com.dhbw.zonk;

import androidx.appcompat.app.AppCompatActivity;

import java.util.*;

import java.util.Random;

import android.media.Image;
import android.os.Bundle;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
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

import android.view.GestureDetector.SimpleOnGestureListener;
import android.util.Log;

import com.dhbw.zonk.gameData.GameState;

public class GameActivity extends AppCompatActivity {
    private List<ImageView> CardViewList = new ArrayList<ImageView>();
    //private List<ImageView> CardstackViewList = new ArrayList<ImageView>(); todo sinnvoll?
    private GameState gameState;
    private static final String DEBUG_TAG = "Velocity";
    private VelocityTracker mVelocityTracker = null;

    ImageView stack_one;
    FrameLayout user_cards;

    private boolean can_drag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("State", "this is a test!");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        stack_one = (ImageView) findViewById(R.id.imageViewStack1);
        stack_one.setOnDragListener(new CardDropListener());
        stack_one.setOnTouchListener(new CardPickupListener());

        user_cards = (FrameLayout) findViewById(R.id.frameLayout);
        user_cards.setOnDragListener(new CardDropListener());

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

    public boolean switch_between_drag_and_scroll(View view){
        can_drag = !can_drag;
        return true;
    }



    private final class CardPickupListener implements OnTouchListener{


        public boolean onTouch(View view, MotionEvent event){
            if(event.getAction() == MotionEvent.ACTION_DOWN && (can_drag || view == stack_one)){ //MotionEvent.ACTION_DOWN might work better
                //Preparing the Drag, no idea what the code does actually...
                ClipData data = ClipData.newPlainText("","");
                DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                //start dragging el cardino
                view.startDragAndDrop(data, shadowBuilder, view, 0);
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
                    if(droppedView == stack_one && destinationView == user_cards){

                        //generation of a random card
                        //java.lang.String cardType = "c01";
                        Random random_gen = new Random();
                        char card_point_1;
                        char card_point_2;
                        char card_point_3;
                        int random_int = random_gen.nextInt(4);
                        switch(random_int){
                            case 0:
                                card_point_1 = 'c';
                                break;
                            case 1:
                                card_point_1 = 'd';
                                break;
                            case 2:
                                card_point_1 = 'h';
                                break;
                            case 3:
                                card_point_1 = 's';
                                break;

                            default: card_point_1 = 'c';
                        }
                        card_point_2 = (char)(random_gen.nextInt(2) +48);
                        if(card_point_2 == 48){
                            card_point_3 = (char) (random_gen.nextInt(9)+1+48);
                        }else{
                            card_point_3 = (char) (random_gen.nextInt(4)+48);
                        }

                        StringBuilder sb = new StringBuilder();
                        sb.append(card_point_1);
                        sb.append(card_point_2);
                        sb.append(card_point_3);
                        String cardType = sb.toString();

                        Log.d("State", "cardType: " + cardType);


                        int tmp = (int)cardType.charAt(0);
                        int cardId = Integer.valueOf(String.valueOf(tmp) + String.valueOf(cardType.charAt(1)) + String.valueOf(cardType.charAt(1)));
                        ImageView newCard = new ImageView(getApplicationContext());
                        newCard.setImageResource(getResources().getIdentifier(cardType, "drawable",getPackageName()));
                        newCard.setId(cardId);
                        newCard.setTag(cardType);
                        newCard.setLayoutParams(new LayoutParams(300, LayoutParams.WRAP_CONTENT));
                        newCard.setOnTouchListener(new CardPickupListener());


                        addToHand(newCard);
                    }else if(droppedView != stack_one && destinationView == stack_one){
                        deleteFromHand(droppedView);
                    }else{
                        return false;
                    }

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

    /*public void addToHand(View view)
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
    }*/

    public void addToHand(ImageView newCard) //todo maybe delete le old version and
    {
        LinearLayout scrollview = (LinearLayout) findViewById(R.id.Karten);
        scrollview.addView(newCard);
        CardViewList.add(newCard);
    }

    public void deleteFromHand(View view) {
        /*java.lang.String cardType = "c01";
        int tmp = (int)cardType.charAt(0);
        int cardId = Integer.valueOf(String.valueOf(tmp) + String.valueOf(cardType.charAt(1)) + String.valueOf(cardType.charAt(1)));
        LinearLayout scrollview = (LinearLayout) findViewById(R.id.Karten);
        ImageView Card_to_remove = findViewById(cardId);
        scrollview.removeView(Card_to_remove);
        CardViewList.remove(Card_to_remove);*/
        LinearLayout scrollview = (LinearLayout) findViewById(R.id.Karten);
        scrollview.removeView(view);
        CardViewList.remove(view);
    }
}


package com.example.android.stumpthetrump;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by mallamace on 4/8/18.
 */

public class Start_dialog extends Dialog {

    public Start_dialog(Context context) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.popup);
    }

    /**
     *
     * @param context - pass in context to display getString()
     * @param scoreVal - get the total score for display in the dialog box
     * @param questionTotal - check to see how many total questions there are
     */
    public void setScore(Context context, int scoreVal, int questionTotal){


        String flavorText;
        TextView setScoreText = (TextView) findViewById(R.id.scoreText);
        setScoreText.setText(scoreVal+"/"+questionTotal);

        if (scoreVal == questionTotal){

            flavorText = context.getResources().getString(R.string.flavorTheDon);

        } else if (scoreVal >= 5){

            flavorText = context.getResources().getString(R.string.flavorHuge);

        } else if (scoreVal >= 2){

            flavorText = context.getResources().getString(R.string.flavorPresidential);

        } else {

            flavorText = context.getResources().getString(R.string.flavorNews);
        }

        TextView setMessageText = (TextView) findViewById(R.id.messageText);
        setMessageText.setText(flavorText);
    }
}



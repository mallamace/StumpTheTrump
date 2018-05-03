package com.example.android.stumpthetrump;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by mallamace on 4/8/18.
 */

public class Start_dialog extends Dialog {



    public Start_dialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        /** 'Window.FEATURE_NO_TITLE' - Used to hide the title */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /** Design the dialog in main.xml file */
        setContentView(R.layout.popup);

        //Button dismissButton = (Button) findViewById(R.id.dismiss);
    }


    /**
     *
     * @param scoreVal - get the total score for display in the dialog box
     * @param questionTotal - check to see how many total questions there are
     */
    public void setScore(int scoreVal, int questionTotal){


        String flavorText;
        TextView setScoreText = (TextView) findViewById(R.id.scoreText);
        setScoreText.setText(scoreVal+"/"+questionTotal);

        if (scoreVal == questionTotal){

            flavorText = "The Don Would be Proud";

        } else if (scoreVal >= 5){

            flavorText = "That's Huuuuge!";

        } else if (scoreVal >= 2){

            flavorText = "Not Quite Presidential Stuff";
        } else {

            flavorText = "Fake News! Try Again!";
            //flavorText = getString(R.string.fakenews);  I CANNOT GET THIS TO WORK, "getString" is not recognized.

        }

        TextView setMessageText = (TextView) findViewById(R.id.messageText);
        setMessageText.setText(flavorText);
    }
}



package com.example.android.stumpthetrump;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    int scoreVal = 0;
    String aSelected = "";
    String qSelected = "";
    View radioButton;
    RadioGroup radioGroup;
    int[][] ids = { {R.id.q4, 0}, {R.id.q9, 0}};
    int questionCount = 0; //track the number of questions answered



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get the spinner from the xml.
        final Spinner dropdown = (Spinner) findViewById(R.id.q3);
        //create a list of items for the spinner.

        Resources res = getResources();
        String[] items = res.getStringArray(R.array.month_array);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);


        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // this was copied code to create a Spinner/Dropdown
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);

                if (pos == 6){ //  Check for correct Answer in position #6

                   Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
                    dropdown.setBackgroundColor(Color.GREEN);

                    scoreVal += 1;
                    questionCount(); // add to question count and check if completed
                    dropdown.setEnabled(false);

                } else if(pos !=0){ //  Check for any incorrect answer except for default position 0

                    Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();
                    dropdown.setBackgroundColor(Color.RED);
                    dropdown.setEnabled(false);
                    questionCount(); // add to question count and check if completed
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }





    /**
     * This is an incredibly long method to determine the values from a array matrix. I honestly count not find a better way to
     * traverse all EditText and capture the resource id. So this was the round about way from endless research. I would really
     * appreciate feedback on this one on how I could have simplified this.
     *
     * Thanks!!!
     *
     * @param view
     */
    public void onTexEditCheck(View view){

        int bId = view.getId();
        Button buttonVal = (Button) findViewById(bId);
        String resId = getResources().getResourceEntryName(bId);

        int row, column;

        for ( row = 0; row < 2; row ++ )
        {
            for(column = 0; column < 1; column++)
            {

                //Toast.makeText(this, ""+ids[row][column], Toast.LENGTH_SHORT).show();

                if(ids[row][0] == R.id.q4 && ids[row][1] != 1 && resId.equals("q4button")){

                    //check answer for q4
                    EditText et = (EditText) findViewById(R.id.q4);
                    String textAnswer = et.getText().toString();
                    textAnswer = textAnswer.toLowerCase();

                    if(textAnswer.equals(""+checkKey(resId))){

                        Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                        et.setBackgroundColor(Color.GREEN);

                        scoreVal += 1;

                    }else{

                        Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
                        et.setBackgroundColor(Color.RED);
                    }

                    et.setEnabled(false);

                    ids[row][1] = 1; //mark that we've set an answer for q4
                }

                if(ids[row][column] == R.id.q9 && ids[row][1] != 1 && resId.equals("q9button")){

                    EditText et = (EditText) findViewById(R.id.q9);
                    String textAnswer = et.getText().toString();
                    textAnswer = textAnswer.toLowerCase();

                    if(textAnswer.equals(""+checkKey(resId))){

                        Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                        et.setBackgroundColor(Color.GREEN);

                        scoreVal += 1;

                    }else{

                        Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
                        et.setBackgroundColor(Color.RED);
                    }

                    et.setEnabled(false);
                    ids[row][1] = 1; //mark that we've set an answer for q9
                }
            }
        }

        buttonVal.setEnabled(false);
        questionCount(); // add to question count and check if completed
    }


    /**
     *
     * @param view
     */
    public void onRadioButtonClicked(View view) {

        int bId = view.getId();
        RadioButton rSelected = (RadioButton) findViewById(bId);
        int gId = ((View) rSelected.getParent()).getId();

        radioGroup  = (RadioGroup) findViewById(gId);
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        radioButton = radioGroup.findViewById(radioButtonID);
        int idx = radioGroup.indexOfChild(radioButton);
        RadioButton r1 = (RadioButton)  radioGroup.getChildAt(idx);
        qSelected = getResources().getResourceEntryName(gId);
        aSelected = r1.getText().toString();

        checkAnswer(qSelected,idx);
    }


    /**
     *
     * @param stringVal
     * @param intVal
     */
    public void checkAnswer (String stringVal, int intVal){


        if(qSelected.equals(stringVal) && intVal == checkKey(stringVal)){

            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            radioButton.setBackgroundColor(Color.GREEN);
            scoreVal += 1;

        } else {

            Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
            radioButton.setBackgroundColor(Color.RED);
        }

        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setEnabled(false);
        }

        questionCount(); // add to question count and check if completed
    }


    /**
     *
     * @param questionVal
     * @return
     */
    public int checkKey(String questionVal){

        if (questionVal.equals("q1")) return 3;
        if (questionVal.equals("q5")) return 2;
        if (questionVal.equals("q6")) return 3;
        if (questionVal.equals("q7")) return 2;
        if (questionVal.equals("q8")) return 1;
        if (questionVal.equals("q10")) return 3;
        if (questionVal.equals("q4button")) return 1;
        if (questionVal.equals("q9button")) return 4;

        return 0;
    }


    /**
     *
     * @param view
     */
    public void onCheckboxButtonClicked(View view) {
        // Is the button now checked?
        CheckBox checkedQ1 = (CheckBox) findViewById(R.id.q2a1);
        CheckBox checkedQ2 = (CheckBox) findViewById(R.id.q2a2);
        CheckBox checkedQ3 = (CheckBox) findViewById(R.id.q2a3);
        CheckBox checkedQ4 = (CheckBox) findViewById(R.id.q2a4);

        if(checkedQ1.isChecked()){

            checkedQ1.setEnabled(false);
            checkedQ1.setBackgroundColor(Color.GREEN);
        }
        if(checkedQ2.isChecked()){

            checkedQ2.setEnabled(false);
            checkedQ2.setBackgroundColor(Color.GREEN);
        }
        if(checkedQ3.isChecked()){

            checkedQ3.setEnabled(false);
            checkedQ3.setBackgroundColor(Color.GREEN);
        }


        if(checkedQ1.isChecked() && checkedQ2.isChecked() && checkedQ3.isChecked()){

            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            checkedQ4.setEnabled(false);
            scoreVal += 1;
            questionCount(); // add to question count and check if completed
        }


        if(checkedQ4.isChecked()){

            checkedQ1.setEnabled(false);
            checkedQ2.setEnabled(false);
            checkedQ3.setEnabled(false);
            checkedQ4.setEnabled(false);
            checkedQ4.setBackgroundColor(Color.RED);
            Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show();

            questionCount(); // add to question count and check if completed

        }
    }


    /**
     *  Once a question has been answered, either correct or incorrect count it, and check for total
     *  If we have reached total questions then create a dialog box and display results with flavor text
     */
    public void questionCount(){

        questionCount += 1;

        if(questionCount == 10){

            Start_dialog start_dialog = new Start_dialog(this);
            start_dialog.setScore(scoreVal, questionCount);
            start_dialog.setTitle("Your Score");
            start_dialog.show();

            //display results
            Toast.makeText(this, "Your Score: "+scoreVal+"/10", Toast.LENGTH_SHORT).show();

        }
    }
}


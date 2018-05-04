package com.example.android.stumpthetrump;


import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    int scoreVal;
    String aSelected = "";
    String qSelected = "";
    View radioButton;
    RadioGroup radioGroup;
    int[][] textQuestionArray = { {R.id.q4, 0}, {R.id.q9, 0}}; //q4 = question #4, q9 = question #9
    //TODO int questionCount; //track the number of questions answered

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get the spinner from the xml.
        final Spinner dropdown = findViewById(R.id.q3);
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

                   Toast.makeText(getApplicationContext(), getString(R.string.correctText), Toast.LENGTH_SHORT).show();
                    dropdown.setBackgroundColor(Color.GREEN);

                    scoreVal += 1;
                    //questionCount(); // add to question count and check if completed
                    dropdown.setEnabled(false);

                } else if(pos !=0){ //  Check for any incorrect answer except for default position 0

                    Toast.makeText(getApplicationContext(), getString(R.string.incorrectText), Toast.LENGTH_SHORT).show();
                    dropdown.setBackgroundColor(Color.RED);
                    dropdown.setEnabled(false);
                    //questionCount(); // add to question count and check if completed
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

        int buttonId = view.getId();
        Button buttonVal = findViewById(buttonId);
        String resId = getResources().getResourceEntryName(buttonId);

        int row, column;

        for ( row = 0; row < 2; row ++ )
        {
            for(column = 0; column < 1; column++)
            {

                if(textQuestionArray[row][0] == R.id.q4 && textQuestionArray[row][1] != 1 && resId.equals("q4button")){

                    //check answer for q4
                    EditText q4EditTextVal = findViewById(R.id.q4);
                    String textAnswer = q4EditTextVal.getText().toString();
                    textAnswer = textAnswer.toLowerCase();

                    if(textAnswer.equals(""+checkKey(resId))){

                        Toast.makeText(this, getString(R.string.correctText), Toast.LENGTH_SHORT).show();
                        q4EditTextVal.setBackgroundColor(Color.GREEN);

                        scoreVal += 1;

                    }else{

                        Toast.makeText(this, getString(R.string.incorrectText), Toast.LENGTH_SHORT).show();
                        q4EditTextVal.setBackgroundColor(Color.RED);
                    }

                    q4EditTextVal.setEnabled(false);

                    textQuestionArray[row][1] = 1; //mark that we've set an answer for q4
                }

                if(textQuestionArray[row][column] == R.id.q9 && textQuestionArray[row][1] != 1 && resId.equals("q9button")){

                    EditText q9EditTextVal = findViewById(R.id.q9);
                    String textAnswer = q9EditTextVal.getText().toString();
                    textAnswer = textAnswer.toLowerCase();

                    if(textAnswer.equals(""+checkKey(resId))){

                        Toast.makeText(this, getString(R.string.correctText), Toast.LENGTH_SHORT).show();
                        q9EditTextVal.setBackgroundColor(Color.GREEN);

                        scoreVal += 1;

                    }else{

                        Toast.makeText(this, getString(R.string.incorrectText), Toast.LENGTH_SHORT).show();
                        q9EditTextVal.setBackgroundColor(Color.RED);
                    }

                    q9EditTextVal.setEnabled(false);
                    textQuestionArray[row][1] = 1; //mark that we've set an answer for q9
                }
            }
        }

        buttonVal.setEnabled(false);
        //questionCount(); // add to question count and check if completed
    }

    /**
     *
     * @param view
     */
    public void onRadioButtonClicked(View view) {

        int radioButtonId = view.getId();
        RadioButton rSelected = findViewById(radioButtonId);
        int radioButtonGroupId = ((View) rSelected.getParent()).getId();

        radioGroup  = findViewById(radioButtonGroupId);
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        radioButton = radioGroup.findViewById(radioButtonID);
        int radioButtonGroupVal = radioGroup.indexOfChild(radioButton);
        RadioButton radioButtonVal = (RadioButton)  radioGroup.getChildAt(radioButtonGroupVal);
        qSelected = getResources().getResourceEntryName(radioButtonGroupId);
        aSelected = radioButtonVal.getText().toString();

        checkAnswer(qSelected,radioButtonGroupVal);
    }

    /**
     *
     * @param stringVal
     * @param intVal
     */
    public void checkAnswer (String stringVal, int intVal){


        if(qSelected.equals(stringVal) && intVal == checkKey(stringVal)){

            Toast.makeText(this, getString(R.string.correctText), Toast.LENGTH_SHORT).show();
            radioButton.setBackgroundColor(Color.GREEN);
            scoreVal += 1;

        } else {

            Toast.makeText(this, getString(R.string.incorrectText), Toast.LENGTH_SHORT).show();
            radioButton.setBackgroundColor(Color.RED);
        }

        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setEnabled(false);
        }

        //questionCount(); // add to question count and check if completed
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
        CheckBox checkedQ1 = findViewById(R.id.q2a1);
        CheckBox checkedQ2 = findViewById(R.id.q2a2);
        CheckBox checkedQ3 = findViewById(R.id.q2a3);
        CheckBox checkedQ4 = findViewById(R.id.q2a4);

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

            Toast.makeText(this, getString(R.string.correctText), Toast.LENGTH_SHORT).show();
            checkedQ4.setEnabled(false);
            scoreVal += 1;
            //questionCount(); // add to question count and check if completed
        }


        if(checkedQ4.isChecked()){

            checkedQ1.setEnabled(false);
            checkedQ2.setEnabled(false);
            checkedQ3.setEnabled(false);
            checkedQ4.setEnabled(false);
            checkedQ4.setBackgroundColor(Color.RED);
            Toast.makeText(this, getString(R.string.incorrectText), Toast.LENGTH_SHORT).show();

            //questionCount(); // add to question count and check if completed

        }
    }


    /**
     *  Once a question has been answered, either correct or incorrect count it, and check for total
     *  If we have reached total questions then create a dialog box and display results with flavor text
     */
    public void questionCount(View view){

        //questionCount += 1;

        //if(questionCount == 10){

            Start_dialog start_dialog = new Start_dialog(this);
            start_dialog.setScore(this, scoreVal, 10); //removed questionCount variable
            start_dialog.setTitle(getString(R.string.yourScoreText));
            start_dialog.show();

            //display results
            Toast.makeText(this, "Your Score: "+scoreVal+"/10", Toast.LENGTH_SHORT).show();

        //}
    }
}


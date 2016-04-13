package com.example.kristjan.mycalc;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //private static final String TAG = "MainActivity";

    // saved instance state keys
    private static final String STATE_X = "x";
    private static final String STATE_Y = "y";
    private static final String STATE_OPERAND = "operand";
    private static final String STATE_BOOLEAN = "operandLastPressed";
    private static final String STATE_TEXTVIEW = "textViewEntryBox";

    // needed variables.
    private TextView textViewEntryBox;
    private String x = "";
    private String y = "";
    private String operand = "";
    private boolean operandLastPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewEntryBox = (TextView) findViewById(R.id.textViewEntryBox);

        if (savedInstanceState != null) {
            operand = savedInstanceState.getString(STATE_OPERAND);
            x = savedInstanceState.getString(STATE_X);
            y = savedInstanceState.getString(STATE_Y);

            operandLastPressed = savedInstanceState.getBoolean(STATE_BOOLEAN);

            textViewEntryBox.setText(savedInstanceState.getString(STATE_TEXTVIEW));
        }
    }

    public void buttonClicked(View view){
        Button button = (Button) view;
        int id = button.getId();

        // If the calc displays an error message, then any button acts as C.
        // Resets everything.
        if (textViewEntryBox.getText().toString().equals("Error") || id == R.id.buttonC) {
            clearInformation();
            return;
        }

        // Block that handles operand buttons..
        if (id == R.id.buttonAdd || id == R.id.buttonSub || id == R.id.buttonMult || id == R.id.buttonDiv || id == R.id.buttonEquals) {
            /*
                if the operand hasn't been set yet and the pressed button is not a ' = ' button,
                whatever is being displayed on the screen will be set as arg X for the operation.
                operation type (operand) will also be set according to the button pressed.
                operandLastPressed boolean set to true so the buttonClicked method knows what to do next.
             */
            if (operand.isEmpty()) {
                if (id != R.id.buttonEquals) {
                    x = textViewEntryBox.getText().toString();
                    operand = button.getText().toString();
                    operandLastPressed = true;
                }

            /*
                in this case the operand and argument X have been set.
             */
            } else {
                if (id == R.id.buttonEquals) {
                    broadcastIntent();
                    operand = "";
                    operandLastPressed = false;
                } else {
                    /*
                        this if block handles a case like this:
                        5 + 5 + 5 +
                        During that case the UI will have broadcasted twice
                        the first broadcast comes after the user has already submitted 5 + 5 and
                        the user presses + again. UI receives the answer from the brain and saves it
                        in the X argument. Since the user pressed +, the new operand is already set too.
                        The second broadcast comes after the user has submitted 5 after the last operation
                        and presses +
                      */
                    if (!operandLastPressed) {
                        broadcastIntent();
                        operandLastPressed = true;
                    }

                    operand = button.getText().toString();
                }
            }

        // block that handles number and decimal point buttons.
        } else {
            /*
                second half of the if-statement is a situation when the last operation done (also button pressed)
                was ' = ' . arg X is the answer of the previous operation, but since ' = ' as a future operation is moot,
                the operand argument is empty. This if block handles a state when the last button pressed was either an
                operation button or the calculator UI is displaying the answer from the last operation and no other button
                has been pressed yet.
                Last operation's answer gets cleared unless the user chooses a new operation straight away after ' = '
             */
            if (operandLastPressed || (!x.isEmpty() && operand.isEmpty())) {
                if (id == R.id.buttonDot) {
                    textViewEntryBox.setText("0.");
                } else {
                    textViewEntryBox.setText(button.getText().toString());
                }

                operandLastPressed = false;
                return;

            /*
               if the screen displays a 0 and button 0 is pressed, nothing happens. If the pressed button is not a
               dot button, the UI display gets emptied. The display gets appended by any number button pressed
               in the end of this big else block.
             */
            } else if (textViewEntryBox.getText().toString().equals("0")) {
                if (id == R.id.button0) {
                    return;
                } else if (id != R.id.buttonDot) {
                    textViewEntryBox.setText("");
                }

            // nothing happens if the display contains a dot and the pressed button is dot button.
            } else if (textViewEntryBox.getText().toString().contains(".") && id == R.id.buttonDot) {
                return;
            }

            textViewEntryBox.append(button.getText().toString());
        }
    }// buttonClicked method

    // makes a new intent and fills it with data so the brain can do the calculations.
    private void broadcastIntent() {
        /*
            whatever the UI displays will be the second argument (Y) in the operation.
          */
        y = textViewEntryBox.getText().toString();

        Intent intent = new Intent();
        intent.setAction("com.example.kristjan.mycalc");
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.putExtra("x", x);
        intent.putExtra("y", y);
        intent.putExtra("operand", operand);

        // sends broadcast and makes a receiver so we can get back the data from the brain
        sendOrderedBroadcast(intent, null, new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String result = getResultData();
                x = result;
                textViewEntryBox.setText(result);
            }
        }, null, Activity.RESULT_OK, null, null);
    }

    // clears the calculator ui from any input data.
    private void clearInformation() {
        textViewEntryBox.setText("0");
        operandLastPressed = false;
        x = "";
        y = "";
        operand = "";
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(STATE_X, x);
        savedInstanceState.putString(STATE_Y, y);
        savedInstanceState.putString(STATE_OPERAND, operand);
        savedInstanceState.putBoolean(STATE_BOOLEAN, operandLastPressed);
        savedInstanceState.putString(STATE_TEXTVIEW, textViewEntryBox.getText().toString());

        super.onSaveInstanceState(savedInstanceState);
    }
}

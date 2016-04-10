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

    private static final String STATE_X = "x";
    private static final String STATE_Y = "y";
    private static final String STATE_OPERAND = "operand";
    private static final String STATE_BOOLEAN = "operandLastPressed";
    private static final String STATE_TEXTVIEW = "textViewEntryBox";

    private boolean operandLastPressed = false;

    private TextView textViewEntryBox;
    private String x;
    private String y;
    private String operand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewEntryBox = (TextView) findViewById(R.id.textViewEntryBox);

        x = "";
        y = "";
        operand = "";

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

        // If the calc displays an error message, then any button acts as C
        if (textViewEntryBox.getText().toString().equals("Error") || id == R.id.buttonC) {
            clearInformation();
            return;
        }

        // Block that handles operands.
        if (id == R.id.buttonAdd || id == R.id.buttonSub || id == R.id.buttonMult || id == R.id.buttonDiv || id == R.id.buttonEquals) {
            x = textViewEntryBox.getText().toString();

            if (!operandIsSet() && id == R.id.buttonEquals) {
                return;
            }

            operand = button.getText().toString();
            operandLastPressed = true;

            if (operandIsSet() && (!operandLastPressed || id == R.id.buttonEquals)) {
                y = textViewEntryBox.getText().toString();

                Intent intent = new Intent();
                intent.setAction("com.example.kristjan.mycalc");
                intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                intent.putExtra("x", x);
                intent.putExtra("y", y);
                intent.putExtra("operand", operand);

                sendOrderedBroadcast(intent, null, new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String result = getResultData();
                        x = result;
                        textViewEntryBox.setText(result);
                    }
                }, null, Activity.RESULT_OK, null, null);

                if (id == R.id.buttonEquals) {
                    operand = "";
                    /*
                    Logically this boolean would have to be true given that the '=' operation was made,
                    but since in this case the operation was used to simply get the answer,
                     */
                    operandLastPressed = false;
                } else {
                    y = "";
                    operand = button.getText().toString();
                }
            }

        // block that handles number and decimal point buttons.
        } else {
            if (operandLastPressed || (xIsSet() && !operandIsSet())) {
                if (id == R.id.buttonDot) {
                    textViewEntryBox.setText("0.");
                } else {
                    textViewEntryBox.setText(button.getText().toString());
                }

                operandLastPressed = false;
                return;

            } else if (textViewEntryBox.getText().toString().equals("0")) {
                if (id == R.id.button0) {
                    return;
                } else if (id != R.id.buttonDot) {
                    textViewEntryBox.setText("");
                }

            } else if (textViewEntryBox.getText().toString().contains(".") && id == R.id.buttonDot) {
                return;

            }

            textViewEntryBox.append(button.getText().toString());
        }
    }

    private void clearInformation() {
        textViewEntryBox.setText("0");
        operandLastPressed = false;
        x = "";
        y = "";
        operand = "";
    }

    private boolean operandIsSet(){
        return operand.length() > 0;
    }

    private boolean yIsSet(){
        return y.length() > 0;
    }

    private boolean xIsSet(){
        return x.length() > 0;
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

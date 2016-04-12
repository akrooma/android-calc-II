package com.example.kristjan.myreceiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.text.DecimalFormat;

public class MyReceiver extends BroadcastReceiver {
    private double x;
    private double y;
    private double resultDouble;
    private String operand;
    private DecimalFormat df;
    private String result;

    private UOW uow;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (isOrderedBroadcast()){
            Bundle extras = intent.getExtras();

            if (extras != null) {
                uow = new UOW(context);

                x = Double.parseDouble(extras.getString("x"));
                y = Double.parseDouble(extras.getString("y"));
                operand = extras.getString("operand");
                df = new DecimalFormat("######.######");

                calculate();

            } else {
                setResultCode(Activity.RESULT_CANCELED);
                return;
            }

            setResultCode(Activity.RESULT_OK);
            setResultData(result);

            uow.updateDatabaseWithNewOperation(x, operand, y, result);
        }
    }

    private void calculate(){
        if (operand.equals("+")) {
            resultDouble = x + y;

        } else if (operand.equals("-")) {
            resultDouble = x - y;

        } else if (operand.equals("*")) {
            resultDouble = x * y;

        } else if (operand.equals("/")) {
            if (y == 0) {
                result = "Error";
                return;

            } else {
                resultDouble = x / y;
            }
        }

        result = df.format(resultDouble);
    }// private void calculate

}// receiver class
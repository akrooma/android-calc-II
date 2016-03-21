package com.example.kristjan.mycalc;

import java.text.DecimalFormat;
import java.util.LinkedList;

/**
 * Created by Kristjan on 08/03/2016.
 */
public class CalculatorEngine {
    //private LinkedList<Double> stack;
    private DecimalFormat df;

    private String operand;
    //private Double x;
    // Double y;

    private double x;
    private double y;

    public CalculatorEngine() {
        //stack = new LinkedList<Double>();
        //x = null;
        //y = null;
        x = 0;
        y = 0;
        //operand = null;
        operand = "";

        df = new DecimalFormat("######.######");
    }

    public String addItem(String input, String operation, boolean anotherOperation){
        if(operand.isEmpty()) {
            x = Double.parseDouble(input);

            if (!operation.equals("=")) {
                operand = operation;
            }

        } else if (anotherOperation) {
            if (!operation.equals("=")) {
                operand = operation;
            }

        } else {
            y = Double.parseDouble(input);
            return calculate(operation);

        }

        return df.format(x);
        //return df.format(stack.getFirst());
    }

    private String calculate(String newOperation) {
        if (newOperation.equals("=")) {
            return calculate("");
        }

        if (operand.equals("+")) {
            x = x + y;

        } else if (operand.equals("-")) {
            x = x - y;

        } else if (operand.equals("*")) {
            x = x * y;

        } else if (operand.equals("/")) {
            if (y == 0) {
                return "Error";

            } else {
                x = x / y;

            }
        }

        operand = newOperation;
        return df.format(x);
    }

    // Erases everything from "memory".
    public void clearCalculator() {
        //x = null;
        //y = null;
        x = 0;
        y = 0;
        //operand = null;
        operand = "";
    }

    // Simple setters and getters.
    public String getOperand(){
        return operand;
    }

    public Double getX(){
        return x;
    }

    public Double getY(){
        return y;
    }

    public void setOperand(String input) {
        operand = input;
    }

    public void setX(Double input){
        x = input;
    }

    public void setY(Double input){
        y = input;
    }
}

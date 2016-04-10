package com.example.kristjan.myreceiver;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Kristjan on 09/04/2016.
 */
public class Operation implements IEntity {
    private long _id;
    private long operandId;
    private double x;
    private double y;
    private String result;
    private long timestamp;

    public Operation() { }

    public Operation(long operandId, double x, double y, String result) {
        setOperandId(operandId);
        setX(x);
        setY(y);
        setResult(result);
        setTimestamp(new Date().getTime());
    }

    public void setId(long id) {
        _id = id;
    }

    public long getId() {
        return _id;
    }

    public void setOperandId(long id) {
        this.operandId = id;
    }

    public long getOperandId() {
        return operandId;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


    @Override
    public String toString() {
        return null;
    }
}

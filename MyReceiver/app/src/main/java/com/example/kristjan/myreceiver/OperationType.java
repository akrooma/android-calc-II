package com.example.kristjan.myreceiver;

/**
 * Created by Kristjan on 09/04/2016.
 */
public class OperationType implements IEntity {
    private long _id;
    private String operand;
    private long lifetimeCounter;

    public OperationType() { }

    public OperationType(String operand, long lifetimeCounter) {
        setOperand(operand);
        setLifetimeCounter(lifetimeCounter);
    }

    public long getId() {
        return _id;
    }

    public void setId(long id) {
        _id = id;
    }

    public String getOperand() {
        return operand;
    }

    public void setOperand(String operand) {
        this.operand = operand.trim();
    }

    public long getLifetimeCounter() {
        return lifetimeCounter;
    }

    public void setLifetimeCounter(long lifetimeCounter) {
        this.lifetimeCounter = lifetimeCounter;
    }

    public void incrementCounter() {
        lifetimeCounter++;
    }

    @Override
    public String toString() {
        return operand;
    }
}

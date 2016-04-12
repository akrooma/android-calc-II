package com.example.kristjan.myreceiver;

/**
 * Created by Kristjan on 10/04/2016.
 */
public class DayStatistic implements IEntity {
    private long _id;
    private long operandId;
    private long dayCounter;
    private long dateInMillis;

    public DayStatistic() { }

    public DayStatistic(long operandId, long dayCounter, long dateInMillis) {
        setOperandId(operandId);
        setDateInMillis(dateInMillis);
        setDayCounter(dayCounter);
    }

    public long getId() {
        return _id;
    }

    public void setId(long id) {
        _id = id;
    }

    public long getOperandId() {
        return operandId;
    }

    public void setOperandId(long operandId) {
        this.operandId = operandId;
    }

    public long getDayCounter() {
        return dayCounter;
    }

    public void setDayCounter(long dayCounter) {
        this.dayCounter = dayCounter;
    }

    public long getDateInMillis() {
        return dateInMillis;
    }

    public void setDateInMillis(Long dateInMillis) {
        this.dateInMillis = dateInMillis;
    }

    public void incrementCounter() {
        dayCounter++;
    }

    /*
    @Override
    public String toString() {
        return null;
    }
    */
}
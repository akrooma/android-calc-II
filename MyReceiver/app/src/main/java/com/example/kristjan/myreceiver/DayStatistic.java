package com.example.kristjan.myreceiver;

/**
 * Created by Kristjan on 10/04/2016.
 */
public class DayStatistic implements IEntity {
    private long _id;
    private long operandId;
    private long dayCounter;
    private long dateStamp;

    public DayStatistic() { }

    public DayStatistic(long operandId, long dayCounter, long dateInMillis) {
        setOperandId(operandId);
        setDateStamp(dateInMillis);
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

    public long getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(Long dateStamp) {
        this.dateStamp = dateStamp;
    }

    public void incrementCounter() {
        dayCounter++;
    }


    public String toString(String operand) {
        return DateUtil.dateStampToString(dateStamp) +
                "\n operand '" + operand + " occurrences: " + dayCounter;
    }

}
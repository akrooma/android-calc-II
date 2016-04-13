package com.example.kristjan.myreceiver;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kristjan on 10/04/2016.
 */
public class DayStatisticRepo extends Repo<DayStatistic> {

    public DayStatisticRepo(SQLiteDatabase database, String tableName, String[] allColumns){
        super(database, tableName, allColumns);
    };

    @Override
    public DayStatistic cursorToEntity(Cursor cursor) {
        DayStatistic dayStatistic = new DayStatistic();

        dayStatistic.setId(cursor.getLong(0));
        dayStatistic.setOperandId(cursor.getLong(1));
        dayStatistic.setDayCounter(cursor.getLong(2));
        dayStatistic.setDateStamp(cursor.getLong(3));

        return dayStatistic;
    }

    @Override
    public ContentValues entityToContentValues(DayStatistic dayStatistic) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MySQLiteHelper.COLUMN_DAYSTATISTICS_OPERANDID, dayStatistic.getOperandId());
        contentValues.put(MySQLiteHelper.COLUMN_DAYSTATISTICS_DAYCOUNTER, dayStatistic.getDayCounter());
        contentValues.put(MySQLiteHelper.COLUMN_DAYSTATISTICS_DATESTAMP, dayStatistic.getDateStamp());

        return contentValues;
    }

    public DayStatistic insertUpdateDayCounter(long operationTypeId, long dateStamp) {
        DayStatistic dayStatistic;

        // Prepare a query to find a database entry with specified operationTypeId and dateStamp(this is a date(year, month and day) in milliseconds).
        Cursor cursor = database.query(tableName,
                allColumns, allColumns[1] + " = "+ operationTypeId + " and " + allColumns[3] + " = " + dateStamp,
                null, null, null, null);

        // If the query found no such entry from the database.
        if (cursor == null || cursor.getCount()<1) {
            // Make a new dayStatistic.
            DayStatistic newDayStatistic = new DayStatistic(operationTypeId, 1, dateStamp);
            dayStatistic = add(newDayStatistic);

            // query found an entry.
        } else {
            // code to increment the dayCounter.
            cursor.moveToFirst();
            dayStatistic = cursorToEntity(cursor);
            dayStatistic.incrementCounter();
            update(dayStatistic);
        }

        return dayStatistic;
    }

    public List<DayStatistic> getForSpecificDate(long dateStamp) {
        List<DayStatistic> dayStatistics = new ArrayList<>();

        // prepare the query
        Cursor cursor = database.query(tableName,
                allColumns, allColumns[3] + " = " + dateStamp, null, null, null, null);

        // start going through the cursor and add the items to the list.
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            DayStatistic dayStatistic = cursorToEntity(cursor);
            dayStatistics.add(dayStatistic);
            cursor.moveToNext();
        }

        cursor.close();

        return dayStatistics;
    }

    public Cursor getDistinctDateStampsCursor() {
        // prepare the query
        Cursor newCursor = database.query(true, tableName, allColumns, null, null, allColumns[3], null, null, null);

        newCursor.moveToFirst();

        return newCursor;
    }
}
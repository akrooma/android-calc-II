package com.example.kristjan.myreceiver;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
        dayStatistic.setDateInMillis(cursor.getLong(3));

        return dayStatistic;
    }

    @Override
    public ContentValues entityToContentValues(DayStatistic dayStatistic) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MySQLiteHelper.COLUMN_DAYSTATISTICS_OPERANDID, dayStatistic.getOperandId());
        contentValues.put(MySQLiteHelper.COLUMN_DAYSTATISTICS_DAYCOUNTER, dayStatistic.getDayCounter());
        contentValues.put(MySQLiteHelper.COLUMN_DAYSTATISTICS_DATEINMILLIS, dayStatistic.getDateInMillis());

        return contentValues;
    }
}

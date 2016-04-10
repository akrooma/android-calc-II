package com.example.kristjan.myreceiver;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Kristjan on 10/04/2016.
 */
public class OperationTypeRepo extends Repo<OperationType> {

    public OperationTypeRepo(SQLiteDatabase database, String tableName, String[] allColumns){
        super(database, tableName, allColumns);
    };

    @Override
    public OperationType cursorToEntity(Cursor cursor) {
        OperationType operationType = new OperationType();

        operationType.setId(cursor.getLong(0));
        operationType.setOperand(cursor.getString(1));
        operationType.setLifetimeCounter(cursor.getLong(2));

        return operationType;
    }

    @Override
    public ContentValues entityToContentValues(OperationType operationType) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MySQLiteHelper.COLUMN_OPERATIONTYPES_OPERAND, operationType.getOperand());
        contentValues.put(MySQLiteHelper.COLUMN_OPERATIONTYPES_LIFETIMECOUNTER, operationType.getLifetimeCounter());

        return contentValues;
    }

}

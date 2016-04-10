package com.example.kristjan.myreceiver;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Kristjan on 10/04/2016.
 */
public class OperationRepo extends Repo<Operation> {

    public OperationRepo(SQLiteDatabase database, String tableName, String[] allColumns){
        super(database, tableName, allColumns);
    };

    @Override
    public Operation cursorToEntity(Cursor cursor) {
        Operation operation = new Operation();

        operation.setId(cursor.getLong(0));
        operation.setOperandId(cursor.getLong(1));
        operation.setX(cursor.getDouble(2));
        operation.setY(cursor.getDouble(3));
        operation.setResult(cursor.getString(4));
        operation.setTimestamp(cursor.getLong(5));

        return operation;
    }

    @Override
    public ContentValues entityToContentValues(Operation operation) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MySQLiteHelper.COLUMN_OPERATIONS_OPERANDID, operation.getOperandId());
        contentValues.put(MySQLiteHelper.COLUMN_OPERATIONS_X, operation.getX());
        contentValues.put(MySQLiteHelper.COLUMN_OPERATIONS_Y, operation.getY());
        contentValues.put(MySQLiteHelper.COLUMN_OPERATIONS_RESULT, operation.getResult());
        contentValues.put(MySQLiteHelper.COLUMN_OPERATIONS_TIMESTAMP, operation.getTimestamp());

        return contentValues;
    }

}

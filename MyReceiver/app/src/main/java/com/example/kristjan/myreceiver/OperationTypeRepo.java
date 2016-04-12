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

    /***
     * Gets an operationType from the database. If the type doesn't exist, it'll be added to the database.
     * @param operation - operation/operationType in String format. Eg "+", "-" etc.
     * @return Desired OperationType object.
     */
    public OperationType getByOperand(String operation) {
        OperationType operationType;
        // query the database for the operationType.
        Cursor cursor = database.query(tableName,
                allColumns, allColumns[1] + " = '" + operation +"'",
                null, null, null, null);

        // if the query found nothing from the database / cursor "is empty".
        if (cursor == null || cursor.getCount()<1) {
            // new operationType will be made and added to the database.
            OperationType newOperationType = new OperationType(operation, 0);
            operationType = add(newOperationType);

            // if an operationType was found.
        } else {
            // the query will always fetch just 1 row, so this will suffice.
            cursor.moveToFirst();
            // cursor will be "turned into" an operationType object.
            operationType = cursorToEntity(cursor);
        }

        return operationType;
    }

}
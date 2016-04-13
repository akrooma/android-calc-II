package com.example.kristjan.myreceiver;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Kristjan on 10/04/2016.
 */
public class UOW {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    private final Context context;

    public OperationRepo operationRepo;
    public OperationTypeRepo operationTypeRepo;
    public DayStatisticRepo dayStatisticRepo;

    public UOW(Context context) {
        this.context = context;

        dbHelper = new MySQLiteHelper(context);
        database = dbHelper.getWritableDatabase();

        operationRepo = new OperationRepo(database, dbHelper.TABLE_OPERATIONS, dbHelper.ALLCOLUMNS_OPERATIONS);
        operationTypeRepo = new OperationTypeRepo(database, dbHelper.TABLE_OPERATIONTYPES, dbHelper.ALLCOLUMNS_OPERATIONTYPES);
        dayStatisticRepo = new DayStatisticRepo(database, dbHelper.TABLE_DAYSTATISTICS, dbHelper.ALLCOLUMNS_DAYSTATISTICS);
    }

    public void DropCreateDatabase() {
        dbHelper.dropCreateDatabase(database);
    }

    public void updateDatabaseWithNewOperation(double x, String operand, double y, String result) {
        OperationType operationType = operationTypeRepo.getByOperand(operand);
        operationType.incrementCounter();
        operationTypeRepo.update(operationType);

        Operation operation = new Operation(operationType.getId(), x, y, result);
        operationRepo.add(operation);

        dayStatisticRepo.insertUpdateDayCounter(operationType.getId(), DateUtil.getDateInMilliseconds(operation.getTimestamp()));
    }
}
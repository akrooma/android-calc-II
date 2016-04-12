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

    public void SeedData() {
        OperationType operationTypeAdd = operationTypeRepo.add(new OperationType("+", 0));
        OperationType operationTypeSubtract = operationTypeRepo.add(new OperationType("-", 0));
        OperationType operationTypeMultiply = operationTypeRepo.add(new OperationType("*", 0));
        OperationType operationTypeDivide = operationTypeRepo.add(new OperationType("/", 0));

        Operation operationAdd = operationRepo.add(new Operation(operationTypeAdd.getId(), 5, 5, "10"));

        Operation operationDivide = new Operation(operationTypeDivide.getId(), 5, 0, "Error");
        // Sun Apr 10 20:13:10 EEST 2016
        operationDivide.setTimestamp(1460308390627L);
        operationRepo.add(operationDivide);

        dayStatisticRepo.add(new DayStatistic(operationTypeAdd.getId(), 1, DateUtil.getDateInMilliseconds(operationAdd.getTimestamp())));
        dayStatisticRepo.add(new DayStatistic(operationTypeDivide.getId(), 1, DateUtil.getDateInMilliseconds(operationDivide.getTimestamp())));
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
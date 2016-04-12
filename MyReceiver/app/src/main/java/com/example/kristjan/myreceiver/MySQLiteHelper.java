package com.example.kristjan.myreceiver;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Kristjan on 10/04/2016.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
    private static final String TAG = MySQLiteHelper.class.getName();
    private final Context context;

    public static final String TABLE_OPERATIONS = "operations";
    public static final String COLUMN_OPERATIONS_ID = "_id";
    public static final String COLUMN_OPERATIONS_OPERANDID = "operandId";
    public static final String COLUMN_OPERATIONS_X = "x";
    public static final String COLUMN_OPERATIONS_Y = "y";
    public static final String COLUMN_OPERATIONS_RESULT = "result";
    public static final String COLUMN_OPERATIONS_TIMESTAMP = "timestamp";
    public static final String[] ALLCOLUMNS_OPERATIONS = {COLUMN_OPERATIONS_ID, COLUMN_OPERATIONS_OPERANDID, COLUMN_OPERATIONS_X,
            COLUMN_OPERATIONS_Y, COLUMN_OPERATIONS_RESULT, COLUMN_OPERATIONS_TIMESTAMP};

    public static final String TABLE_OPERATIONTYPES = "operationTypes";
    public static final String COLUMN_OPERATIONTYPES_ID = "_id";
    public static final String COLUMN_OPERATIONTYPES_OPERAND = "operand";
    public static final String COLUMN_OPERATIONTYPES_LIFETIMECOUNTER = "lifetimeCounter";
    public static final String[] ALLCOLUMNS_OPERATIONTYPES = {COLUMN_OPERATIONTYPES_ID, COLUMN_OPERATIONTYPES_OPERAND,
            COLUMN_OPERATIONTYPES_LIFETIMECOUNTER};

    public static final String TABLE_DAYSTATISTICS = "dayStatistics";
    public static final String COLUMN_DAYSTATISTICS_ID = "_id";
    public static final String COLUMN_DAYSTATISTICS_OPERANDID = "operandId";
    public static final String COLUMN_DAYSTATISTICS_DAYCOUNTER = "dayCounter";
    public static final String COLUMN_DAYSTATISTICS_DATEINMILLIS = "dateInMillis";
    public static final String[] ALLCOLUMNS_DAYSTATISTICS = {COLUMN_DAYSTATISTICS_ID, COLUMN_DAYSTATISTICS_OPERANDID,
            COLUMN_DAYSTATISTICS_DAYCOUNTER, COLUMN_DAYSTATISTICS_DATEINMILLIS};

    private static final String DATABASE_NAME = "operation.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE_OPERATIONS = "create table "
            + TABLE_OPERATIONS + "("
            + COLUMN_OPERATIONS_ID + " integer primary key autoincrement, "
            + COLUMN_OPERATIONS_OPERANDID + " integer, "
            + COLUMN_OPERATIONS_X + " integer, "
            + COLUMN_OPERATIONS_Y + " integer, "
            + COLUMN_OPERATIONS_RESULT + " text not null, "
            + COLUMN_OPERATIONS_TIMESTAMP + " integer );";

    private static final String DATABASE_CREATE_OPERATIONTYPES = "create table "
            + TABLE_OPERATIONTYPES + "("
            + COLUMN_OPERATIONTYPES_ID + " integer primary key autoincrement, "
            + COLUMN_OPERATIONTYPES_OPERAND + " text not null, "
            + COLUMN_OPERATIONTYPES_LIFETIMECOUNTER + " integer);";

    private static final String DATABASE_CREATE_DAYSTATISTICS = "create table "
            + TABLE_DAYSTATISTICS + "("
            + COLUMN_DAYSTATISTICS_ID + " integer primary key autoincrement, "
            + COLUMN_DAYSTATISTICS_OPERANDID + " integer, "
            + COLUMN_DAYSTATISTICS_DAYCOUNTER + " integer, "
            + COLUMN_DAYSTATISTICS_DATEINMILLIS + " integer);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public void dropCreateDatabase(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPERATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPERATIONTYPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAYSTATISTICS);

        db.execSQL(DATABASE_CREATE_OPERATIONS);
        db.execSQL(DATABASE_CREATE_OPERATIONTYPES);
        db.execSQL(DATABASE_CREATE_DAYSTATISTICS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_OPERATIONS);
        db.execSQL(DATABASE_CREATE_OPERATIONTYPES);
        db.execSQL(DATABASE_CREATE_DAYSTATISTICS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG,
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPERATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPERATIONTYPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAYSTATISTICS);
        onCreate(db);
    }

}
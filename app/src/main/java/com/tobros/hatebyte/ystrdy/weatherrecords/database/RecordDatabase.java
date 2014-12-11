package com.tobros.hatebyte.ystrdy.weatherrecords.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by scott on 11/26/14.
 */
public class RecordDatabase extends SQLiteOpenHelper {

    private static final String TYPE_TEXT = " TEXT";
    private static final String TYPE_INTEGER = " integer";
    private static final String TYPE_DOUBLE = " double";
    private static final String TYPE_LONG = " long";
    private static final String TYPE_FLOAT = " float";
    private static final String TYPE_BOOL = " boolean";
    private static final String COMMA_SEP = ", ";
    public static final String DATABASE_NAME = "TemperatureRecords.db";
    public static final int DATA_VERSION = 1;

    private static final String SQL_CREATE_NOW_RECORDS =
                "CREATE TABLE " +
                RecordDescription.NowRecord.TABLE_NAME + " (" +
                RecordDescription.NowRecord._ID + TYPE_INTEGER + " primary key autoincrement" + COMMA_SEP +
                RecordDescription.NowRecord.COLUMN_LATITUDE + TYPE_FLOAT + COMMA_SEP +
                RecordDescription.NowRecord.COLUMN_LONGITUDE + TYPE_FLOAT + COMMA_SEP +
                RecordDescription.NowRecord.COLUMN_DATE + TYPE_LONG + COMMA_SEP +
                RecordDescription.NowRecord.COLUMN_REGION_NAME + TYPE_TEXT + COMMA_SEP +
                RecordDescription.NowRecord.COLUMN_CITY_NAME + TYPE_TEXT + COMMA_SEP +
                RecordDescription.NowRecord.COLUMN_WOEID + TYPE_TEXT + COMMA_SEP +
                RecordDescription.NowRecord.COLUMN_TEMPERATURE + TYPE_FLOAT + COMMA_SEP +
                RecordDescription.NowRecord.COLUMN_IS_FIRST + TYPE_INTEGER + ")";
    private static final String SQL_DELETE_NOW_RECORDS =
                "DROP TABLE IF EXISTS " + RecordDescription.NowRecord.TABLE_NAME;

    private static final String SQL_CREATE_YSTRDY_RECORDS =
                "CREATE TABLE " +
                RecordDescription.YstrdayRecord.TABLE_NAME + " (" +
                RecordDescription.YstrdayRecord.COLUMN_DIFFERENCE + TYPE_FLOAT + COMMA_SEP +
                RecordDescription.YstrdayRecord.COLUMN_DATE + TYPE_LONG + COMMA_SEP +
                RecordDescription.YstrdayRecord.COLUMN_NOW_RECORD_ID + TYPE_INTEGER + ")";
    private static final String SQL_DELETE_YSTRDY_RECORDS =
                "DROP TABLE IF EXISTS " + RecordDescription.YstrdayRecord.TABLE_NAME;

    public RecordDatabase(Context context, String databaseName) {
        super(context, databaseName, null, DATA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_NOW_RECORDS);
        db.execSQL(SQL_CREATE_YSTRDY_RECORDS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_NOW_RECORDS);
        db.execSQL(SQL_DELETE_YSTRDY_RECORDS);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}



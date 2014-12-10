package com.tobros.hatebyte.ystrdy.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by scott on 11/26/14.
 */
public class LocationRecordDbHelper extends SQLiteOpenHelper {

    private static final String TYPE_TEXT = " TEXT";
    private static final String TYPE_INTEGER = " integer";
    private static final String TYPE_DOUBLE = " double";
    private static final String TYPE_LONG = " long";
    private static final String TYPE_FLOAT = " float";
    private static final String TYPE_BOOL = " boolean";


    private static final String COMMA_SEP = ", ";
    public static final String DATABASE_NAME = "LocationRecords.db";
    public static final int DATA_VERSION = 1;

    private static final String SQL_CREATE_RECORDS =
                "CREATE TABLE " +
                LocationRecordContract.LocationRecord.TABLE_NAME + " (" +
                LocationRecordContract.LocationRecord._ID + TYPE_INTEGER + " primary key autoincrement" + COMMA_SEP +
                LocationRecordContract.LocationRecord.COLUMN_LATITUDE + TYPE_DOUBLE + COMMA_SEP +
                LocationRecordContract.LocationRecord.COLUMN_LONGITUDE + TYPE_DOUBLE + COMMA_SEP +
                LocationRecordContract.LocationRecord.COLUMN_DATE + TYPE_LONG + COMMA_SEP +
                LocationRecordContract.LocationRecord.COLUMN_REGION_NAME + TYPE_TEXT + COMMA_SEP +
                LocationRecordContract.LocationRecord.COLUMN_CITY_NAME + TYPE_TEXT + COMMA_SEP +
                LocationRecordContract.LocationRecord.COLUMN_WOEID + TYPE_TEXT + COMMA_SEP +
                LocationRecordContract.LocationRecord.COLUMN_TEMPERATURE + TYPE_FLOAT + COMMA_SEP +
                LocationRecordContract.LocationRecord.COLUMN_IS_FIRST + TYPE_INTEGER + ")";
    private static final String SQL_DELETE_RECORDS =
                "DROP TABLE IF EXISTS " + LocationRecordContract.LocationRecord.TABLE_NAME;

    public LocationRecordDbHelper(Context context, String databaseName) {
        super(context, databaseName, null, DATA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_RECORDS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_RECORDS);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}

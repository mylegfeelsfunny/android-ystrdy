package com.tobros.hatebyte.ystrdy.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;
import java.util.InvalidPropertiesFormatException;

/**
 * Created by scott on 11/26/14.
 */
public class LocationRecordsDbConnector {

    private static final String TAG = "LocationRecordsDbConnector";

    public SQLiteDatabase database;
    public LocationRecordDbHelper databaseHelper;

    public LocationRecordsDbConnector(LocationRecordDbHelper dbHelper) {
        databaseHelper = dbHelper;
    }

    public void open() {
        database = databaseHelper.getWritableDatabase();
    }
    public void close() {
        if (databaseHelper != null) {
            database.close();
        }
    }

    public  void clearDatabase() {
        open();
        databaseHelper.onDowngrade(database, LocationRecordDbHelper.DATA_VERSION, LocationRecordDbHelper.DATA_VERSION+1);
        close();
    }

    public long insertLocationRecord(float latitude, float longitude, Date date) throws InvalidPropertiesFormatException {
        if (latitude == 0 || longitude == 0 || date == null) {
            InvalidPropertiesFormatException e = new InvalidPropertiesFormatException("Check your longitude, latitude and date properties");
            throw e;
        }

        ContentValues values = new ContentValues();
        values.put(LocationRecordContract.LocationRecord.COLUMN_LATITUDE, latitude);
        values.put(LocationRecordContract.LocationRecord.COLUMN_LONGITUDE, longitude);
        values.put(LocationRecordContract.LocationRecord.COLUMN_DATE, date.getTime());

        open();
        long newRowId = database.insert(
                LocationRecordContract.LocationRecord.TABLE_NAME,
                null,
                values
        );
        close();

        return newRowId;
    }
}

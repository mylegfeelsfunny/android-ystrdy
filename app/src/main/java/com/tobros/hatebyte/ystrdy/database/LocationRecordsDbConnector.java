package com.tobros.hatebyte.ystrdy.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

/**
 * Created by scott on 11/26/14.
 */
public class LocationRecordsDbConnector {

    private static final String TAG = "LocationRecordsDbConnector";
    public LocationRecordDbHelper databaseHelper;
    public SQLiteDatabase database;

    public LocationRecordsDbConnector(Context context, String databaseName) {
        databaseHelper = new LocationRecordDbHelper(context, databaseName);
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

    public long insertLocationRecord(float latitude, float longitude, Date date) throws Exception {
        if (latitude == 0 || longitude == 0 || date == null) {
            throw new Exception();
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

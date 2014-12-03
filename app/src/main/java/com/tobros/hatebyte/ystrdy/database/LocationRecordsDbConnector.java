package com.tobros.hatebyte.ystrdy.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tobros.hatebyte.ystrdy.database.YstrRecord.YstrRecord;
import com.tobros.hatebyte.ystrdy.date.YstrDate;

import java.util.ArrayList;
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

    public long insertLocationRecord(float latitude, float longitude, Date date, float temp, String region, Boolean isFirst) throws InvalidPropertiesFormatException {
        if (date == null) {
            InvalidPropertiesFormatException e = new InvalidPropertiesFormatException("Check your longitude, latitude and date properties");
            throw e;
        }

        ContentValues values = new ContentValues();
        values.put(LocationRecordContract.LocationRecord.COLUMN_LATITUDE, latitude);
        values.put(LocationRecordContract.LocationRecord.COLUMN_LONGITUDE, longitude);
        values.put(LocationRecordContract.LocationRecord.COLUMN_DATE, date.getTime());
        values.put(LocationRecordContract.LocationRecord.COLUMN_TEMPERATURE, temp);
        values.put(LocationRecordContract.LocationRecord.COLUMN_REGION_NAME, region);
        values.put(LocationRecordContract.LocationRecord.COLUMN_IS_FIRST, ((isFirst) ? 1 : 0));

        open();
        long newRowId = database.insert(
                LocationRecordContract.LocationRecord.TABLE_NAME,
                null,
                values
        );
        close();

        return newRowId;
    }

    public YstrRecord getRecordById(long id) {
        String[] projection = {
                LocationRecordContract.LocationRecord.COLUMN_LATITUDE,
                LocationRecordContract.LocationRecord.COLUMN_LONGITUDE,
                LocationRecordContract.LocationRecord.COLUMN_DATE,
                LocationRecordContract.LocationRecord.COLUMN_TEMPERATURE,
                LocationRecordContract.LocationRecord.COLUMN_REGION_NAME,
                LocationRecordContract.LocationRecord.COLUMN_IS_FIRST,
                LocationRecordContract.LocationRecord._ID
        };
        String selectStatement = LocationRecordContract.LocationRecord._ID + " = ?";
        String[] selectArgs = { id + "" };

        open();
        Cursor c = database.query(
                LocationRecordContract.LocationRecord.TABLE_NAME,
                projection,
                selectStatement,
                selectArgs,
                null,
                null,
                null
        );
        c.moveToFirst();
        YstrRecord ystrRecord = new YstrRecord(c);
        close();
        return ystrRecord;
    }

    public YstrRecord getClosestRecordFromYstrdy() {
        Date ystrdy = new Date();
        int twentyFourHours = (24 * 60 * 60 + 1) * 1000;
        ystrdy.setTime(ystrdy.getTime() - twentyFourHours);

        String[] projection = {
                LocationRecordContract.LocationRecord.COLUMN_LATITUDE,
                LocationRecordContract.LocationRecord.COLUMN_LONGITUDE,
                LocationRecordContract.LocationRecord.COLUMN_DATE,
                LocationRecordContract.LocationRecord.COLUMN_TEMPERATURE,
                LocationRecordContract.LocationRecord.COLUMN_REGION_NAME,
                LocationRecordContract.LocationRecord.COLUMN_IS_FIRST,
                LocationRecordContract.LocationRecord._ID
        };
        String selectStatement = "abs("+ystrdy.getTime()+" - date) < " + twentyFourHours;
        String orderBy = "abs("+ystrdy.getTime()+" - date) ASC";

        open();
        Cursor c = database.query(
                LocationRecordContract.LocationRecord.TABLE_NAME,
                projection,
                selectStatement,
                null,
                null,
                null,
                orderBy
        );
        c.moveToFirst();
        YstrRecord ystrRecord = new YstrRecord(c);
        close();
        return ystrRecord;
    }

    public YstrRecord getEarliestRecord() {
        String[] projection = {
                LocationRecordContract.LocationRecord.COLUMN_LATITUDE,
                LocationRecordContract.LocationRecord.COLUMN_LONGITUDE,
                LocationRecordContract.LocationRecord.COLUMN_DATE,
                LocationRecordContract.LocationRecord.COLUMN_TEMPERATURE,
                LocationRecordContract.LocationRecord.COLUMN_REGION_NAME,
                LocationRecordContract.LocationRecord.COLUMN_IS_FIRST,
                LocationRecordContract.LocationRecord._ID
        };
        String orderBy = "date ASC";

        open();
        Cursor c = database.query(
                LocationRecordContract.LocationRecord.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                orderBy,
                "1"
        );

        c.moveToFirst();
        YstrRecord ystrRecord = new YstrRecord(c);
        close();
        return ystrRecord;
    }

}

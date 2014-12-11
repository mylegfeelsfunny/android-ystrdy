package com.tobros.hatebyte.ystrdy.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tobros.hatebyte.ystrdy.database.YstrRecord.YstrRecord;
import com.tobros.hatebyte.ystrdy.date.YstrDate;

import java.util.Date;
import java.util.InvalidPropertiesFormatException;

/**
 * Created by scott on 11/26/14.
 */
public class NowRecordsDbConnector {

    private static final String TAG = "LocationRecordsDbConnector";

    public SQLiteDatabase database;
    public NowRecordDbHelper databaseHelper;

    public NowRecordsDbConnector(NowRecordDbHelper dbHelper) {
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
        databaseHelper.onDowngrade(database, NowRecordDbHelper.DATA_VERSION, NowRecordDbHelper.DATA_VERSION+1);
        close();
    }

    public long insertLocationRecord(double latitude, double longitude, Date date, float temp, String region, String city, String woeid, Boolean isFirst) throws InvalidPropertiesFormatException {
        if (date == null) {
            InvalidPropertiesFormatException e = new InvalidPropertiesFormatException("Check your longitude, latitude and date properties");
            throw e;
        }

        ContentValues values = new ContentValues();
        values.put(NowRecordContract.NowRecord.COLUMN_LATITUDE, latitude);
        values.put(NowRecordContract.NowRecord.COLUMN_LONGITUDE, longitude);
        values.put(NowRecordContract.NowRecord.COLUMN_DATE, date.getTime());
        values.put(NowRecordContract.NowRecord.COLUMN_TEMPERATURE, temp);
        values.put(NowRecordContract.NowRecord.COLUMN_REGION_NAME, region);
        values.put(NowRecordContract.NowRecord.COLUMN_CITY_NAME, city);
        values.put(NowRecordContract.NowRecord.COLUMN_WOEID, woeid);
        values.put(NowRecordContract.NowRecord.COLUMN_IS_FIRST, ((isFirst) ? 1 : 0));

        open();
        long newRowId = database.insert(
                NowRecordContract.NowRecord.TABLE_NAME,
                null,
                values
        );
        close();

        return newRowId;
    }

    public YstrRecord getRecordById(long id) {
        String[] projection = {
                NowRecordContract.NowRecord.COLUMN_LATITUDE,
                NowRecordContract.NowRecord.COLUMN_LONGITUDE,
                NowRecordContract.NowRecord.COLUMN_DATE,
                NowRecordContract.NowRecord.COLUMN_TEMPERATURE,
                NowRecordContract.NowRecord.COLUMN_REGION_NAME,
                NowRecordContract.NowRecord.COLUMN_CITY_NAME,
                NowRecordContract.NowRecord.COLUMN_WOEID,
                NowRecordContract.NowRecord.COLUMN_IS_FIRST,
                NowRecordContract.NowRecord._ID
        };
        String selectStatement = NowRecordContract.NowRecord._ID + " = ?";
        String[] selectArgs = { id + "" };

        open();
        Cursor c = database.query(
                NowRecordContract.NowRecord.TABLE_NAME,
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
                NowRecordContract.NowRecord.COLUMN_LATITUDE,
                NowRecordContract.NowRecord.COLUMN_LONGITUDE,
                NowRecordContract.NowRecord.COLUMN_DATE,
                NowRecordContract.NowRecord.COLUMN_TEMPERATURE,
                NowRecordContract.NowRecord.COLUMN_REGION_NAME,
                NowRecordContract.NowRecord.COLUMN_CITY_NAME,
                NowRecordContract.NowRecord.COLUMN_WOEID,
                NowRecordContract.NowRecord.COLUMN_IS_FIRST,
                NowRecordContract.NowRecord._ID
        };
        String selectStatement = "abs("+ystrdy.getTime()+" - date) < " + twentyFourHours;
        String orderBy = "abs("+ystrdy.getTime()+" - date) ASC";

        open();
        Cursor c = database.query(
                NowRecordContract.NowRecord.TABLE_NAME,
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
                NowRecordContract.NowRecord.COLUMN_LATITUDE,
                NowRecordContract.NowRecord.COLUMN_LONGITUDE,
                NowRecordContract.NowRecord.COLUMN_DATE,
                NowRecordContract.NowRecord.COLUMN_TEMPERATURE,
                NowRecordContract.NowRecord.COLUMN_REGION_NAME,
                NowRecordContract.NowRecord.COLUMN_CITY_NAME,
                NowRecordContract.NowRecord.COLUMN_WOEID,
                NowRecordContract.NowRecord.COLUMN_IS_FIRST,
                NowRecordContract.NowRecord._ID
        };
        String orderBy = "date ASC";

        open();
        Cursor c = database.query(
                NowRecordContract.NowRecord.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                orderBy,
                "1"
        );
        YstrRecord ystrRecord;
        if (c.getCount()== 0) {
            ystrRecord = null;
        } else {
            c.moveToFirst();
            ystrRecord = new YstrRecord(c);
        }
        close();
        return ystrRecord;
    }

    public int numRecords() {
        String[] projection = {
                NowRecordContract.NowRecord._ID
        };
        open();
        Cursor c = database.query(
                NowRecordContract.NowRecord.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        int count = c.getCount();
        close();
        return count;
    }

    public void deleteExpiredRecords() {
        open();
        Date now = new Date();
        String whereString = YstrDate.threeDayTime() + " + date < " + now.getTime();
        database.delete(NowRecordContract.NowRecord.TABLE_NAME, whereString, null);
        close();
    }

}

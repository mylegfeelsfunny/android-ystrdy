package com.tobros.hatebyte.ystrdy.database.YstrRecord;

import android.database.Cursor;

import com.tobros.hatebyte.ystrdy.database.LocationRecordContract;

import java.util.Date;

/**
 * Created by scott on 12/1/14.
 */
public class YstrRecord {

    public double latitude;
    public double longitude;
    public Date date;
    public float temperature;
    public boolean isFirst;
    public String regionName;

    public YstrRecord(Cursor c) {
        latitude = c.getDouble(c.getColumnIndex(LocationRecordContract.LocationRecord.COLUMN_LATITUDE));
        longitude = c.getDouble(c.getColumnIndex(LocationRecordContract.LocationRecord.COLUMN_LONGITUDE));
        temperature = c.getFloat(c.getColumnIndex(LocationRecordContract.LocationRecord.COLUMN_TEMPERATURE));
        regionName = c.getString(c.getColumnIndex(LocationRecordContract.LocationRecord.COLUMN_REGION_NAME));
        date = new Date(c.getLong(c.getColumnIndex(LocationRecordContract.LocationRecord.COLUMN_DATE)));
        int isFirstInt = c.getInt(c.getColumnIndex(LocationRecordContract.LocationRecord.COLUMN_IS_FIRST));
        isFirst = (isFirstInt == 1);
    }
}

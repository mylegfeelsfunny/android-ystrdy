package com.tobros.hatebyte.ystrdy.database.YstrRecord;

import android.database.Cursor;

import com.tobros.hatebyte.ystrdy.database.NowRecordContract;

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
    public String woeid;
    public String cityName;

    public YstrRecord(Cursor c) {
        latitude = c.getDouble(c.getColumnIndex(NowRecordContract.NowRecord.COLUMN_LATITUDE));
        longitude = c.getDouble(c.getColumnIndex(NowRecordContract.NowRecord.COLUMN_LONGITUDE));
        temperature = c.getFloat(c.getColumnIndex(NowRecordContract.NowRecord.COLUMN_TEMPERATURE));
        regionName = c.getString(c.getColumnIndex(NowRecordContract.NowRecord.COLUMN_REGION_NAME));
        cityName = c.getString(c.getColumnIndex(NowRecordContract.NowRecord.COLUMN_CITY_NAME));
        woeid = c.getString(c.getColumnIndex(NowRecordContract.NowRecord.COLUMN_WOEID));
        date = new Date(c.getLong(c.getColumnIndex(NowRecordContract.NowRecord.COLUMN_DATE)));
        int isFirstInt = c.getInt(c.getColumnIndex(NowRecordContract.NowRecord.COLUMN_IS_FIRST));
        isFirst = (isFirstInt == 1);
    }

    public YstrRecord() {

    }

}

package com.tobros.hatebyte.ystrdy.weatherrecords.gateway;

import android.content.ContentValues;
import android.database.Cursor;

import com.tobros.hatebyte.ystrdy.weatherrecords.database.RecordDescription;
import com.tobros.hatebyte.ystrdy.weatherrecords.entity.NowRecordEntity;

import java.util.Date;

/**
 * Created by scott on 12/1/14.
 */
public class NowRecordEG {

    public NowRecordEntity record;
    public NowRecordEG() {}
    public NowRecordEG(Cursor c) {
        if (c.getCount()== 0) {
            return;
        }
        c.moveToFirst();
        record = new NowRecordEntity();
        record.latitude = c.getFloat(c.getColumnIndex(RecordDescription.NowRecord.COLUMN_LATITUDE));
        record.longitude = c.getFloat(c.getColumnIndex(RecordDescription.NowRecord.COLUMN_LONGITUDE));
        record.temperature = c.getFloat(c.getColumnIndex(RecordDescription.NowRecord.COLUMN_TEMPERATURE));
        record.regionName = c.getString(c.getColumnIndex(RecordDescription.NowRecord.COLUMN_REGION_NAME));
        record.cityName = c.getString(c.getColumnIndex(RecordDescription.NowRecord.COLUMN_CITY_NAME));
        record.woeid = c.getString(c.getColumnIndex(RecordDescription.NowRecord.COLUMN_WOEID));
        record.date = new Date(c.getLong(c.getColumnIndex(RecordDescription.NowRecord.COLUMN_DATE)));
        int isFirstInt = c.getInt(c.getColumnIndex(RecordDescription.NowRecord.COLUMN_IS_FIRST));
        record.isFirst = (isFirstInt == 1);
    }

    public ContentValues contentValues() {
        ContentValues values = new ContentValues();
        values.put(RecordDescription.NowRecord.COLUMN_LATITUDE, record.latitude);
        values.put(RecordDescription.NowRecord.COLUMN_LONGITUDE, record.longitude);
        values.put(RecordDescription.NowRecord.COLUMN_TEMPERATURE, record.temperature);
        values.put(RecordDescription.NowRecord.COLUMN_REGION_NAME, record.regionName);
        values.put(RecordDescription.NowRecord.COLUMN_CITY_NAME, record.cityName);
        values.put(RecordDescription.NowRecord.COLUMN_WOEID, record.woeid);
        values.put(RecordDescription.NowRecord.COLUMN_DATE, record.date.getTime());
        values.put(RecordDescription.NowRecord.COLUMN_IS_FIRST, ((record.isFirst) ? 1 : 0));
        return values;
    }

    public static String[] projection() {
        return new String[]{
            RecordDescription.NowRecord.COLUMN_LATITUDE,
            RecordDescription.NowRecord.COLUMN_LONGITUDE,
            RecordDescription.NowRecord.COLUMN_DATE,
            RecordDescription.NowRecord.COLUMN_TEMPERATURE,
            RecordDescription.NowRecord.COLUMN_REGION_NAME,
            RecordDescription.NowRecord.COLUMN_CITY_NAME,
            RecordDescription.NowRecord.COLUMN_WOEID,
            RecordDescription.NowRecord.COLUMN_IS_FIRST,
            RecordDescription.NowRecord._ID
        };
    }

}

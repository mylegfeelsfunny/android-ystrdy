package com.tobros.hatebyte.ystrdy.weatherrecords.gateway;

import android.content.ContentValues;
import android.database.Cursor;

import com.tobros.hatebyte.ystrdy.weatherrecords.database.RecordDescription;
import com.tobros.hatebyte.ystrdy.weatherrecords.entity.NowRecordEntity;

import java.util.Date;
import java.util.InvalidPropertiesFormatException;

/**
 * Created by scott on 12/1/14.
 */
public class NowRecordEG {

    private NowRecordEntity entity;
    public NowRecordEG() {}

    public NowRecordEG(Cursor c) {
        if (c.getCount()== 0) {
            return;
        }
        c.moveToFirst();

        entity = new NowRecordEntity();
        entity.latitude = c.getFloat(c.getColumnIndex(RecordDescription.NowRecord.COLUMN_LATITUDE));
        entity.longitude = c.getFloat(c.getColumnIndex(RecordDescription.NowRecord.COLUMN_LONGITUDE));
        entity.temperature = c.getFloat(c.getColumnIndex(RecordDescription.NowRecord.COLUMN_TEMPERATURE));
        entity.regionName = c.getString(c.getColumnIndex(RecordDescription.NowRecord.COLUMN_REGION_NAME));
        entity.cityName = c.getString(c.getColumnIndex(RecordDescription.NowRecord.COLUMN_CITY_NAME));
        entity.woeid = c.getString(c.getColumnIndex(RecordDescription.NowRecord.COLUMN_WOEID));
        entity.date = new Date(c.getLong(c.getColumnIndex(RecordDescription.NowRecord.COLUMN_DATE)));
        int isFirstInt = c.getInt(c.getColumnIndex(RecordDescription.NowRecord.COLUMN_IS_FIRST));
        entity.isFirst = (isFirstInt == 1);
    }

    public void setEntity(NowRecordEntity r) throws InvalidPropertiesFormatException{
        if (r.date == null) {
            throw new InvalidPropertiesFormatException("There is no date associated with this NowRecordEntity");
        }
        entity = r;
    }

    public NowRecordEntity getEntity() {
        return entity;
    }

    public ContentValues contentValues() {
        ContentValues values = new ContentValues();
        values.put(RecordDescription.NowRecord.COLUMN_LATITUDE, entity.latitude);
        values.put(RecordDescription.NowRecord.COLUMN_LONGITUDE, entity.longitude);
        values.put(RecordDescription.NowRecord.COLUMN_TEMPERATURE, entity.temperature);
        values.put(RecordDescription.NowRecord.COLUMN_REGION_NAME, entity.regionName);
        values.put(RecordDescription.NowRecord.COLUMN_CITY_NAME, entity.cityName);
        values.put(RecordDescription.NowRecord.COLUMN_WOEID, entity.woeid);
        values.put(RecordDescription.NowRecord.COLUMN_DATE, entity.date.getTime());
        values.put(RecordDescription.NowRecord.COLUMN_IS_FIRST, ((entity.isFirst) ? 1 : 0));
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

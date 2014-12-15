package com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entitygateway;

import android.content.ContentValues;
import android.database.Cursor;

import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entity.RecordEntity;

import java.util.Date;
import java.util.InvalidPropertiesFormatException;

/**
 * NowRecordEntityGateway
 * Created by scott on 12/1/14.
 */
public class RecordEG extends AbstractEntityGateway {

    public static final String TABLE_NAME                           = "nowrecords";
    public static final String COLUMN_LATITUDE                      = "latitude";
    public static final String COLUMN_LONGITUDE                     = "longitude";
    public static final String COLUMN_DATE                          = "date";
    public static final String COLUMN_TEMPERATURE                   = "temperature";
    public static final String COLUMN_REGION_NAME                   = "region_name";
    public static final String COLUMN_CITY_NAME                     = "city_name";
    public static final String COLUMN_WOEID                         = "woeid";
    public static final String COLUMN_ID                            = "_id";

    private RecordEntity entity;
    public RecordEG() {}
    public RecordEG(Cursor c) {
        mapFromCursor(c);
    }

    @Override
    public void setEntity(Object e) throws InvalidPropertiesFormatException {
        RecordEntity re = (RecordEntity)e;
        if (re.date == null) {
            throw new InvalidPropertiesFormatException("There is no date associated with this NowRecordEntity");
        }
        entity = re;
    }

    @Override
    public RecordEntity getEntity() {
        return entity;
    }

    @Override
    public void mapFromCursor(Cursor c) {
        if (c.getCount()== 0) {
            return;
        }
        c.moveToFirst();

        entity = new RecordEntity();
        entity.latitude = c.getFloat(c.getColumnIndex(RecordEG.COLUMN_LATITUDE));
        entity.longitude = c.getFloat(c.getColumnIndex(RecordEG.COLUMN_LONGITUDE));
        entity.temperature = c.getFloat(c.getColumnIndex(RecordEG.COLUMN_TEMPERATURE));
        entity.regionName = c.getString(c.getColumnIndex(RecordEG.COLUMN_REGION_NAME));
        entity.cityName = c.getString(c.getColumnIndex(RecordEG.COLUMN_CITY_NAME));
        entity.woeid = c.getString(c.getColumnIndex(RecordEG.COLUMN_WOEID));
        entity.date = new Date(c.getLong(c.getColumnIndex(RecordEG.COLUMN_DATE)));
        entity.id = c.getInt(c.getColumnIndex(RecordEG.COLUMN_ID));
    }

    @Override
    public ContentValues contentValues() {
        ContentValues values = new ContentValues();
        values.put(RecordEG.COLUMN_LATITUDE, entity.latitude);
        values.put(RecordEG.COLUMN_LONGITUDE, entity.longitude);
        values.put(RecordEG.COLUMN_TEMPERATURE, entity.temperature);
        values.put(RecordEG.COLUMN_REGION_NAME, entity.regionName);
        values.put(RecordEG.COLUMN_CITY_NAME, entity.cityName);
        values.put(RecordEG.COLUMN_WOEID, entity.woeid);
        values.put(RecordEG.COLUMN_DATE, entity.date.getTime());
        return values;
    }

    public static String[] projection() {
        return new String[]{
                RecordEG.COLUMN_LATITUDE,
                RecordEG.COLUMN_LONGITUDE,
                RecordEG.COLUMN_DATE,
                RecordEG.COLUMN_TEMPERATURE,
                RecordEG.COLUMN_REGION_NAME,
                RecordEG.COLUMN_CITY_NAME,
                RecordEG.COLUMN_WOEID,
                RecordEG.COLUMN_ID
        };
    }

}

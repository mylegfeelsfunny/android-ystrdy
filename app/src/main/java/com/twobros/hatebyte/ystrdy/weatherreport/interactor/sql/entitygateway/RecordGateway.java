package com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.entitygateway;

import android.content.ContentValues;
import android.database.Cursor;

import com.twobros.hatebyte.ystrdy.date.YstrDate;
import com.twobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;

import java.util.Date;
import java.util.InvalidPropertiesFormatException;

/**
 * NowRecordEntityGateway
 * Created by scott on 12/1/14.
 */
public class RecordGateway extends AbstractEntityGateway {

    public static final String TABLE_NAME                           = "record";
    public static final String COLUMN_LATITUDE                      = "latitude";
    public static final String COLUMN_LONGITUDE                     = "longitude";
    public static final String COLUMN_DATE                          = "date";
    public static final String COLUMN_TEMPERATURE                   = "temperature";
    public static final String COLUMN_REGION_NAME                   = "region_name";
    public static final String COLUMN_CITY_NAME                     = "city_name";
    public static final String COLUMN_WOEID                         = "woeid";
    public static final String COLUMN_ID                            = "_id";

    public static String[] projectionMap = new String[]{
            RecordGateway.COLUMN_LATITUDE,
            RecordGateway.COLUMN_LONGITUDE,
            RecordGateway.COLUMN_DATE,
            RecordGateway.COLUMN_TEMPERATURE,
            RecordGateway.COLUMN_REGION_NAME,
            RecordGateway.COLUMN_CITY_NAME,
            RecordGateway.COLUMN_WOEID,
            RecordGateway.COLUMN_ID
    };

    private RecordEntity entity;
    public RecordGateway() {
        tableName = RecordGateway.TABLE_NAME;
        projection = RecordGateway.projectionMap;
    }

    public RecordGateway(Cursor c) {
        mapFromCursor(c);
    }

    @Override
    public void setEntity(Object e) {
        assert(e.getClass().isInstance(RecordEntity.class) || e instanceof RecordEntity);
        RecordEntity re = (RecordEntity)e;
        entity = re;
    }

    @Override
    public RecordEntity getEntity() {
        return entity;
    }

    @Override
    public void mapFromCursor(Cursor c) {
        if (c.getColumnCount() == 0) {
            return;
        }

        entity = new RecordEntity();
        entity.location.setLatitude(c.getFloat(c.getColumnIndex(RecordGateway.COLUMN_LATITUDE)));
        entity.location.setLongitude(c.getFloat(c.getColumnIndex(RecordGateway.COLUMN_LONGITUDE)));
        entity.temperature = c.getFloat(c.getColumnIndex(RecordGateway.COLUMN_TEMPERATURE));
        entity.regionName = c.getString(c.getColumnIndex(RecordGateway.COLUMN_REGION_NAME));
        entity.cityName = c.getString(c.getColumnIndex(RecordGateway.COLUMN_CITY_NAME));
        entity.woeid = c.getString(c.getColumnIndex(RecordGateway.COLUMN_WOEID));
        entity.date = new Date(c.getLong(c.getColumnIndex(RecordGateway.COLUMN_DATE)));
        entity.id = c.getInt(c.getColumnIndex(RecordGateway.COLUMN_ID));
    }

    @Override
    public ContentValues contentValues() {
        ContentValues values = new ContentValues();
        values.put(RecordGateway.COLUMN_LATITUDE, entity.location.getLatitude());
        values.put(RecordGateway.COLUMN_LONGITUDE, entity.location.getLongitude());
        values.put(RecordGateway.COLUMN_TEMPERATURE, entity.temperature);
        values.put(RecordGateway.COLUMN_REGION_NAME, entity.regionName);
        values.put(RecordGateway.COLUMN_CITY_NAME, entity.cityName);
        values.put(RecordGateway.COLUMN_WOEID, entity.woeid);
        values.put(RecordGateway.COLUMN_DATE, entity.date.getTime());
        return values;
    }

    @Override
    public Boolean isValid() {
        if (entity == null) {
            return false;
        }
        if (entity.date == null) {
            return false;
        }
        return true;
    }

    public long save() throws InvalidPropertiesFormatException {
        reset();
        long id = sqlDatabaseEGI.insert(this);
        return id;
    }

    public int numRecords() {
        reset();
        projection = new String[]{
            RecordGateway.COLUMN_ID
        };

        return sqlDatabaseEGI.count(this);
    }

    public RecordEntity getClosestRecordFromYstrdy() {
        reset();
        Date ystrdy = new Date();
        int twentyFourHours = (24 * 60 * 60 + 1) * 1000;
        ystrdy.setTime(ystrdy.getTime() - twentyFourHours);

        projection = RecordGateway.projectionMap;
        selectString = "abs("+ystrdy.getTime()+" - date) < " + twentyFourHours;
        orderBy = "abs("+ystrdy.getTime()+" - date) ASC";

        return (RecordEntity) sqlDatabaseEGI.get(this).getEntity();
    }

    public RecordEntity getEarliestRecord() {
        reset();
        projection = RecordGateway.projectionMap;
        orderBy = "date ASC";
        limit = "1";

        return (RecordEntity) sqlDatabaseEGI.get(this).getEntity();
    }

    public RecordEntity getRecordById(long id) {
        reset();
        selectString = RecordGateway.COLUMN_ID + " = " + id;
        limit = "1";

        return (RecordEntity) sqlDatabaseEGI.get(this).getEntity();
    }

    public void deleteExpiredRecords() {
        Date now = new Date();
        selectString = YstrDate.threeDayTime() + " + date < " + now.getTime();

        sqlDatabaseEGI.delete(this);
    }

}

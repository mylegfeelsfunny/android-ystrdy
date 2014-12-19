package com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.entitygateway;

import android.content.ContentValues;
import android.database.Cursor;

import com.twobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;
import com.twobros.hatebyte.ystrdy.date.YstrDate;

import java.util.Date;
import java.util.InvalidPropertiesFormatException;

/**
 * NowRecordEntityGateway
 * Created by scott on 12/1/14.
 */
public class RecordEG extends AbstractEntityGateway {

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
            RecordEG.COLUMN_LATITUDE,
            RecordEG.COLUMN_LONGITUDE,
            RecordEG.COLUMN_DATE,
            RecordEG.COLUMN_TEMPERATURE,
            RecordEG.COLUMN_REGION_NAME,
            RecordEG.COLUMN_CITY_NAME,
            RecordEG.COLUMN_WOEID,
            RecordEG.COLUMN_ID
    };

    private RecordEntity entity;
    public RecordEG() {
        tableName = RecordEG.TABLE_NAME;
        projection = RecordEG.projectionMap;
        SQLDatabaseEGI = SQLDatabaseEGI.getInstance();
    }

    public RecordEG(Cursor c) {
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
        entity.location.setLatitude(c.getFloat(c.getColumnIndex(RecordEG.COLUMN_LATITUDE)));
        entity.location.setLongitude(c.getFloat(c.getColumnIndex(RecordEG.COLUMN_LONGITUDE)));
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
        values.put(RecordEG.COLUMN_LATITUDE, entity.location.getLatitude());
        values.put(RecordEG.COLUMN_LONGITUDE, entity.location.getLongitude());
        values.put(RecordEG.COLUMN_TEMPERATURE, entity.temperature);
        values.put(RecordEG.COLUMN_REGION_NAME, entity.regionName);
        values.put(RecordEG.COLUMN_CITY_NAME, entity.cityName);
        values.put(RecordEG.COLUMN_WOEID, entity.woeid);
        values.put(RecordEG.COLUMN_DATE, entity.date.getTime());
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
        long id = SQLDatabaseEGI.insert(this);
        return id;
    }

    public int numRecords() {
        reset();
        projection = new String[]{
            RecordEG.COLUMN_ID
        };

        return SQLDatabaseEGI.count(this);
    }

    public RecordEntity getClosestRecordFromYstrdy() {
        reset();
        Date ystrdy = new Date();
        int twentyFourHours = (24 * 60 * 60 + 1) * 1000;
        ystrdy.setTime(ystrdy.getTime() - twentyFourHours);

        projection = RecordEG.projectionMap;
        selectString = "abs("+ystrdy.getTime()+" - date) < " + twentyFourHours;
        orderBy = "abs("+ystrdy.getTime()+" - date) ASC";

        return (RecordEntity) SQLDatabaseEGI.get(this).getEntity();
    }

    public RecordEntity getEarliestRecord() {
        reset();
        projection = RecordEG.projectionMap;
        orderBy = "date ASC";
        limit = "1";

        return (RecordEntity) SQLDatabaseEGI.get(this).getEntity();
    }

//
//    public RecordEG getRecordById(long id) {
//        getDatabaseAPI().open();
//        String selectStatement = RecordEntity.COLUMN_ID + " = ?";
//        String[] selectArgs = { id + "" };
//        Cursor c = databaseAPI.get(RecordEntity.TABLE_NAME, RecordEG.projection, selectStatement, null, null);
//
//        RecordEG recordEG = new RecordEG(c);
//        databaseAPI.close();
//        return recordEG;
//    }
//
    public void deleteExpiredRecords() {
        Date now = new Date();
        selectString = YstrDate.threeDayTime() + " + date < " + now.getTime();

        SQLDatabaseEGI.delete(this);
    }

}

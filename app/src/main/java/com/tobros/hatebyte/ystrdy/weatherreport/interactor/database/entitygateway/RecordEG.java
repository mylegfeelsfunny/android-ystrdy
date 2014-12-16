package com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entitygateway;

import android.content.ContentValues;
import android.database.Cursor;

import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.EntityGatewayImplementation;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entity.RecordEntity;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.date.YstrDate;

import java.util.Date;
import java.util.InvalidPropertiesFormatException;

/**
 * NowRecordEntityGateway
 * Created by scott on 12/1/14.
 */
public class RecordEG extends AbstractEntityGateway {

    public static String[] projectionMap = new String[]{
            RecordEntity.COLUMN_LATITUDE,
            RecordEntity.COLUMN_LONGITUDE,
            RecordEntity.COLUMN_DATE,
            RecordEntity.COLUMN_TEMPERATURE,
            RecordEntity.COLUMN_REGION_NAME,
            RecordEntity.COLUMN_CITY_NAME,
            RecordEntity.COLUMN_WOEID,
            RecordEntity.COLUMN_ID
    };

    private RecordEntity entity;
    public RecordEG() {
        tableName = RecordEntity.TABLE_NAME;
        projection = RecordEG.projectionMap;
        entityGatewayImplementation = new EntityGatewayImplementation();
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
        if (c.getCount()== 0) {
            return;
        }

        entity = new RecordEntity();
        entity.latitude = c.getFloat(c.getColumnIndex(RecordEntity.COLUMN_LATITUDE));
        entity.longitude = c.getFloat(c.getColumnIndex(RecordEntity.COLUMN_LONGITUDE));
        entity.temperature = c.getFloat(c.getColumnIndex(RecordEntity.COLUMN_TEMPERATURE));
        entity.regionName = c.getString(c.getColumnIndex(RecordEntity.COLUMN_REGION_NAME));
        entity.cityName = c.getString(c.getColumnIndex(RecordEntity.COLUMN_CITY_NAME));
        entity.woeid = c.getString(c.getColumnIndex(RecordEntity.COLUMN_WOEID));
        entity.date = new Date(c.getLong(c.getColumnIndex(RecordEntity.COLUMN_DATE)));
        entity.id = c.getInt(c.getColumnIndex(RecordEntity.COLUMN_ID));
    }

    @Override
    public ContentValues contentValues() {
        ContentValues values = new ContentValues();
        values.put(RecordEntity.COLUMN_LATITUDE, entity.latitude);
        values.put(RecordEntity.COLUMN_LONGITUDE, entity.longitude);
        values.put(RecordEntity.COLUMN_TEMPERATURE, entity.temperature);
        values.put(RecordEntity.COLUMN_REGION_NAME, entity.regionName);
        values.put(RecordEntity.COLUMN_CITY_NAME, entity.cityName);
        values.put(RecordEntity.COLUMN_WOEID, entity.woeid);
        values.put(RecordEntity.COLUMN_DATE, entity.date.getTime());
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
        long id = entityGatewayImplementation.insert(this);
        return id;
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
//    public RecordEG getClosestRecordFromYstrdy() {
//        databaseAPI.open();
//        Date ystrdy = new Date();
//        int twentyFourHours = (24 * 60 * 60 + 1) * 1000;
//        ystrdy.setTime(ystrdy.getTime() - twentyFourHours);
//
//        String selectStatement = "abs("+ystrdy.getTime()+" - date) < " + twentyFourHours;
//        String orderBy = "abs("+ystrdy.getTime()+" - date) ASC";
//
//        Cursor c = databaseAPI.get(RecordEntity.TABLE_NAME, RecordEG.projection, selectStatement, orderBy, null);
//        RecordEG recordEG = new RecordEG(c);
//        databaseAPI.close();
//        return recordEG;
//    }
//
//    public RecordEG getEarliestRecord() {
//        getDatabaseAPI().open();
//        String orderBy = "date ASC";
//
//        Cursor c = databaseAPI.get(RecordEntity.TABLE_NAME, RecordEG.projection, null, orderBy, "1");
//        RecordEG recordEG = new RecordEG(c);
//        databaseAPI.close();
//        return recordEG;
//    }
//
//    public int numRecords() {
//        getDatabaseAPI().open();
//        String[] projection = {
//                RecordEntity.COLUMN_ID
//        };
//
//        Cursor c = databaseAPI.get(RecordEntity.TABLE_NAME, projection, null, null, null);
//        int num = c.getCount();
//        databaseAPI.close();
//        return num;
//    }
//
//    public void deleteExpiredRecords() {
//        getDatabaseAPI().open();
//        Date now = new Date();
//        String whereString = YstrDate.threeDayTime() + " + date < " + now.getTime();
//        databaseAPI.delete(RecordEntity.TABLE_NAME, whereString);
//        databaseAPI.close();
//    }



}

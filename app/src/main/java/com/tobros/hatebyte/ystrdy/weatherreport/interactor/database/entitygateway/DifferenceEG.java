package com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entitygateway;

import android.content.ContentValues;
import android.database.Cursor;

import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.EntityGatewayImplementation;
import com.tobros.hatebyte.ystrdy.weatherreport.entity.DifferenceEntity;

import java.util.Date;
import java.util.InvalidPropertiesFormatException;

/**
 * YstrdyRecordEntityGateway
 * Created by scott on 12/11/14.
 */
public class DifferenceEG extends AbstractEntityGateway {

    public static String[] projectionMap = new String[]{
                DifferenceEntity.COLUMN_DIFFERENCE,
                DifferenceEntity.COLUMN_DATE,
                DifferenceEntity.COLUMN_NOW_RECORD_ID,
                DifferenceEntity.COLUMN_ID
    };

    private DifferenceEntity entity;

    public DifferenceEG() {
        tableName = DifferenceEntity.TABLE_NAME;
        projection = DifferenceEG.projectionMap;
        entityGatewayImplementation = new EntityGatewayImplementation();
    }

    public DifferenceEG(Cursor c) {
        mapFromCursor(c);
    }

    @Override
    public void mapFromCursor(Cursor c) {
        if (c.getColumnCount() == 0) {
            return;
        }
        entity = new DifferenceEntity();
        entity.difference = c.getFloat(c.getColumnIndex(DifferenceEntity.COLUMN_DIFFERENCE));
        entity.date = new Date(c.getLong(c.getColumnIndex(DifferenceEntity.COLUMN_DATE)));
        entity.recordId = c.getInt(c.getColumnIndex(DifferenceEntity.COLUMN_NOW_RECORD_ID));
        entity.id = c.getInt(c.getColumnIndex(DifferenceEntity.COLUMN_ID));
    }

    @Override
    public void setEntity(Object e)  {
        assert(e.getClass().isInstance(DifferenceEntity.class) || e instanceof DifferenceEntity);
        DifferenceEntity re = (DifferenceEntity) e;
        entity = re;
    }

    @Override
    public DifferenceEntity getEntity() {
        return entity;
    }

    @Override
    public ContentValues contentValues() {
        ContentValues values = new ContentValues();
        values.put(DifferenceEntity.COLUMN_DIFFERENCE, entity.difference);
        values.put(DifferenceEntity.COLUMN_NOW_RECORD_ID, entity.recordId);
        values.put(DifferenceEntity.COLUMN_DATE, entity.date.getTime());
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
        long id = entityGatewayImplementation.insert(this);
        return id;
    }

    public DifferenceEntity getLatestDifferenceRecord() {
        reset();
        projection = DifferenceEG.projectionMap;
        orderBy = "date DESC";
        limit = "1";

        return (DifferenceEntity)entityGatewayImplementation.get(this).getEntity();
    }

    public int numDifferenceRecords() {
        reset();
        projection = new String[]{
            DifferenceEntity.COLUMN_ID
        };

        return entityGatewayImplementation.count(this);
    }

//    public long insertDifferenceRecord(AbstractEntityGateway entityGateway) throws InvalidPropertiesFormatException {
//        getDatabaseAPI().open();
//
//        long id = databaseAPI.insert(entityGateway.tableName(), entityGateway.contentValues());
//        databaseAPI.close();
//        return  id;
//    }
//
//
//
//    public void deleteExpiredDifferenceRecords() {
//        getDatabaseAPI().open();
//        Date now = new Date();
//        String whereString = YstrDate.threeDayTime() + " + date < " + now.getTime();
//        databaseAPI.delete(DifferenceEntity.TABLE_NAME, whereString);
//        databaseAPI.close();
//    }
}
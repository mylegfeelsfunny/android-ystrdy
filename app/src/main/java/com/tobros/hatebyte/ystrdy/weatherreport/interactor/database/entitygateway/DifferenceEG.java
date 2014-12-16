package com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entitygateway;

import android.content.ContentValues;
import android.database.Cursor;

import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.EntityGatewayImplementation;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entity.DifferenceEntity;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entity.RecordEntity;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.date.YstrDate;

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
        if (c.getCount() == 0) {
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

//    public long insertDifferenceRecord(AbstractEntityGateway entityGateway) throws InvalidPropertiesFormatException {
//        getDatabaseAPI().open();
//
//        long id = databaseAPI.insert(entityGateway.tableName(), entityGateway.contentValues());
//        databaseAPI.close();
//        return  id;
//    }
//
//    public DifferenceEG getLatestDifferenceRecord() {
//        String orderBy = "date DESC";
//
//        getDatabaseAPI().open();
//        Cursor c = databaseAPI.get(
//                DifferenceEntity.TABLE_NAME,
//                DifferenceEG.projection,
//                null,
//                orderBy,
//                "1"
//        );
//        DifferenceEG ystrRecord = new DifferenceEG(c);
//        databaseAPI.close();
//        return ystrRecord;
//    }
//
//    public int numDifferenceRecords() {
//        getDatabaseAPI().open();
//        String[] projection = {
//                DifferenceEntity.COLUMN_ID
//        };
//
//        Cursor c = databaseAPI.get(DifferenceEntity.TABLE_NAME, projection, null, null, null);
//        int num = c.getCount();
//        databaseAPI.close();
//        return num;
//    }
//
//    public void deleteExpiredDifferenceRecords() {
//        getDatabaseAPI().open();
//        Date now = new Date();
//        String whereString = YstrDate.threeDayTime() + " + date < " + now.getTime();
//        databaseAPI.delete(DifferenceEntity.TABLE_NAME, whereString);
//        databaseAPI.close();
//    }
}
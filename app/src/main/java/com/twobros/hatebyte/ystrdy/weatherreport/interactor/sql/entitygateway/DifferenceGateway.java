package com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.entitygateway;

import android.content.ContentValues;
import android.database.Cursor;

import com.twobros.hatebyte.ystrdy.weatherreport.entity.DifferenceEntity;

import java.util.Date;
import java.util.InvalidPropertiesFormatException;

/**
 * YstrdyRecordEntityGateway
 * Created by scott on 12/11/14.
 */
public class DifferenceGateway extends AbstractEntityGateway {

    public static final String TABLE_NAME                           = "difference";
    public static final String COLUMN_TODAY_RECORD_ID               = "today_record_id";
    public static final String COLUMN_YSTRDY_RECORD_ID              = "ystrdy_record_id";
    public static final String COLUMN_DIFFERENCE                    = "difference";
    public static final String COLUMN_DATE                          = "date";
    public static final String COLUMN_ID                            = "_id";

    public static String[] projectionMap = new String[]{
        DifferenceGateway.COLUMN_DIFFERENCE,
        DifferenceGateway.COLUMN_DATE,
        DifferenceGateway.COLUMN_TODAY_RECORD_ID,
        DifferenceGateway.COLUMN_YSTRDY_RECORD_ID,
        DifferenceGateway.COLUMN_ID
    };

    private DifferenceEntity entity;

    public DifferenceGateway() {
        tableName = DifferenceGateway.TABLE_NAME;
        projection = DifferenceGateway.projectionMap;
    }

    public DifferenceGateway(Cursor c) {
        mapFromCursor(c);
    }

    @Override
    public void mapFromCursor(Cursor c) {
        if (c.getColumnCount() == 0) {
            return;
        }
        entity = new DifferenceEntity();
        entity.difference = c.getFloat(c.getColumnIndex(DifferenceGateway.COLUMN_DIFFERENCE));
        entity.date = new Date(c.getLong(c.getColumnIndex(DifferenceGateway.COLUMN_DATE)));
        entity.todayRecordId = c.getInt(c.getColumnIndex(DifferenceGateway.COLUMN_TODAY_RECORD_ID));
        entity.ystrdyRecordId = c.getInt(c.getColumnIndex(DifferenceGateway.COLUMN_YSTRDY_RECORD_ID));
        entity.id = c.getInt(c.getColumnIndex(DifferenceGateway.COLUMN_ID));
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
        values.put(DifferenceGateway.COLUMN_DIFFERENCE, entity.difference);
        values.put(DifferenceGateway.COLUMN_TODAY_RECORD_ID, entity.todayRecordId);
        values.put(DifferenceGateway.COLUMN_YSTRDY_RECORD_ID, entity.ystrdyRecordId);
        values.put(DifferenceGateway.COLUMN_DATE, entity.date.getTime());
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

    public DifferenceEntity getLatestDifferenceRecord() {
        reset();
        projection = DifferenceGateway.projectionMap;
        orderBy = "date DESC";
        limit = "1";

        return (DifferenceEntity) sqlDatabaseEGI.get(this).getEntity();
    }

    public int numDifferenceRecords() {
        reset();
        projection = new String[]{
            DifferenceGateway.COLUMN_ID
        };

        return sqlDatabaseEGI.count(this);
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
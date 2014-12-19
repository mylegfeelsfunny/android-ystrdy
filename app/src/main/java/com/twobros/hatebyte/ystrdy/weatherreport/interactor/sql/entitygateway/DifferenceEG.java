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
public class DifferenceEG extends AbstractEntityGateway {

    public static final String TABLE_NAME                           = "difference";
    public static final String COLUMN_NOW_RECORD_ID                 = "record_id";
    public static final String COLUMN_DIFFERENCE                    = "difference";
    public static final String COLUMN_DATE                          = "date";
    public static final String COLUMN_ID                            = "_id";

    public static String[] projectionMap = new String[]{
        DifferenceEG.COLUMN_DIFFERENCE,
        DifferenceEG.COLUMN_DATE,
        DifferenceEG.COLUMN_NOW_RECORD_ID,
        DifferenceEG.COLUMN_ID
    };

    private DifferenceEntity entity;

    public DifferenceEG() {
        tableName = DifferenceEG.TABLE_NAME;
        projection = DifferenceEG.projectionMap;
        SQLDatabaseEGI = SQLDatabaseEGI.getInstance();
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
        entity.difference = c.getFloat(c.getColumnIndex(DifferenceEG.COLUMN_DIFFERENCE));
        entity.date = new Date(c.getLong(c.getColumnIndex(DifferenceEG.COLUMN_DATE)));
        entity.recordId = c.getInt(c.getColumnIndex(DifferenceEG.COLUMN_NOW_RECORD_ID));
        entity.id = c.getInt(c.getColumnIndex(DifferenceEG.COLUMN_ID));
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
        values.put(DifferenceEG.COLUMN_DIFFERENCE, entity.difference);
        values.put(DifferenceEG.COLUMN_NOW_RECORD_ID, entity.recordId);
        values.put(DifferenceEG.COLUMN_DATE, entity.date.getTime());
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

    public DifferenceEntity getLatestDifferenceRecord() {
        reset();
        projection = DifferenceEG.projectionMap;
        orderBy = "date DESC";
        limit = "1";

        return (DifferenceEntity) SQLDatabaseEGI.get(this).getEntity();
    }

    public int numDifferenceRecords() {
        reset();
        projection = new String[]{
            DifferenceEG.COLUMN_ID
        };

        return SQLDatabaseEGI.count(this);
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
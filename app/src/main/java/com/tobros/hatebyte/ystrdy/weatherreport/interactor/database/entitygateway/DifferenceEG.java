package com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entitygateway;

import android.content.ContentValues;
import android.database.Cursor;

import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entity.DifferenceEntity;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entity.RecordEntity;

import java.util.Date;
import java.util.InvalidPropertiesFormatException;

/**
 * YstrdyRecordEntityGateway
 * Created by scott on 12/11/14.
 */
public class DifferenceEG extends AbstractEntityGateway {

    public static final String TABLE_NAME                           = "ystrdyrecords";
    public static final String COLUMN_NOW_RECORD_ID                 = "recordId";
    public static final String COLUMN_DIFFERENCE                    = "difference";
    public static final String COLUMN_DATE                          = "date";
    public static final String COLUMN_ID                            = "_id";

    private DifferenceEntity entity;
    public DifferenceEG() {}
    public DifferenceEG(Cursor c) {
        mapFromCursor(c);
    }

    @Override
    public void mapFromCursor(Cursor c) {
        if (c.getCount() == 0) {
            return;
        }
        c.moveToFirst();

        entity = new DifferenceEntity();
        entity.difference = c.getFloat(c.getColumnIndex(DifferenceEG.COLUMN_DIFFERENCE));
        entity.date = new Date(c.getLong(c.getColumnIndex(DifferenceEG.COLUMN_DATE)));
        entity.recordId = c.getInt(c.getColumnIndex(DifferenceEG.COLUMN_NOW_RECORD_ID));
        entity.id = c.getInt(c.getColumnIndex(DifferenceEG.COLUMN_ID));
    }

    @Override
    public void setEntity(Object e) throws InvalidPropertiesFormatException {
        DifferenceEntity re = (DifferenceEntity)e;
        if (re.date == null) {
            throw new InvalidPropertiesFormatException("There is no date associated with this YstrdyRecordEntity");
        }
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

    public static String[] projection() {
        return new String[]{
                DifferenceEG.COLUMN_DIFFERENCE,
                DifferenceEG.COLUMN_DATE,
                DifferenceEG.COLUMN_NOW_RECORD_ID,
                DifferenceEG.COLUMN_ID
        };
    }

}

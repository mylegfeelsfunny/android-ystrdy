package com.tobros.hatebyte.ystrdy.weatherrecords.gateway;

import android.content.ContentValues;
import android.database.Cursor;

import com.tobros.hatebyte.ystrdy.weatherrecords.database.RecordDescription;
import com.tobros.hatebyte.ystrdy.weatherrecords.entity.YstrdyRecordEntity;

import java.util.Date;
import java.util.InvalidPropertiesFormatException;

/**
 * YstrdyRecordEntityGateway
 * Created by scott on 12/11/14.
 */
public class YstrdyRecordEG {

    private YstrdyRecordEntity entity;
    public YstrdyRecordEG() {}
    public YstrdyRecordEG(Cursor c) {
        if (c.getCount()== 0) {
            return;
        }
        c.moveToFirst();

        entity = new YstrdyRecordEntity();
        entity.difference = c.getFloat(c.getColumnIndex(RecordDescription.YstrdayRecord.COLUMN_DIFFERENCE));
        entity.date = new Date(c.getLong(c.getColumnIndex(RecordDescription.YstrdayRecord.COLUMN_DATE)));
        entity.nowRecordId = c.getInt(c.getColumnIndex(RecordDescription.YstrdayRecord.COLUMN_NOW_RECORD_ID));
        entity.id = c.getInt(c.getColumnIndex(RecordDescription.YstrdayRecord._ID));
    }

    public void setEntity(YstrdyRecordEntity r) throws InvalidPropertiesFormatException {
        if (r.date == null) {
            throw new InvalidPropertiesFormatException("There is no date associated with this YstrdyRecordEntity");
        }
        entity = r;
    }

    public YstrdyRecordEntity getEntity() {
        return entity;
    }

    public ContentValues contentValues() {
        ContentValues values = new ContentValues();
        values.put(RecordDescription.YstrdayRecord.COLUMN_DIFFERENCE, entity.difference);
        values.put(RecordDescription.YstrdayRecord.COLUMN_NOW_RECORD_ID, entity.nowRecordId);
        values.put(RecordDescription.YstrdayRecord.COLUMN_DATE, entity.date.getTime());
        return values;
    }

    public static String[] projection() {
        return new String[]{
            RecordDescription.YstrdayRecord.COLUMN_DIFFERENCE,
            RecordDescription.YstrdayRecord.COLUMN_DATE,
            RecordDescription.YstrdayRecord.COLUMN_NOW_RECORD_ID,
            RecordDescription.YstrdayRecord._ID
        };
    }

}

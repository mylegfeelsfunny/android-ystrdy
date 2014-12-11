package com.tobros.hatebyte.ystrdy.weatherrecords.gateway;

import android.content.ContentValues;
import android.database.Cursor;

import com.tobros.hatebyte.ystrdy.weatherrecords.database.RecordDescription;
import com.tobros.hatebyte.ystrdy.weatherrecords.entity.YstrdyRecordEntity;

import java.util.Date;

/**
 * Created by scott on 12/11/14.
 */
public class YstrdyRecordEG {

    public YstrdyRecordEntity record;
    public YstrdyRecordEG() {}
    public YstrdyRecordEG(Cursor c) {
        record = new YstrdyRecordEntity();
        record.difference = c.getFloat(c.getColumnIndex(RecordDescription.YstrdayRecord.COLUMN_DIFFERENCE));
        record.date = new Date(c.getLong(c.getColumnIndex(RecordDescription.YstrdayRecord.COLUMN_DATE)));
        record.nowRecordId = c.getInt(c.getColumnIndex(RecordDescription.YstrdayRecord.COLUMN_NOW_RECORD_ID));
    }

    public ContentValues contentValues() {
        ContentValues values = new ContentValues();
        values.put(RecordDescription.YstrdayRecord.COLUMN_DIFFERENCE, record.difference);
        values.put(RecordDescription.YstrdayRecord.COLUMN_NOW_RECORD_ID, record.nowRecordId);
        values.put(RecordDescription.YstrdayRecord.COLUMN_DATE, record.date.getTime());
        return values;
    }

    public static String[] projection() {
        return new String[]{
            RecordDescription.YstrdayRecord.COLUMN_DIFFERENCE,
            RecordDescription.YstrdayRecord.COLUMN_DATE,
            RecordDescription.YstrdayRecord._ID
        };
    }

}

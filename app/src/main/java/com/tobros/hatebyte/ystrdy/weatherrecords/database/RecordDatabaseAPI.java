package com.tobros.hatebyte.ystrdy.weatherrecords.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tobros.hatebyte.ystrdy.weatherrecords.entity.NowRecordEntity;
import com.tobros.hatebyte.ystrdy.weatherrecords.entity.YstrdyRecordEntity;
import com.tobros.hatebyte.ystrdy.weatherrecords.gateway.NowRecordEG;
import com.tobros.hatebyte.ystrdy.weatherrecords.gateway.YstrdyRecordEG;
import com.tobros.hatebyte.ystrdy.date.YstrDate;

import java.util.Date;
import java.util.InvalidPropertiesFormatException;

/**
 * Created by scott on 11/26/14.
 */
public class RecordDatabaseAPI {

    private static final String TAG = "LocationRecordsDbConnector";

    public SQLiteDatabase database;
    public RecordDatabase databaseHelper;

    public RecordDatabaseAPI(RecordDatabase dbHelper) {
        databaseHelper = dbHelper;
    }

    public void open() {
        database = databaseHelper.getWritableDatabase();
    }
    public void close() {
        if (databaseHelper != null) {
            database.close();
        }
    }

    public void clearDatabase() {
        open();
        databaseHelper.onDowngrade(database, RecordDatabase.DATA_VERSION, RecordDatabase.DATA_VERSION+1);
        close();
    }

    public long insertNowRecord(NowRecordEG nowRecordEG) {

        open();
        long newRowId = database.insert(
                RecordDescription.NowRecord.TABLE_NAME,
                null,
                nowRecordEG.contentValues()
        );
        close();

        return newRowId;
    }

    public long insertYstrdyRecord(YstrdyRecordEG ystrdyRecordEG) {

        open();
        long newRowId = database.insert(
                RecordDescription.YstrdayRecord.TABLE_NAME,
                null,
                ystrdyRecordEG.contentValues()
        );
        close();

        return newRowId;
    }

    public NowRecordEG getNowRecordById(long id) {
        String selectStatement = RecordDescription.NowRecord._ID + " = ?";
        String[] selectArgs = { id + "" };

        open();
        Cursor c = database.query(
                RecordDescription.NowRecord.TABLE_NAME,
                NowRecordEG.projection(),
                selectStatement,
                selectArgs,
                null,
                null,
                null
        );
        NowRecordEG ystrRecord = new NowRecordEG(c);
        close();
        return ystrRecord;
    }

    public NowRecordEG getClosestNowRecordFromYstrdy() {
        Date ystrdy = new Date();
        int twentyFourHours = (24 * 60 * 60 + 1) * 1000;
        ystrdy.setTime(ystrdy.getTime() - twentyFourHours);

        String selectStatement = "abs("+ystrdy.getTime()+" - date) < " + twentyFourHours;
        String orderBy = "abs("+ystrdy.getTime()+" - date) ASC";

        open();
        Cursor c = database.query(
                RecordDescription.NowRecord.TABLE_NAME,
                NowRecordEG.projection(),
                selectStatement,
                null,
                null,
                null,
                orderBy
        );
        NowRecordEG ystrRecord = new NowRecordEG(c);
        close();
        return ystrRecord;
    }

    public NowRecordEG getEarliestNowRecord() {
        String orderBy = "date ASC";

        open();
        Cursor c = database.query(
                RecordDescription.NowRecord.TABLE_NAME,
                NowRecordEG.projection(),
                null,
                null,
                null,
                null,
                orderBy,
                "1"
        );
        NowRecordEG ystrRecord = new NowRecordEG(c);
        close();
        return ystrRecord;
    }

    public int numNowRecords() {
        String[] projection = {
            RecordDescription.NowRecord._ID
        };
        open();
        Cursor c = database.query(
                RecordDescription.NowRecord.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        int count = c.getCount();
        close();
        return count;
    }

    public void deleteExpiredNowRecords() {
        open();
        Date now = new Date();
        String whereString = YstrDate.threeDayTime() + " + date < " + now.getTime();
        database.delete(RecordDescription.NowRecord.TABLE_NAME, whereString, null);
        close();
    }

}

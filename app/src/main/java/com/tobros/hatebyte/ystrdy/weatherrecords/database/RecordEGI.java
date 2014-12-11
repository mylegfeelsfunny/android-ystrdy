package com.tobros.hatebyte.ystrdy.weatherrecords.database;

import android.content.Context;

import com.tobros.hatebyte.ystrdy.weatherrecords.entity.NowRecordEntity;
import com.tobros.hatebyte.ystrdy.weatherrecords.entity.YstrdyRecordEntity;
import com.tobros.hatebyte.ystrdy.weatherrecords.gateway.NowRecordEG;
import com.tobros.hatebyte.ystrdy.weatherrecords.gateway.YstrdyRecordEG;

import java.util.InvalidPropertiesFormatException;

/**
 * Created by scott on 12/11/14.
 */
public class RecordEGI {

    public RecordDatabaseAPI dbConnector;

    public RecordEGI() {

    }

    public RecordEGI(RecordDatabase recordDatabase) {
        dbConnector = new RecordDatabaseAPI(recordDatabase);
    }

    public void clearDatabase() {
        dbConnector.clearDatabase();
    }

    public long insertNowRecord(NowRecordEntity nowRecordEntity) throws InvalidPropertiesFormatException {
        if (nowRecordEntity.date == null) {
            throw new InvalidPropertiesFormatException("Check your date property");
        }

        NowRecordEG nowRecordEG = new NowRecordEG();
        nowRecordEG.record = nowRecordEntity;
        return dbConnector.insertNowRecord(nowRecordEG);
    }

    public long insertYstrdyRecord(YstrdyRecordEntity ystrdyRecordEntity) throws InvalidPropertiesFormatException {
        if (ystrdyRecordEntity.date == null) {
            InvalidPropertiesFormatException e = new InvalidPropertiesFormatException("Check your date property");
            throw e;
        }

        YstrdyRecordEG ystrdyRecordEG = new YstrdyRecordEG();
        ystrdyRecordEG.record = ystrdyRecordEntity;
        return dbConnector.insertYstrdyRecord(ystrdyRecordEG);
    }

    public NowRecordEntity getNowRecordById(long id) {
        return dbConnector.getNowRecordById(id).record;
    }

    public NowRecordEntity getClosestNowRecordFromYstrdy() {
        return dbConnector.getClosestNowRecordFromYstrdy().record;
    }

    public NowRecordEntity getEarliestNowRecord() {
        return dbConnector.getEarliestNowRecord().record;
    }

    public int numNowRecords() {
        return dbConnector.numNowRecords();
    }

    public void deleteExpiredNowRecords() {
        dbConnector.deleteExpiredNowRecords();
    }

}

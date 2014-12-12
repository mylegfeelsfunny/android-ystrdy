package com.tobros.hatebyte.ystrdy.weatherrecords.database;

import com.tobros.hatebyte.ystrdy.weatherrecords.entity.NowRecordEntity;
import com.tobros.hatebyte.ystrdy.weatherrecords.entity.YstrdyRecordEntity;
import com.tobros.hatebyte.ystrdy.weatherrecords.gateway.NowRecordEG;
import com.tobros.hatebyte.ystrdy.weatherrecords.gateway.YstrdyRecordEG;

import java.util.InvalidPropertiesFormatException;

/**
 * RecordEntityGatewayImplementation
 * Created by scott on 12/11/14.
 */
public class RecordEGI {

    private static final String TAG = " RecordEntityGatewayImplementation";

    public RecordDatabaseAPI databaseAPI;

    public RecordEGI() {

    }

    public RecordEGI(RecordDatabase recordDatabase) {
        databaseAPI = new RecordDatabaseAPI(recordDatabase);
    }

    public void clearDatabase() {
        databaseAPI.clearDatabase();
    }

    public long insertNowRecord(NowRecordEntity nowRecordEntity) throws InvalidPropertiesFormatException {
        NowRecordEG nowRecordEG = new NowRecordEG();
        nowRecordEG.setEntity(nowRecordEntity);
        return databaseAPI.insertNowRecord(nowRecordEG);
    }

    public long insertYstrdyRecord(YstrdyRecordEntity ystrdyRecordEntity) throws InvalidPropertiesFormatException {
        YstrdyRecordEG ystrdyRecordEG = new YstrdyRecordEG();
        ystrdyRecordEG.setEntity(ystrdyRecordEntity);
        return databaseAPI.insertYstrdyRecord(ystrdyRecordEG);
    }

    public NowRecordEntity getNowRecordById(long id) {
        return databaseAPI.getNowRecordById(id).getEntity();
    }

    public NowRecordEntity getClosestNowRecordFromYstrdy() {
        return databaseAPI.getClosestNowRecordFromYstrdy().getEntity();
    }

    public NowRecordEntity getEarliestNowRecord() {
        return databaseAPI.getEarliestNowRecord().getEntity();
    }

    public YstrdyRecordEntity getLatestYstrdyRecord() {
        return databaseAPI.getLatestYstrdyRecord().getEntity();
    }

    public int numNowRecords() {
        return databaseAPI.numNowRecords();
    }

    public int numYstrdyRecords() {
        return databaseAPI.numYstrdyRecords();
    }

    public void deleteExpiredNowRecords() {
        databaseAPI.deleteExpiredNowRecords();
    }

}

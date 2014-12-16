package com.tobros.hatebyte.ystrdy.weatherreport.interactor.database;

import android.database.Cursor;

import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entity.RecordEntity;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entitygateway.RecordEG;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.date.YstrDate;

import java.util.Date;
import java.util.InvalidPropertiesFormatException;

/**
 * RecordEntityGatewayImplementation
 * Created by scott on 12/11/14.
 */
public class RecordEGI extends EntityGatewayImplementation {

    private static final String TAG = " RecordEntityGatewayImplementation";

    private static RecordEGI instance = null;
    public static RecordEGI getInstance() {
        if(instance == null) {
            instance = new RecordEGI();
        }
        return instance;
    }

    protected RecordEGI() {}

//    public long insertRecord(RecordEG nowRecordEG) throws InvalidPropertiesFormatException {
//        getDatabaseAPI().open();
//
//        long id = databaseAPI.insert(RecordEntity.TABLE_NAME, nowRecordEG.contentValues());
//        databaseAPI.close();
//        return  id;
//    }
//
//    public RecordEG getRecordById(long id) {
//        getDatabaseAPI().open();
//        String selectStatement = RecordEntity.COLUMN_ID + " = ?";
//        String[] selectArgs = { id + "" };
//        Cursor c = databaseAPI.get(RecordEntity.TABLE_NAME, RecordEG.projection, selectStatement, null, null);
//
//        RecordEG recordEG = new RecordEG(c);
//        databaseAPI.close();
//        return recordEG;
//    }
//
//    public RecordEG getClosestRecordFromYstrdy() {
//        databaseAPI.open();
//        Date ystrdy = new Date();
//        int twentyFourHours = (24 * 60 * 60 + 1) * 1000;
//        ystrdy.setTime(ystrdy.getTime() - twentyFourHours);
//
//        String selectStatement = "abs("+ystrdy.getTime()+" - date) < " + twentyFourHours;
//        String orderBy = "abs("+ystrdy.getTime()+" - date) ASC";
//
//        Cursor c = databaseAPI.get(RecordEntity.TABLE_NAME, RecordEG.projection, selectStatement, orderBy, null);
//        RecordEG recordEG = new RecordEG(c);
//        databaseAPI.close();
//        return recordEG;
//    }
//
//    public RecordEG getEarliestRecord() {
//        getDatabaseAPI().open();
//        String orderBy = "date ASC";
//
//        Cursor c = databaseAPI.get(RecordEntity.TABLE_NAME, RecordEG.projection, null, orderBy, "1");
//        RecordEG recordEG = new RecordEG(c);
//        databaseAPI.close();
//        return recordEG;
//    }
//
//    public int numRecords() {
//        getDatabaseAPI().open();
//        String[] projection = {
//                RecordEntity.COLUMN_ID
//        };
//
//        Cursor c = databaseAPI.get(RecordEntity.TABLE_NAME, projection, null, null, null);
//        int num = c.getCount();
//        databaseAPI.close();
//        return num;
//    }
//
//    public void deleteExpiredRecords() {
//        getDatabaseAPI().open();
//        Date now = new Date();
//        String whereString = YstrDate.threeDayTime() + " + date < " + now.getTime();
//        databaseAPI.delete(RecordEntity.TABLE_NAME, whereString);
//        databaseAPI.close();
//    }

}

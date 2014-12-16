package com.tobros.hatebyte.ystrdy.weatherreport.interactor.database;

import android.database.Cursor;

import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entity.DifferenceEntity;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entitygateway.AbstractEntityGateway;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entitygateway.DifferenceEG;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.date.YstrDate;

import java.util.Date;
import java.util.InvalidPropertiesFormatException;

/**
 * Created by scott on 12/14/14.
 */
public class DifferenceEGI extends EntityGatewayImplementation {

    private static final String TAG = " RecordEntityGatewayImplementation";

    private static DifferenceEGI instance = null;
    public static DifferenceEGI getInstance() {
        if(instance == null) {
            instance = new DifferenceEGI();
        }
        return instance;
    }

    protected DifferenceEGI() {}

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

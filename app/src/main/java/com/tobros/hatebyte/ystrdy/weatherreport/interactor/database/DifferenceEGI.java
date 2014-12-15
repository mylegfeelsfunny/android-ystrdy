package com.tobros.hatebyte.ystrdy.weatherreport.interactor.database;

import android.database.Cursor;

import com.tobros.hatebyte.ystrdy.YstrdyApp;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.database.YstrdyDatabaseAPI;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entity.DifferenceEntity;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entitygateway.DifferenceEG;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.date.YstrDate;

import java.util.Date;
import java.util.InvalidPropertiesFormatException;

/**
 * Created by scott on 12/14/14.
 */
public class DifferenceEGI {

    private static final String TAG = " RecordEntityGatewayImplementation";

    public YstrdyDatabaseAPI databaseAPI;

    public DifferenceEGI() {}

    public YstrdyDatabaseAPI getDatabaseAPI() {
        if (databaseAPI == null) {
            databaseAPI = new YstrdyDatabaseAPI(YstrdyApp.getContext(), YstrdyDatabaseAPI.DATABASE_NAME);
        }
        return databaseAPI;
    }

    public long insertDifferenceRecord(DifferenceEG differenceEG) throws InvalidPropertiesFormatException {
        getDatabaseAPI().open();

        long id = databaseAPI.insert(DifferenceEG.TABLE_NAME, differenceEG.contentValues());
        databaseAPI.close();
        return  id;
    }

    public DifferenceEG getLatestDifferenceRecord() {
        String orderBy = "date DESC";

        getDatabaseAPI().open();
        Cursor c = databaseAPI.get(
                DifferenceEG.TABLE_NAME,
                DifferenceEG.projection(),
                null,
                orderBy,
                "1"
        );
        DifferenceEG ystrRecord = new DifferenceEG(c);
        databaseAPI.close();
        return ystrRecord;
    }

    public int numDifferenceRecords() {
        getDatabaseAPI().open();
        String[] projection = {
                DifferenceEG.COLUMN_ID
        };

        Cursor c = databaseAPI.get(DifferenceEG.TABLE_NAME, projection, null, null, null);
        int num = c.getCount();
        databaseAPI.close();
        return num;
    }

    public void deleteExpiredDifferenceRecords() {
        getDatabaseAPI().open();
        Date now = new Date();
        String whereString = YstrDate.threeDayTime() + " + date < " + now.getTime();
        databaseAPI.delete(DifferenceEG.TABLE_NAME, whereString);
        databaseAPI.close();
    }


}

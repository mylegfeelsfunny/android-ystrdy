package com.twobros.hatebyte.ystrdy.egi.mock;

import android.content.Context;

import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.implementation.SQLDatabaseEGI;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.dataapi.IDatabaseAPI;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.dataapi.YstrdyDatabaseAPI;

import org.robolectric.Robolectric;

/**
 * Created by scott on 12/16/14.
 */
public class TestEGI extends SQLDatabaseEGI {

    public TestEGI() {
        Context context = Robolectric.getShadowApplication().getApplicationContext();
        databaseAPI = new YstrdyDatabaseAPI(context, "testLocationRecord.db");
    }

    public void setDataBaseAPI(IDatabaseAPI dataBaseAPI) {
        databaseAPI = dataBaseAPI;
    }

    public IDatabaseAPI getDataBaseAPI() {
        return databaseAPI;
    }

    public void close(){
        databaseAPI.close();
    }

}

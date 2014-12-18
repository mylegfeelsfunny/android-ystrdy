package com.tobros.hatebyte.ystrdy.egi.mock;

import android.content.Context;

import com.tobros.hatebyte.ystrdy.YstrdyApp;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.EntityGatewayImplementation;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.database.IDatabaseAPI;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.database.YstrdyDatabaseAPI;

import org.robolectric.Robolectric;

/**
 * Created by scott on 12/16/14.
 */
public class TestEGI extends EntityGatewayImplementation {

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

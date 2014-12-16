package com.tobros.hatebyte.ystrdy.EGI.mock;

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

    public IDatabaseAPI getDatabaseAPI() {
        if (databaseAPI == null) {
            Context context = Robolectric.getShadowApplication().getApplicationContext();
            databaseAPI = new YstrdyDatabaseAPI(context, "testLocationRecord.db");
        }
        return databaseAPI;
    }

}

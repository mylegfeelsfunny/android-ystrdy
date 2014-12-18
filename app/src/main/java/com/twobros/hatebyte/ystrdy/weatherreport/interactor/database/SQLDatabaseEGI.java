package com.twobros.hatebyte.ystrdy.weatherreport.interactor.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.twobros.hatebyte.ystrdy.YstrdyApp;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.database.database.IDatabaseAPI;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.database.database.YstrdyDatabaseAPI;

import java.util.InvalidPropertiesFormatException;

/**
 * Created by scott on 12/15/14.
 */
public class SQLDatabaseEGI {

    public interface ISQLEntityGateway {
        public String tableName();
        public String[] projection();
        public String orderBy();
        public String limit();
        public String selectString();
        public ContentValues contentValues();
        public Boolean isValid();
        public void mapFromCursor(Cursor c);

        public void setEntity(Object e);
        public Object getEntity();
    }

    protected IDatabaseAPI databaseAPI;

//    protected IDatabaseAPI getDatabaseAPI() {
//        return databaseAPI;
//    }

    private static SQLDatabaseEGI instance = null;
    public SQLDatabaseEGI() {
        databaseAPI = new YstrdyDatabaseAPI(YstrdyApp.getContext(), YstrdyDatabaseAPI.DATABASE_NAME);
    }
    public static SQLDatabaseEGI getInstance() {
        if(instance == null) {
            instance = new SQLDatabaseEGI();
        }
        return instance;
    }

    public long insert(ISQLEntityGateway entityGateway) throws InvalidPropertiesFormatException {
        if (entityGateway.isValid() == false) {
            throw new InvalidPropertiesFormatException("Invalid Entity");
        }
        databaseAPI.open();
        long id = databaseAPI.insert(entityGateway.tableName(), entityGateway.contentValues());
        databaseAPI.close();
        return id;
    }

    public int count(ISQLEntityGateway entityGateway) {
        databaseAPI.open();
        Cursor c = databaseAPI.get(
                entityGateway.tableName(),
                entityGateway.projection(),
                null,
                null,
                null);
        int num = c.getCount();
        databaseAPI.close();
        return num;
    }

    public ISQLEntityGateway get(ISQLEntityGateway entityGateway) {
        databaseAPI.open();
        Cursor c = databaseAPI.get(
                entityGateway.tableName(),
                entityGateway.projection(),
                entityGateway.selectString(),
                entityGateway.orderBy(),
                entityGateway.limit()
        );
        if (c.getCount() > 0) {
            c.moveToFirst();
            entityGateway.mapFromCursor(c);
        }
        databaseAPI.close();
        return entityGateway;
    }

    public void delete(ISQLEntityGateway entityGateway) {
        databaseAPI.open();
        databaseAPI.delete(entityGateway.tableName(), entityGateway.selectString());
        databaseAPI.close();
    }



}

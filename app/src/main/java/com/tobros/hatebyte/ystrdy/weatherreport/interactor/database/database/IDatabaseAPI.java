package com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.database;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by scott on 12/16/14.
 */
public interface IDatabaseAPI {
    public void open();
    public void close();
    public void clear();
    public long insert(String tableName, ContentValues contentValues);
    public Cursor get(String tableName, String[] projection, String select, String orderBy, String limit);
    public void delete(String tableName, String where);
}

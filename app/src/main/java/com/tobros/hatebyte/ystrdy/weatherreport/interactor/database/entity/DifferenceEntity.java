package com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entity;

import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entitygateway.RecordEG;

import java.util.Date;

/**
 * Created by scott on 12/11/14.
 */
public class DifferenceEntity {

    public static final String TABLE_NAME = "difference";
    public static final String COLUMN_NOW_RECORD_ID = "record_id";
    public static final String COLUMN_DIFFERENCE = "difference";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_ID = "_id";

    public float difference;
    public long recordId;
    public Date date;
    public RecordEG nowRecordGateway;
    public Integer id;

}

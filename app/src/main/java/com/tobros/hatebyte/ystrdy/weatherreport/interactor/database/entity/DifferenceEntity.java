package com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entity;

import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entitygateway.RecordEG;

import java.util.Date;

/**
 * Created by scott on 12/11/14.
 */
public class DifferenceEntity {

    public float difference;
    public long recordId;
    public Date date;
    public RecordEG nowRecordGateway;
    public Integer id;

}

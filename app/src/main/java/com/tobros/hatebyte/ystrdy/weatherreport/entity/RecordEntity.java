package com.tobros.hatebyte.ystrdy.weatherreport.entity;

import java.util.Date;

/**
 * Created by scott on 12/11/14.
 */
public class RecordEntity {

    public static final String TABLE_NAME                           = "record";
    public static final String COLUMN_LATITUDE                      = "latitude";
    public static final String COLUMN_LONGITUDE                     = "longitude";
    public static final String COLUMN_DATE                          = "date";
    public static final String COLUMN_TEMPERATURE                   = "temperature";
    public static final String COLUMN_REGION_NAME                   = "region_name";
    public static final String COLUMN_CITY_NAME                     = "city_name";
    public static final String COLUMN_WOEID                         = "woeid";
    public static final String COLUMN_ID                            = "_id";

    public float latitude;
    public float longitude;
    public Date date;
    public float temperature;
    public String regionName;
    public String woeid;
    public String cityName;
    public int id;

}

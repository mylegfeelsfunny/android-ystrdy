package com.twobros.hatebyte.ystrdy.weatherreport.entity;

import android.location.Location;

import java.util.Date;

/**
 * Created by scott on 12/11/14.
 */
public class RecordEntity {

    public static float voidTemperature = -1000;

    public Location location;
    public Date date;
    public float temperature;
    public String regionName;
    public String woeid;
    public String cityName;
    public int id;

    public RecordEntity() {
        temperature = RecordEntity.voidTemperature;
        location = new Location("nil provider");
    }

}

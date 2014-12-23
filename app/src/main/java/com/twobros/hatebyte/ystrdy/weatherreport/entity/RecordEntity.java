package com.twobros.hatebyte.ystrdy.weatherreport.entity;

import android.location.Location;

import java.text.SimpleDateFormat;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("City : ");
        sb.append(cityName);
        sb.append("\n");
        sb.append("Temperature : ");
        sb.append(temperature);
        sb.append("\n");
        sb.append("At : ");
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        sb.append(DATE_FORMAT.format(date));
        return sb.toString();
    }

}

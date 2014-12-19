package com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.entitygateway;

import android.util.Log;

import com.twobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.implementation.JSONEGI;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by scott on 12/18/14.
 */
public abstract class AbstractWeatherGateway implements JSONEGI.IJSONEntityGateway {

    private static final String TAG = " AbstractWeatherGateway";

    protected JSONEGI jsonGatewayImp;
    protected RecordEntity recordEntity;
    protected JSONObject json = null;

    public AbstractWeatherGateway() {
        jsonGatewayImp = getEntityGateway();
    }

    public JSONEGI getEntityGateway() {
        return new JSONEGI();
    }

    public RecordEntity requestData(RecordEntity re) {
        recordEntity = re;
        JSONObject json = jsonGatewayImp.get(this);
        if (json != null) {
            mapFromJSON(json);
        }
        return recordEntity;
    }

    public static String valueStringForKey(JSONObject json, String key) {
        String value = null;
        try {
            value = (String) json.get(key);
        } catch (JSONException e) {
            Log.e(TAG, "JSONException " + TAG + ": valueStringForKey" + e);
        } finally {
            return value;
        }
    }

    public Boolean isValid() {
        return false;
    }

    public void mapFromJSON(JSONObject json) {

    }

}
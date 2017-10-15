package org.owm;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherInfo extends WeatherRequest {
    public String city;
    public String country;

    public static String getCityFromJSON(JSONObject jsonObject) {
        String result;
        try {
            result = jsonObject.getJSONObject("city").get("name").toString();
        } catch (JSONException e) {
            result = jsonObject.get("name").toString();
        }
        return result;
    }

    public static String getCountryFromJSON(JSONObject jsonObject) {
        String result;
        try {
            result = jsonObject.getJSONObject("city").get("country").toString();
        } catch (JSONException e) {
            result = jsonObject.getJSONObject("sys").get("country").toString();
        }
        return result;
    }

    public static String getCountryIDFromJSON(JSONObject jsonObject) {
        String result;
        try {
            result = jsonObject.getJSONObject("city").get("ID").toString();
        } catch (JSONException e) {
            result = jsonObject.get("ID").toString();
        }
        return result;
    }

    public static String getCoordinatesFromJSON(JSONObject jsonObject) {
        String result;
        try {
            result = jsonObject.getJSONObject("city").getJSONObject("coord").get("lon").toString() + ", " +
                    jsonObject.getJSONObject("city").getJSONObject("coord").get("lat").toString();
        } catch (JSONException e) {
            result = jsonObject.getJSONObject("coord").get("lon").toString() + ", " +
                    jsonObject.getJSONObject("coord").get("lat").toString();
        }
        return result;
    }

    public static void main(String[] args) {
        JSONObject jsForecast = WeatherRequest.getJSON("Tallinn", "EE", 1, "metric");
        System.out.println(getCoordinatesFromJSON(jsForecast));
    }
}

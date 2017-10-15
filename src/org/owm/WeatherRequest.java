package org.owm;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherRequest {
    private static final String openWeatherMapCurrent = "https://api.openweathermap.org/data/2.5/weather?q=%s&units=%s";
    private static final String openWeatherMap5Day = "https://api.openweathermap.org/data/2.5/forecast?q=%s,%s&units=%s";
    private static String apiKey = "5d4f58cb84fdb1ecb383a02c099fd81c";

//    public WeatherRequest(String apiKey) {
//        this.apiKey = apiKey;
//    }

    public static JSONObject getJSON(String city, String countryID, int type, String unit) {
        try {
            String url = "";
            unit = unit.toLowerCase();

            // checks if user wants current weather or a forecast
            if (type == 0) {
                url = openWeatherMapCurrent;
            } else if (type == 1) {
                url = openWeatherMap5Day;
            }

            // opens connection to API url
            URL apiURL = new URL(String.format(url, city, countryID, unit));

            // starts request
            HttpURLConnection apiConnection = (HttpURLConnection) apiURL.openConnection();

            // request with API key
            apiConnection.addRequestProperty("x-api-key", apiKey);

            // reads from API
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            apiConnection.getInputStream(), "utf-8"), 8);
            StringBuilder JSON = new StringBuilder(2048);
            String tmp;
            while ((tmp = reader.readLine()) != null) {
                JSON.append(tmp).append("");
            }
            reader.close();

            return new JSONObject(JSON.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        System.out.println(getJSON("Tallinn", "EE", 0, "metric"));
        System.out.println(getJSON("Tallinn", "EE", 1, "metric"));
        JSONObject js = getJSON("Tallinn", "EE", 1, "metric");
        System.out.println(js.get("city"));
        for (int i = 0; i < 2; i++) {
            System.out.println(js.getJSONObject("city").get("id"));
        }
    }
}

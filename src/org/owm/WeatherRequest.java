package org.owm;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WeatherRequest {
    public static final String openWeatherMapCurrent = "https://api.openweathermap.org/data/2.5/weather?q=%s&units=%s";
    public static final String openWeatherMap5Day = "https://api.openweathermap.org/data/2.5/forecast?q=%s&units=%s";
    private static String apiKey = "5d4f58cb84fdb1ecb383a02c099fd81c";
    static String inputFile = "src/org/owm/input.txt";

    public static List<String> readLinesFromFile(String fileName) {
        List<String> listOfLines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                listOfLines.add(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return listOfLines;
    }

    public static JSONObject getWeatherJSON(String city, String url) {
        return getJSON(city, url, "metric");
    }

    public static JSONObject getJSON(String city, String url, String unit) {
        try {
            unit = unit.toLowerCase();
            // opens connection to API url
            URL apiURL = new URL(String.format(url, city, unit));
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
        System.out.println(getWeatherJSON("Tallinn", openWeatherMapCurrent));
        System.out.println(getWeatherJSON("Tallinn", openWeatherMap5Day));
    }
}

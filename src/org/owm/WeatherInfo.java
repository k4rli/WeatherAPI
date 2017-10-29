package org.owm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.text.Collator;
import java.util.*;

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
            result = jsonObject.getJSONObject("city").getJSONObject("coord").get("lat").toString() + ", " +
                    jsonObject.getJSONObject("city").getJSONObject("coord").get("lon").toString();
        } catch (JSONException e) {
            result = jsonObject.getJSONObject("coord").get("lat").toString() + ", " +
                    jsonObject.getJSONObject("coord").get("lon").toString();
        }
        return result;
    }

    public static Double getCurrentTempFromCurrentWeatherJSON(JSONObject jsonObject) {
        Double result = 273.0;
        try {
            Double temp = Double.parseDouble(jsonObject.getJSONObject("main").get("temp").toString()) - 273.15;
            result = (double) Math.round(temp * 100) / 100;
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static Map<String, List<Double>> getAllTempsForEveryDayFromForecastJSON(JSONObject jsonObject) {
        Map<String, List<Double>> datesAndTemperatures = new HashMap<>();
        JSONArray dataPointArray = jsonObject.getJSONArray("list");
        try {
            for (int i = 0; i < dataPointArray.length(); i++) {
                JSONObject dataPoint = dataPointArray.getJSONObject(i);
                String date = dataPoint.get("dt_txt").toString().substring(0, 10);
                Double temp = Double.parseDouble(dataPoint.getJSONObject("main").get("temp").toString());
                List<Double> tempList;

                if (!datesAndTemperatures.containsKey(date)) {
                    tempList = new ArrayList<>();
                    tempList.add(temp);
                    datesAndTemperatures.put(date, tempList);
                } else {
                    tempList = datesAndTemperatures.get(date);
                    tempList.add(temp);
                    datesAndTemperatures.put(date, tempList);
                }
            }
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }
        return datesAndTemperatures;
    }

    public static void writeHighsAndLowsForEveryDayFromForecastToFile(Map<String, List<Double>> forecast) {
        Writer writer;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("output.txt"), "utf-8"));
            for (Map.Entry<String, List<Double>> entry : forecast.entrySet()) {
                String key = entry.getKey();
                Double max = Collections.max(entry.getValue());
                Double min = Collections.min(entry.getValue());
                writer.write("Date: " + key + ", lowest temp: " + min + ", highest temp: " + max + "\n");
            }
            writer.close();
            sortFile("output.txt", "output.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sortFile(String inputFileName, String outputFileName) throws IOException {
        FileReader reader = new FileReader(inputFileName);
        BufferedReader buffReader = new BufferedReader(reader);
        List<String> lines = new ArrayList<>();
        String line;

        while ((line = buffReader.readLine()) != null) {
            lines.add(line);
        }
        buffReader.close();
        Collections.sort(lines, Collator.getInstance());
        FileWriter writer = new FileWriter(outputFileName);

        for (String str : lines) {
            writer.write(str + "\n");
        }
        writer.close();
    }

    public static void getAllWeather(JSONObject currentWeather, JSONObject forecastWeather) {
        try {
            BufferedWriter buffWriter = new BufferedWriter(new FileWriter("output.txt", true));
            writeHighsAndLowsForEveryDayFromForecastToFile(getAllTempsForEveryDayFromForecastJSON(forecastWeather));
            buffWriter.write("Current temperature is " + getCurrentTempFromCurrentWeatherJSON(currentWeather) +
                                " degrees.\n");
            buffWriter.write("Location: " + getCityFromJSON(currentWeather) + ", " +
                    getCountryFromJSON(currentWeather) + "; geo coordinates: " + getCoordinatesFromJSON(currentWeather));
            buffWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    public static void main(String[] args) {
//        JSONObject jsForecast = WeatherRequest.getJSON("Tallinn", "EE", 1, "metric");
//        JSONObject jsCurrent = WeatherRequest.getJSON("Tallinn", "EE", 0, "metric");
//        System.out.println(jsForecast);
//        getAllWeather(jsCurrent, jsForecast);
//    }
}

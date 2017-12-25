package org.owm;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.util.*;

public class WeatherInfo extends WeatherRequest {
    // gets name of city from json, example: Tallinn
    public static String getCityFromJSON(JSONObject jsonObject) {
        String result;
        try {
            result = jsonObject.getJSONObject("city").get("name").toString();
        } catch (JSONException e) {
            result = jsonObject.get("name").toString();
        }
        return result;
    }
    // example: EE
    public static String getCountryCodeFromJSON(JSONObject jsonObject) {
        String result;
        try {
            result = getCorrectCountryValue(jsonObject, "sys");
        } catch (JSONException e) {
            result = getCorrectCountryValue(jsonObject, "city");
        }
        return result;
    }
    // gets coordinates from json, example: 59.44, 24.75
    public static String getCoordinatesFromJSON(JSONObject jsonObject) {
        String result;
        try {
            result = coordinatesIfCurrentWeather(jsonObject);
        } catch (JSONException e) {
            result = coordinatesIfForecastWeather(jsonObject);
        }
        return result;
    }

    public static String getCorrectCountryValue(JSONObject jsonObject, String value) {
        return jsonObject.getJSONObject(value).get("country").toString();
    }

    public static String coordinatesIfCurrentWeather(JSONObject jsonObject) {
        return jsonObject.getJSONObject("coord").get("lat").toString() + ", " +
                jsonObject.getJSONObject("coord").get("lon").toString();
    }

    public static String coordinatesIfForecastWeather(JSONObject jsonObject) {
        return jsonObject.getJSONObject("city").getJSONObject("coord").get("lat").toString() + ", " +
                jsonObject.getJSONObject("city").getJSONObject("coord").get("lon").toString();
    }

    // gets current temp from json, example: -2.0
    public static Double getCurrentTempFromCurrentWeatherJSON(JSONObject jsonObject) {
        Double result = null;
        try {
            Double temp = getTemperatureFromDataPoint(jsonObject);
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
                String date = getDateFromDataPoint(dataPoint);
                Double temp = getTemperatureFromDataPoint(dataPoint);
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

    public static String getDateFromDataPoint(JSONObject jsonObject) {
        return jsonObject.get("dt_txt").toString().substring(0, 10);
    }

    public static double getTemperatureFromDataPoint(JSONObject jsonObject) {
        return Double.parseDouble(jsonObject.getJSONObject("main").get("temp").toString());
    }

    public static String highsAndLowsForEveryDayFromForecastToFile(Map<String, List<Double>> forecast) {
         StringBuffer output = new StringBuffer();
         String result = "\n";
         for (Map.Entry<String, List<Double>> entry : forecast.entrySet()) {
             String key = entry.getKey();
             Double max = Collections.max(entry.getValue());
             Double min = Collections.min(entry.getValue());
             output.append(result + "Date: " + key + ", lowest temp: " + min + "°C, highest temp: " + max + "°C");
         }
        return output.toString();
    }

    public static String currentTempAsString(JSONObject JSON) {
        return "Current temperature is " + getCurrentTempFromCurrentWeatherJSON(JSON) + "°C.\n";
    }
    public static String getLocationAndCoordinatesFromJSON(JSONObject JSON) {
        return "Location: " + getCityFromJSON(JSON) + ", " + getCountryCodeFromJSON(JSON) + "; geo coordinates: " +
                getCoordinatesFromJSON(JSON);
    }

    // handles writing weather forecast and current info to specific file
    public static void getAllWeather(JSONObject currentWeather, JSONObject forecastWeather, String outputFileName) {
        try {
            BufferedWriter buffWriter = new BufferedWriter(new FileWriter(outputFileName, false));
            buffWriter.write(currentTempAsString(currentWeather));
            buffWriter.write(getLocationAndCoordinatesFromJSON(currentWeather));
            buffWriter.write(highsAndLowsForEveryDayFromForecastToFile(getAllTempsForEveryDayFromForecastJSON(forecastWeather)));
            buffWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void weatherForCitiesFromInput() {
        List<String> cities = WeatherRequest.readLinesFromFile(inputFile);
        for (String city : cities) {
            JSONObject jsCurrent = WeatherRequest.getWeatherJSON(city ,openWeatherMapCurrent);
            JSONObject jsForecast = WeatherRequest.getWeatherJSON(city, openWeatherMap5Day);
            getAllWeather(jsCurrent, jsForecast, "output/" + city + ".txt");
        }
    }

    public static void main(String[] args) {
        weatherForCitiesFromInput();
        JSONObject jsForecast = WeatherRequest.getWeatherJSON("Tallinn", openWeatherMap5Day);
//        JSONObject forecastFatHead = WeatherRequest.getWeatherJSON("London", openWeatherMap5Day);
//        JSONObject currentExtraFatHead = WeatherRequest.getWeatherJSON("London", openWeatherMapCurrent);
//        System.out.println(forecastFatHead);
//        System.out.println(currentExtraFatHead);
//        System.out.println(highsAndLowsForEveryDayFromForecastToFile(getAllTempsForEveryDayFromForecastJSON(jsForecast)));
    }
}

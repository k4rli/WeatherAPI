package org.owm;

public class Weather {
    private static double currentTemp = 0;
    private static double threeDayLow = 0;
    private static double threeDayHigh = 0;
    private static String coordinates = "000:000";

    //CurrentWeather getCurrentWeather()

    public Weather() {
    }

    public void requestWeather() {

    }

    public static double getCurrentTemp() {
        return currentTemp;
    }

    public static double getThreeDayLow() {
        return threeDayLow;
    }

    public static double getThreeDayHigh() {
        return threeDayHigh;
    }

    public static String getCoordinates() {
        return coordinates;
    }
}

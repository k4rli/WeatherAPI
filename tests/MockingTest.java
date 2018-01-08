import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;
import org.owm.WeatherInfo;
import java.io.*;

public class MockingTest {
    private static String forecastData = "./tests/json_data_mock/forecast_mock_1.json";
    private static String currentData = "./tests/json_data_mock/current_mock_1.json";

    private final JSONObject forecast = readJsonFileToObject(forecastData);
    private final JSONObject current = readJsonFileToObject(currentData);

    private static JSONObject readJsonFileToObject(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuilder JSON = new StringBuilder(2048);
            String tmp;
            while ((tmp = reader.readLine()) != null) {
                JSON.append(tmp).append("");
            }
            reader.close();
            return new JSONObject(JSON.toString());
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }

    @Test
    public void getCorrectCountryCodeFromForecast() throws Exception {
        String givenValue = WeatherInfo.getCountryCodeFromJSON(forecast);
        assertEquals(givenValue, "GB");
    }

    @Test
    public void getCoordinatesFromForecast() throws Exception {
        String givenValue = WeatherInfo.getCoordinatesFromJSON(forecast);
        assertEquals(givenValue, "51.5073, -0.1277");
    }

    @Test
    public void getCoordinatesFromCurrent() throws Exception {
        String givenValue = WeatherInfo.getCoordinatesFromJSON(current);
        assertEquals(givenValue, "51.51, -0.13");
    }

    @Test
    public void getLocationNameAndCoordsForecast() throws Exception {
        String givenValue = WeatherInfo.getLocationAndCoordinatesFromJSON(forecast);
        assertEquals(givenValue, "Location: London, GB; geo coordinates: 51.5073, -0.1277");
    }

    @Test
    public void getLocationNameAndCoordsCurrent() throws Exception {
        String givenValue = WeatherInfo.getLocationAndCoordinatesFromJSON(current);
        assertEquals(givenValue, "Location: London, GB; geo coordinates: 51.51, -0.13");
    }

    @Test
    public void getCurrentTemperatureFromCurrent() throws Exception {
        if (current != null) {
            Double givenValue = WeatherInfo.getTemperatureFromDataPoint(current);
            assertEquals(9.74, givenValue, 0);
        }
    }

    @Test
    public void getFirstDateFromForecast() throws Exception {
        if (forecast != null) {
            String givenValue = WeatherInfo.getDateFromDataPoint(forecast.getJSONArray("list").getJSONObject(0));
            assertEquals(givenValue, "2017-12-25");
        }
    }

    @Test
    public void getSecondDateFromForecast() throws Exception {
        if (forecast != null) {
            String givenValue = WeatherInfo.getDateFromDataPoint(forecast.getJSONArray("list").getJSONObject(5));
            assertEquals(givenValue, "2017-12-26");
        }
    }

    @Test
    public void getThirdDateFromForecast() throws Exception {
        if (forecast != null) {
            String givenValue = WeatherInfo.getDateFromDataPoint(forecast.getJSONArray("list").getJSONObject(10));
            assertEquals(givenValue, "2017-12-26");
        }
    }

    @Test
    public void getTemperatureFromForecastFirst() throws Exception {
        Double givenValue = WeatherInfo.getTemperatureFromDataPoint(forecast.getJSONArray("list").getJSONObject(0));
        assertEquals(10.71, givenValue, 0);
    }

    @Test
    public void getTemperatureFromForecastSecond() throws Exception {
        Double givenValue = WeatherInfo.getTemperatureFromDataPoint(forecast.getJSONArray("list").getJSONObject(5));
        assertEquals(4.65, givenValue, 0);
    }

    @Test
    public void getTemperatureFromForecastThird() throws Exception {
        Double givenValue = WeatherInfo.getTemperatureFromDataPoint(forecast.getJSONArray("list").getJSONObject(10));
        assertEquals(7.14, givenValue, 0);
    }
}

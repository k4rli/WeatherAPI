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
        assertEquals(WeatherInfo.getCountryCodeFromJSON(forecast), "GB");
    }

    @Test
    public void getCoordinatesFromForecast() throws Exception {
        assertEquals(WeatherInfo.getCoordinatesFromJSON(forecast), "51.5073, -0.1277");
    }

    @Test
    public void getCoordinatesFromCurrent() throws Exception {
        assertEquals(WeatherInfo.getCoordinatesFromJSON(current), "51.51, -0.13");
    }

    @Test
    public void getLocationNameAndCoordsForecast() throws Exception {
        assertEquals(WeatherInfo.getLocationAndCoordinatesFromJSON(forecast),
                "Location: London, GB; geo coordinates: 51.5073, -0.1277");
    }

    @Test
    public void getLocationNameAndCoordsCurrent() throws Exception {
        assertEquals(WeatherInfo.getLocationAndCoordinatesFromJSON(current),
                "Location: London, GB; geo coordinates: 51.51, -0.13");
    }

    @Test
    public void getCurrentTemperatureFromCurrent() throws Exception {
        if (current != null) {
            assertEquals(9.74, WeatherInfo.getTemperatureFromDataPoint(current), 0);
        }
    }
}

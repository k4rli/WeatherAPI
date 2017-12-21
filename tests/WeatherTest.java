import org.json.JSONObject;
import org.junit.Test;
import org.owm.WeatherInfo;
import org.owm.WeatherRequest;
import static org.junit.Assert.*;

public class WeatherTest extends WeatherRequest {
    private JSONObject jsCurrent = WeatherRequest.getWeatherJSON("Tallinn", openWeatherMapCurrent);
    private JSONObject jsForecast = WeatherRequest.getWeatherJSON("Tallinn", openWeatherMap5Day);

    @Test
    public void getCurrentTemp() {
    }

    @Test
    public void checkIfJSONRightCityForForecast() {
        try {
            assertEquals(WeatherInfo.getCityFromJSON(jsCurrent).toLowerCase(), "tallinn");
        } catch (Exception e) {
            fail("Error: " + e.getMessage());
        }
    }

    @Test
    public void checkIfJSONRightCityForCurrentWeather() {
        try {
            assertEquals(WeatherInfo.getCityFromJSON(jsForecast).toLowerCase(), "tallinn");
        } catch (Exception e) {
            fail("Error: " + e.getMessage());
        }
    }

    @Test
    public void checkIfJSONRightCountryForForecast() {
        try {
            assertEquals(WeatherInfo.getCountryFromJSON(jsCurrent).toLowerCase(), "ee");
        } catch (Exception e) {
            fail("Error: " + e.getMessage());
        }
    }

    @Test
    public void checkIfJSONRightCountryForCurrentWeather() {
        try {
            assertEquals(WeatherInfo.getCountryFromJSON(jsForecast).toLowerCase(), "ee");
        } catch (Exception e) {
            fail("Error: " + e.getMessage());
        }
    }

    @Test
    public void checkIfCoordinatesAreCorrectForCurrentWeather() throws Exception {
        try {
            assertEquals(WeatherInfo.getCoordinatesFromJSON(jsCurrent), "59.44, 24.75");
        } catch (Exception e) {
            fail("Error: " + e.getMessage());
        }
    }

    @Test
    public void checkIfCoordinatesAreCorrectForForecast() throws Exception {
        try {
            assertEquals(WeatherInfo.getCoordinatesFromJSON(jsForecast), "59.4372, 24.7454");
        } catch (Exception e) {
            fail("Error: " + e.getMessage());
        }
    }
}
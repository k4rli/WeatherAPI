import org.json.JSONObject;
import org.junit.Test;
import org.owm.Weather;
import org.owm.WeatherInfo;
import org.owm.WeatherRequest;
import static org.junit.Assert.*;

public class WeatherTest {
    private JSONObject jsCurrent = WeatherRequest.getJSON("Tallinn", "EE", 0, "metric");
    private JSONObject jsForecast = WeatherRequest.getJSON("Tallinn", "EE", 1, "metric");

    @Test
    public void getCurrentTemp() {
    }

    @Test
    public void getThreeDayLowNotNull() {
        try {
            double low = Weather.getThreeDayLow();
            assertFalse(Double.isNaN(low));
        } catch (Exception e) {
            fail("Error: " + e.getMessage());
        }
    }

    @Test
    public void getCurrentTempNotNull() {
        try {
            double current = Weather.getCurrentTemp();
            assertFalse(Double.isNaN(current));
        } catch (Exception e) {
            fail("Error: " + e.getMessage());
        }

    }

    @Test
    public void getThreeDayHighNotNull() {
        try {
            double high = Weather.getThreeDayHigh();
            assertFalse(Double.isNaN(high));
        } catch (Exception e) {
            fail("Error: " + e.getMessage());
        }
    }

    @Test
    public void getCoordinatesFormatCorrect(){
        try {
            String re = "^([0-9])+(:)+([0-9]+)";
            String data = Weather.getCoordinates();
            assertTrue(data.matches(re));
        } catch (Exception e) {
            fail("Error: " + e.getMessage());
        }
    }

    @Test
    public void getCoordinatesExist() {
        try {
            String data = Weather.getCoordinates();
            assertTrue(!data.isEmpty());
        } catch (Exception e) {
            fail("Error: " + e.getMessage());
        }
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
            assertEquals(WeatherInfo.getCoordinatesFromJSON(jsCurrent), "59.44, 59.4424.75");
        } catch (Exception e) {
            fail("Error: " + e.getMessage());
        }
    }

    @Test
    public void checkIfCoordinatesAreCorrectForForecast() throws Exception {
        try {
            assertEquals(WeatherInfo.getCoordinatesFromJSON(jsForecast), "59.437, 24.7535");
        } catch (Exception e) {
            fail("Error: " + e.getMessage());
        }
    }
}
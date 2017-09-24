import org.junit.Test;
import static org.junit.Assert.*;

public class WeatherTest {
    @Test
    public void getCurrentTemp() throws Exception {
    }

    @Test
    public void getThreeDayLowNotNull() throws Exception {
        double low = Weather.getThreeDayLow();
        assert(!Double.isNaN(low));
    }

    @Test
    public void getCurrentTempNotNull() throws Exception {
        double current = Weather.getCurrentTemp();
        assert(!Double.isNaN(current));
    }

    @Test
    public void getThreeDayHighNotNull() throws Exception {
        double high = Weather.getThreeDayHigh();
        assert(!Double.isNaN(high));
    }

    @Test
    public void getCoordinatesFormatCorrect() throws Exception {
        String re = "^([0-9])+(:)+([0-9]+)";
        String data = Weather.getCoordinates();
        assert(data.matches(re));
    }

    @Test
    public void getCoordinatesExist() throws Exception {
        String data = Weather.getCoordinates();
        assert(!data.isEmpty());
    }
}
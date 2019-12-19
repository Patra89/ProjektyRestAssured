import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ISSPassTimesTests {

    private ApiResponse res;
    float validLat = 50.0f;
    float validLon = 20.0f;

    @Test(priority = 1, alwaysRun = true)
    public void checkSchemaAndResponseCode(){
        res = new ApiResponse(Constants.ISS_PASS_TIMES_URL, validLat,validLon);
        res.checkIfStatusCodeEqualTo(200);
        res.checkIfBodyMatchesSchema(Constants.ISS_PASS_TIMES_SCHEMA);
    }

    @Test(priority = 2, dependsOnMethods = "checkSchemaAndResponseCode")
    public void checkIfNumberMatchesLengthOfPeopleArray(){
        res.checkIfArraySizeIsEqualTo("response", res.getValueForKey("request.passes"));
    }

    @Test(priority = 3, dependsOnMethods = "checkSchemaAndResponseCode")
    public void checkIfLatTheSameAsInRequest(){
        res.checkIfValueEqualTo("request.latitude", validLat);
    }

    @Test(priority = 3, dependsOnMethods = "checkSchemaAndResponseCode")
    public void checkIfLonTheSameAsInRequest(){
        res.checkIfValueEqualTo("request.longitude", validLon);
    }

    @Test(priority = 3, dependsOnMethods = "checkSchemaAndResponseCode")
    public void checkIfPassTimesAreInFuture() {
        res.checkIfPassTimesAreGreaterThenCurrentTime("request.datetime","response.risetime");
    }

    @Test(priority = 4, dataProvider = "validLatLon")
    public void checkResponseCodesForBoundaryValues(float validLat, float validLon){
        res = new ApiResponse(Constants.ISS_PASS_TIMES_URL, validLat, validLon);
        res.checkIfStatusCodeEqualTo(200);
    }

    @Test(priority = 5, dataProvider = "invalidLatLon")
    public void checkResponseCodesForInvalidValues(float validLat, float validLon) {
        res = new ApiResponse(Constants.ISS_PASS_TIMES_URL, validLat, validLon);
        res.checkIfStatusCodeEqualTo(400);
        res.checkIfBodyMatchesSchema(Constants.ISS_INVALID_LAT_LON_SCHEMA);
    }

    @DataProvider(name = "validLatLon")
    public Object[][] getValidGeolocation(){
        return new Object[][]{{0.0f, 0.0f}, {Constants.MIN_LAT, 0.0f}, {Constants.MAX_LAT, 0.0f}, {0.0f, Constants.MAX_LON},
                {0.0, Constants.MIN_LON}, {Constants.MAX_LAT, Constants.MAX_LON}, {Constants.MIN_LAT, Constants.MAX_LON},
                {Constants.MIN_LON, Constants.MIN_LON}, {Constants.MAX_LAT, Constants.MIN_LON}};
    }

    @DataProvider(name = "invalidLatLon")
    public Object[][] getInvalidGeolocation(){
        return new Object[][]{{Constants.OVER_MIN_LAT, 0.0f}, {Constants.OVER_MAX_LAT, 0.0f}, {0.0f, Constants.OVER_MAX_LON},
                {0.0, Constants.OVER_MIN_LON}, {Constants.OVER_MAX_LAT, Constants.OVER_MAX_LON}, {Constants.OVER_MIN_LAT,
                Constants.OVER_MAX_LON}, {Constants.OVER_MIN_LON, Constants.OVER_MIN_LON}, {Constants.OVER_MAX_LAT,
                Constants.OVER_MIN_LON}};
    }

}

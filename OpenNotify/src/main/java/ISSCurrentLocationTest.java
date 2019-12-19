import org.testng.annotations.Test;

import java.time.Instant;

public class ISSCurrentLocationTest {

    private ApiResponse res;
    long currentEpochTime;
    long fiveSecondsAgo;

    @Test(priority = 1, alwaysRun = true)
    public void checkSchemaAndResponseCode(){
        res = new ApiResponse(Constants.ISS_CURRENT_LOCATION_URL);
        currentEpochTime = Instant.now().getEpochSecond();
        fiveSecondsAgo = currentEpochTime-5;
        res.checkIfStatusCodeEqualTo(200);
        res.checkIfBodyMatchesSchema(Constants.ISS_CURRENT_LOCATION_SCHEMA);
    }

    @Test(priority = 2, dependsOnMethods = "checkSchemaAndResponseCode")
    public void checkIfLatitudeWithinRange(){
        res.checkIfValueWithinRange("iss_position.latitude", (long)Constants.MIN_LAT, (long)Constants.MAX_LAT);
    }

    @Test(priority = 2, dependsOnMethods = "checkSchemaAndResponseCode")
    public void checkIfLongitudeWithinRange(){
        res.checkIfValueWithinRange("iss_position.longitude", (long)Constants.MIN_LON, (long)Constants.MAX_LON);
    }

    @Test(priority = 2, dependsOnMethods = "checkSchemaAndResponseCode")
    public void checkIfTimestampWithinRange(){
        res.checkIfValueWithinRange("timestamp", fiveSecondsAgo, currentEpochTime);
    }

}

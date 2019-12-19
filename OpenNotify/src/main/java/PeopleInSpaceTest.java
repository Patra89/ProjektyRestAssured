import org.testng.annotations.Test;

public class PeopleInSpaceTest {

    private ApiResponse res;

    @Test(priority = 1, alwaysRun = true)
    public void checkSchemaAndResponseCode(){
        res = new ApiResponse(Constants.PEOPLE_IN_SPACE_URL);
        res.checkIfStatusCodeEqualTo(200);
        res.checkIfBodyMatchesSchema(Constants.PEOPLE_IN_SPACE_SCHEMA);
    }

    @Test(priority = 2, dependsOnMethods = "checkSchemaAndResponseCode")
    public void checkIfNumberMatchesLengthOfPeopleArray(){
        res.checkIfArraySizeIsEqualTo("people", res.getValueForKey("number"));
    }

}

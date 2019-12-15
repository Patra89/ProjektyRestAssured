import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class AutocompleteTest {

    String phrase = "house";
    private ApiResponse res;

    @Test(priority = 1)
    public void checkStatusCodeAndSchema() {
        res = new ApiResponse(Constants.AUTOCOMPLETE_URL,"query", phrase);
        res.checkIfStatusCodeEqualTo(200);
        res.checkIfBodyMatchesSchema(Constants.AUTOCOMPLETE_SCHEMA);
    }

    @Test(priority = 2, dependsOnMethods = "checkStatusCodeAndSchema")
    public void checkSize(){
        res.checkIfArraySizeIsEqualTo(Constants.DEFAULT_VALUE_AUTOCOMPLETE);
    }

    @Test(priority = 3, dependsOnMethods = "checkStatusCodeAndSchema")
    public void checkIfNoRepetitions(){
        assertThat(ArrayUtils.doesArrayContainRepetitions(res.getListOfValuesForKey("id")), is(false));
    }
}

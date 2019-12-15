import org.testng.annotations.Test;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class ItemsPerPageTest {

    private ApiResponse res;

    @Test(priority = 0)
    public void checkSchema() {
        res = new ApiResponse(Constants.DEFAULT_URL);
        res.checkIfStatusCodeEqualTo(200);
        res.checkIfBodyMatchesSchema(Constants.DEFAULT_SCHEMA);
        assertThat(ArrayUtils.doesArrayContainRepetitions(res.getListOfValuesForKey("id")), is(false));
    }

    @Test(priority = 1)
    public void checkDefaultArrayLength() {
        res.checkIfArraySizeIsEqualTo(Constants.DEFAULT_VALUE);
    }

    @Test(priority = 2)
    public void setArrayLengthToMaxValue() {
        res = new ApiResponse(Constants.DEFAULT_URL, "per_page", String.valueOf(Constants.MAX_VALUE));
        res.checkIfArraySizeIsEqualTo(Constants.MAX_VALUE);
    }

    @Test(priority = 3)
    public void setArrayLengthToNumberHigherThanMaxValue() {
        int tooBigValue = Constants.MAX_VALUE+1;
        res = new ApiResponse(Constants.DEFAULT_URL, "per_page", String.valueOf(tooBigValue));
        res.checkIfArraySizeIsEqualTo(Constants.MAX_VALUE);
    }

    @Test(priority = 4)
    public void setArrayLengthToMinValue() {
        res = new ApiResponse(Constants.DEFAULT_URL,"per_page", String.valueOf(Constants.MIN_VALUE));
        res.checkIfArraySizeIsEqualTo(Constants.MIN_VALUE);
    }

    @Test(priority = 5, dataProvider = "invalidPerPage", dataProviderClass = DataProviders.class)
    public void setArrayLenghtToInvalidValue(String invalidValue) {
        res = new ApiResponse(Constants.DEFAULT_URL, "per_page", invalidValue);
        res.checkIfArraySizeIsEqualTo(Constants.DEFAULT_VALUE);
    }

}

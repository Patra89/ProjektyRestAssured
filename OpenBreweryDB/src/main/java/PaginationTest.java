import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PaginationTest {

    private ApiResponse res;

    @Test(priority = 1)
    public void setArrayLengthToMaxValue() {
        res = new ApiResponse(Constants.DEFAULT_URL, "per_page","1");
        res.checkIfStatusCodeEqualTo(200);
        res.checkIfBodyMatchesSchema(Constants.DEFAULT_SCHEMA);
        assertThat(ArrayUtils.doesArrayContainRepetitions(res.getListOfValuesForKey("id")), is(false));

        ArrayList<String> firstPageResults = res.getListOfValuesForKey("id");
        int initialSize = firstPageResults.size();

        res = new ApiResponse(Constants.DEFAULT_URL, "per_page","2");
        ArrayList<String> secondPageResults = res.getListOfValuesForKey("id");

        //if no items get removed and the size is the same, as in the beggining, then it means the pages have different items
        firstPageResults.remove(secondPageResults);
        assertThat(firstPageResults.size(), equalTo(initialSize));
    }
}

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SearchTest {

    String phrase = "house"; //common word, should give a lot of results when used with search function
    private ApiResponse res;

    @Test(priority = 1)
    public void checkStatusCodeAndSchema() {
        res = new ApiResponse(Constants.SEARCH_URL, "query", phrase);
        res.checkIfStatusCodeEqualTo(200);
        res.checkIfBodyMatchesSchema(Constants.DEFAULT_SCHEMA);
        assertThat(ArrayUtils.doesArrayContainRepetitions(res.getListOfValuesForKey("id")), is(false));
    }

    @Test(priority = 2, dataProvider = "getObjects")
    public void checkIfResponseContainPhrase(Object obj){
        assertThat(res.doesObjectContainPhrase((LinkedHashMap)obj, phrase), is(true));
    }

    @DataProvider(name = "getObjects")
    public Object[] getObjectsFromResponse(){
        return res.getListOfObjects();
    }

}

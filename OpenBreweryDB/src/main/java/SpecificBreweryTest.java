import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;


public class SpecificBreweryTest {

    private static int maxId;
    private static int minId;
    private ApiResponse res;

    @BeforeClass
    public void getIdRange(){
        minId = getMinId();
        maxId = getMaxId();
    }

    @Test(priority = 1)
    public void breweryWithMinId() {
        res = new ApiResponse(Constants.DEFAULT_URL+"/"+minId);
        res.checkIfStatusCodeEqualTo(200);
        res.checkIfBodyMatchesSchema(Constants.SPECIFIC_BREWERY_SCHEMA);
        res.checkValueForKey("id", minId);
    }

    @Test(priority = 2)
    public void breweryWithMaxId() {
        res = new ApiResponse(Constants.DEFAULT_URL+"/"+maxId);
        res.checkIfStatusCodeEqualTo(200);
        res.checkIfBodyMatchesSchema(Constants.SPECIFIC_BREWERY_SCHEMA);
        res.checkValueForKey("id", maxId);
    }

    @Test(priority = 3, dataProvider = "invalidIds")
    public void breweryWithInvalidId(String invalidId) {
        res = new ApiResponse(Constants.DEFAULT_URL+"/"+invalidId);
        res.checkIfStatusCodeEqualTo(404);
        res.checkIfBodyMatchesSchema(Constants.INVALID_ID_SCHEMA);
    }

    private int getMinId(){
        res = new ApiResponse(Constants.DEFAULT_URL, "sort","id");
        ArrayList<Integer> results = res.getListOfValuesForKey("id");
        return results.get(0);
    }
    private int getMaxId(){
        res = new ApiResponse(Constants.DEFAULT_URL, "sort","-id");
        ArrayList<Integer> results = res.getListOfValuesForKey("id");
        return results.get(0);
    }

    @DataProvider(name = "invalidIds")
    private Object[] getInvalidIds(){
        return new String[]{String.valueOf(maxId+1), String.valueOf(minId-1), String.valueOf(-1), String.valueOf(0),
                String.valueOf(Integer.MAX_VALUE+1), String.valueOf(Long.MAX_VALUE+1)};
    }
}

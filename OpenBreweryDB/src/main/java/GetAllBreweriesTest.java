import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashSet;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class GetAllBreweriesTest {

    private ApiResponse res;
    @Test(priority = 1)
    public void getAllBreweries() {
        res = new ApiResponse(Constants.DEFAULT_URL);
        res.checkIfStatusCodeEqualTo(200);
        res.checkIfBodyMatchesSchema(Constants.DEFAULT_SCHEMA);
        assertThat(ArrayUtils.doesArrayContainRepetitions(res.getListOfValuesForKey("id")), is(false));
    }

    @Test(priority = 2, dependsOnMethods = "getAllBreweries")
    public void filterBreweriesByState() {
        String state = "Arizona";
        res = new ApiResponse(Constants.DEFAULT_URL, "by_state", state);

        res.checkIfStatusCodeEqualTo(200);
        res.checkIfBodyMatchesSchema(Constants.DEFAULT_SCHEMA);
        assertThat(ArrayUtils.doesArrayContainRepetitions(res.getListOfValuesForKey("id")), is(false));

        //if the size of states is 1 and it contains state value
        // then it means it all states in array are the same as value states
        ArrayList<String> list = res.getListOfValuesForKey("state");
        HashSet<String> states = new HashSet<>(list);
        assertThat(states.size(),equalTo(1));
        assertThat(states.contains(state), is(true));
    }

    @Test(priority = 3, dependsOnMethods = "getAllBreweries")
    public void filterBreweriesByCity() {
        String city = "Chicago";
        res = new ApiResponse(Constants.DEFAULT_URL, "by_city", city);
        res.checkIfStatusCodeEqualTo(200);
        res.checkIfBodyMatchesSchema(Constants.DEFAULT_SCHEMA);
        assertThat(ArrayUtils.doesArrayContainRepetitions(res.getListOfValuesForKey("id")), is(false));

        ArrayList<String> list = res.getListOfValuesForKey("city");
        HashSet<String> cities = new HashSet<>(list);
        assertThat(cities.size(),equalTo(1));
        assertThat(cities.contains(city), is(true));
    }


    @Test(priority = 3, dependsOnMethods = "getAllBreweries")
    public void filterBreweriesByName() {
        String name = "dog";
        res = new ApiResponse(Constants.DEFAULT_URL,"by_name", name);
        res.checkIfStatusCodeEqualTo(200);
        res.checkIfBodyMatchesSchema(Constants.DEFAULT_SCHEMA);
        assertThat(ArrayUtils.doesArrayContainRepetitions(res.getListOfValuesForKey("id")), is(false));

        ArrayList<String> list = res.getListOfValuesForKey("name");
        HashSet<String> names = new HashSet<>(list);
        for(String breweryName : names){
            assertThat(breweryName.toLowerCase().contains(name), is(true));
        }
    }

    @Test(priority = 4, dependsOnMethods = "getAllBreweries")
    public void filterBreweriesByType() {
        String type = "micro";
        res = new ApiResponse(Constants.DEFAULT_URL,"by_type", type);

        res.checkIfStatusCodeEqualTo(200);
        res.checkIfBodyMatchesSchema(Constants.DEFAULT_SCHEMA);
        assertThat(ArrayUtils.doesArrayContainRepetitions(res.getListOfValuesForKey("id")), is(false));

        ArrayList<String> list = res.getListOfValuesForKey("brewery_type");
        HashSet<String> types = new HashSet<>(list);
        assertThat(types.size(), equalTo(1));
        assertThat(types.contains(type), is(true));
    }

}


import org.testng.annotations.DataProvider;

public class DataProviders {

    @DataProvider(name = "invalidPerPage")
    public static Object[] getInvalidPerPage(){
        return new FileReader().readDataFromFile(Constants.INVALID_PER_PAGE_VALUES);
    }

    @DataProvider(name = "sortingParametersWithStringValues")
    public static Object[] getSortingParametersWithStringValues(){
        return new Object[]{"country","state","city","brewery_type"};
    }

}

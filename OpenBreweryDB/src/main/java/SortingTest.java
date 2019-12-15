
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.Collection;

public class SortingTest {

    private ApiResponse res;

    @Test(priority = 1, dataProviderClass = DataProviders.class, dataProvider = "sortingParametersWithStringValues")
    public void ascendingSortStringValues(String sortingParameter) {
        sortTest(String.class, sortingParameter,true);
    }

    @Test(priority = 1, dataProviderClass = DataProviders.class, dataProvider = "sortingParametersWithStringValues")
    public void descendingSortStringValues(String sortingParameter) {
        sortTest(String.class, sortingParameter,false);
    }
    @Test(priority = 2)
    public void descendingSortId(){
        sortTest(Integer.class,"id",false);
    }

    @Test(priority = 3)
    public void ascendingSortId(){
        sortTest(Integer.class,"id",true);
    }

    public <valueType> void sortTest(Class<valueType>valueTypeClass, String parameter, boolean asc){
        if(asc) {
            res = new ApiResponse(Constants.DEFAULT_URL, "sort", parameter);
        }else{
            res = new ApiResponse(Constants.DEFAULT_URL, "sort","-"+parameter);
        }
        ArrayList<valueType> results = res.getListOfValuesForKey(parameter);
        ArrayList<valueType> orderedCopy;

        if(valueTypeClass != Integer.class) {
            results = ArrayUtils.replaceNullValues(new ArrayList<>((Collection<? extends String>) results));
        }
        orderedCopy = new ArrayList<>(results);

        if(asc) {
            ArrayUtils.sortArray(orderedCopy);
        }else{
            ArrayUtils.reverseSortArray(orderedCopy);
        }
        ArrayUtils.compareTwoLists(results, orderedCopy);
    }

}

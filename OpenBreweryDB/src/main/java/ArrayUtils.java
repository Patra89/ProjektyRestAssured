import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class ArrayUtils {

    public static boolean doesArrayContainRepetitions(ArrayList list) {
        int listSize = list.size();
        int hashSetSize = new HashSet<>(list).size();
        return listSize > hashSetSize;
    }

    public static ArrayList replaceNullValues (ArrayList<String> results) {
        for (int i = 0; i < results.size(); i++) {
            if (results.get(i) == null) {
                results.set(i, "0");
            }
        }
        return results;
    }

    public static void sortArray(ArrayList list) {
        Collections.sort(list);
    }

    public static void reverseSortArray(ArrayList list) {
        Collections.sort(list);
        Collections.reverse(list);
    }

    public static void compareTwoLists(ArrayList list1, ArrayList list2){
        SoftAssert softAssert = new SoftAssert();
        for (int i = 0; i < list2.size(); i++) {
            softAssert.assertEquals(list1.get(i), list2.get(i));
        }
        softAssert.assertAll();
    }

}

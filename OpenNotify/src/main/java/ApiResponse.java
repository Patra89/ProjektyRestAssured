import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class ApiResponse{

    public io.restassured.response.Response res;

    public ApiResponse(String url, float lat, float lon) {
        RestAssured.baseURI = url;
        res = given()
                .contentType(Constants.RESPONSE_CONTENT_TYPE)
                .param("lat",lat)
                .param("lon", lon)
                .get();
    }

    public ApiResponse(String url) {
        RestAssured.baseURI = url;
        res = given()
                .contentType(Constants.RESPONSE_CONTENT_TYPE)
                .get();
    }


    public void checkIfStatusCodeEqualTo(int statusCode) {
        res.then().assertThat().statusCode(is(statusCode));
    }

    public void checkIfBodyMatchesSchema(String schema) {
        res.then().assertThat().body(matchesJsonSchemaInClasspath(schema));
    }

    public void checkIfArraySizeIsEqualTo(String arrayName, int size) {
        res.then().assertThat().body(arrayName+".size()", is(size));
    }

    public void checkIfValueWithinRange(String key, long min, long max){
        String value = res.then().extract().path(key).toString();
        float valueAsDouble = Float.valueOf(value);
        Assert.assertTrue(valueAsDouble<=max && valueAsDouble>=min);
    }

    public int getValueForKey(String key){
        return res.path(key);
    }

    public void checkIfValueEqualTo(String key, double expectedValue){
        float value = res.then().extract().path(key);
        Assert.assertEquals(value, expectedValue);
    }

    public void checkIfPassTimesAreGreaterThenCurrentTime(String key, String arrKey){
        int value = res.then().extract().path(key);
        ArrayList<Integer> list = res.then().extract().path(arrKey);
        SoftAssert softly = new SoftAssert();
        for(long element : list){
            softly.assertTrue(value<element);
        }
        softly.assertAll();
    }

}

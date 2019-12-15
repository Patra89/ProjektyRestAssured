import io.restassured.RestAssured;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.*;

public class ApiResponse {

    public io.restassured.response.Response res;

    public ApiResponse(String url, String key, String value) {
        RestAssured.baseURI = url;
        res = given()
                .contentType(Constants.RESPONSE_CONTENT_TYPE)
                .body("{"+ "\""+key+"\""+": "+"\""+value+"\""+"}")
                .get();
    }

    public ApiResponse(String url) {
        RestAssured.baseURI = url;
        res = given()
                .contentType(Constants.RESPONSE_CONTENT_TYPE)
                .get();
    }

    public void checkIfStatusCodeEqualTo(int statusCode) {
        res.then().assertThat().statusCode(statusCode);
    }

    public void checkIfBodyMatchesSchema(String schema) {
        res.then().assertThat().body(matchesJsonSchemaInClasspath(schema));
    }

    public void checkIfArraySizeIsEqualTo(int size) {
        res.then().assertThat().body("size()", is(size));
    }

    public void checkValueForKey(String key, int value) {
        res.then().assertThat().body(key, equalTo(value));
    }

    public ArrayList getListOfValuesForKey(String key) {
        String response = res.body().asString();
        return new ArrayList<>(from(response).getList(key));
    }

    public Object[] getListOfObjects() {
        List list = new ArrayList<>();
        list = res.then().extract().body().as(list.getClass());
        return list.toArray();
    }

    public boolean doesObjectContainPhrase(LinkedHashMap obj, String phrase) {

        String name = (String) obj.get("name");
        String address = (String) obj.get("street");
        String type = (String) obj.get("brewery_type");
        String website = (String) obj.get("website_url");
        String[] tags = (String[]) obj.get("tags");

        if (website == null) {
            website = "";
        }
        if (type == null) {
            type = "";
        }
        if (address == null) {
            address = "";
        }
        if (name == null) {
            name = "";
        }
        if (tags == null) {
            tags = new String[0];
        }


        if (name.toLowerCase().contains(phrase)) {
            return true;
        } else if (address.toLowerCase().contains(phrase)) {
            return true;
        } else if (type.toLowerCase().contains(phrase)) {
            return true;
        } else if (website.toLowerCase().contains(phrase)) {
            return true;
        } else if (tags.length > 0) {
            for (String tag : tags) {
                if (tag.toLowerCase().contains(phrase)) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }

    }

}

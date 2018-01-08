package acceptance;

import com.eclipsesource.json.JsonObject;
import org.junit.Test;

import static acceptance.APITestSuit.BASE_URL;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;

public class RegistrationAPI_AcceptanceTest {

    @Test public void
    register_a_new_user() {
        given()
                .body(withJsonContaining("Alice", "alki324d", "About Alice"))
        .when()
                .post(BASE_URL + "/registration")
        .then()
                .statusCode(201)
                .contentType(JSON)
                .body("userId", notNullValue())
                .body("username", is("Alice"))
                .body("about", is("About Alice"));
    }

    private String withJsonContaining(String username, String password, String about) {
        return new JsonObject()
                        .add("username", username)
                        .add("password", password)
                        .add("about", about)
                        .toString();
    }
}

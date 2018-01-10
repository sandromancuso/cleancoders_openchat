package acceptance;

import com.eclipsesource.json.JsonObject;
import org.junit.Test;

import static acceptance.APITestSuit.ALICE;
import static acceptance.APITestSuit.BASE_URL;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class LoginAPI_AcceptanceTest {

    @Test public void
    perform_login() {
        given()
                .body(withJsonContaining(ALICE.username(), ALICE.password()))
        .when()
                .post(BASE_URL + "/login")
        .then()
                .statusCode(200)
                .contentType(JSON)
                .body("userId", is(ALICE.userId()))
                .body("username", is(ALICE.username()))
                .body("about", is(ALICE.about()));
    }

    private String withJsonContaining(String username, String password) {
        return new JsonObject()
                .add("username", username)
                .add("password", password)
                .toString();
    }
}

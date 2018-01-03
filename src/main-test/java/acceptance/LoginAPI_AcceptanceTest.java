package acceptance;

import com.eclipsesource.json.JsonObject;
import org.junit.Test;

import static acceptance.APITestSuite.BASE_URL;
import static acceptance.RegisterTestUsers.CHARLIE;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class LoginAPI_AcceptanceTest {

    @Test public void
    return_logged_in_user_after_successful_login() {
        givenSuccessfulRegistrationForCharlie();
        given()
                .body(withLoginJson())
                .contentType(JSON)
        .when()
                .post(BASE_URL + "/login")
        .then()
                .statusCode(200)
                .contentType(JSON)
                .body("userId", notNullValue())
                .body("username", is(CHARLIE.username()))
                .body("about", is(CHARLIE.about()));
    }

    private void givenSuccessfulRegistrationForCharlie() {
        given()
                .body(withRegistrationJson())
                .contentType("application/json")
        .when()
                .post(BASE_URL + "/registration");
    }

    private String withRegistrationJson() {
        return new JsonObject()
                .add("username", CHARLIE.username())
                .add("password", CHARLIE.password())
                .add("about", CHARLIE.about())
                .toString();
    }

    private String withLoginJson() {
        return new JsonObject()
                .add("username", CHARLIE.username())
                .add("password", CHARLIE.password())
                .toString();
    }
}

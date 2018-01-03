package acceptance;

import com.eclipsesource.json.JsonObject;
import org.junit.Test;

import static acceptance.APITestSuite.BASE_URL;
import static acceptance.RegisterTestUsers.USER;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class RegistrationAPI_AcceptanceTest {

    @Test public void
    register_a_user() {
        given()
                .body(withRegistrationJson())
                .contentType(JSON)
        .when()
                .post(BASE_URL + "/registration")
        .then()
                .statusCode(201)
                .contentType(JSON)
                .body("userId", notNullValue())
                .body("username", is(USER.username()))
                .body("about", is(USER.about()));
    }

    private String withRegistrationJson() {
        return new JsonObject()
                        .add("username", USER.username())
                        .add("password", USER.password())
                        .add("about", USER.about())
                        .toString();
    }
}

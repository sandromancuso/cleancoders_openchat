package integration;

import com.eclipsesource.json.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.openchat.domain.user.User;

import static integration.APITestSuit.BASE_URL;
import static integration.OpenChatTestDSL.register;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.openchat.domain.user.UserBuilder.aUser;

public class IT_LoginAPI {

    private static User ANTONY = aUser().withUsername("Antony").build();

    @Before
    public void initialise() {
        ANTONY = register(ANTONY);
    }

    @Test public void
    perform_login() {
        given()
                .body(withJsonContaining(ANTONY.username(), ANTONY.password()))
        .when()
                .post(BASE_URL + "/login")
        .then()
                .statusCode(200)
                .contentType(JSON)
                .body("userId", is(ANTONY.userId()))
                .body("username", is(ANTONY.username()))
                .body("about", is(ANTONY.about()));
    }

    private String withJsonContaining(String username, String password) {
        return new JsonObject()
                .add("username", username)
                .add("password", password)
                .toString();
    }
}

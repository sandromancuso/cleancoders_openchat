package integration;

import com.eclipsesource.json.JsonObject;
import integration.dsl.UserDSL.ITUser;
import org.junit.Before;
import org.junit.Test;

import static integration.APITestSuit.BASE_URL;
import static integration.dsl.OpenChatTestDSL.register;
import static integration.dsl.UserDSL.ITUserBuilder.aUser;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.is;

public class IT_LoginAPI {

    private static ITUser ANTONY = aUser().withUsername("Antony").build();

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
                .body("id", is(ANTONY.id()))
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

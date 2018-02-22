package integration;

import com.eclipsesource.json.JsonObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openchat.OpenChat;
import org.openchat.OpenChatLauncher;
import org.openchat.api.APIContext;
import org.openchat.usecases.UseCaseContext;

import static integration.APITestSuit.BASE_URL;
import static integration.APITestSuit.UUID_PATTERN;
import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.post;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;

public class IT_RegistrationAPI {
    @Before
    public void initialize() {
        delete(BASE_URL+"/repositories");
    }

    @Test public void
    register_a_new_user() {
        given()
                .body(withJsonContaining("username", "password", "about"))
        .when()
                .post(BASE_URL + "/users")
        .then()
                .statusCode(201)
                .contentType(JSON)
                .body("id", matchesPattern(UUID_PATTERN))
                .body("username", is("username"))
                .body("about", is("about"));
    }

    @Test
    public void register_a_duplicate_user() throws Exception {
          given()
                .body(withJsonContaining("username", "password", "about"))
                .post(BASE_URL+"/users");
          given()
                .body(withJsonContaining("username", "xyzzy", "about"))
          .when()
                .post(BASE_URL + "/users")
          .then()
                .statusCode(400)
                .assertThat().body(is("Username already in use."));
    }

    private String withJsonContaining(String username, String password, String about) {
        return new JsonObject()
                        .add("username", username)
                        .add("password", password)
                        .add("about", about)
                        .toString();
    }
}

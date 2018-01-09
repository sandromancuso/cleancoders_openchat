package acceptance;

import com.eclipsesource.json.JsonObject;
import org.junit.Test;

import static acceptance.APITestSuit.ALICE;
import static acceptance.APITestSuit.BASE_URL;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;

public class PostAPI_AcceptanceTest {

    @Test public void
    store_a_new_post() {
        given()
                .body(withJsonContaining("Hello, I'm Alice."))
        .when()
                .post(BASE_URL + "/user/" + ALICE.userId() + "/posts")
        .then()
                .statusCode(201)
                .contentType(JSON)
                .body("postId", notNullValue())
                .body("userId", is(ALICE.userId()))
                .body("text", is("Hello, I'm Alice."))
                .body("date", notNullValue())
                .body("time", notNullValue());
    }

    private String withJsonContaining(String text) {
        return new JsonObject().add("text", text).toString();
    }
}

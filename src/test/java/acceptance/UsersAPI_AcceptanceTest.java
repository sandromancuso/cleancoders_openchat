package acceptance;

import org.junit.Test;

import static acceptance.APITestSuit.*;
import static io.restassured.RestAssured.when;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class UsersAPI_AcceptanceTest {

    @Test public void
    return_all_users() {
        when()
                .get(BASE_URL + "/users")
        .then()
                .statusCode(200)
                .contentType(JSON)
                .body("userId[0]", is(ALICE.userId()))
                .body("userId[1]", is(BOB.userId()))
                .body("userId[2]", is(CHARLIE.userId()))
                .body("userId[3]", is(JULIE.userId()));
    }
}

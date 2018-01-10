package acceptance;

import org.junit.Test;

import static acceptance.APITestSuit.*;
import static io.restassured.RestAssured.when;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class FolloweesAPI_AcceptanceTest {

    @Test
    public void
    return_all_followees_for_a_given_user() {
        when()
                .get(BASE_URL + "/user/" + ALICE.userId() + "/followees")
        .then()
                .statusCode(200)
                .contentType(JSON)
                .body("userId[0]", is(BOB.userId()))
                .body("userId[1]", is(CHARLIE.userId()));
    }

}

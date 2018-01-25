package acceptance;

import org.junit.Test;
import org.openchat.domain.user.User;

import static acceptance.APITestSuit.*;
import static io.restassured.RestAssured.when;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.openchat.domain.user.UserBuilder.aUser;

public class FolloweesAPI_AcceptanceTest {

    private static User ALICE   = aUser().withUsername("Alice"  ).build();
    private static User BOB     = aUser().withUsername("Bob"    ).build();
    private static User CHARLIE = aUser().withUsername("Charlie").build();
    private static User JULIE   = aUser().withUsername("Julie"  ).build();


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

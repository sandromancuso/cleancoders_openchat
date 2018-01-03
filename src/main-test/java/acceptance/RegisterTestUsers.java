package acceptance;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.junit.Test;
import org.openchat.core.domain.user.User;

import java.util.List;

import static acceptance.APITestSuite.BASE_URL;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.util.Arrays.asList;
import static org.openchat.builders.UserBuilder.aCloneFrom;
import static org.openchat.builders.UserBuilder.aUser;

public class RegisterTestUsers {

    static User ALICE = aUser().withUsername("Alice").withPassword("Alice123").withAbout("About Alice").build();
    static User BOB = aUser().withUsername("Bob").withPassword("Bob123").withAbout("About Bob").build();
    static User CHARLIE = aUser().withUsername("Charlie").withPassword("Charlie123").withAbout("About Charlie").build();
    static User JULIE = aUser().withUsername("Julie").withPassword("Julie123").withAbout("About Julie").build();

    static User USER = aUser().build(); // used by the Registration API Acceptance Test.

    static List<User> REGISTERED_USERS = asList(ALICE, BOB, CHARLIE, JULIE, USER);

    @Test public void
    register_test_users() {
        ALICE = register(ALICE);
        BOB = register(BOB);
        CHARLIE = register(CHARLIE);
        JULIE = register(JULIE);
    }

    private User register(User user) {
        String registeredUserJson = invokeRegistrationFor(user);
        String userId = Json.parse(registeredUserJson).asObject().get("userId").asString();
        return aCloneFrom(user).withId(userId).build();
    }

    private String invokeRegistrationFor(User user) {
        return given()
                    .body(withRegistrationJsonFor(user))
                    .contentType(JSON)
            .when()
                    .post(BASE_URL + "/registration")
                    .asString();
    }

    private String withRegistrationJsonFor(User user) {
        return new JsonObject()
                .add("username", user.username())
                .add("password", user.password())
                .add("about", user.about())
                .toString();
    }
}

package acceptance;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import io.restassured.response.Response;
import org.junit.Test;
import org.openchat.domain.user.User;

import java.util.List;

import static acceptance.APITestSuit.BASE_URL;
import static acceptance.OpenChatTestDSL.register;
import static io.restassured.RestAssured.when;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.openchat.domain.user.UserBuilder.aUser;

public class UsersAPI_AcceptanceTest {

    private static User SANDRO = aUser().withUsername("Sandro").build();
    private static User MASH   = aUser().withUsername("Mash"  ).build();
    private static User STEVE  = aUser().withUsername("Steve" ).build();
    private static User PEDRO  = aUser().withUsername("Pedro" ).build();

    @Test public void
    return_all_users() {
        SANDRO = register(SANDRO);
        MASH   = register(MASH  );
        STEVE  = register(STEVE );
        PEDRO  = register(PEDRO );

        Response response = when().get(BASE_URL + "/users");

        assertAllUsersAreReturned(response, asList(SANDRO, MASH, STEVE, PEDRO));
    }

    private void assertAllUsersAreReturned(Response response, List<User> users) {
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.contentType()).isEqualTo("application/json");

        JsonArray usersArray = Json.parse(response.body().asString()).asArray();
        users.forEach(user -> assertThat(usersArray.values().contains(jsonFor(user))).isTrue());
    }

    private JsonObject jsonFor(User user) {
        return new JsonObject()
                            .add("userId", user.userId())
                            .add("username", user.username())
                            .add("about", user.about());
    }
}

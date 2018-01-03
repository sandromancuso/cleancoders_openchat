package acceptance;

import com.eclipsesource.json.Json;
import io.restassured.response.Response;
import org.junit.Test;
import org.openchat.core.domain.user.User;

import java.util.List;

import static acceptance.APITestSuite.BASE_URL;
import static acceptance.RegisterTestUsers.REGISTERED_USERS;
import static io.restassured.RestAssured.when;
import static io.restassured.http.ContentType.JSON;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class RetrieveUsersAPI_AcceptanceTest {

    @Test public void
    retrieve_all_users() {
        Response response = when().get(BASE_URL + "/users");
        String usersJson = response.asString();

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.contentType()).isEqualTo(JSON.toString());
        assertThatAllRegisteredUsersAreReturned(usersJson, REGISTERED_USERS);
    }

    private void assertThatAllRegisteredUsersAreReturned(String usersJsonString, List<User> registeredUsers) {
        List<String> registeredUsernames = registeredUsers.stream().map(User::username).collect(toList());
        String[] usernames = Json.parse(usersJsonString)
                .asArray()
                .values()
                .stream()
                .map(userJson -> userJson.asObject().getString("username", ""))
                .toArray(String[]::new);
        assertThat(registeredUsernames).containsExactlyInAnyOrder(usernames);
    }
}

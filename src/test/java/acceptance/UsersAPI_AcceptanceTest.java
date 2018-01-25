package acceptance;

import io.restassured.response.Response;
import org.junit.Test;
import org.openchat.domain.user.User;

import static acceptance.APITestSuit.BASE_URL;
import static acceptance.OpenChatTestDSL.assertAllUsersAreReturned;
import static acceptance.OpenChatTestDSL.register;
import static io.restassured.RestAssured.when;
import static java.util.Arrays.asList;
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

}

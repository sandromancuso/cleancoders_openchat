package acceptance;

import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.openchat.domain.user.User;

import static acceptance.APITestSuit.BASE_URL;
import static acceptance.OpenChatTestDSL.*;
import static io.restassured.RestAssured.when;
import static java.util.Arrays.asList;
import static org.openchat.domain.user.UserBuilder.aUser;

public class FolloweesAPI_AcceptanceTest {

    private static User VIVIANE = aUser().withUsername("Viviane").build();
    private static User SAMUEL  = aUser().withUsername("Samuel" ).build();
    private static User OLIVIA  = aUser().withUsername("Olivia" ).build();

    @Before
    public void initialise() {
        VIVIANE = register(VIVIANE);
        SAMUEL  = register(SAMUEL);
        OLIVIA  = register(OLIVIA);
    }

    @Test public void
    return_all_followees_for_a_given_user() {
        givenVivianeFollows(SAMUEL, OLIVIA);

        Response response = when().get(BASE_URL + "/user/" + VIVIANE.userId() + "/followees");

        assertAllUsersAreReturned(response, asList(SAMUEL, OLIVIA));
    }

    private void givenVivianeFollows(User... followees) {
        asList(followees).forEach(followee -> createFollowing(VIVIANE, followee));
    }

}

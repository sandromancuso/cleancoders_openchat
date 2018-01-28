package integration;

import integration.dsl.UserDSL.User;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static integration.APITestSuit.BASE_URL;
import static integration.dsl.OpenChatTestDSL.*;
import static integration.dsl.UserDSL.UserBuilder.aUser;
import static io.restassured.RestAssured.when;
import static java.util.Arrays.asList;

public class IT_FolloweesAPI {

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

        Response response = when().get(BASE_URL + "/user/" + VIVIANE.id() + "/followees");

        assertAllUsersAreReturned(response, asList(SAMUEL, OLIVIA));
    }

    private void givenVivianeFollows(User... followees) {
        asList(followees).forEach(followee -> createFollowing(VIVIANE, followee));
    }

}

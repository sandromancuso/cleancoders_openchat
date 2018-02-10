package integration;

import integration.dsl.UserDSL.ITUser;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static integration.APITestSuit.BASE_URL;
import static integration.dsl.OpenChatTestDSL.*;
import static integration.dsl.UserDSL.ITUserBuilder.aUser;
import static io.restassured.RestAssured.when;
import static java.util.Arrays.asList;

public class IT_FolloweesAPI {

    private static ITUser VIVIANE = aUser().withUsername("Viviane").build();
    private static ITUser SAMUEL  = aUser().withUsername("Samuel" ).build();
    private static ITUser OLIVIA  = aUser().withUsername("Olivia" ).build();

    @Before
    public void initialise() {
        VIVIANE = register(VIVIANE);
        SAMUEL  = register(SAMUEL);
        OLIVIA  = register(OLIVIA);
    }

    @Test public void
    return_all_followees_for_a_given_user() {
        givenVivianeFollows(SAMUEL, OLIVIA);

        Response response = when().get(BASE_URL + "/followings/" + VIVIANE.id() + "/followees");

        assertAllUsersAreReturned(response, asList(SAMUEL, OLIVIA));
    }

    private void givenVivianeFollows(ITUser... followees) {
        asList(followees).forEach(followee -> createFollowing(VIVIANE, followee));
    }

}

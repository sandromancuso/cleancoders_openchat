package integration;

import integration.dsl.UserDSL.ITUser;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static integration.APITestSuit.BASE_URL;
import static integration.dsl.OpenChatTestDSL.assertAllUsersAreReturned;
import static integration.dsl.OpenChatTestDSL.register;
import static integration.dsl.UserDSL.ITUserBuilder.aUser;
import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.when;
import static java.util.Arrays.asList;

public class IT_UsersAPI {
    private static ITUser SANDRO = aUser().withUsername("Sandro").build();
    private static ITUser MASH   = aUser().withUsername("Mash"  ).build();
    private static ITUser STEVE  = aUser().withUsername("Steve" ).build();
    private static ITUser PEDRO  = aUser().withUsername("Pedro" ).build();

    @Before
    public void initialize() {
        delete(BASE_URL+"/repositories");
    }

    @Test public void
    return_all_users() {
        SANDRO = register(SANDRO);
        MASH   = register(MASH  );
        STEVE  = register(STEVE );
        PEDRO  = register(PEDRO );

        Response response = when().get(BASE_URL + "/users");

        assertAllUsersAreReturned(response, asList(SANDRO, MASH, STEVE, PEDRO));
    }

    @Test
    public void when_there_are_no_users() throws Exception {
        Response response = when().get(BASE_URL + "/users");
        assertAllUsersAreReturned(response, asList());
    }

}

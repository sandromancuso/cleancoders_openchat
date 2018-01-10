package org.openchat.api;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openchat.domain.user.User;
import org.openchat.domain.user.UserService;
import spark.Request;
import spark.Response;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.openchat.domain.user.UserBuilder.aUser;

@RunWith(MockitoJUnitRunner.class)
public class FolloweesAPIShould {

    private static final User ALICE = aUser().build();
    private static final User BOB = aUser().build();
    private static final User CHARLIE = aUser().build();

    @Mock Request request;
    @Mock Response response;
    @Mock UserService userService;

    private FolloweesAPI followeeAPI;

    @Before
    public void initialise() {
        followeeAPI = new FolloweesAPI(userService);
    }

    @Test public void
    return_all_users_followed_by_another_user() {
        given(request.params("userId")).willReturn(ALICE.userId());
        given(userService.followeesFor(ALICE.userId())).willReturn(asList(BOB, CHARLIE));

        String result = followeeAPI.allFollowees(request, response);

        verify(response).status(200);
        verify(response).type("application/json");
        assertThat(result).isEqualTo(jsonContaining(BOB, CHARLIE));
    }

    private String jsonContaining(User... users) {
        JsonArray usersJson = new JsonArray();
        asList(users).forEach(user -> usersJson.add(jsonFor(user)));
        return usersJson.toString();
    }

    private JsonObject jsonFor(User user) {
        return new JsonObject()
                        .add("userId", user.userId())
                        .add("username", user.username())
                        .add("about", user.about());
    }

}
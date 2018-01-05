package org.openchat.api;

import com.eclipsesource.json.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openchat.domain.user.Following;
import org.openchat.domain.user.InvalidUserException;
import org.openchat.domain.user.User;
import org.openchat.domain.user.UserService;
import spark.Request;
import spark.Response;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.openchat.builders.UserBuilder.aUser;

@RunWith(MockitoJUnitRunner.class)
public class FollowAPIShould {

    private static final User ALICE = aUser().withUsername("Alice").build();
    private static final User BOB = aUser().withUsername("Bob").build();
    private static final User UNKNOWN_USER = aUser().build();

    @Mock Request request;
    @Mock Response response;
    @Mock UserService userService;

    private FollowAPI followAPI;

    @Before
    public void initialise() {
        followAPI = new FollowAPI(userService);
    }

    @Test public void
    create_following_relationship() {
        given(request.body()).willReturn(jsonContaining(ALICE.id(), BOB.id()));
        Following following = new Following(ALICE.id(), BOB.id());

        followAPI.follow(request, response);

        verify(userService).create(following);
        verify(response).status(201);
    }

    @Test public void
    return_bad_request_if_either_follower_or_followee_do_not_exist() {
        given(request.body()).willReturn(jsonContaining(ALICE.id(), UNKNOWN_USER.id()));
        Following following = new Following(ALICE.id(), UNKNOWN_USER.id());
        doThrow(InvalidUserException.class).when(userService).create(following);

        followAPI.follow(request, response);

        verify(response).status(400);
    }

    private String jsonContaining(String followerId, String followeeId) {
        return new JsonObject()
                        .add("followerId", followerId)
                        .add("followeeId", followeeId)
                        .toString();
    }
}

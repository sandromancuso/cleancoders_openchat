package org.openchat.web.api;

import com.eclipsesource.json.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openchat.core.actions.CreateFollowing;
import org.openchat.core.actions.CreateFollowing.FollowingData;
import org.openchat.core.actions.CreateFollowing.InvalidUserException;
import org.openchat.core.domain.user.User;
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
    @Mock CreateFollowing createFollowing;

    private FollowAPI followAPI;

    @Before
    public void initialise() {
        followAPI = new FollowAPI(createFollowing);
    }

    @Test public void
    create_following_relationship() {
        given(request.body()).willReturn(jsonContaining(ALICE.id(), BOB.id()));
        FollowingData followingData = new FollowingData(ALICE.id(), BOB.id());

        followAPI.follow(request, response);

        verify(createFollowing).execute(followingData);
        verify(response).status(201);
    }

    @Test public void
    return_bad_request_if_either_follower_or_followee_do_not_exist() {
        given(request.body()).willReturn(jsonContaining(ALICE.id(), UNKNOWN_USER.id()));
        FollowingData followingData = new FollowingData(ALICE.id(), UNKNOWN_USER.id());
        doThrow(InvalidUserException.class).when(createFollowing).execute(followingData);

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

package org.openchat.api;

import com.eclipsesource.json.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openchat.domain.user.UserDoesNotExistException;
import org.openchat.domain.user.UserService;
import spark.Request;
import spark.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FollowAPIShould {

    private static final String FOLLOWER_ID = "12344";
    private static final String FOLLOWEE_ID = "431234";

    @Mock Request request;
    @Mock Response response;

    @Mock UserService userService;

    private FollowAPI followAPI;

    @Before
    public void initialise() {
        followAPI = new FollowAPI(userService);
    }

    @Test public void
    create_a_following_relationship() {
        given(request.body()).willReturn(jsonContaining(FOLLOWER_ID, FOLLOWEE_ID));

        followAPI.follow(request, response);

        verify(userService).createFollowing(FOLLOWER_ID, FOLLOWEE_ID);
        verify(response).status(201);
    }

    @Test public void
    return_error_when_one_of_the_users_does_not_exist() {
        given(request.body()).willReturn(jsonContaining(FOLLOWER_ID, FOLLOWEE_ID));
        doThrow(UserDoesNotExistException.class).when(userService).createFollowing(FOLLOWER_ID, FOLLOWEE_ID);

        String result = followAPI.follow(request, response);

        verify(response).status(400);
        assertThat(result).isEqualTo("At least one of the users does not exist.");
    }

    private String jsonContaining(String followerId, String followeeId) {
        return new JsonObject()
                        .add("followerId", followerId)
                        .add("followeeId", followeeId)
                        .toString();
    }

}
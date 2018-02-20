package org.openchat.api;

import com.eclipsesource.json.JsonObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openchat.domain.users.Following;
import org.openchat.domain.users.UserService;
import spark.Request;
import spark.Response;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FollowingAPIShould {
    private static final String FOLLOWER_ID = UUID.randomUUID().toString();
    private static final String FOLLOWEE_ID = UUID.randomUUID().toString();

    public static final Following FOLLOWING = new Following(FOLLOWER_ID, FOLLOWEE_ID);

    @Mock Request request;
    @Mock Response response;
    @Mock UserService userService;
    private FollowingAPI followingAPI = new FollowingAPI(userService);

    @Test public void
    register_a_following() {
        given(request.body()).willReturn(JsonContaining(FOLLOWING));

        String result = followingAPI.createFollowing(request, response);

        verify(userService).addFollowing(FOLLOWING);
        verify(response).status(201);
        assertThat(result).isEqualTo("");
    }

    private String JsonContaining(Following following) {
        return new JsonObject()
                .add("followerId", following.followerId())
                .add("followeeId", following.followeeId())
                .toString();
    }

}
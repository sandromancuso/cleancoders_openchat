package org.openchat.api;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openchat.domain.users.Following;
import org.openchat.domain.users.FollowingAlreadyExistsException;
import org.openchat.domain.users.User;
import org.openchat.domain.users.UserService;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.openchat.infrastructure.builders.UserBuilder.aUser;

@RunWith(MockitoJUnitRunner.class)
public class FollowingAPIShould {
    private static final String FOLLOWER_ID = UUID.randomUUID().toString();
    private static final String FOLLOWEE_ID = UUID.randomUUID().toString();

    public static final Following FOLLOWING = new Following(FOLLOWER_ID, FOLLOWEE_ID);
    private static final List<User> FOLLOWEES = asList(aUser().build());

    @Mock Request request;
    @Mock Response response;
    @Mock UserService userService;

    private FollowingAPI followingAPI;

    @Before
    public void initialise() {
        followingAPI = new FollowingAPI(userService);
        given(request.body()).willReturn(JsonContaining(FOLLOWING));
    }

    @Test public void
    register_a_following() throws FollowingAlreadyExistsException {
        String result = followingAPI.createFollowing(request, response);

        verify(userService).addFollowing(FOLLOWING);
        verify(response).status(201);
        assertThat(result).isEqualTo("");
    }
    
    @Test public void
    return_an_error_if_following_already_exists() throws FollowingAlreadyExistsException {
        doThrow(FollowingAlreadyExistsException.class).when(userService).addFollowing(FOLLOWING);

        String result = followingAPI.createFollowing(request, response);

        verify(response).status(400);
        assertThat(result).isEqualTo("Following already exists.");
    }

    @Test public void
    return_a_json_containing_all_the_users_followed_by_a_given_user() {
        given(request.params("followerId")).willReturn(FOLLOWER_ID);
        given(userService.followeesFor(FOLLOWER_ID)).willReturn(FOLLOWEES);

        String result = followingAPI.getFollowees(request, response);

        verify(response).status(200);
        verify(response).type("application/json");
        assertThat(result).isEqualTo(jsonContaining(FOLLOWEES));
    }

    private String jsonContaining(List<User> followees) {
        JsonArray json = new JsonArray();
        followees.forEach(followee -> json.add(jsonFor(followee)));
        return json.toString();
    }

    private JsonObject jsonFor(User followee) {
        return new JsonObject()
                        .add("id", followee.id())
                        .add("username", followee.username())
                        .add("about", followee.about());
    }

    private String JsonContaining(Following following) {
        return new JsonObject()
                        .add("followerId", following.followerId())
                        .add("followeeId", following.followeeId())
                        .toString();
    }

}
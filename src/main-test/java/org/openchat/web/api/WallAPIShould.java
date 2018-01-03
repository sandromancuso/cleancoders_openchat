package org.openchat.web.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openchat.core.actions.RetrieveWall;
import org.openchat.core.domain.post.Post;
import org.openchat.core.domain.user.User;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.openchat.builders.PostBuilder.aPost;
import static org.openchat.builders.UserBuilder.aUser;

@RunWith(MockitoJUnitRunner.class)
public class WallAPIShould {

    private static final String JSON = "application/json";

    private static final Post POST_1 = aPost().build();
    private static final Post POST_2 = aPost().build();
    private static final User ALICE = aUser().build();
    private static final User UNKNOWN_USER = aUser().build();

    @Mock Request request;
    @Mock Response response;
    @Mock RetrieveWall retrieveWall;

    private WallAPI wallAPI;

    @Before
    public void initialise() {
        wallAPI = new WallAPI(retrieveWall);
    }

    @Test public void
    return_json_containing_user_wall() {
        List<Post> posts = asList(POST_2, POST_1);
        given(request.params("userId")).willReturn(ALICE.id());
        given(retrieveWall.execute(ALICE.id())).willReturn(Optional.of(posts));

        String wallJson = wallAPI.wall(request, response);

        verifyWallJsonContains(wallJson, posts);
        verify(response).status(200);
        verify(response).type(JSON);
    }

    @Test public void
    return_400_if_user_does_not_exist() {
        given(request.params("userId")).willReturn(UNKNOWN_USER.id());
        given(retrieveWall.execute(UNKNOWN_USER.id())).willReturn(Optional.empty());

        wallAPI.wall(request, response);

        verify(response).status(400);
    }

    private void verifyWallJsonContains(String wallJson, List<Post> posts) {
        JsonArray timeline = Json.parse(wallJson).asArray();
        Post post;
        JsonObject postJson;
        for (int i = 0; i < posts.size() - 1; i++) {
            post = posts.get(i);
            postJson = timeline.get(i).asObject();
            verifyPostJson(postJson.toString(), post);
        }
    }

    private void verifyPostJson(String postJson, Post post) {
        String expectedJson = new JsonObject()
                                    .add("postId", post.id())
                                    .add("userId", post.userId())
                                    .add("text", post.text())
                                    .add("date", post.dateAsString())
                                    .add("time", post.timeAsString())
                                    .toString();
        assertThat(postJson).isEqualTo(expectedJson);
    }
}
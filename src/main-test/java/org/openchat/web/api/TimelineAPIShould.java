package org.openchat.web.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openchat.core.domain.post.Post;
import org.openchat.core.domain.post.PostService;
import org.openchat.core.domain.user.User;
import spark.Request;
import spark.Response;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.openchat.builders.PostBuilder.aPost;
import static org.openchat.builders.UserBuilder.aUser;

@RunWith(MockitoJUnitRunner.class)
public class TimelineAPIShould {

    private static final String JSON = "application/json";
    private static final User ALICE = aUser().build();
    private static final Post POST_1 = aPost().withUserId(ALICE.id()).withText("post 1").build();
    private static final Post POST_2 = aPost().withUserId(ALICE.id()).withText("post 2").build();
    private static final Post POST_3 = aPost().withUserId(ALICE.id()).withText("post 3").build();

    @Mock Request request;
    @Mock Response response;
    @Mock PostService postService;

    private TimelineAPI timelineAPI;

    @Before
    public void initialise() {
        timelineAPI = new TimelineAPI(postService);
    }

    @Test
    public void
    return_json_containing_user_timeline() {
        given(request.params("userId")).willReturn(ALICE.id());
        List<Post> AlicePosts = asList(POST_3, POST_2, POST_1);
        given(postService.timelineFor(ALICE.id())).willReturn(AlicePosts);

        String timelineJson = timelineAPI.timeline(request, response);

        verifyTimelineJsonContains(timelineJson, AlicePosts);
        verify(response).status(200);
        verify(response).type(JSON);
    }

    private void verifyTimelineJsonContains(String timelineJson, List<Post> userPosts) {
        JsonArray timeline = Json.parse(timelineJson).asArray();
        Post post;
        JsonObject postJson;
        for (int i = 0; i < userPosts.size() - 1; i++) {
            post = userPosts.get(i);
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

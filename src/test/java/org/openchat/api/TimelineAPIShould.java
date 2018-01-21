package org.openchat.api;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openchat.domain.post.Post;
import org.openchat.domain.post.PostService;
import org.openchat.domain.user.UserDoesNotExistException;
import spark.Request;
import spark.Response;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.openchat.domain.post.PostBuilder.aPost;

@RunWith(MockitoJUnitRunner.class)
public class TimelineAPIShould {

    private static final String USER_ID = "1232324";
    private static final Post POST_2 = aPost().withUserId(USER_ID).build();
    private static final Post POST_1 = aPost().withUserId(USER_ID).build();

    @Mock Request request;
    @Mock Response response;
    @Mock PostService postService;

    private TimelineAPI timelineAPI;

    @Before
    public void initialise() {
        timelineAPI = new TimelineAPI(postService);
        given(request.params("userId")).willReturn(USER_ID);
    }

    @Test public void
    return_json_representing_posts_from_a_user() throws UserDoesNotExistException {
        given(postService.timelineFor(USER_ID)).willReturn(asList(POST_2, POST_1));

        String result = timelineAPI.timeline(request, response);

        assertThat(result).isEqualTo(jsonContaining(POST_2, POST_1));
        verify(response).status(200);
        verify(response).type("application/json");
    }
    
    @Test public void
    return_error_when_user_does_not_exist() throws UserDoesNotExistException {
        doThrow(UserDoesNotExistException.class).when(postService).timelineFor(USER_ID);

        String result = timelineAPI.timeline(request, response);

        verify(response).status(400);
        assertThat(result).isEqualTo("User does not exist.");
    }

    private String jsonContaining(Post... posts) {
        JsonArray postArray = new JsonArray();
        asList(posts).forEach(post -> postArray.add(jsonObject(post)));
        return postArray.toString();
    }

    private JsonObject jsonObject(Post post) {
        return new JsonObject()
                        .add("postId", post.postId())
                        .add("userId", post.userId())
                        .add("text", post.text())
                        .add("dateTime", post.dateTimeAsString());
    }

}
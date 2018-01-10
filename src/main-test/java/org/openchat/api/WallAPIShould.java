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
public class WallAPIShould {

    private static final String USER_ID = "234324";
    private static final Post POST_1 = aPost().build();
    private static final Post POST_2 = aPost().build();

    @Mock Request request;
    @Mock Response response;
    @Mock PostService postService;

    private WallAPI wallAPI;

    @Before
    public void initialise() {
        wallAPI = new WallAPI(postService);
        given(request.params("userId")).willReturn(USER_ID);
    }

    @Test public void
    return_json_representing_a_users_wall() {
        given(postService.wallFor(USER_ID)).willReturn(asList(POST_2, POST_1));

        String result = wallAPI.wall(request, response);

        verify(response).status(200);
        verify(response).type("application/json");
        assertThat(result).isEqualTo(jsonContaining(POST_2, POST_1));
    }
    
    @Test public void
    return_error_if_the_user_does_not_exist() {
        doThrow(UserDoesNotExistException.class).when(postService).wallFor(USER_ID);

        String result = wallAPI.wall(request, response);

        verify(response).status(400);
        assertThat(result).isEqualTo("User does not exist.");
    } 

    private String jsonContaining(Post... posts) {
        JsonArray json = new JsonArray();
        asList(posts).forEach(post -> json.add(jsonObjectFor(post)));
        return json.toString();
    }

    private JsonObject jsonObjectFor(Post post) {
        return new JsonObject()
                        .add("postId", post.postId())
                        .add("userId", post.userId())
                        .add("text", post.text())
                        .add("date", post.dateAsString())
                        .add("time", post.timeAsString());
    }

}
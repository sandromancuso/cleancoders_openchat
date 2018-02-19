package org.openchat.api;

import com.eclipsesource.json.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openchat.domain.posts.PostService;
import spark.Request;
import spark.Response;

import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PostsAPIShould {

    private static final String USER_ID = UUID.randomUUID().toString();
    private static final String POST_TEXT = "Some text";

    @Mock Request request;
    @Mock Response response;

    @Mock PostService postService;

    private PostsAPI postsAPI;

    @Before
    public void initialise() {
        postsAPI = new PostsAPI(postService);
    }

    @Test public void
    create_a_post() {
        given(request.params("userId")).willReturn(USER_ID);
        given(request.body()).willReturn(jsonContaining(POST_TEXT));

        postsAPI.createPost(request, response);

        verify(postService).createPost(USER_ID, POST_TEXT);
    }
    
    @Test public void
    return_a_json_representing_a_newly_created_post() {
        assertThat().isEqualTo();
    } 

    private String jsonContaining(String text) {
        return new JsonObject().add("text", text).toString();
    }

}
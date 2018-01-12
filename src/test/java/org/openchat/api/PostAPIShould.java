package org.openchat.api;

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

import static com.eclipsesource.json.Json.parse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.openchat.domain.post.PostBuilder.aPost;

@RunWith(MockitoJUnitRunner.class)
public class PostAPIShould {

    private static final String USER_ID = "1231234";
    private static final String POST_TEXT = "Hello everyone.";
    private static final Post POST = aPost().withUserId(USER_ID).withText(POST_TEXT).build();

    @Mock Request request;
    @Mock Response response;

    @Mock PostService postService;

    private PostAPI postAPI;

    @Before
    public void initialise() throws UserDoesNotExistException {
        postAPI = new PostAPI(postService);
        given(request.params("userId")).willReturn(USER_ID);
        given(request.body()).willReturn(jsonContaining(POST_TEXT));
        given(postService.createPost(USER_ID, POST_TEXT)).willReturn(POST);
    }

    @Test public void
    store_new_post() throws UserDoesNotExistException {
        postAPI.createPost(request, response);

        verify(postService).createPost(USER_ID, POST_TEXT);
    }

    @Test public void
    return_json_representing_newly_created_post() {
        String postJson = postAPI.createPost(request, response);

        verifyPostJsonContains(postJson, POST);
        verify(response).status(201);
        verify(response).type("application/json");
    }

    @Test public void
    return_error_when_user_does_not_exist() throws UserDoesNotExistException {
        doThrow(UserDoesNotExistException.class).when(postService).createPost(USER_ID, POST_TEXT);

        String result = postAPI.createPost(request, response);

        verify(response).status(400);
        assertThat(result).isEqualTo("User does not exist.");
    }

    private void verifyPostJsonContains(String postJson, Post post) {
        JsonObject jsonObject = parse(postJson).asObject();
        assertIsEqual(jsonObject, "postId", post.postId());
        assertIsEqual(jsonObject, "userId", post.userId());
        assertIsEqual(jsonObject, "text", post.text());
        assertIsEqual(jsonObject, "date", post.dateAsString());
        assertIsEqual(jsonObject, "time", post.timeAsString());
    }

    private void assertIsEqual(JsonObject jsonObject, String value, String expected) {
        assertThat(jsonObject.getString(value, "")).isEqualTo(expected);
    }

    private String jsonContaining(String postText) {
        return new JsonObject().add("text", postText).toString();
    }

}
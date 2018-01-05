package org.openchat.api;

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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.openchat.builders.PostBuilder.aPost;
import static org.openchat.builders.UserBuilder.aUser;

@RunWith(MockitoJUnitRunner.class)
public class PostAPIShould {

    private static final String JSON = "application/json";
    private static final User ALICE = aUser().build();
    private static final Post POST_1 = aPost().withUserId(ALICE.id()).withText("post 1").build();
    private static final Post POST_2 = aPost().withUserId(ALICE.id()).withText("post 2").build();
    private static final Post POST_3 = aPost().withUserId(ALICE.id()).withText("post 3").build();

    @Mock Request request;
    @Mock Response response;
    @Mock PostService postService;

    private PostAPI postAPI;

    @Before
    public void initialise() {
        postAPI = new PostAPI(postService);
        given(request.params("userId")).willReturn(ALICE.id());
        given(request.body()).willReturn(jsonRequestContaining(POST_1.text()));
    }

    @Test public void
    return_json_containing_the_created_post() {
        given(postService.createPost(ALICE.id(), POST_1.text())).willReturn(Optional.of(POST_1));

        String postJson = postAPI.createPost(request, response);

        verifyPostJson(postJson, POST_1);
        verify(response).status(201);
        verify(response).type(JSON);
    }

    @Test public void
    return_bad_request_when_post_cannot_be_created() {
        given(postService.createPost(ALICE.id(), POST_1.text())).willReturn(Optional.empty());

        postAPI.createPost(request, response);

        verify(response).status(400);
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

    private String jsonRequestContaining(String text) {
        return new JsonObject().add("text", text).toString();
    }

}
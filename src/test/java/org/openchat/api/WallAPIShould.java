package org.openchat.api;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openchat.domain.posts.Post;
import org.openchat.domain.posts.WallService;
import spark.Request;
import spark.Response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.openchat.infrastructure.builders.PostBuilder.aPost;

@RunWith(MockitoJUnitRunner.class)
public class WallAPIShould {

    private static final String USER_ID = UUID.randomUUID().toString();

    private static final LocalDateTime DATE_TIME = LocalDateTime.of(2018, 1, 10, 14, 30, 0);
    private static final List<Post> WALL_POSTS = asList(aPost().withDateTime(DATE_TIME).build());

    @Mock Request request;
    @Mock Response response;

    @Mock WallService wallService;

    private WallAPI wallAPI;

    @Before
    public void initialise() {
        wallAPI = new WallAPI(wallService);
    }

    @Test public void
    return_json_with_posts_from_a_user_and_from_all_users_she_follows() {
        given(request.params("userId")).willReturn(USER_ID);
        given(wallService.wallFor(USER_ID)).willReturn(WALL_POSTS);

        String result = wallAPI.wallByUser(request, response);

        verify(response).status(200);
        verify(response).type("application/json");
        assertThat(result).isEqualTo(jsonContaining(WALL_POSTS));
    }

    private String jsonContaining(List<Post> posts) {
        JsonArray json = new JsonArray();
        posts.forEach(post -> json.add(jsonObjectFor(post)));
        return json.toString();
    }

    private JsonObject jsonObjectFor(Post post) {
        return new JsonObject()
                        .add("postId", post.postId())
                        .add("userId", post.userId())
                        .add("text", post.text())
                        .add("dateTime", "2018-01-10T14:30:00Z");
    }}
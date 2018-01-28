package integration;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.openchat.domain.post.Post;
import org.openchat.domain.user.User;

import java.util.ArrayList;
import java.util.List;

import static integration.APITestSuit.BASE_URL;
import static integration.OpenChatTestDSL.register;
import static com.google.common.collect.Lists.reverse;
import static io.restassured.RestAssured.when;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.openchat.domain.post.PostBuilder.aPost;
import static org.openchat.domain.user.UserBuilder.aUser;

public class IT_TimelineAPI {

    private static User DAVID = aUser().withUsername("David").build();

    private JsonArray timeline;
    private List<Post> POSTS;

    @Before
    public void initialise() {
        DAVID = register(DAVID);
        POSTS = createPostsFor(DAVID, 2);
    }

    @Test public void
    retrieve_a_timeline_with_all_posts_from_a_user_in_reverse_chronological_order() {
        givenDavidPosts(POSTS);

        whenHeChecksHisTimeline();

        thenHeShouldSee(reverse(POSTS));
    }

    private List<Post> createPostsFor(User user, int numberOfPosts) {
        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < numberOfPosts; i++) {
            Post post = aPost().withUserId(user.userId()).withText("Post " + i).build();
            posts.add(post);
        }
        return posts;
    }

    private void givenDavidPosts(List<Post> posts) {
        posts.forEach(OpenChatTestDSL::create);
    }

    private void whenHeChecksHisTimeline() {
        Response response = when().get(BASE_URL + "/user/" + DAVID.userId() + "/timeline");
        timeline = Json.parse(response.asString()).asArray();

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.contentType()).isEqualTo(JSON.toString());
    }

    private void thenHeShouldSee(List<Post> posts) {
        for (int index = 0; index < posts.size(); index++) {
            assertThatTimelineContains(posts.get(index), index);
        }
    }

    private void assertThatTimelineContains(Post post, int index) {
        String text = timeline.get(index).asObject().getString("text", "");
        assertThat(text).isEqualTo(post.text());
    }

}

package integration;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import integration.dsl.OpenChatTestDSL;
import integration.dsl.PostDSL.ITPost;
import integration.dsl.UserDSL.ITUser;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.reverse;
import static integration.APITestSuit.BASE_URL;
import static integration.dsl.OpenChatTestDSL.assertThatJsonPostMatchesPost;
import static integration.dsl.OpenChatTestDSL.register;
import static integration.dsl.PostDSL.ITPostBuilder.aPost;
import static integration.dsl.UserDSL.ITUserBuilder.aUser;
import static io.restassured.RestAssured.when;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

public class IT_TimelineAPI {

    private static ITUser DAVID = aUser().withUsername("David").build();

    private JsonArray timeline;
    private List<ITPost> POSTS;

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

    private List<ITPost> createPostsFor(ITUser user, int numberOfPosts) {
        List<ITPost> posts = new ArrayList<>();
        for (int i = 0; i < numberOfPosts; i++) {
            ITPost post = aPost().withUserId(user.id()).withText("Post " + i).build();
            posts.add(post);
        }
        return posts;
    }

    private void givenDavidPosts(List<ITPost> posts) {
        posts.forEach(OpenChatTestDSL::create);
    }

    private void whenHeChecksHisTimeline() {
        Response response = when().get(BASE_URL + "/users/" + DAVID.id() + "/timeline");
        timeline = Json.parse(response.asString()).asArray();

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.contentType()).isEqualTo(JSON.toString());
    }

    private void thenHeShouldSee(List<ITPost> posts) {
        for (int index = 0; index < posts.size(); index++) {
            assertThatJsonPostMatchesPost(timeline.get(index), posts.get(index));
        }
    }

}

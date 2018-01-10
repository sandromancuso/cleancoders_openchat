package acceptance;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import io.restassured.response.Response;
import org.junit.Test;
import org.openchat.domain.post.Post;

import java.time.LocalDateTime;

import static acceptance.APITestSuit.ALICE;
import static acceptance.APITestSuit.BASE_URL;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.http.ContentType.JSON;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.openchat.domain.post.PostBuilder.aPost;

public class TimelineAPI_AcceptanceTest {

    private static final LocalDateTime TODAY = LocalDateTime.now();
    private static final LocalDateTime YESTERDAY = TODAY.minusDays(1);

    private static final Post POST_1 = aPost().withUserId(ALICE.userId()).withText("Post 1").withDateTime(YESTERDAY).build();
    private static final Post POST_2 = aPost().withUserId(ALICE.userId()).withText("Post 2").withDateTime(TODAY).build();

    private Response response;
    private JsonArray timeline;

    @Test public void
    retrieve_a_timeline_with_all_posts_from_a_user_in_reverse_chronological_order() {
        givenAlicePosts(POST_1, POST_2);

        whenSheChecksHerTimeline();

        thenSheShouldSee(POST_2, POST_1);
    }

    private void givenAlicePosts(Post... posts) {
        asList(posts).forEach(post -> create(post));
    }

    private void whenSheChecksHerTimeline() {
        response = when().get(BASE_URL + "/user/" + ALICE.userId() + "/timeline");
        timeline = Json.parse(response.asString()).asArray();

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.contentType()).isEqualTo(JSON.toString());
    }

    private void thenSheShouldSee(Post... posts) {
        for (int index = 0; index < posts.length; index++) {
            assertThatTimelineContains(posts[index], index);
        }
    }

    private void assertThatTimelineContains(Post post, int index) {
        String text = timeline.get(index).asObject().getString("text", "");
        assertThat(text).isEqualTo(post.text());
    }

    private void create(Post post) {
        given()
                .body(withJsonContaining(post.text()))
        .when()
                .post(BASE_URL + "/user/" + post.userId() + "/posts")
        .then()
                .statusCode(201)
                .contentType(JSON)
                .body("postId", notNullValue())
                .body("userId", is(post.userId()))
                .body("text", is(post.text()))
                .body("date", notNullValue())
                .body("time", notNullValue());
    }

    private String withJsonContaining(String text) {
        return new JsonObject().add("text", text).toString();
    }
}

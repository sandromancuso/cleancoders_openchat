package acceptance;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import io.restassured.response.Response;
import org.junit.Test;

import static acceptance.APITestSuite.BASE_URL;
import static acceptance.RegisterTestUsers.ALICE;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

public class RetrieveTimelineAPI_AcceptanceTest {

    private static final String POST_1 = "Hello, I'm Alice.";
    private static final String POST_2 = "Anyone up for a movie tonight?";

    private String timeline;
    private Response response;

    @Test public void
    retrieve_a_users_timeline_with_posts_in_reverse_chronological_order() {
        givenAlicePosts(POST_1, POST_2);

        whenSheChecksHerTimeline();

        thenSheWillSee(POST_2, POST_1);
    }

    private void thenSheWillSee(String post2, String post1) {
        JsonArray postsJson = Json.parse(timeline).asArray();
        assertThat(postText(postsJson, 0)).isEqualTo(post2);
        assertThat(postText(postsJson, 1)).isEqualTo(post1);
    }

    private String postText(JsonArray postsJson, int index) {
        return postsJson.get(index).asObject().getString("text", "");
    }

    private void whenSheChecksHerTimeline() {
        response = when().get(BASE_URL + "/user/" + ALICE.id() + "/timeline");
        timeline = response.asString();

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.contentType()).isEqualTo(JSON.toString());
    }

    private void givenAlicePosts(String post1, String post2) {
        createPost(post1);
        createPost(post2);
    }

    private void createPost(String post) {
        given()
                .body(withNewPostJsonFor(post))
                .contentType(JSON)
        .when()
                .post(BASE_URL + "/user/" + ALICE.id() + "/posts")
        .then()
                .statusCode(201);
    }

    private String withNewPostJsonFor(String post1) {
        return new JsonObject().add("text", post1).toString();
    }
}

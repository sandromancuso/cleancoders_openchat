package acceptance;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import io.restassured.response.Response;
import org.junit.Test;
import org.openchat.domain.post.Post;
import org.openchat.domain.user.User;

import static acceptance.APITestSuit.*;
import static com.eclipsesource.json.Json.parse;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.openchat.domain.post.PostBuilder.aPost;

public class WallAPI_AcceptanceTest {

    private static final Post POST_1 = aPost().withText("Post 1").build();
    private static final Post POST_2 = aPost().withText("Post 2").build();
    private static final Post POST_3 = aPost().withText("Post 3").build();
    private static final Post POST_4 = aPost().withText("Post 4").build();
    private static final Post POST_5 = aPost().withText("Post 5").build();
    private static final Post POST_6 = aPost().withText("Post 6").build();

    private Response response;
    private JsonArray wall;


    @Test public void
    return_a_wall_containing_posts_from_the_user_and_her_followees() {
        givenAUserPost(ALICE, POST_1);
        givenAUserPost(BOB, POST_2);
        givenAUserPost(CHARLIE, POST_3);
        givenAUserPost(JULIE, POST_4);
        givenAUserPost(ALICE, POST_5);
        givenAUserPost(BOB, POST_6);

        givenAliceFollows(BOB, CHARLIE);

        whenAliceChecksHerWall();

        thenSheSeesThePosts(POST_6, POST_5, POST_3, POST_2, POST_1);
    }

    private void givenAUserPost(User user, Post post) {
        given()
                .body(withJsonContaining(post.text()))
        .when()
                .post(BASE_URL + "/user/" + user.userId() + "/posts")
        .then()
                .statusCode(201);
    }

    private void givenAliceFollows(User... followees) {
        asList(followees).forEach(followee -> createFollowing(ALICE, followee));
    }

    private void createFollowing(User follower, User followee) {
        given()
                .body(withJsonContaining(follower, followee))
        .when()
                .post(BASE_URL + "/follow")
        .then()
                .statusCode(201);
    }

    private void whenAliceChecksHerWall() {
        response = when().get(BASE_URL + "/user/" + ALICE.userId() + "/wall");
        wall = parse(response.asString()).asArray();

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.contentType()).isEqualTo("application/json");
    }

    private void thenSheSeesThePosts(Post... posts) {
        for (int index = 0; index < posts.length; index++) {
            assertThatTimelineContains(posts[index], index);
        }
    }

    private void assertThatTimelineContains(Post post, int index) {
        String text = wall.get(index).asObject().getString("text", "");
        assertThat(text).isEqualTo(post.text());
    }

    private String withJsonContaining(User follower, User followee) {
        return new JsonObject()
                        .add("followerId", follower.userId())
                        .add("followeeId", followee.userId())
                        .toString();
    }

    private String withJsonContaining(String text) {
        return new JsonObject().add("text", text).toString();
    }

}

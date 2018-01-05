package acceptance;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import io.restassured.response.Response;
import org.junit.Test;
import org.openchat.domain.user.User;

import static acceptance.APITestSuite.BASE_URL;
import static acceptance.RegisterTestUsers.*;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.http.ContentType.JSON;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class RetrieveWallAPI_AcceptanceTest {

    private static final String POST_1 = "Post 1";
    private static final String POST_2 = "Post 2";
    private static final String POST_3 = "Post 3";
    private static final String POST_4 = "Post 4";
    private static final String POST_5 = "Post 5";
    private static final String POST_6 = "Post 6";

    private Response response;
    private String wall;

    @Test public void
    retrieve_a_users_wall_containing_all_posts_from_the_user_and_from_all_users_she_follows() {
        givenPost(ALICE, POST_1);
        givenPost(BOB, POST_2);
        givenPost(CHARLIE, POST_3);
        givenPost(JULIE, POST_4);
        givenPost(ALICE, POST_5);
        givenPost(BOB, POST_6);

        givenAliceFollows(BOB, CHARLIE);

        whenAliceChecksHerWall();

        thenAliceShouldSeePosts(POST_6, POST_5, POST_3, POST_2, POST_1);
    }

    private void thenAliceShouldSeePosts(String... postTexts) {
        JsonArray wallPosts = Json.parse(wall).asArray();
        for (int i = 0; i < postTexts.length; i++) {
            assertThat(postText(wallPosts, i)).isEqualTo(postTexts[i]);
        }
    }

    private String postText(JsonArray wallPosts, int index) {
        return wallPosts.get(index).asObject().getString("text", "");
    }

    private void whenAliceChecksHerWall() {
        response = when().get(BASE_URL + "/user/" + ALICE.id() + "/wall");
        wall = response.asString();

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.contentType()).isEqualTo(JSON.toString());
    }

    private void givenAliceFollows(User... followees) {
        asList(followees).forEach(user -> follow(ALICE, user));
    }

    private void follow(User follower, User followee) {
        given()
                .body(withFollowingJsonFor(follower, followee))
                .contentType(JSON)
        .when()
                .post(BASE_URL + "/follow")
        .then()
                .statusCode(201);
    }

    private String withFollowingJsonFor(User follower, User followee) {
        return new JsonObject()
                        .add("followerId", follower.id())
                        .add("followeeId", followee.id())
                        .toString();
    }

    private void givenPost(User user, String postText) {
        given()
                .body(withNewPostJsonFor(postText))
                .contentType(JSON)
        .when()
                .post(BASE_URL + "/user/" + user.id() + "/posts")
        .then()
                .statusCode(201);
    }

    private String withNewPostJsonFor(String post1) {
        return new JsonObject().add("text", post1).toString();
    }

}

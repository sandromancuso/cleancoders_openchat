package acceptance;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.openchat.domain.post.Post;
import org.openchat.domain.user.User;

import static acceptance.APITestSuit.BASE_URL;
import static acceptance.OpenChatTestDSL.create;
import static acceptance.OpenChatTestDSL.register;
import static com.eclipsesource.json.Json.parse;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.openchat.domain.post.PostBuilder.aPost;
import static org.openchat.domain.user.UserBuilder.aUser;

public class WallAPI_AcceptanceTest {

    private static User ALICE   = aUser().withUsername("Alice"  ).build();
    private static User BOB     = aUser().withUsername("Bob"    ).build();
    private static User CHARLIE = aUser().withUsername("Charlie").build();
    private static User JULIE   = aUser().withUsername("Julie"  ).build();

    private static Post POST_1_ALICE = aPost().withText("Post 1").build();
    private static Post POST_2_BOB = aPost().withText("Post 2").build();
    private static Post POST_3_CHARLIE = aPost().withText("Post 3").build();
    private static Post POST_4_JULIE = aPost().withText("Post 4").build();
    private static Post POST_5_ALICE = aPost().withText("Post 5").build();
    private static Post POST_6_BOB = aPost().withText("Post 6").build();

    private JsonArray wall;

    @Before
    public void initialise() {
        ALICE = register(ALICE);
        BOB = register(BOB);
        CHARLIE = register(CHARLIE);
        JULIE = register(JULIE);

        POST_1_ALICE   = aPost().withUserId(ALICE  .userId()).withText("Post 1").build();
        POST_2_BOB     = aPost().withUserId(BOB    .userId()).withText("Post 2").build();
        POST_3_CHARLIE = aPost().withUserId(CHARLIE.userId()).withText("Post 3").build();
        POST_4_JULIE   = aPost().withUserId(JULIE  .userId()).withText("Post 4").build();
        POST_5_ALICE   = aPost().withUserId(ALICE  .userId()).withText("Post 5").build();
        POST_6_BOB     = aPost().withUserId(BOB    .userId()).withText("Post 6").build();
    }
    
    @Test public void
    return_a_wall_containing_posts_from_the_user_and_her_followees() {
        create(POST_1_ALICE);
        create(POST_2_BOB);
        create(POST_3_CHARLIE);
        create(POST_4_JULIE);
        create(POST_5_ALICE);
        create(POST_6_BOB);

        givenAliceFollows(BOB, CHARLIE);

        whenAliceChecksHerWall();

        thenSheSeesThePosts(POST_6_BOB, POST_5_ALICE, POST_3_CHARLIE, POST_2_BOB, POST_1_ALICE);
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
        Response response = when().get(BASE_URL + "/user/" + ALICE.userId() + "/wall");
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

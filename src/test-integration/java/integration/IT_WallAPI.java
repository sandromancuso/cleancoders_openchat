package integration;

import com.eclipsesource.json.JsonArray;
import integration.dsl.OpenChatTestDSL;
import integration.dsl.PostDSL.ITPost;
import integration.dsl.UserDSL.ITUser;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static com.eclipsesource.json.Json.parse;
import static integration.APITestSuit.BASE_URL;
import static integration.dsl.OpenChatTestDSL.assertThatJsonPostMatchesPost;
import static integration.dsl.OpenChatTestDSL.createFollowing;
import static integration.dsl.OpenChatTestDSL.register;
import static integration.dsl.PostDSL.ITPostBuilder.aPost;
import static integration.dsl.UserDSL.ITUserBuilder.aUser;
import static io.restassured.RestAssured.when;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class IT_WallAPI {

    private static ITUser ALICE   = aUser().withUsername("Alice"  ).build();
    private static ITUser BOB     = aUser().withUsername("Bob"    ).build();
    private static ITUser CHARLIE = aUser().withUsername("Charlie").build();
    private static ITUser JULIE   = aUser().withUsername("Julie"  ).build();

    private static ITPost POST_1_ALICE = aPost().withText("Post 1").build();
    private static ITPost POST_2_BOB = aPost().withText("Post 2").build();
    private static ITPost POST_3_CHARLIE = aPost().withText("Post 3").build();
    private static ITPost POST_4_JULIE = aPost().withText("Post 4").build();
    private static ITPost POST_5_ALICE = aPost().withText("Post 5").build();
    private static ITPost POST_6_BOB = aPost().withText("Post 6").build();

    private JsonArray wall;

    @Before
    public void initialise() {
        ALICE = register(ALICE);
        BOB = register(BOB);
        CHARLIE = register(CHARLIE);
        JULIE = register(JULIE);

        POST_1_ALICE   = aPost().withUserId(ALICE  .id()).withText("Post 1").build();
        POST_2_BOB     = aPost().withUserId(BOB    .id()).withText("Post 2").build();
        POST_3_CHARLIE = aPost().withUserId(CHARLIE.id()).withText("Post 3").build();
        POST_4_JULIE   = aPost().withUserId(JULIE  .id()).withText("Post 4").build();
        POST_5_ALICE   = aPost().withUserId(ALICE  .id()).withText("Post 5").build();
        POST_6_BOB     = aPost().withUserId(BOB    .id()).withText("Post 6").build();
    }
    
    @Test public void
    return_a_wall_containing_posts_from_the_user_and_her_followees() {
        givenPosts(POST_1_ALICE,
                   POST_2_BOB,
                   POST_3_CHARLIE,
                   POST_4_JULIE,
                   POST_5_ALICE,
                   POST_6_BOB);
        andAliceFollows(BOB, CHARLIE);

        whenAliceChecksHerWall();

        thenSheSeesThePosts(POST_6_BOB,
                            POST_5_ALICE,
                            POST_3_CHARLIE,
                            POST_2_BOB,
                            POST_1_ALICE);
    }

    private void givenPosts(ITPost... posts) {
        asList(posts).forEach(OpenChatTestDSL::create);
    }

    private void andAliceFollows(ITUser... followees) {
        asList(followees).forEach(followee -> createFollowing(ALICE, followee));
    }

    private void whenAliceChecksHerWall() {
        Response response = when().get(BASE_URL + "/users/" + ALICE.id() + "/wall");
        wall = parse(response.asString()).asArray();

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.contentType()).isEqualTo("application/json");
    }

    private void thenSheSeesThePosts(ITPost... posts) {
        for (int index = 0; index < posts.length; index++) {
            assertThatJsonPostMatchesPost(wall.get(index), posts[index]);
        }
    }

}

package integration.dsl;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import integration.dsl.PostDSL.ITPost;
import integration.dsl.UserDSL.ITUser;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static integration.APITestSuit.DATE_PATTERN;
import static integration.APITestSuit.UUID_PATTERN;
import static integration.dsl.UserDSL.ITUserBuilder.aUser;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.matchesPattern;

public class OpenChatTestDSL {

    private static Logger logger = LoggerFactory.getLogger(OpenChatTestDSL.class);

    private static final String BASE_URL = "http://localhost:4321";

    public static ITUser register(ITUser user) {
        logger.info("Register user: " + user);
        Response response = given()
                                .body(withRegistrationJsonFor(user))
                            .when()
                                .post(BASE_URL + "/users");
        logger.info("Registration response: " + response.body().asString());
        String userId = userIdFrom(response);
        ITUser registeredUser = aUser().clonedFrom(user).withId(userId).build();
        logger.info("Registration done. User: " + registeredUser);
        return registeredUser;
    }

    public static void create(ITPost post) {
        logger.info("Create post: " + post);
        given()
                .body(withPostJsonContaining(post.text()))
        .when()
                .post(BASE_URL + "/users/" + post.userId() + "/timeline")
        .then()
                .statusCode(201)
                .contentType(JSON)
                .body("postId", matchesPattern(UUID_PATTERN))
                .body("userId", is(post.userId()))
                .body("text", is(post.text()))
                .body("dateTime", notNullValue());
        logger.info("Post created.");
    }

    public static void createFollowing(ITUser follower, ITUser followee) {
        logger.info("Create following - follower [" + follower + "], followee [" + followee + "]");
        given()
                .body(withFollowingJsonContaining(follower, followee))
        .when()
                .post(BASE_URL + "/followings")
        .then()
                .statusCode(201);
        logger.info("Following created");
    }

    public static void assertAllUsersAreReturned(Response response, List<ITUser> users) {
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.contentType()).isEqualTo("application/json");

        JsonArray usersArray = Json.parse(response.body().asString()).asArray();
        users.forEach(user -> assertThat(usersArray.values().contains(jsonFor(user))).isTrue());
    }

    public static void assertThatJsonPostMatchesPost(JsonValue jsonValue, ITPost post) {
        JsonObject postJson = jsonValue.asObject();
        assertThat(postJson.getString("postId", "")).matches(UUID_PATTERN);
        assertThat(postJson.getString("userId", "")).matches(UUID_PATTERN);
        assertThat(postJson.getString("text", "")).isEqualTo(post.text());
        assertThat(postJson.getString("dateTime", "")).matches(DATE_PATTERN);
    }

    private static JsonObject jsonFor(ITUser user) {
        return new JsonObject()
                        .add("id", user.id())
                        .add("username", user.username())
                        .add("about", user.about());
    }

    private static String withFollowingJsonContaining(ITUser follower, ITUser followee) {
        return new JsonObject()
                        .add("followerId", follower.id())
                        .add("followeeId", followee.id())
                        .toString();
    }

    private static String withPostJsonContaining(String text) {
        return new JsonObject().add("text", text).toString();
    }

    private static String userIdFrom(Response response) {
        JsonObject responseJson = Json.parse(response.body().asString()).asObject();
        return responseJson.getString("id", "");
    }

    private static String withRegistrationJsonFor(ITUser user) {
        return new JsonObject()
                        .add("username", user.username())
                        .add("password", user.password())
                        .add("about", user.about())
                        .toString();
    }
}

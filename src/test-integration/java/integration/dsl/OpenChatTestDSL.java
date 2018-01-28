package integration.dsl;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import integration.dsl.PostDSL.Post;
import integration.dsl.UserDSL.User;
import io.restassured.response.Response;

import java.util.List;

import static integration.dsl.UserDSL.UserBuilder.aUser;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OpenChatTestDSL {

    private static final String BASE_URL = "http://localhost:4321";

    public static User register(User user) {
        Response response = given()
                                .body(withRegistrationJsonFor(user))
                            .when()
                                .post(BASE_URL + "/registration");
        String userId = userIdFrom(response);
        return aUser().clonedFrom(user).withId(userId).build();
    }

    public static void create(Post post) {
        given()
                .body(withPostJsonContaining(post.text()))
        .when()
                .post(BASE_URL + "/user/" + post.userId() + "/posts")
        .then()
                .statusCode(201)
                .contentType(JSON)
                .body("postId", notNullValue())
                .body("userId", is(post.userId()))
                .body("text", is(post.text()))
                .body("dateTime", notNullValue());
    }

    public static void createFollowing(User follower, User followee) {
        given()
                .body(withFollowingJsonContaining(follower, followee))
        .when()
                .post(BASE_URL + "/follow")
        .then()
                .statusCode(201);
    }

    public static void assertAllUsersAreReturned(Response response, List<User> users) {
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.contentType()).isEqualTo("application/json");

        JsonArray usersArray = Json.parse(response.body().asString()).asArray();
        users.forEach(user -> assertThat(usersArray.values().contains(jsonFor(user))).isTrue());
    }

    private static JsonObject jsonFor(User user) {
        return new JsonObject()
                        .add("userId", user.id())
                        .add("username", user.username())
                        .add("about", user.about());
    }

    private static String withFollowingJsonContaining(User follower, User followee) {
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
        return responseJson.getString("userId", "");
    }

    private static String withRegistrationJsonFor(User user) {
        return new JsonObject()
                .add("username", user.username())
                .add("password", user.password())
                .add("about", user.about())
                .toString();
    }
}

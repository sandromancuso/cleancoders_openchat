package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.openchat.entities.User;
import org.openchat.usecases.CreateUserRequest;
import spark.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class UserApiTest {

  private UserApi userApi;

  @Before
  public void setUp() throws Exception {
    userApi = new UserApi();
  }

  @Test
  public void canMakeRequest() throws Exception {
    String json = new JsonObject()
                              .add("username", "username")
                              .add("password", "password")
                              .add("about", "about").toString();

    CreateUserRequest cur = userApi.makeCreateUserRequest(json);
    assertThat(cur.username).isEqualTo("username");
    assertThat(cur.password).isEqualTo("password");
    assertThat(cur.about).isEqualTo("about");
  }

  @Test
  public void createsAppropriateResponseForDuplicateUser() throws Exception {
    StubResponse res = new StubResponse();
    String body = userApi.makeDuplicateUserResponse(res);
    assertThat(res.status()).isEqualTo(400);
    assertThat(body).isEqualTo("Username already in use.");
  }

  @Test
  public void createsAppropriateCreateUserResponse() throws Exception {
    StubResponse res = new StubResponse();
    User user = new User();
    user.username = "username";
    user.password = "don't care";
    user.about = "about";

    APIContext.makeUUIDFor("username");

    String body = userApi.makeCreatedUserResponse(user, res);
    JsonObject actual = Json.parse(body).asObject();

    assertThat(res.status()).isEqualTo(201);
    assertThat(res.type()).isEqualTo("application/json");

    assertThat(actual.getString("username", "")).isEqualTo("username");
    assertThat(actual.getString("about", "")).isEqualTo("about");
    assertThat(actual.getString("id", "")).isEqualTo(APIContext.getUUIDForUser("username"));
  }

  class StubResponse extends Response {
    private int status = -1;
    private String type = "TILT";

    public String type() {
      return type;
    }

    public void type(String contentType) {
      type = contentType;
    }

    public void status(int statusCode) {
      status = statusCode;
    }

    public int status() {
      return status;
    }
  }

}
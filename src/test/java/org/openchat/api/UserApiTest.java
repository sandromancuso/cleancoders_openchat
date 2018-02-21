package org.openchat.api;

import com.eclipsesource.json.JsonObject;
import org.junit.Before;
import org.junit.Test;
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
    Response res = new Response();
    userApi.makeDuplicateUserReponse(res)
  }

}
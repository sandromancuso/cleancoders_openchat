package org.openchat.usecases;

import org.junit.Before;
import org.junit.Test;
import org.openchat.entities.User;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateUserTest {
  private CreateUser useCase;

  @Before
  public void setUp() throws Exception {
    useCase = new CreateUser();
  }

  @Test
  public void createCreateUser() throws Exception {
    CreateUser cu = new CreateUser();
  }

  @Test
  public void canCreateUser() throws Exception {
    CreateUserRequest request = new CreateUserRequest();
    request.username = "username";
    request.password = "password";
    request.about = "about";
    User user = useCase.createUser(request);
    assertThat(user.username).isEqualTo("username");
    assertThat(user.password).isEqualTo("password");
    assertThat(user.about).isEqualTo("about");
  }

  @Test
  public void createdUserIsRegistered() throws Exception {

  }
}

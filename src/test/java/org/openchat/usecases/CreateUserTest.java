package org.openchat.usecases;

import org.junit.Before;
import org.junit.Test;
import org.openchat.entities.User;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateUserTest {
  private CreateUser useCase;
  private CreateUserRequest request;
  private User user;

  @Before
  public void setUp() throws Exception {
    UseCaseContext.initialize();
    useCase = new CreateUser();
    request = new CreateUserRequest();
    request.username = "username";
    request.password = "password";
    request.about = "about";
    user = useCase.createUser(request);
  }

  @Test
  public void userFieldsAreAllCorrect() throws Exception {
    assertThat(user.username).isEqualTo("username");
    assertThat(user.password).isEqualTo("password");
    assertThat(user.about).isEqualTo("about");
  }

  @Test
  public void createdUserIsRegistered() throws Exception {
    User fetchedUser = UseCaseContext.repository.getUser("username");
    assertThat(fetchedUser).isEqualTo(user);
  }

  @Test(expected=Repository.DuplicateUser.class)
  public void throwsExceptionIfDuplicateIsCreated() throws Exception {
    useCase.createUser(request);
  }
}

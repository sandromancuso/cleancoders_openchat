package org.openchat.usecases;

import org.junit.Before;
import org.junit.Test;
import org.openchat.entities.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openchat.entities.UserBuilder.aUser;

public class LoginTest {

  private User user;
  private Login login;

  @Before
  public void initialize() {
    UseCaseContext.initialize();
    user = aUser().build();
    login = new Login();
  }

  @Test
  public void canLoginWithGoodCredentials() throws Exception {
    UseCaseContext.repository.addUser(user);
    User u = login.validate(user.username, user.password);
    assertThat(u).isNotNull();
  }

  @Test
  public void canNotLoginIfcredentialsAreBad() throws Exception {
    assertThat(login.validate("nobody", "nopassword")).isNull();
  }

  @Test
  public void canNotLoginWithGoodNameButBadPassword() throws Exception {
    UseCaseContext.repository.addUser(user);
    assertThat(login.validate(user.username, "bad")).isNull();
  }
}

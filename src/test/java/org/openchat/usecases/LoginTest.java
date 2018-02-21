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
    boolean result = login.validate(user.username, user.password);
    assertThat(result).isTrue();
  }

  @Test
  public void canNotLoginIfcredentialsAreBad() throws Exception {
    assertThat(login.validate("nobody", "nopassword")).isFalse();
  }

  @Test
  public void canNotLoginWithGoodNameButBadPassword() throws Exception {
    UseCaseContext.repository.addUser(user);
    boolean result = login.validate(user.username, "bad");
    assertThat(result).isFalse();
  }
}

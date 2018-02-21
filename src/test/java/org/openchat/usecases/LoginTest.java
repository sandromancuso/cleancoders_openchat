package org.openchat.usecases;

import org.junit.Test;
import org.openchat.entities.UserBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openchat.entities.UserBuilder.aUser;

public class LoginTest {
  @Test
  public void canLoginWithGoodCredentials() throws Exception {
    UseCaseContext.repository.addUser(aUser().build());
    Boolean result = login.validate("username", "password");
    assertThat(result).isTrue();

  }
}

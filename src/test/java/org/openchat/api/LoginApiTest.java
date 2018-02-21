package org.openchat.api;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginApiTest {
  @Test
  public void canExtractRequest() throws Exception {
    LoginApi loginApi = new LoginApi();

    StubResponse res = new StubResponse();
    String result = loginApi.login(req, res);
    assertThat(loginApi.getUsername()).isEqualTo("username");
    assertThat(loginApi.getPassword()).isEqualTo("password");

  }
}

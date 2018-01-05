package org.openchat.api;

import com.eclipsesource.json.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openchat.core.domain.user.LoginData;
import org.openchat.core.domain.user.User;
import org.openchat.core.domain.user.UserService;
import spark.Request;
import spark.Response;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.openchat.builders.UserBuilder.aUser;

@RunWith(MockitoJUnitRunner.class)
public class LoginAPIShould {

    private static final String USERNAME = "Alice";
    private static final String PASSWORD = "123lk234";
    private static final User USER = aUser().withUsername(USERNAME).withPassword(PASSWORD).build();

    @Mock Request request;
    @Mock Response response;

    @Mock UserService userService;

    private LoginAPI loginAPI;

    @Before
    public void initialise() {
        loginAPI = new LoginAPI(userService);
        given(request.body()).willReturn(loginRequestJsonWith(USERNAME, PASSWORD));
    }

    @Test public void
    return_logged_in_user_json_after_successful_login() {
        given(userService.login(loginData(USERNAME, PASSWORD))).willReturn(Optional.of(USER));

        String responseJson = loginAPI.login(request, response);

        verify(response).status(200);
        verify(response).type("application/json");
        assertThat(responseJson).isEqualTo(jsonFor(USER));
    }

    @Test public void
    inform_when_credentials_are_not_valid() {
        given(userService.login(loginData(USERNAME, PASSWORD))).willReturn(Optional.empty());

        String responseBody = loginAPI.login(request, response);

        verify(response).status(400);
        assertThat(responseBody).isEqualTo("Invalid credentials.");
    }

    private Object jsonFor(User user) {
        return new JsonObject()
                        .add("userId", USER.id())
                        .add("username", USER.username())
                        .add("about", USER.about())
                        .toString();
    }

    private LoginData loginData(String username, String password) {
        return new LoginData(username, password);
    }

    private String loginRequestJsonWith(String username, String password) {
        return new JsonObject()
                        .add("username", USERNAME)
                        .add("password", PASSWORD)
                        .toString();
    }

}
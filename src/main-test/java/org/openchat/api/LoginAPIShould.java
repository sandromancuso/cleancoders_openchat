package org.openchat.api;

import com.eclipsesource.json.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openchat.domain.user.User;
import org.openchat.domain.user.UserService;
import spark.Request;
import spark.Response;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.openchat.domain.user.UserBuilder.aUser;

@RunWith(MockitoJUnitRunner.class)
public class LoginAPIShould {

    private static final User ALICE = aUser().build();
    private static final String JSON = "application/json";

    @Mock Request request;
    @Mock Response response;

    @Mock UserService userService;

    private LoginAPI loginAPI;

    @Before
    public void initialise() {
        loginAPI = new LoginAPI(userService);
    }

    @Test public void
    return_a_json_representing_an_existing_user() {
        given(request.body()).willReturn(jsonContaining(ALICE.username(), ALICE.password()));
        given(userService.userFor(ALICE.username(), ALICE.password())).willReturn(Optional.of(ALICE));

        String result = loginAPI.login(request, response);

        assertThat(result).isEqualTo(jsonContaining(ALICE));
        verify(response).status(200);
        verify(response).type(JSON);
    }

    @Test public void
    return_invalid_credentials_when_username_or_password_do_not_match() {
        given(request.body()).willReturn(jsonContaining("Unknown", "234234"));
        given(userService.userFor("Unknown", "234234")).willReturn(empty());

        String result = loginAPI.login(request, response);

        verify(response).status(400);
        assertThat(result).isEqualTo("Invalid credentials.");
    }

    private String jsonContaining(User user) {
        return new JsonObject()
                .add("userId", user.userId())
                .add("username", user.username())
                .add("about", user.about())
                .toString();
    }

    private String jsonContaining(String username, String password) {
        return new JsonObject()
                        .add("username", username)
                        .add("password", password)
                        .toString();
    }

}
package org.openchat.api;

import com.eclipsesource.json.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openchat.domain.users.User;
import org.openchat.domain.users.UserCredentials;
import org.openchat.domain.users.UserRepository;
import spark.Request;
import spark.Response;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.openchat.infrastructure.builders.UserBuilder.aUser;

@RunWith(MockitoJUnitRunner.class)
public class LoginAPIShould {

    private static final User USER = aUser().build();
    private static final String PASSWORD = "as234sdf";
    private static final String USERNAME = "Alice";
    private static final UserCredentials USER_CREDENTIALS = new UserCredentials(USERNAME, PASSWORD);

    @Mock Request request;
    @Mock Response response;

    @Mock UserRepository userRepository;

    private LoginAPI loginAPI;

    @Before
    public void initialise() {
        loginAPI = new LoginAPI(userRepository);
    }
    
    @Test public void
    return_a_json_representation_of_a_valid_user() {
        given(request.body()).willReturn(jsonContaining(USER_CREDENTIALS));
        given(userRepository.userFor(USER_CREDENTIALS)).willReturn(Optional.of(USER));

        String result = loginAPI.login(request, response);

        verify(response).status(200);
        verify(response).type("application/json");
        assertThat(result).isEqualTo(jsonContaining(USER));
    }

    @Test public void
    return_a_error_when_credentials_are_invalid() {
        given(request.body()).willReturn(jsonContaining(USER_CREDENTIALS));
        given(userRepository.userFor(USER_CREDENTIALS)).willReturn(empty());

        String result = loginAPI.login(request, response);

        verify(response).status(404);
        assertThat(result).isEqualTo("Invalid credentials.");
    } 
    
    private String jsonContaining(User user) {
        return new JsonObject()
                        .add("id", user.id())
                        .add("username", user.username())
                        .add("about", user.about())
                        .toString();
    }

    private String jsonContaining(UserCredentials userCredentials) {
        return new JsonObject()
                        .add("username", userCredentials.username())
                        .add("password", userCredentials.password())
                        .toString();
    }

}
package org.openchat.web.api;

import com.eclipsesource.json.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openchat.core.domain.user.RegistrationData;
import org.openchat.core.domain.user.User;
import org.openchat.core.domain.user.UserService;
import spark.Request;
import spark.Response;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationAPIShould {

    private static final String USERNAME = "Alice";
    private static final String PASSWORD = "user password";
    private static final String ABOUT = "About the user";
    private static final String USER_ID = "user id";
    private static final User USER = new User(USER_ID, USERNAME, PASSWORD, ABOUT);

    private RegistrationAPI registrationAPI;
    private RegistrationData registrationData;

    @Mock Request request;
    @Mock Response response;

    @Mock UserService userService;

    @Before
    public void initialise() {
        registrationAPI = new RegistrationAPI(userService);
        given(request.body()).willReturn(registrationJsonWith(USERNAME, PASSWORD, ABOUT));
        registrationData = new RegistrationData(USERNAME, PASSWORD, ABOUT);
    }

    @Test public void
    register_a_new_user() {
        given(userService.createUser(registrationData)).willReturn(Optional.of(USER));

        registrationAPI.registerUser(request, response);

        verify(userService).createUser(registrationData);
    }

    @Test public void
    return_json_containing_new_user_information() {
        given(userService.createUser(registrationData)).willReturn(Optional.of(USER));

        String userJson = registrationAPI.registerUser(request, response);

        verify(response).status(201);
        assertThat(userJson).isEqualTo(new JsonObject()
                                                .add("userId", USER.id())
                                                .add("username", USER.username())
                                                .add("about", USER.about())
                                                .toString());
    }

    @Test public void
    inform_when_username_already_exist() {
        given(userService.createUser(registrationData)).willReturn(empty());

        String responseBody = registrationAPI.registerUser(request, response);

        verify(response).status(400);
        assertThat(responseBody).isEqualTo("Username already in use.");
    }

    private String registrationJsonWith(String username, String password, String about) {
        return new JsonObject()
                        .add("username", username)
                        .add("password", password)
                        .add("about", about)
                        .toString();
    }

}
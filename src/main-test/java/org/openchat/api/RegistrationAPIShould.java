package org.openchat.api;

import com.eclipsesource.json.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openchat.domain.user.RegistrationData;
import org.openchat.domain.user.User;
import org.openchat.domain.user.UserService;
import spark.Request;
import spark.Response;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationAPIShould {

    private static final String USER_ID = "12343";
    private static final String USERNAME = "Alice";
    private static final String PASSWORD = "adsflk234";
    private static final String ABOUT = "About Alice";
    private static final User NEW_USER = new User(USER_ID, USERNAME, PASSWORD, ABOUT);
    private static final String JSON = "application/json";

    @Mock Request request;
    @Mock Response response;
    @Mock UserService userService;

    private RegistrationData registrationData;

    private RegistrationAPI registrationAPI;

    @Before
    public void initialise() {
        registrationAPI = new RegistrationAPI(userService);
        given(request.body()).willReturn(registrationDataWith(USERNAME, PASSWORD, ABOUT));
        registrationData = new RegistrationData(USERNAME, PASSWORD, ABOUT);
        given(userService.create(registrationData)).willReturn(Optional.of(NEW_USER));
    }

    @Test public void
    create_a_new_user() {
        registrationAPI.register(request, response);

        verify(userService).create(registrationData);
    }

    @Test public void
    return_a_json_representing_the_newly_created_user() {
        String resultJson = registrationAPI.register(request, response);

        assertThat(resultJson).isEqualTo(jsonContaining(NEW_USER));
        verify(response).status(201);
        verify(response).type(JSON);
    }

    @Test public void
    inform_username_is_already_in_use() {
        given(userService.create(registrationData)).willReturn(empty());

        String result = registrationAPI.register(request, response);

        verify(response).status(400);
        assertThat(result).isEqualTo("Username already in use.");
    }

    private String jsonContaining(User user) {
        return new JsonObject()
                        .add("userId", user.userId())
                        .add("username", user.username())
                        .add("about", user.about())
                        .toString();
    }

    private String registrationDataWith(String username, String password, String about) {
        return new JsonObject()
                        .add("username", username)
                        .add("password", password)
                        .add("about", about)
                        .toString();
    }

}
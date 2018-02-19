package org.openchat.api;

import com.eclipsesource.json.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openchat.domain.users.RegistrationData;
import org.openchat.domain.users.User;
import org.openchat.domain.users.UserService;
import spark.Request;
import spark.Response;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.openchat.infrastructure.builders.UserBuilder.aUser;

@RunWith(MockitoJUnitRunner.class)
public class UsersAPIShould {

    private static final String USER_ID = UUID.randomUUID().toString();
    private static final String USERNAME = "Alice";
    private static final String PASSWORD = "1slkjf234";
    private static final String ABOUT = "About Alice";

    private static final RegistrationData REGISTRATION_DATA = new RegistrationData(USERNAME, PASSWORD, ABOUT);

    private static final User USER = aUser()
                                            .withId(USER_ID)
                                            .withUsername(USERNAME)
                                            .withPassword(PASSWORD)
                                            .withAbout(ABOUT)
                                            .build();

    @Mock Request request;
    @Mock Response response;

    @Mock UserService userService;

    private UsersAPI usersAPI;

    @Before
    public void initialise() {
        usersAPI = new UsersAPI(userService);
        given(request.body()).willReturn(jsonContaining(REGISTRATION_DATA));
        given(userService.createUser(REGISTRATION_DATA)).willReturn(USER);
    }

    @Test public void
    create_a_new_user() {
        usersAPI.createUser(request, response);

        verify(userService).createUser(REGISTRATION_DATA);
    }

    @Test public void
    return_json_representing_a_newly_created_user() {
        String result = usersAPI.createUser(request, response);

        verify(response).status(201);
        verify(response).type("application/json");
        assertThat(result).isEqualTo(jsonContaining(USER));
    }

    private String jsonContaining(User user) {
        return new JsonObject()
                        .add("id", user.id())
                        .add("username", user.username())
                        .add("about", user.about())
                        .toString();
    }

    private String jsonContaining(RegistrationData registrationData) {
        return new JsonObject()
                        .add("username", registrationData.username())
                        .add("password", registrationData.password())
                        .add("about", registrationData.about())
                        .toString();
    }

}
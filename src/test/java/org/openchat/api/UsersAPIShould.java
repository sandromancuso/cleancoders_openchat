package org.openchat.api;

import com.eclipsesource.json.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openchat.domain.users.RegistrationData;
import org.openchat.domain.users.UserService;
import spark.Request;
import spark.Response;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UsersAPIShould {

    private static final String USERNAME = "Alice";
    private static final String PASSWORD = "1slkjf234";
    private static final String ABOUT = "About Alice";
    private static final RegistrationData REGISTRATION_DATA = new RegistrationData(USERNAME, PASSWORD, ABOUT);

    @Mock Request request;
    @Mock Response response;

    @Mock
    UserService userService;

    private UsersAPI usersAPI;

    @Before
    public void initialise() {
        usersAPI = new UsersAPI(userService);
    }

    @Test public void
    create_a_new_user() {
        given(request.body()).willReturn(jsonContaining(REGISTRATION_DATA));

        usersAPI.createUser(request, response);

        verify(userService).createUser(REGISTRATION_DATA);
    }

    private String jsonContaining(RegistrationData registrationData) {
        return new JsonObject()
                        .add("username", registrationData.username())
                        .add("password", registrationData.password())
                        .add("about", registrationData.about())
                        .toString();
    }

}
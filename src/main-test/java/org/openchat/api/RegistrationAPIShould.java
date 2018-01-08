package org.openchat.api;

import com.eclipsesource.json.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openchat.domain.user.RegistrationData;
import org.openchat.domain.user.UserService;
import spark.Request;
import spark.Response;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationAPIShould {

    private static final String USERNAME = "Alice";
    private static final String PASSWORD = "adsflk234";
    private static final String ABOUT = "About Alice";

    @Mock Request request;
    @Mock Response response;
    @Mock UserService userService;

    private RegistrationAPI registrationAPI;

    @Before
    public void initialise() {
        registrationAPI = new RegistrationAPI(userService);
    }

    @Test public void
    create_a_new_user() {
        given(request.body()).willReturn(registrationDataWith(USERNAME, PASSWORD, ABOUT));
        RegistrationData registrationData = new RegistrationData(USERNAME, PASSWORD, ABOUT);

        registrationAPI.register(request, response);

        verify(userService).create(registrationData);
    }

    private String registrationDataWith(String username, String password, String about) {
        return new JsonObject()
                        .add("username", username)
                        .add("password", password)
                        .add("about", about)
                        .toString();
    }

}
package org.openchat.core.actions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openchat.core.actions.RegisterUser.RegistrationData;
import org.openchat.core.domain.user.User;
import org.openchat.core.domain.user.UserRepositoryInMemory;
import org.openchat.core.infrastructure.IDGenerator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RegisterUserShould {

    private static final String USERNAME = "Alice";
    private static final String PASSWORD = "234lks34";
    private static final String ABOUT = "about the user";
    private static final String USER_ID = "123523";

    @Mock IDGenerator idGenerator;
    @Mock
    UserRepositoryInMemory userRepository;

    private RegisterUser registerUser;

    @Before
    public void initialise() {
        registerUser = new RegisterUser(idGenerator, userRepository);
        given(idGenerator.nextId()).willReturn(USER_ID);
        given(userRepository.isUsernameTaken(USERNAME)).willReturn(false);
    }

    @Test public void
    return_a_newly_created_user() {
        RegistrationData registrationData = new RegistrationData(USERNAME, PASSWORD, ABOUT);
        User user = new User(USER_ID, USERNAME, PASSWORD, ABOUT);

        User createdUser = registerUser.execute(registrationData).get();

        verify(userRepository).add(user);
        assertThat(createdUser).isEqualTo(user);
    }

    @Test public void
    return_no_user_if_username_is_already_taken() {
        given(userRepository.isUsernameTaken(USERNAME)).willReturn(true);
        RegistrationData registrationData = new RegistrationData(USERNAME, PASSWORD, ABOUT);

        assertThat(registerUser.execute(registrationData)).isEmpty();
    }

}
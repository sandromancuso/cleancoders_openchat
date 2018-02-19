package org.openchat.domain.users;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceShould {

    private static final String USER_ID = UUID.randomUUID().toString();

    private static final String USERNAME = "Alice";
    private static final String PASSWORD = "23dsd";
    private static final String ABOUT = "About";
    private static final RegistrationData REGISTRATION_DATA =
                                            new RegistrationData(USERNAME, PASSWORD, ABOUT);

    private static final User USER = new User(USER_ID, USERNAME, PASSWORD, ABOUT);

    @Mock IdGenerator idGenerator;
    @Mock UserRepository userRepository;

    private UserService userService;

    @Before
    public void initialise() {
        userService = new UserService(idGenerator, userRepository);
    }

    @Test public void
    create_a_user() throws UsernameAlreadyInUseException {
        given(idGenerator.next()).willReturn(USER_ID);

        User result = userService.createUser(REGISTRATION_DATA);

        verify(userRepository).add(USER);
        assertThat(result).isEqualTo(USER);
    }
    
}
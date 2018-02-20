package org.openchat.domain.users;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;
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
    private static final List<User> USERS = asList(USER);

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

    @Test(expected = UsernameAlreadyInUseException.class) public void
    throw_exception_when_attempting_to_create_a_duplicate_user() throws UsernameAlreadyInUseException {
        given(userRepository.isUsernameTaken(USERNAME)).willReturn(true);

        userService.createUser(REGISTRATION_DATA);
    }
    
    @Test public void
    return_all_users() {
        given(userRepository.all()).willReturn(USERS);

        List<User> result = userService.allUsers();

        assertThat(result).isEqualTo(USERS);
    }
    
}
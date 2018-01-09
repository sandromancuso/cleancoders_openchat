package org.openchat.domain.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.openchat.domain.user.UserBuilder.aUser;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceShould {

    private static final String USER_ID = "1232345234";
    private static final String USERNAME = "ALICE";
    private static final String PASSWORD = "ASLDFKJW34" ;
    private static final String ABOUT_ALICE = "About Alice";

    private static final User ALICE = aUser().build();

    @Mock IdGenerator idGenerator;
    @Mock UserRepository userRepository;

    private UserService userService;
    private static final RegistrationData REGISTRATION_DATA = new RegistrationData(USERNAME, PASSWORD, ABOUT_ALICE);

    @Before
    public void initialise() {
        userService = new UserService(idGenerator, userRepository);
        given(idGenerator.nextId()).willReturn(USER_ID);
        given(userRepository.isUsernameInUse(USERNAME)).willReturn(false);
    }

    @Test public void
    create_a_new_user() {
        User user = aUser()
                        .withUserId(idGenerator.nextId())
                        .withUsername(USERNAME)
                        .withPassword(PASSWORD)
                        .withAbout(ABOUT_ALICE)
                        .build();

        userService.create(REGISTRATION_DATA);

        verify(userRepository).add(user);
    }

    @Test public void
    return_the_newly_created_user() {
        User user = aUser()
                        .withUserId(idGenerator.nextId())
                        .withUsername(USERNAME)
                        .withPassword(PASSWORD)
                        .withAbout(ABOUT_ALICE)
                        .build();

        User result = userService.create(REGISTRATION_DATA);

        assertThat(result).isEqualTo(user);
    }

    @Test(expected = UsernameAlreadyInUseException.class) public void
    throws_exception_when_username_already_in_user() {
        given(userRepository.isUsernameInUse(USERNAME)).willReturn(true);

        userService.create(REGISTRATION_DATA);
    }

    @Test public void
    return_a_user_for_a_matching_username_and_password() {
        given(userRepository.userFor(ALICE.username(), ALICE.password())).willReturn(Optional.of(ALICE));

        Optional<User> user = userService.userFor(ALICE.username(), ALICE.password());

        assertThat(user.get()).isEqualTo(ALICE);
    }

    @Test public void
    return_a_user_for_a_matching_user_id() {
        given(userRepository.userForId(ALICE.userId())).willReturn(Optional.of(ALICE));

        Optional<User> user = userService.userForId(ALICE.userId());

        assertThat(user.get()).isEqualTo(ALICE);
    }



}
package org.openchat.core.domain.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openchat.core.infrastructure.IDGenerator;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.openchat.builders.UserBuilder.aUser;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceShould {

    private static final String USERNAME = "Alice";
    private static final String PASSWORD = "234lks34";
    private static final String ABOUT = "about the user";
    private static final String USER_ID = "123523";

    private static final LoginData LOGIN_DATA = new LoginData(USERNAME, PASSWORD);
    private static final User USER = aUser().withUsername(USERNAME).withPassword(PASSWORD).build();

    private static final User ALICE = aUser().build();
    private static final User BOB = aUser().build();

    private static final User UNKNOWN_USER = aUser().build();

    private static final List<User> ALL_USERS = emptyList();

    @Mock
    IDGenerator idGenerator;
    @Mock
    UserRepositoryInMemory userRepository;

    private UserService userService;

    @Before
    public void initialise() {
        userService = new UserService(idGenerator, userRepository);
        given(idGenerator.nextId()).willReturn(USER_ID);
        given(userRepository.isUsernameTaken(USERNAME)).willReturn(false);
        given(userRepository.userFor(UNKNOWN_USER.id())).willReturn(Optional.empty());
        given(userRepository.userFor(ALICE.id())).willReturn(Optional.of(ALICE));
        given(userRepository.userFor(BOB.id())).willReturn(Optional.of(BOB));
    }

    @Test
    public void
    return_a_newly_created_user() {
        RegistrationData registrationData = new RegistrationData(USERNAME, PASSWORD, ABOUT);
        User user = new User(USER_ID, USERNAME, PASSWORD, ABOUT);

        User createdUser = userService.createUser(registrationData).get();

        verify(userRepository).add(user);
        assertThat(createdUser).isEqualTo(user);
    }

    @Test public void
    return_no_user_if_username_is_already_taken() {
        given(userRepository.isUsernameTaken(USERNAME)).willReturn(true);
        RegistrationData registrationData = new RegistrationData(USERNAME, PASSWORD, ABOUT);

        assertThat(userService.createUser(registrationData)).isEmpty();
    }

    @Test public void
    return_no_user_when_credentials_do_not_match_any_existing_users() {
        given(userRepository.userWithMatchingCredentials(LOGIN_DATA)).willReturn(empty());

        assertThat(userService.login(LOGIN_DATA)).isEmpty();
    }

    @Test public void
    return_user_with_matching_credentials() {
        given(userRepository.userWithMatchingCredentials(LOGIN_DATA)).willReturn(Optional.of(USER));

        assertThat(userService.login(LOGIN_DATA)).isEqualTo(Optional.of(USER));
    }

    @Test public void
    retrieve_all_users() {
        given(userRepository.allUsers()).willReturn(ALL_USERS);

        List<User> returnedUsers = userService.allUsers();

        assertThat(returnedUsers).isEqualTo(ALL_USERS);
    }

    @Test(expected = InvalidUserException.class) public void
    throw_exception_when_follower_does_not_exist() {
        Following following = new Following(UNKNOWN_USER.id(), BOB.id());

        try {
            userService.create(following);
        } finally {
            verify(userRepository).userFor(following.followerId());
        }
    }

    @Test(expected = InvalidUserException.class) public void
    throw_exception_when_followee_does_not_exist() {
        Following following = new Following(ALICE.id(), UNKNOWN_USER.id());

        try {
            userService.create(following);
        } finally {
            verify(userRepository).userFor(following.followeeId());
        }
    }

    @Test public void
    create_following() {
        Following following = new Following(ALICE.id(), BOB.id());

        userService.create(following);

        verify(userRepository).add(new Following(ALICE.id(), BOB.id()));
    }

    @Test public void
    return_a_user_for_a_matching_id() {
        given(userRepository.userFor(USER_ID)).willReturn(Optional.of(USER));

        assertThat(userService.userFor(USER_ID)).contains(USER);
    }

    @Test public void
    return_followees_for_a_given_user() {
        given(userRepository.followeesFor(USER_ID)).willReturn(asList(ALICE, BOB));

        assertThat(userService.followeesFor(USER_ID)).contains(ALICE, BOB);
    }
}
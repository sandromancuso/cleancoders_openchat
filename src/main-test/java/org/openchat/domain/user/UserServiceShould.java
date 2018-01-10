package org.openchat.domain.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.openchat.domain.user.UserBuilder.aUser;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceShould {

    private static final String USER_ID = "1232345234";
    private static final String UNKNOWN_USER_ID = "23452324";

    private static final String USERNAME = "ALICE";
    private static final String PASSWORD = "ASLDFKJW34" ;
    private static final String ABOUT_ALICE = "About Alice";

    private static final User ALICE = aUser().build();
    private static final User BOB = aUser().build();
    private static final User CHARLIE = aUser().build();

    @Mock IdGenerator idGenerator;
    @Mock UserRepository userRepository;

    private UserService userService;
    private static final RegistrationData REGISTRATION_DATA = new RegistrationData(USERNAME, PASSWORD, ABOUT_ALICE);

    @Before
    public void initialise() {
        userService = new UserService(idGenerator, userRepository);
        given(idGenerator.nextId()).willReturn(USER_ID);
        given(userRepository.isUsernameInUse(USERNAME)).willReturn(false);
        given(userRepository.userForId(ALICE.userId())).willReturn(Optional.of(ALICE));
        given(userRepository.userForId(BOB.userId())).willReturn(Optional.of(BOB));
        given(userRepository.userForId(UNKNOWN_USER_ID)).willReturn(empty());
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

        Optional<User> user = userService.userBy(ALICE.username(), ALICE.password());

        assertThat(user.get()).isEqualTo(ALICE);
    }

    @Test public void
    return_a_user_for_a_matching_user_id() {
        Optional<User> user = userService.userBy(ALICE.userId());

        assertThat(user.get()).isEqualTo(ALICE);
    }

    @Test public void
    create_a_following_relationship() {
        userService.createFollowing(ALICE.userId(), BOB.userId());

        verify(userRepository).createFollowing(ALICE, BOB);
    }

    @Test(expected = UserDoesNotExistException.class) public void
    throw_exception_when_creating_a_following_if_follower_does_not_exist() {
        userService.createFollowing(UNKNOWN_USER_ID, ALICE.userId());
    }

    @Test(expected = UserDoesNotExistException.class) public void
    throw_exception_when_creating_a_following_if_followee_does_not_exist() {
        userService.createFollowing(ALICE.userId(), UNKNOWN_USER_ID);
    }

    @Test public void
    return_the_followees_for_a_given_user() {
        given(userRepository.followeesFor(ALICE.userId())).willReturn(asList(BOB, CHARLIE));

        List<User> followees = userService.followeesFor(ALICE.userId());

        assertThat(followees).containsExactly(BOB, CHARLIE);
    }
    
    @Test public void
    return_all_users() {
        given(userRepository.all()).willReturn(asList(ALICE, BOB));

        List<User> users = userService.allUsers();

        assertThat(users).containsExactly(ALICE, BOB);
    }

}
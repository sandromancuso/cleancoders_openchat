package org.openchat.core.domain.user;

import org.junit.Before;
import org.junit.Test;
import org.openchat.core.actions.Login.LoginData;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openchat.builders.UserBuilder.aUser;

public class UserRepositoryShould {

    private static final String NON_EXISTENT_USER_ID = "Non-existent ID";
    private static final String USER_ID = "21432343213";
    private static final User ALICE = aUser().build();
    private static final User BOB = aUser().build();
    private static final User CHARLIE = aUser().build();

    private UserRepository userRepository;

    @Before
    public void initialise() {
        userRepository = new UserRepository();
    }

    @Test public void
    store_a_user() {
        userRepository.add(aUser().withUsername("Alice").build());

        assertThat(userRepository.isUsernameTaken("Alice")).isTrue();
    }

    @Test public void
    inform_if_a_username_is_already_taken() {
        userRepository.add(aUser().withUsername("Bob").build());

        assertThat(userRepository.isUsernameTaken("Bob")).isTrue();
        assertThat(userRepository.isUsernameTaken("Charlie")).isFalse();
    }

    @Test public void
    return_no_user_when_no_existing_users_match_credentials() {
        LoginData invalidCredentials = new LoginData("invalid username", "invalid password");

        assertThat(userRepository.userWithMatchingCredentials(invalidCredentials)).isEmpty();
    }

    @Test public void
    return_user_with_matching_credentials() {
        userRepository.add(ALICE);
        LoginData invalidCredentials = new LoginData(ALICE.username(), ALICE.password());

        Optional<User> user = userRepository.userWithMatchingCredentials(invalidCredentials);

        assertThat(user).isEqualTo(Optional.of(ALICE));
    }
    
    @Test public void
    return_no_user_if_user_id_is_not_found() {
        Optional<User> result = userRepository.userFor(NON_EXISTENT_USER_ID);

        assertThat(result).isEmpty();
    }

    @Test public void
    return_user_with_a_matching_user_id() {
        User user = aUser().withId(USER_ID).build();
        userRepository.add(user);

        Optional<User> result = userRepository.userFor(USER_ID);

        assertThat(result).contains(user);
    }
    
    @Test public void
    return_followees_for_a_given_user() {
        userRepository.add(ALICE);
        userRepository.add(BOB);
        userRepository.add(CHARLIE);
        userRepository.addFollowing(ALICE.id(), BOB.id());
        userRepository.addFollowing(ALICE.id(), CHARLIE.id());

        List<User> followees = userRepository.followeesFor(ALICE.id());

        assertThat(followees).containsExactly(BOB, CHARLIE);
    }
    
    @Test public void
    return_all_users() {
        userRepository.add(ALICE);
        userRepository.add(BOB);
        userRepository.add(CHARLIE);

        List<User> users = userRepository.allUsers();

        assertThat(users).containsExactlyInAnyOrder(ALICE, BOB, CHARLIE);
    }

}
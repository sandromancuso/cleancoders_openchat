package org.openchat.domain.user;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openchat.domain.user.UserBuilder.aUser;

public class UserRepositoryShould {

    private static final User ALICE = aUser().withUsername("Alice").build();
    private static final User BOB = aUser().withUsername("Bob").build();
    private static final User CHARLIE = aUser().withUsername("CHARLIE").build();
    private static final User UNKNOWN = aUser().withUsername("Unknown").build();
    private UserRepository userRepository;

    @Before
    public void initialise() {
        userRepository = new UserRepository();

        userRepository.add(ALICE);
        userRepository.add(BOB);
        userRepository.add(CHARLIE);
    }

    @Test public void
    inform_when_username_is_in_use() {                   
        assertThat(userRepository.isUsernameInUse(ALICE.username())).isTrue();
        assertThat(userRepository.isUsernameInUse(BOB.username())).isTrue();

        assertThat(userRepository.isUsernameInUse(UNKNOWN.username())).isFalse();
    }

    @Test public void
    return_user_matching_username_and_password() {
        assertThat(userRepository.userFor(ALICE.username(), ALICE.password())).contains(ALICE);

        assertThat(userRepository.userFor(UNKNOWN.username(), UNKNOWN.password())).isEmpty();
    }

    @Test public void
    return_user_matching_user_id() {
        assertThat(userRepository.userForId(ALICE.userId())).contains(ALICE);

        assertThat(userRepository.userForId(UNKNOWN.userId())).isEmpty();
    }

    @Test public void
    return_the_followees_for_a_given_user() {
        userRepository.createFollowing(ALICE, BOB);
        userRepository.createFollowing(ALICE, CHARLIE);

        List<User> followees = userRepository.followeesFor(ALICE.userId());

        assertThat(followees).containsExactly(BOB, CHARLIE);
    }

}
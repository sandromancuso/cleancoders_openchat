package org.openchat.domain.users;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openchat.infrastructure.builders.UserBuilder.aUser;

public class UserRepositoryShould {

    private static final User ALICE = aUser().withUsername("Alice").build();
    private static final User CHARLIE = aUser().withUsername("Charlie").build();
    private static final UserCredentials ALICE_CREDENTIALS = new UserCredentials(ALICE.username(), ALICE.password());
    private static final UserCredentials CHARLIE_CREDENTIALS = new UserCredentials(CHARLIE.username(), CHARLIE.password());
    private static final UserCredentials UNKNOWN_CREDENTIALS = new UserCredentials("unknown", "unknown");

    private UserRepository userRepository;

    @Before
    public void initialise() {
        userRepository = new UserRepository();
    }

    @Test public void
    inform_when_a_username_is_already_taken() {
        userRepository.add(ALICE);

        assertThat(userRepository.isUsernameTaken(ALICE.username())).isTrue();
        assertThat(userRepository.isUsernameTaken(CHARLIE.username())).isFalse();
    }

    @Test public void
    return_user_matching_valid_credentials() {
        userRepository.add(ALICE);
        userRepository.add(CHARLIE);

        assertThat(userRepository.userFor(ALICE_CREDENTIALS)).contains(ALICE);
        assertThat(userRepository.userFor(CHARLIE_CREDENTIALS)).contains(CHARLIE);
        assertThat(userRepository.userFor(UNKNOWN_CREDENTIALS)).isEmpty();
    }
    
    @Test public void
    return_all_users() {
        userRepository.add(ALICE);
        userRepository.add(CHARLIE);

        assertThat(userRepository.all()).containsExactly(ALICE, CHARLIE);
    }

}
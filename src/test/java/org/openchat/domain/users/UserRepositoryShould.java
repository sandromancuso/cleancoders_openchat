package org.openchat.domain.users;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openchat.infrastructure.builders.UserBuilder.aUser;

public class UserRepositoryShould {

    private static final User ALICE = aUser().withUsername("Alice").build();
    private static final User CHARLIE = aUser().withUsername("Charlie").build();

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

}
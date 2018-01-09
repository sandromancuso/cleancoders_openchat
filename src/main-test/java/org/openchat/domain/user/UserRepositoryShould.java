package org.openchat.domain.user;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openchat.domain.user.UserBuilder.aUser;

public class UserRepositoryShould {

    private static final User ALICE = aUser().withUsername("Alice").build();
    private static final User BOB = aUser().withUsername("Bob").build();
    private static final User UNKNOWN = aUser().withUsername("Unknown").build();
    private UserRepository userRepository;

    @Before
    public void initialise() {
        userRepository = new UserRepository();

        userRepository.add(ALICE);
        userRepository.add(BOB);
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

}
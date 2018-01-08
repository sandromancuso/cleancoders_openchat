package org.openchat.domain.user;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openchat.domain.user.UserBuilder.aUser;

public class UserRepositoryShould {

    private UserRepository userRepository;

    @Before
    public void initialise() {
        userRepository = new UserRepository();
    }

    @Test public void
    inform_when_username_is_in_use() {
        userRepository.add(aUser().withUsername("Alice").build());
        userRepository.add(aUser().withUsername("Bob").build());

        assertThat(userRepository.isUsernameInUse("Alice")).isTrue();
        assertThat(userRepository.isUsernameInUse("Bob")).isTrue();
        assertThat(userRepository.isUsernameInUse("Charlie")).isFalse();
    }

}
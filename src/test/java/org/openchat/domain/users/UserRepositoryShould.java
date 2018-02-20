package org.openchat.domain.users;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openchat.infrastructure.builders.UserBuilder.aUser;

public class UserRepositoryShould {

    private static final User ALICE = aUser().withUsername("Alice").build();
    private static final User CHARLIE = aUser().withUsername("Charlie").build();
    private static final User BOB = aUser().build();
    private static final User LUCY = aUser().build();

    private static final UserCredentials ALICE_CREDENTIALS = new UserCredentials(ALICE.username(), ALICE.password());
    private static final UserCredentials CHARLIE_CREDENTIALS = new UserCredentials(CHARLIE.username(), CHARLIE.password());
    private static final UserCredentials UNKNOWN_CREDENTIALS = new UserCredentials("unknown", "unknown");

    private static final Following ALICE_FOLLOWS_CHARLIE = new Following(ALICE.id(), CHARLIE.id());
    private static final Following CHARLIE_FOLLOWS_ALICE = new Following(CHARLIE.id(), ALICE.id());

    private static final Following BOB_FOLLOWS_CHARLIE = new Following(BOB.id(), CHARLIE.id());
    private static final Following ALICE_FOLLOWS_LUCY = new Following(ALICE.id(), LUCY.id());
    private static final Following LUCY_FOLLOWS_BOB = new Following(LUCY.id(), BOB.id());

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

    @Test public void
    detect_a_following_already_exists() {
        userRepository.add(ALICE_FOLLOWS_CHARLIE);

        assertThat(userRepository.hasFollowing(ALICE_FOLLOWS_CHARLIE)).isTrue();
        assertThat(userRepository.hasFollowing(CHARLIE_FOLLOWS_ALICE)).isFalse();
    }
    
    @Test public void
    return_followees_for_a_given_follower_id() {
        userRepository.add(ALICE);
        userRepository.add(CHARLIE);
        userRepository.add(BOB);
        userRepository.add(LUCY);

        userRepository.add(ALICE_FOLLOWS_CHARLIE);
        userRepository.add(BOB_FOLLOWS_CHARLIE);
        userRepository.add(ALICE_FOLLOWS_LUCY);
        userRepository.add(LUCY_FOLLOWS_BOB);

        List<User> result = userRepository.followeesBy(ALICE.id());

        assertThat(result).containsExactly(CHARLIE, LUCY);
    }

}
package org.openchat.core.actions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openchat.core.domain.user.Following;
import org.openchat.core.domain.user.InvalidUserException;
import org.openchat.core.domain.user.User;
import org.openchat.core.domain.user.UserRepositoryInMemory;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.openchat.builders.UserBuilder.aUser;

@RunWith(MockitoJUnitRunner.class)
public class CreateFollowingShould {

    private static final User UNKNOWN_USER = aUser().build();
    private static final User ALICE = aUser().build();
    private static final User BOB = aUser().build();

    @Mock
    UserRepositoryInMemory userRepository;

    private CreateFollowing createFollowing;

    @Before
    public void initialise() {
        createFollowing = new CreateFollowing(userRepository);
        given(userRepository.userFor(UNKNOWN_USER.id())).willReturn(Optional.empty());
        given(userRepository.userFor(ALICE.id())).willReturn(Optional.of(ALICE));
        given(userRepository.userFor(BOB.id())).willReturn(Optional.of(BOB));
    }

    @Test(expected = InvalidUserException.class) public void
    throw_exception_when_follower_does_not_exist() {
        Following followingData = new Following(UNKNOWN_USER.id(), BOB.id());

        try {
            createFollowing.execute(followingData);
        } finally {
            verify(userRepository).userFor(followingData.followerId());
        }
    }

    @Test(expected = InvalidUserException.class) public void
    throw_exception_when_followee_does_not_exist() {
        Following followingData = new Following(ALICE.id(), UNKNOWN_USER.id());

        try {
            createFollowing.execute(followingData);
        } finally {
            verify(userRepository).userFor(followingData.followeeId());
        }
    }

    @Test public void
    create_following() {
        Following followingData = new Following(ALICE.id(), BOB.id());

        createFollowing.execute(followingData);

        verify(userRepository).add(followingData);
    }

}
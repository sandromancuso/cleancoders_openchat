package org.openchat.core.actions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openchat.core.domain.user.User;
import org.openchat.core.domain.user.UserRepositoryInMemory;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class RetrieveAllUsersShould {

    private static final List<User> ALL_USERS = emptyList();

    @Mock
    UserRepositoryInMemory userRepository;

    private RetrieveAllUsers retrieveAllUsers;

    @Before
    public void initialise() {
        retrieveAllUsers = new RetrieveAllUsers(userRepository);
    }

    @Test public void
    retrieve_all_users() {
        given(userRepository.allUsers()).willReturn(ALL_USERS);

        List<User> returnedUsers = retrieveAllUsers.execute();

        assertThat(returnedUsers).isEqualTo(ALL_USERS);
    }

}
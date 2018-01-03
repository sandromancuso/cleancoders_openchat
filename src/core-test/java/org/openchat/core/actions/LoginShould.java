package org.openchat.core.actions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openchat.core.actions.Login.LoginData;
import org.openchat.core.domain.user.User;
import org.openchat.core.domain.user.UserRepositoryInMemory;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.openchat.builders.UserBuilder.aUser;

@RunWith(MockitoJUnitRunner.class)
public class LoginShould {

    private static final String USERNAME = "Alice";
    private static final String PASSWORD = "a324fs23";
    private static final LoginData LOGIN_DATA = new LoginData(USERNAME, PASSWORD);
    private static final User USER = aUser().withUsername(USERNAME).withPassword(PASSWORD).build();

    @Mock
    UserRepositoryInMemory userRepository;

    private Login login;

    @Before
    public void initialise() {
        login = new Login(userRepository);
    }

    @Test public void
    return_no_user_when_credentials_do_not_match_any_existing_users() {
        given(userRepository.userWithMatchingCredentials(LOGIN_DATA)).willReturn(empty());

        assertThat(login.execute(LOGIN_DATA)).isEmpty();
    }

    @Test public void
    return_user_with_matching_credentials() {
        given(userRepository.userWithMatchingCredentials(LOGIN_DATA)).willReturn(Optional.of(USER));

        assertThat(login.execute(LOGIN_DATA)).isEqualTo(Optional.of(USER));
    }
}
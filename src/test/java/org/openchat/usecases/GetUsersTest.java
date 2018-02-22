package org.openchat.usecases;

import org.junit.Before;
import org.junit.Test;
import org.openchat.entities.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openchat.entities.UserBuilder.aUser;

public class GetUsersTest {
  @Before
  public void initialize() {
    UseCaseContext.initialize();
  }
  @Test
  public void canGetAllUsers() throws Exception {
    User bob = aUser().setUsername("Bob").build();
    UseCaseContext.repository.addUser(bob);
    GetUsers getUsers = new GetUsers();
    List<User> users = getUsers.getAll();
    assertThat(users).containsExactly(bob);


  }


}

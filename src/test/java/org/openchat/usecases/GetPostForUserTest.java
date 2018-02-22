package org.openchat.usecases;

import org.junit.Before;
import org.junit.Test;
import org.openchat.entities.User;

import java.util.List;

import static com.google.common.primitives.Ints.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.openchat.entities.UserBuilder.aUser;
import static org.openchat.usecases.UseCaseContext.repository;

public class GetPostForUserTest {
  @Before
  public void initialize() {
    UseCaseContext.initialize();
  }

  @Test
  public void canGetAllPostsForOneUser() throws Exception {
    User user = aUser().build();
    repository.addUser(user);
    repository.addDocument();

    assertThat(result).containsExactlyInAnyOrder();
  }
}

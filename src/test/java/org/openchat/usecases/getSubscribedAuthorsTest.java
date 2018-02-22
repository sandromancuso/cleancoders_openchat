package org.openchat.usecases;

import org.junit.Before;
import org.junit.Test;
import org.openchat.entities.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openchat.entities.UserBuilder.aUser;
import static org.openchat.usecases.UseCaseContext.repository;


public class getSubscribedAuthorsTest {
  @Before
  public void initialize() {
    UseCaseContext.initialize();
  }

  @Test
  public void canGetListOfAuthorsWeSubscribeTo() throws Exception {
    User subscriber = aUser().setUsername("subscriber").build();
    User author1 = aUser().setUsername("author1").build();
    User author2 = aUser().setUsername("author2").build();
    User author3 = aUser().setUsername("author3").build();

    repository.addUser(subscriber);
    repository.addUser(author1);
    repository.addUser(author2);
    repository.addUser(author3);

    repository.addSubscription(subscriber.username, author1.username);
    repository.addSubscription(subscriber.username, author2.username);

    List<User> authors = new GetSubscribedAuthors().exec(subscriber.username);
    assertThat(authors).containsExactlyInAnyOrder(author1, author2);
  }
}

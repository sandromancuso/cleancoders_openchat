package org.openchat.usecases;

import org.junit.Before;
import org.junit.Test;
import org.openchat.entities.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openchat.entities.UserBuilder.aUser;
import static org.openchat.usecases.UseCaseContext.repository;

public class SubscribeTest {

  @Before
  public void initialize() {
    UseCaseContext.initialize();
  }
  @Test
  public void canAddSubscription() throws Exception {
    User subscriber = aUser().setUsername("subscriber").build();
    User author = aUser().setUsername("author").build();
    repository.addUser(subscriber);
    repository.addUser(author);

    Subscribe subscribe = new Subscribe();
    subscribe.exec(subscriber.username, author.username);

    assertThat(follows(subscriber, author)).isTrue();
  }



  private boolean follows(User subscriber, User author) {
    return repository.follows(subscriber.username, author.username);
  }
}

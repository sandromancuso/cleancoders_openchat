package org.openchat.usecases;

import org.junit.Before;
import org.junit.Test;
import org.openchat.entities.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openchat.entities.UserBuilder.aUser;
import static org.openchat.usecases.UseCaseContext.repository;

public class SubscribeTest {

  private User subscriber;
  private User author;
  private Subscribe subscribe;

  @Before
  public void initialize() {
    UseCaseContext.initialize();
    subscriber = aUser().setUsername("subscriber").build();
    author = aUser().setUsername("author").build();
    repository.addUser(subscriber);
    repository.addUser(author);
    subscribe = new Subscribe();
  }
  @Test
  public void canAddSubscription() throws Exception {
    boolean added = subscribe.exec(subscriber.username, author.username);
    assertThat(added).isTrue();
    assertThat(follows(subscriber, author)).isTrue();
  }

  @Test
  public void cannotDuplicateSubscription() throws Exception {
    subscribe.exec(subscriber.username, author.username);
    boolean added = subscribe.exec(subscriber.username, author.username);
    assertThat(added).isFalse();
  }

  private boolean follows(User subscriber, User author) {
    return repository.follows(subscriber.username, author.username);
  }
}

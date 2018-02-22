package org.openchat.usecases;

import org.junit.Before;
import org.junit.Test;
import org.openchat.entities.Document;
import org.openchat.entities.DocumentBuilder;
import org.openchat.entities.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openchat.entities.DocumentBuilder.aDoc;
import static org.openchat.entities.UserBuilder.aUser;
import static org.openchat.usecases.UseCaseContext.repository;

public class GetPostsForUserTest {
  @Before
  public void initialize() {
    UseCaseContext.initialize();
  }

  @Test
  public void canGetAllPostsForOneUser() throws Exception {
    User user = aUser().setUsername("u1").build();
    Document d1 = aDoc().setId(1).setUsername("u1").build();
    Document d2 = aDoc().setId(2).setUsername("u1").build();
    Document d3 = aDoc().setId(3).setUsername("u2").build();
    repository.addUser(user);
    repository.addUser(aUser().setUsername("u2").build());
    repository.addDocument(d1);
    repository.addDocument(d2);
    repository.addDocument(d3);

    GetPostsForUser getPostsForUser = new GetPostsForUser();
    List<Document> docs = getPostsForUser.exec("u1");

    assertThat(docs).containsExactlyInAnyOrder(d1, d2);
  }
}

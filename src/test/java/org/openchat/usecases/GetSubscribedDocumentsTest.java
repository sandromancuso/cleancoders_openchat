package org.openchat.usecases;

import org.junit.Before;
import org.junit.Test;
import org.openchat.entities.Document;
import org.openchat.entities.User;

import javax.jws.soap.SOAPBinding;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openchat.entities.DocumentBuilder.aDoc;
import static org.openchat.entities.UserBuilder.aUser;
import static org.openchat.usecases.UseCaseContext.repository;

public class GetSubscribedDocumentsTest {
  @Before
  public void initialize() {
    UseCaseContext.initialize();
  }

  @Test
  public void canGetAllSubscribedDocuments() throws Exception {
    User subscriber = aUser().setUsername("subscriber").build();
    User author1 = aUser().setUsername("author1").build();
    User author2 = aUser().setUsername("author2").build();
    User dummy = aUser().setUsername("dummy").build();

    Document d1 = aDoc().setId(1).setUsername("subscriber").build();
    Document d2 = aDoc().setId(2).setUsername("author1").build();
    Document d3 = aDoc().setId(3).setUsername("author1").build();
    Document d4 = aDoc().setId(4).setUsername("author2").build();
    Document d5 = aDoc().setId(5).setUsername("dummy").build();

    repository.addUser(subscriber);
    repository.addUser(author1);
    repository.addUser(author2);
    repository.addUser(dummy);

    repository.addDocument(d1);
    repository.addDocument(d2);
    repository.addDocument(d3);
    repository.addDocument(d4);
    repository.addDocument(d5);

    repository.addSubscription(subscriber.username, author1.username);
    repository.addSubscription(subscriber.username, author2.username);

    GetSubscribedDocuments getSubscribedDocuments = new GetSubscribedDocuments();
    List<Document> documents = getSubscribedDocuments.exec(subscriber.username);

    assertThat(documents).containsExactlyInAnyOrder(d1, d2, d3, d4);
  }
}

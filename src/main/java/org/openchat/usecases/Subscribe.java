package org.openchat.usecases;

import static org.openchat.usecases.UseCaseContext.repository;

public class Subscribe {
  public boolean exec(String subscriberName, String authorName) {
    if (repository.follows(subscriberName, authorName))
      return false;
    else {
      repository.addSubscription(subscriberName, authorName);
      return true;
    }
  }
}

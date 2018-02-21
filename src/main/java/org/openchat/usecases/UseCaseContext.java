package org.openchat.usecases;

import org.openchat.repositories.InMemoryRepository;

public class UseCaseContext {
  public static void initialize() {
    repository = new InMemoryRepository();
  }

  public static Repository repository;
}

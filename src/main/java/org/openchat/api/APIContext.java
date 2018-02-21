package org.openchat.api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class APIContext {
  public static APIContext instance = null;
  public static void initialize() {
    instance = new APIContext();
  }
  private Map<String, String> userUUIDS = new HashMap<>();

  public String getUUIDForUser(String username) {
    return userUUIDS.get(username);
  }

  public void makeUUIDForUser(String username) {
    userUUIDS.put(username, UUID.randomUUID().toString());
  }
}

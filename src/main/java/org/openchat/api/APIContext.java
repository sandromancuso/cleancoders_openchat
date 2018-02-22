package org.openchat.api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class APIContext {
  public static APIContext instance = null;
  public static void initialize() {
    instance = new APIContext();
  }
  private Map<String, String> usernameToUUID = new HashMap<>();
  private Map<String, String> UUIDToUsername = new HashMap<>();

  public void makeUUIDForUser(String username) {
    String uuid = UUID.randomUUID().toString();
    usernameToUUID.put(username, uuid);
    UUIDToUsername.put(uuid, username);

  }

  public String getUUIDForUser(String username) {
    return usernameToUUID.get(username);
  }

  public String getUserNameForUUID(String uuid) {
    return UUIDToUsername.get(uuid);
  }
}

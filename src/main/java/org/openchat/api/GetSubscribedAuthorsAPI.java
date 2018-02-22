package org.openchat.api;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.openchat.entities.User;
import org.openchat.usecases.GetSubscribedAuthors;
import spark.Request;
import spark.Response;

import java.util.List;

public class GetSubscribedAuthorsAPI {

  public String exec(Request req, Response res) {
    String subscriberId = req.params("followerId");
    String subscriberName = APIContext.instance.getUserNameForUUID(subscriberId);
    GetSubscribedAuthors getSubscribedAuthors = new GetSubscribedAuthors();
    List<User> authors = getSubscribedAuthors.exec(subscriberName);

    JsonArray authorArray = new JsonArray();
    for (User author : authors) {
      JsonObject user = new JsonObject()
                          .add("id", APIContext.instance.getUUIDForUser(author.username))
                          .add("username", author.username)
                          .add("about", author.about);

      authorArray.add(user);
    }

    res.status(200);
    res.type("application/json");
    return authorArray.toString();
  }
}

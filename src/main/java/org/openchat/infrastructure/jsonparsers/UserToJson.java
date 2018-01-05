package org.openchat.infrastructure.jsonparsers;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.openchat.domain.user.User;

import java.util.List;

public class UserToJson {
    public static String jsonFor(List<User> users) {
        JsonArray usersJson = new JsonArray();
        users.forEach(user -> usersJson.add(jsonFor(user)));
        return usersJson.toString();
    }

    public static JsonObject jsonFor(User user) {
        return new JsonObject()
                        .add("userId", user.id())
                        .add("username", user.username())
                        .add("about", user.about());
    }
}

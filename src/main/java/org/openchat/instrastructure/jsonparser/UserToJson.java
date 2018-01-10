package org.openchat.instrastructure.jsonparser;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.openchat.domain.user.User;

import java.util.List;

public class UserToJson {

    public static String jsonFor(User user) {
        return jsonObjectFor(user).toString();
    }

    public static String jsonFor(List<User> users) {
        JsonArray usersJson = new JsonArray();
        users.forEach(user -> usersJson.add(jsonObjectFor(user)));
        return usersJson.toString();
    }

    private static JsonObject jsonObjectFor(User user) {
        return new JsonObject()
                        .add("userId", user.userId())
                        .add("username", user.username())
                        .add("about", user.about());
    }
}

package org.openchat.infrastructure.json;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.openchat.domain.users.User;

import java.util.List;

public class UserJson {
    public static String jsonFor(User user) {
        return jsonObjectFor(user)
                        .toString();
    }

    public static String jsonFor(List<User> users) {
        JsonArray jsonArray = new JsonArray();
        users.forEach(user -> jsonArray.add(jsonObjectFor(user)));
        return jsonArray.toString();
    }

    private static JsonObject jsonObjectFor(User user) {
        return new JsonObject()
                .add("id", user.id())
                .add("username", user.username())
                .add("about", user.about());
    }
}

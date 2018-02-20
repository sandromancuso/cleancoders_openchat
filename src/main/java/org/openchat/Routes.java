package org.openchat;

import static spark.Spark.get;
import static spark.Spark.options;

public class Routes {

    public void create() {
        openchatRoutes();
    }

    private void openchatRoutes() {
        get("status", (req, res) -> "OpenChat: OK!");
    }

}

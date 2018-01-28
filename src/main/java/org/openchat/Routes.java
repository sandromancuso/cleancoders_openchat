package org.openchat;

import static spark.Spark.get;

public class Routes {

    public void create() {
        get("status", (req, res) -> "OpenChat: OK!");
    }
}

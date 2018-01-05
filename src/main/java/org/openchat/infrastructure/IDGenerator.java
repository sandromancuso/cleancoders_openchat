package org.openchat.infrastructure;

import java.util.UUID;

public class IDGenerator {
    public String nextId() {
        return UUID.randomUUID().toString();
    }
}

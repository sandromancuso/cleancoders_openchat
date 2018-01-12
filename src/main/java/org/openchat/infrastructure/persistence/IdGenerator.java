package org.openchat.infrastructure.persistence;

import java.util.UUID;

public class IdGenerator {
    public String nextId() {
        return UUID.randomUUID().toString();
    }
}

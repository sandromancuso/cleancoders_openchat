package org.openchat.domain.user;

import java.util.UUID;

public class IdGenerator {
    public String nextId() {
        return UUID.randomUUID().toString();
    }
}

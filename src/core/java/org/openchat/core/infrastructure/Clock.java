package org.openchat.core.infrastructure;

import java.time.LocalDateTime;

public class Clock {
    public LocalDateTime dateTime() {
        return LocalDateTime.now();
    }
}

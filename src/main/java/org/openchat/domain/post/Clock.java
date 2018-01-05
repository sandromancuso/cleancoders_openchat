package org.openchat.domain.post;

import java.time.LocalDateTime;

public class Clock {
    public LocalDateTime dateTime() {
        return LocalDateTime.now();
    }
}

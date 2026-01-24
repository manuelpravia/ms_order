package org.mpravia.handler;

import java.time.LocalDateTime;

public record ErrorDetails(
        String code,
        String message,
        int status,
        LocalDateTime timestamp,
        String path
) {}

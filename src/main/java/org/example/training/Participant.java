package org.example.training;

import java.time.ZoneOffset;

record Participant(
    String fullName,
    String role,
    String region,
    ZoneOffset timezoneOffset,
    int acceptableHoursShift
) {}

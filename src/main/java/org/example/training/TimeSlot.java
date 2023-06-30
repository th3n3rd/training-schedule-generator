package org.example.training;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

record TimeSlot(OffsetDateTime startTime, Duration duration) {

    public TimeSlot(LocalTime startTime, Duration duration) {
        this(
            OffsetDateTime.of(
                LocalDate.now(),
                startTime,
                ZoneOffset.UTC
            ),
            duration
        );
    }

}

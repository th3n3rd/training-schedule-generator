package org.example.training;

import static org.example.training.WorkingHours.HalfDay;

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

    public boolean isBetween(OffsetDateTime startTime, OffsetDateTime endTime) {
        return !startTime().isBefore(startTime) && !endTime().isAfter(endTime);
    }

    public OffsetDateTime endTime() {
        return startTime.plus(duration);
    }

    public static TimeSlot halfDay(LocalTime startTime) {
        return new TimeSlot(startTime, HalfDay);
    }
}

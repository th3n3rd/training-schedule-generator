package org.example.training;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

record Participant(
    String fullName,
    String role,
    String region,
    ZoneOffset timezoneOffset,
    int acceptableHoursShift
) {
    public OffsetDateTime earliestStartTime() {
        return OffsetDateTime.of(
            LocalDate.now(),
            WorkingHours.StartTime,
            timezoneOffset
        ).plusHours(acceptableHoursShift);
    }

    public OffsetDateTime latestEndTime() {
        return OffsetDateTime.of(
            LocalDate.now(),
            WorkingHours.EndTime,
            timezoneOffset
        ).plusHours(acceptableHoursShift);
    }

    public boolean isAvailableFor(TimeSlot slot) {
        if (Objects.isNull(slot)) {
            return false;
        }
        return slot.isBetween(
            earliestStartTime(),
            latestEndTime()
        );
    }
}

package org.example.training;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TimeSlotTests {

    private final TimeSlot slot24h = new TimeSlot(
        LocalTime.MIDNIGHT,
        Duration.ofHours(24)
    );

    @Nested
    class TimeBoundaries {

        @Test
        void againstIntervalsInTheSameTimezone() {
            assertSlotBoundariesAround(slot24h.startTime(), slot24h.endTime());
        }

        @Test
        void againstIntervalsInDifferentTimezones() {
            assertSlotBoundariesAround(
                equivalentInTimezone(slot24h.startTime(), -5),
                equivalentInTimezone(slot24h.endTime(), -5)
            );
            assertSlotBoundariesAround(
                equivalentInTimezone(slot24h.startTime(), +7),
                equivalentInTimezone(slot24h.endTime(), +7)
            );
        }

    }

    private void assertSlotBoundariesAround(OffsetDateTime startTime, OffsetDateTime endTime) {
        assertThat(slot24h.isBetween(startTime, endTime))
            .as("Slot should be within the exact interval")
            .isTrue();

        assertThat(slot24h.isBetween(startTime.minusSeconds(1), endTime))
            .as("Slot should be within an interval with earlier start time")
            .isTrue();

        assertThat(slot24h.isBetween(startTime, endTime.plusSeconds(1)))
            .as("Slot should be within an interval with later end time")
            .isTrue();

        assertThat(slot24h.isBetween(startTime.minusSeconds(1), endTime.plusSeconds(1)))
            .as("Slot should be within a larger interval")
            .isTrue();

        assertThat(slot24h.isBetween(startTime.plusSeconds(1), endTime))
            .as("Slot should not be within an interval with later start time")
            .isFalse();

        assertThat(slot24h.isBetween(startTime, endTime.minusSeconds(1)))
            .as("Slot should not be within an interval with earlier end time")
            .isFalse();

        assertThat(slot24h.isBetween(startTime.plusSeconds(1), endTime.minusSeconds(1)))
            .as("Slot should not be within an smaller interval")
            .isFalse();
    }

    private OffsetDateTime equivalentInTimezone(OffsetDateTime originalTime, int hoursShift) {
        return originalTime.minusHours(hoursShift * -1).withOffsetSameLocal(ZoneOffset.ofHours(hoursShift));
    }
}

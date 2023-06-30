package org.example.training;

import static java.time.ZoneOffset.UTC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.example.training.WorkingHours.NoHoursShift;

import com.github.javafaker.Faker;
import java.time.LocalTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ParticipantTests {

    private final Faker faker = new Faker();

    @Nested
    class Availability {
        private static final ZoneOffset OneHourAhead = ZoneOffset.ofHours(+1);
        private static final ZoneOffset OneHourBehind = ZoneOffset.ofHours(-1);

        @Test
        void withinUtc() {
            assertThat(anyParticipant(UTC).isAvailableFor(halfDaySessionAt("08:00"))).isFalse();
            assertThat(anyParticipant(UTC).isAvailableFor(halfDaySessionAt("09:00"))).isTrue();
            assertThat(anyParticipant(UTC).isAvailableFor(halfDaySessionAt("13:00"))).isTrue();
            assertThat(anyParticipant(UTC).isAvailableFor(halfDaySessionAt("14:00"))).isFalse();
        }

        @Test
        void differentTimezone() {
            assertThat(anyParticipant(OneHourAhead).isAvailableFor(halfDaySessionAt("07:00"))).isFalse();
            assertThat(anyParticipant(OneHourAhead).isAvailableFor(halfDaySessionAt("08:00"))).isTrue();
            assertThat(anyParticipant(OneHourAhead).isAvailableFor(halfDaySessionAt("12:00"))).isTrue();
            assertThat(anyParticipant(OneHourAhead).isAvailableFor(halfDaySessionAt("13:00"))).isFalse();

            assertThat(anyParticipant(OneHourBehind).isAvailableFor(halfDaySessionAt("09:00"))).isFalse();
            assertThat(anyParticipant(OneHourBehind).isAvailableFor(halfDaySessionAt("10:00"))).isTrue();
            assertThat(anyParticipant(OneHourBehind).isAvailableFor(halfDaySessionAt("14:00"))).isTrue();
            assertThat(anyParticipant(OneHourBehind).isAvailableFor(halfDaySessionAt("15:00"))).isFalse();
        }
    }

    private TimeSlot halfDaySessionAt(String time) {
        return TimeSlot.halfDay(LocalTime.parse(time));
    }

    private Participant anyParticipant(ZoneOffset timezoneOffset) {
        return anyParticipant(timezoneOffset, NoHoursShift);
    }

    private Participant anyParticipant(ZoneOffset timezoneOffset, int acceptableHoursShift) {
        return new Participant(
            faker.name().fullName(),
            faker.job().title(),
            "dont-care",
            timezoneOffset,
            acceptableHoursShift
        );
    }
}

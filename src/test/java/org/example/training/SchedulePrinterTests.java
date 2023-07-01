package org.example.training;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.Test;

class SchedulePrinterTests {

    private final SchedulePrinter printer = new SchedulePrinter();

    @Test
    void singleTrainingSessionOneParticipant() {
        var schedule = new Schedule(
            List.of(
                participant("Aaron Armstrong").scheduleFor(halfDaySessionAt("09:00"))
            ),
            emptyList()
        );

        var printedSchedule = printer.print(schedule);

        assertConsecutiveLines(
            printedSchedule,
            "From 09:00 to 13:00 UTC",
            "1 Participants: Aaron Armstrong"
        );
    }

    @Test
    void singleTrainingSessionsManyParticipants() {
        var schedule = new Schedule(
            List.of(
                participant("Aaron Armstrong").scheduleFor(halfDaySessionAt("09:00")),
                participant("Bella Bennet").scheduleFor(halfDaySessionAt("09:00"))
            ),
            emptyList()
        );

        var printedSchedule = printer.print(schedule);

        assertConsecutiveLines(
            printedSchedule,
            "From 09:00 to 13:00 UTC",
            "2 Participants: Aaron Armstrong, Bella Bennet"
        );
    }

    @Test
    void manyTrainingSessionsWithManyParticipants() {
        var schedule = new Schedule(
            List.of(
                participant("Aaron Armstrong").scheduleFor(halfDaySessionAt("10:00")),
                participant("Bella Bennet").scheduleFor(halfDaySessionAt("10:00")),
                participant("Calvin Cooper").scheduleFor(halfDaySessionAt("11:00")),
                participant("Daphne Davis").scheduleFor(halfDaySessionAt("11:00"))
            ),
            emptyList()
        );

        var printedSchedule = printer.print(schedule);


        assertConsecutiveLines(
            printedSchedule,
            "From 10:00 to 14:00 UTC",
            "2 Participants: Aaron Armstrong, Bella Bennet"
        );

        assertConsecutiveLines(
            printedSchedule,
            "From 11:00 to 15:00 UTC",
            "2 Participants: Calvin Cooper, Daphne Davis"
        );
    }

    @Test
    void unplacedParticipants() {
        var schedule = new Schedule(
            List.of(
                participant("Ethan Evans").unplaced(),
                participant("Felicity Fisher").unplaced()
            ),
            emptyList()
        );

        var printedSchedule = printer.print(schedule);

        assertConsecutiveLines(
            printedSchedule,
            "2 Unplaced: Ethan Evans, Felicity Fisher"
        );
    }

    @Test
    void placedAndUnplacedParticipants() {
        var schedule = new Schedule(
            List.of(
                participant("Ethan Evans").unplaced(),
                participant("Felicity Fisher").unplaced(),
                participant("Aaron Armstrong").scheduleFor(halfDaySessionAt("12:00")),
                participant("Bella Bennet").scheduleFor(halfDaySessionAt("12:00"))
            ),
            emptyList()
        );

        var printedSchedule = printer.print(schedule);

        assertConsecutiveLines(
            printedSchedule,
            "From 12:00 to 16:00 UTC",
            "2 Participants: Aaron Armstrong, Bella Bennet"
        );

        assertConsecutiveLines(
            printedSchedule,
            "2 Unplaced: Ethan Evans, Felicity Fisher"
        );
    }

    private static void assertConsecutiveLines(String printedSchedule, String ...lines) {
        assertThat(printedSchedule).contains(String.join("\n", lines));
    }

    private TimeSlot halfDaySessionAt(String time) {
        return TimeSlot.halfDay(LocalTime.parse(time));
    }

    private Participant participant(String fullName) {
        return new Participant(
            fullName,
            "dont-care",
            "dont-care",
            ZoneOffset.UTC,
            WorkingHours.NoHoursShift
        );
    }
}

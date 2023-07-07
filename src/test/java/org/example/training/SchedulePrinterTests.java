package org.example.training;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class SchedulePrinterTests {

    public static final int NotFound = -1;
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

        assertContainsLinesInOrder(
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

        assertContainsLinesInOrder(
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

        assertContainsLinesInOrder(
            printedSchedule,
            "From 10:00 to 14:00 UTC",
            "2 Participants: Aaron Armstrong, Bella Bennet"
        );

        assertContainsLinesInOrder(
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

        assertContainsLinesInOrder(
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

        assertContainsLinesInOrder(
            printedSchedule,
            "From 12:00 to 16:00 UTC",
            "2 Participants: Aaron Armstrong, Bella Bennet"
        );

        assertContainsLinesInOrder(
            printedSchedule,
            "2 Unplaced: Ethan Evans, Felicity Fisher"
        );
    }

    @Test
    void orderSessionsChronologicallyByStartTime() {
        var schedule = new Schedule(
            List.of(
                participant("Aaron Armstrong").scheduleFor(halfDaySessionAt("11:00")),
                participant("Bella Bennet").scheduleFor(halfDaySessionAt("09:00")),
                participant("Calvin Cooper").scheduleFor(halfDaySessionAt("12:00")),
                participant("Daphne Davis").scheduleFor(halfDaySessionAt("10:00"))
            ),
            emptyList()
        );

        var printedSchedule = printer.print(schedule);

        assertContainsLinesInOrder(
            printedSchedule,
            "From 09:00 to 13:00 UTC",
            "1 Participants: Bella Bennet",
            "From 10:00 to 14:00 UTC",
            "1 Participants: Daphne Davis",
            "From 11:00 to 15:00 UTC",
            "1 Participants: Aaron Armstrong",
            "From 12:00 to 16:00 UTC",
            "1 Participants: Calvin Cooper"
        );
    }

    @Test
    void printPlacedParticipantsDetails() {
        var schedule = new Schedule(
            List.of(
                engineerInNewYork("Aaron Armstrong").scheduleFor(halfDaySessionAt("13:00")),
                designerInUk("Bella Bennet").scheduleFor(halfDaySessionAt("13:00"))
            ),
            emptyList()
        );

        var printedSchedule = printer.print(schedule);

        assertContainsLinesInOrder(
            printedSchedule,
            "From 13:00 to 17:00 UTC",
            "2 Participants: Aaron Armstrong, Bella Bennet",
            "> Aaron Armstrong (Software Engineer in AMER, available from 09:00 to 17:00 UTC-04:00)",
            "> Bella Bennet (Designer in EMEA, available from 09:00 to 17:00 UTC+01:00)"
        );
    }

    @Test
    void printUnplacedParticipantsDetails() {
        var schedule = new Schedule(
            List.of(
                designerInUk("Ethan Evans").unplaced(),
                engineerInNewYork("Felicity Fisher").unplaced()
            ),
            emptyList()
        );

        var printedSchedule = printer.print(schedule);

        assertContainsLinesInOrder(
            printedSchedule,
            "2 Unplaced: Ethan Evans, Felicity Fisher",
            "> Ethan Evans (Designer in EMEA, available from 09:00 to 17:00 UTC+01:00)",
            "> Felicity Fisher (Software Engineer in AMER, available from 09:00 to 17:00 UTC-04:00)"
        );
    }

    private void assertContainsLinesInOrder(String printedSchedule, String ...expectedLines) {
        var actualLines = splitLinesAndDiscardLineFeeds(printedSchedule);
        var lastFoundAtPosition = NotFound;
        for (var expectedLine : expectedLines) {
            var foundAtPosition = actualLines.indexOf(expectedLine);

            assertThat(foundAtPosition)
                .withFailMessage("Expected line \"%s\" to be found in \n%s".formatted(expectedLine, printedSchedule))
                .isNotEqualTo(NotFound);

            if (lastFoundAtPosition != NotFound) { // just to avoid out of bounds issues on the error message
                assertThat(foundAtPosition)
                    .withFailMessage("Expected line \"%s\" to be found after \"%s\" in \n%s".formatted(
                        expectedLine,
                        actualLines.get(lastFoundAtPosition),
                        printedSchedule
                    ))
                    .isGreaterThan(lastFoundAtPosition);
            }

            lastFoundAtPosition = foundAtPosition;
        }
    }

    private List<String> splitLinesAndDiscardLineFeeds(String printedSchedule) {
        return Arrays.stream(printedSchedule.split("\n")).filter(it -> !it.isEmpty()).toList();
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

    private Participant engineerInNewYork(String fullName) {
        return new Participant(
            fullName,
            "Software Engineer",
            "AMER",
            ZoneOffset.ofHours(-4),
            WorkingHours.NoHoursShift
        );
    }

    private Participant designerInUk(String fullName) {
        return new Participant(
            fullName,
            "Designer",
            "EMEA",
            ZoneOffset.ofHours(+1),
            WorkingHours.NoHoursShift
        );
    }
}

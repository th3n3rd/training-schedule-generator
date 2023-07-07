package org.example.training;

import static java.util.Comparator.comparing;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class SchedulePrinter {

    String print(Schedule schedule) {
        var content = new StringBuilder();
        appendIntro(content);
        appendTrainingSessionsInfo(content, schedule.sessions());
        appendUnplacedParticipantsInfo(content, schedule.unplacedParticipants());
        return content.toString();
    }

    private static void appendIntro(StringBuilder content) {
        content.append("\nSolved training schedule:\n\n");
    }

    private static void appendUnplacedParticipantsInfo(
        StringBuilder content,
        List<Participant> unplacedParticipants
    ) {
        if (!unplacedParticipants.isEmpty()) {
            content
                .append(unplacedParticipants.size())
                .append(" Unplaced: ")
                .append(unplacedParticipants
                    .stream()
                    .map(Participant::fullName)
                    .collect(Collectors.joining(", "))
                )
                .append("\n")
                .append(participantsDetails(unplacedParticipants))
                .append("\n\n");
        }
    }

    private static void appendTrainingSessionsInfo(
        StringBuilder content,
        List<Session> trainingSessions
    ) {
        trainingSessions
            .stream()
            .sorted(comparing(Session::startTime))
            .forEach(session -> {
                var slot = session.slot();
                var participants = session.participants();
                content
                    .append("From ")
                    .append(slot.startTime().toLocalTime())
                    .append(" to ")
                    .append(slot.endTime().toLocalTime())
                    .append(" UTC\n")
                    .append(participants.size())
                    .append(" Participants: ")
                    .append(participants
                        .stream()
                        .map(Participant::fullName)
                        .collect(Collectors.joining(", "))
                    )
                    .append("\n")
                    .append(participantsDetails(participants))
                    .append("\n\n");
            });
    }

    private static String participantsDetails(List<Participant> participants) {
        return participants.stream().map(participant -> "> %s (%s in %s, available from %s to %s UTC%s)".formatted(
            participant.fullName(),
            participant.role(),
            participant.region(),
            participant.earliestStartTime().toLocalTime(),
            participant.latestEndTime().toLocalTime(),
            participant.timezoneOffset()
        )).collect(Collectors.joining("\n"));
    }

}

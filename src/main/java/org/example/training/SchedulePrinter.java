package org.example.training;

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
                .append("\n\n");
        }
    }

    private static void appendTrainingSessionsInfo(
        StringBuilder content,
        List<Session> trainingSessions
    ) {
        trainingSessions.forEach(session -> {
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
                .append("\n\n");
        });
    }

}

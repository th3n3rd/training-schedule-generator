package org.example.training;

import java.util.stream.Collectors;

class SchedulePrinter {

    String print(Schedule schedule) {
        var content = new StringBuilder();
        content.append("\nSolved training schedule:\n\n");
        schedule.sessions().forEach(session -> {
            var participants = session.participants();
            content
                .append(participants.size())
                .append(" Participants: ")
                .append(participants
                    .stream()
                    .map(Participant::fullName)
                    .collect(Collectors.joining(", "))
                )
                .append("\n\n");
        });

        var unplacedParticipants = schedule.unplacedParticipants();
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

        return content.toString();
    }

}

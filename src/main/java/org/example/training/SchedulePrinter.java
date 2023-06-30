package org.example.training;

import java.util.stream.Collectors;

class SchedulePrinter {

    String print(Schedule schedule) {
        var content = new StringBuilder();
        content.append("\nSolved training schedule:\n\n");
        schedule.getSessions().forEach(session -> {
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

        return content.toString();
    }

}

package org.example.training;

import java.util.List;

record Session(TimeSlot slot, List<Participant> participants) {
    public boolean suitsParticipantsSchedule() {
        return participants
            .stream()
            .allMatch(participant -> participant.isAvailableFor(slot));
    }
}

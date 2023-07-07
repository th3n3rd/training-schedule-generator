package org.example.training;

import java.time.OffsetDateTime;
import java.util.List;

record Session(TimeSlot slot, List<Participant> participants) {
    public OffsetDateTime startTime() {
        return slot.startTime();
    }
}

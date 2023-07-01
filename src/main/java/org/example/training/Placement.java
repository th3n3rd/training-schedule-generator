package org.example.training;

import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@NoArgsConstructor
@PlanningEntity
class Placement {

    private Participant participant;

    @PlanningVariable(nullable = true)
    private TimeSlot slot;

    public Placement(Participant participant) {
        this(participant, null);
    }

    public Placement(Participant participant, TimeSlot slot) {
        this.participant = participant;
        this.slot = slot;
    }

    public Participant participant() {
        return participant;
    }

    public TimeSlot slot() {
        return slot;
    }
}

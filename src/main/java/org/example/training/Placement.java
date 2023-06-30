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
        this.participant = participant;
    }

    public Participant participant() {
        return participant;
    }

    public TimeSlot slot() {
        return slot;
    }
}

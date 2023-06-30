package org.example.training;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@NoArgsConstructor
@PlanningEntity
class Placement {
    @Getter
    private Participant participant;

    @Getter
    @PlanningVariable
    private TimeSlot slot;

    public Placement(Participant participant) {
        this.participant = participant;
    }
}

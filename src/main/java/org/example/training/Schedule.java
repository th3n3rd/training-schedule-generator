package org.example.training;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;

@NoArgsConstructor
@PlanningSolution
class Schedule {

    @PlanningEntityCollectionProperty
    private List<Placement> placements;

    @ValueRangeProvider
    private List<TimeSlot> availableSlots;

    @PlanningScore
    private HardMediumSoftScore score;

    public Schedule(List<Placement> placements, List<TimeSlot> availableSlots) {
        this.placements = placements;
        this.availableSlots = availableSlots;
    }

    public List<Session> getSessions() {
        var sessions = new ArrayList<Session>();
        placements
            .stream()
            .collect(Collectors.groupingBy(Placement::getSlot))
            .forEach((slot, placements) -> sessions.add(
                new Session(
                    slot,
                    placements
                        .stream()
                        .map(Placement::getParticipant)
                        .toList()
                )
            ));
        return sessions;
    }
}

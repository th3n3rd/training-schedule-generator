package org.example.training;

import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.core.api.score.calculator.EasyScoreCalculator;

public class ScoreCalculator implements EasyScoreCalculator<Schedule, HardMediumSoftScore> {

    public HardMediumSoftScore calculateScore(Schedule schedule) {
        var hardScore = 0;
        for (var placement : schedule.placements()) {
            if (!placement.suitsParticipantSchedule()) {
                hardScore--;
            }
        }
        return HardMediumSoftScore.of(hardScore, 0, 0);
    }
}

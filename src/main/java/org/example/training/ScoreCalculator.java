package org.example.training;

import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.core.api.score.calculator.EasyScoreCalculator;

public class ScoreCalculator implements EasyScoreCalculator<Schedule, HardMediumSoftScore> {

    public HardMediumSoftScore calculateScore(Schedule schedule) {
        return HardMediumSoftScore.of(0, 0, 0);
    }
}

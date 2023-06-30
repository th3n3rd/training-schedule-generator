package org.example.training;

import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.core.api.score.calculator.EasyScoreCalculator;

public class ScoreCalculator implements EasyScoreCalculator<Schedule, HardMediumSoftScore> {

    public HardMediumSoftScore calculateScore(Schedule schedule) {
        var hardScore = 0;
        var mediumScore = 0;
        var softScore = 0;

        // encourage to place every participant
        mediumScore -= schedule.unplacedParticipants().size();

        for (var session : schedule.sessions()) {
            if (session.participants().size() < 3) {
                // encourage to have bigger sessions
                mediumScore--;
            }
            for (var participant : session.participants()) {
                if (!participant.isAvailableFor(session.slot())) {
                    // ensure the schedule is suitable for the participants
                    hardScore--;
                }
            }
        }

        return HardMediumSoftScore.of(hardScore, mediumScore, softScore);
    }
}

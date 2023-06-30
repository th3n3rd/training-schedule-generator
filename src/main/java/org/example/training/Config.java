package org.example.training;

import static org.optaplanner.core.config.constructionheuristic.ConstructionHeuristicType.FIRST_FIT;
import static org.optaplanner.core.config.localsearch.LocalSearchType.TABU_SEARCH;

import java.time.Duration;
import java.time.ZoneOffset;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.constructionheuristic.ConstructionHeuristicPhaseConfig;
import org.optaplanner.core.config.localsearch.LocalSearchPhaseConfig;
import org.optaplanner.core.config.solver.SolverConfig;

@RequiredArgsConstructor
class Config {

    ParticipantsRepository participantsRepository() {
        return new InMemoryParticipantsRepository(
            List.of(
                new Participant(
                    "Andy Anderson",
                    "Software Engineer",
                    "EMEA",
                    ZoneOffset.UTC,
                    WorkingHours.NoHoursShift
                ),
                new Participant(
                    "Bella Brown",
                    "Product Manager",
                    "EMEA",
                    ZoneOffset.UTC,
                    WorkingHours.NoHoursShift
                ),
                new Participant(
                    "Charlie Clark",
                    "Designer",
                    "EMEA",
                    ZoneOffset.UTC,
                    WorkingHours.NoHoursShift
                )
            )
        );
    }

    TimeSlotsRepository timeSlotsRepository() {
        return new TimeSlotsRepository();
    }


    Solver<Schedule> scheduleSolver() {
        var solverConfig = new SolverConfig()
            .withSolutionClass(Schedule.class)
            .withEntityClasses(Placement.class)
            .withEasyScoreCalculatorClass(ScoreCalculator.class)
            .withTerminationSpentLimit(Duration.ofSeconds(5))
            .withPhases(
                new ConstructionHeuristicPhaseConfig().withConstructionHeuristicType(FIRST_FIT),
                new LocalSearchPhaseConfig().withLocalSearchType(TABU_SEARCH)
            );
        SolverFactory<Schedule> solverFactory = SolverFactory.create(solverConfig);
        return solverFactory.buildSolver();
    }

    public SchedulePrinter schedulerPrinter() {
        return new SchedulePrinter();
    }
}

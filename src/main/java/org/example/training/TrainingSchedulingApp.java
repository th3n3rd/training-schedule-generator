package org.example.training;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TrainingSchedulingApp implements Runnable {

    private final PrintWriter output;
    private final Config config;

    public TrainingSchedulingApp(Writer output, Config config) {
        this.output = new PrintWriter(output, true);
        this.config = config;
    }

    @Override
    public void run() {
        var initialPlacements = config.participantsRepository().findAll()
            .stream()
            .map(Placement::new)
            .toList();
        var availableSlots = config.timeSlotsRepository().findAll();
        var unsolvedSchedule = new Schedule(initialPlacements, availableSlots);
        var solvedSchedule = config.scheduleSolver().solve(unsolvedSchedule);
        var printedSchedule = config.schedulerPrinter().print(solvedSchedule);
        output.println(printedSchedule);
    }

    public static void main(String[] args) {
        new TrainingSchedulingApp(
            new OutputStreamWriter(System.out),
            new Config()
        ).run();
    }
}

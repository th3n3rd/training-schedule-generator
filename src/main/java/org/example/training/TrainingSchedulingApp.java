package org.example.training;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TrainingSchedulingApp implements Runnable {

    private final PrintWriter output;
    private final PrintWriter error;
    private final Config config;

    public TrainingSchedulingApp(Writer output, Writer error, Config config) {
        this.output = new PrintWriter(output, true);
        this.error = new PrintWriter(error, true);
        this.config = config;
    }

    @Override
    public void run() {
        try {
            var initialPlacements = config.participantsRepository().findAll()
                .stream()
                .map(Placement::new)
                .toList();
            var availableSlots = config.timeSlotsRepository().findAll();
            var unsolvedSchedule = new Schedule(initialPlacements, availableSlots);
            var solvedSchedule = config.scheduleSolver().solve(unsolvedSchedule);
            var printedSchedule = config.schedulerPrinter().print(solvedSchedule);
            output.println(printedSchedule);
        } catch (Exception e) {
            error.println("Error: %s".formatted(e.getMessage()));
            error.println("Usage: java -jar training-schedule-generator.jar /path/to/your/participants/file.csv");
        }
    }

    public static void main(String[] args) {
        new TrainingSchedulingApp(
            new OutputStreamWriter(System.out),
            new OutputStreamWriter(System.err),
            new Config(args[0])
        ).run();
    }
}

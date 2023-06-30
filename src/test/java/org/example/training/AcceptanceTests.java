package org.example.training;

import static java.util.concurrent.CompletableFuture.runAsync;
import static org.assertj.core.api.Assertions.assertThat;
import static org.example.training.CapturingWriter.capturingStandardOut;

import org.junit.jupiter.api.Test;

public class AcceptanceTests {

    private final CapturingWriter output = capturingStandardOut();

    @Test
    void basicTrainingSchedule() {
        runAppAndWait();
        assertOutputContainsLine("3 Participants: Andy Anderson, Bella Brown, Charlie Clark");
    }

    private void assertOutputContainsLine(String expected) {
        assertThat(output.captured()).contains("%s\n".formatted(expected));
    }

    private void runAppAndWait() {
        var app = runAsync(new TrainingSchedulingApp(
            output,
            new Config()
        ));
        app.join();
    }
}

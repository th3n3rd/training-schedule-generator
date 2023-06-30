package org.example.training;

import static java.util.concurrent.CompletableFuture.runAsync;
import static org.assertj.core.api.Assertions.assertThat;
import static org.example.training.CapturingWriter.capturingStandardErr;
import static org.example.training.CapturingWriter.capturingStandardOut;

import org.junit.jupiter.api.Test;

public class AcceptanceTests {

    private final CapturingWriter output = capturingStandardOut();
    private final CapturingWriter error = capturingStandardErr();

    @Test
    void basicTrainingSchedule() {
        runAppAndWait("./src/test/resources/participants-basic.csv");
        assertOutputContainsLine("3 Participants: Andy Anderson, Bella Brown, Charlie Clark");
        assertNoErrors();
    }

    @Test
    void showUsageWhenErrorsOccur() {
        runAppAndWait("invalid-file-path");
        assertErrorContainsLine("Error: There was a problem loading the participants");
        assertErrorContainsLine("Usage: java -jar training-schedule-generator.jar /path/to/your/participants/file.csv");
    }

    private void assertErrorContainsLine(String expected) {
        assertThat(error.captured()).contains("%s\n".formatted(expected));
    }

    private void assertOutputContainsLine(String expected) {
        assertThat(output.captured()).contains("%s\n".formatted(expected));
    }

    private void assertNoErrors() {
        assertThat(error.captured()).isEmpty();
    }

    private void runAppAndWait(String participantsFilePath) {
        var app = runAsync(new TrainingSchedulingApp(
            output,
            error,
            new Config(participantsFilePath)
        ));
        app.join();
    }
}

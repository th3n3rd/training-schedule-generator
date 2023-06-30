package org.example.training;

import static java.util.concurrent.CompletableFuture.runAsync;
import static org.assertj.core.api.Assertions.assertThat;
import static org.example.training.CapturingWriter.capturingStandardOut;

import org.junit.jupiter.api.Test;

public class AcceptanceTests {

    private final CapturingWriter output = capturingStandardOut();

    @Test
    public void greetingTheWorld() {
        runAppAndWait();
        assertOutputContainsLine("Hello World!");
    }

    private void assertOutputContainsLine(String expected) {
        assertThat(output.captured()).contains("%s\n".formatted(expected));
    }

    private void runAppAndWait() {
        var app = runAsync(new TrainingSchedulingApp(output));
        app.join();
    }
}

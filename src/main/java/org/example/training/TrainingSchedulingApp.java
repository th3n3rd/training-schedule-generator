package org.example.training;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TrainingSchedulingApp implements Runnable {

    private final PrintWriter output;

    public TrainingSchedulingApp(Writer output) {
        this.output = new PrintWriter(output, true);
    }

    @Override
    public void run() {
        output.println("Hello World!");
    }

    public static void main(String[] args) {
        new TrainingSchedulingApp(
            new OutputStreamWriter(System.out)
        ).run();
    }
}

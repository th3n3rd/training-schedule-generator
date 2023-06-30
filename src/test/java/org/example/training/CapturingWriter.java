package org.example.training;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

class CapturingWriter extends Writer {

    private final Writer source;
    private final Writer sink;
    private final StringBuilder sinkBuffer;

    private CapturingWriter(Writer source) {
        this.source = source;
        this.sink = new StringWriter();
        this.sinkBuffer = new StringBuilder();
    }

    @Override
    public void write(char[] buffer, int offset, int length) throws IOException {
        source.write(buffer, offset, length);
        sinkBuffer.append(buffer, offset, length);
    }

    @Override
    public void flush() throws IOException {
        source.flush();
        sink.write(sinkBuffer.toString());
        sink.flush();
        sinkBuffer.setLength(0);
    }

    @Override
    public void close() throws IOException {
        source.close();
        sink.close();
    }

    public String captured() {
        return sink.toString();
    }

    public static CapturingWriter capturingStandardOut() {
        return new CapturingWriter(
            new PrintWriter(new PrintStream(System.out) {
                @Override
                public void close() {}
            })
        );
    }

    public static CapturingWriter capturingStandardErr() {
        return new CapturingWriter(
            new PrintWriter(new PrintStream(System.err) {
                @Override
                public void close() {}
            })
        );
    }
}

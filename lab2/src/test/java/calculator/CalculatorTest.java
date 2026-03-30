package calculator;

import baseTest.BaseTest;
import factory.CommandFactory;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTest extends BaseTest {

    @Test
    void shouldExecuteCommands() throws Exception {
        Calculator calculator = new Calculator(new CommandFactory("testFactory.properties"));
        Scanner scanner = new Scanner(new StringReader("""
                PUSH 4
                PUSH 5
                +
                PRINT
                """));

        CapturedOutput output = captureOutput(() -> calculator.run(scanner));

        assertEquals("9.0" + System.lineSeparator(), output.out());
        assertEquals("", output.err());
    }

    @Test
    void shouldSkipEmptyLinesAndComments() throws Exception {
        Calculator calculator = new Calculator(new CommandFactory("testFactory.properties"));
        Scanner scanner = new Scanner(new StringReader("""
                
                # comment
                PUSH 16
                
                SQRT
                PRINT
                """));

        CapturedOutput output = captureOutput(() -> calculator.run(scanner));

        assertEquals("4.0" + System.lineSeparator(), output.out());
        assertEquals("", output.err());
    }

    @Test
    void shouldPrintErrorForUnknownCommand() throws Exception {
        Calculator calculator = new Calculator(new CommandFactory("testFactory.properties"));
        Scanner scanner = new Scanner(new StringReader("""
                UNKNOWN
                """));

        CapturedOutput output = captureOutput(() -> calculator.run(scanner));

        assertEquals("", output.out());
        assertEquals("Error: Unknown command: UNKNOWN" + System.lineSeparator(), output.err());
    }

    @Test
    void shouldPrintErrorForInvalidCommandArguments() throws Exception {
        Calculator calculator = new Calculator(new CommandFactory("testFactory.properties"));
        Scanner scanner = new Scanner(new StringReader("""
                PUSH 1 2
                """));

        CapturedOutput output = captureOutput(() -> calculator.run(scanner));

        assertEquals("", output.out());
        assertEquals("Error: PUSH expected 1 args, got 2" + System.lineSeparator(), output.err());
    }

    @Test
    void shouldContinueAfterCommandError() throws Exception {
        Calculator calculator = new Calculator(new CommandFactory("testFactory.properties"));
        Scanner scanner = new Scanner(new StringReader("""
                PUSH 4
                PUSH 0
                /
                PUSH 9
                PRINT
                """));

        CapturedOutput output = captureOutput(() -> calculator.run(scanner));

        assertEquals("9.0" + System.lineSeparator(), output.out());
        assertEquals("Error: / division by zero" + System.lineSeparator(), output.err());
    }

    @Test
    void shouldWorkWithDefineAndPushVariable() throws Exception {
        Calculator calculator = new Calculator(new CommandFactory("testFactory.properties"));
        Scanner scanner = new Scanner(new StringReader("""
                DEFINE x 25
                PUSH x
                SQRT
                PRINT
                """));

        CapturedOutput output = captureOutput(() -> calculator.run(scanner));

        assertEquals("5.0" + System.lineSeparator(), output.out());
        assertEquals("", output.err());
    }

    @Test
    void shouldPrintSeveralResults() throws Exception {
        Calculator calculator = new Calculator(new CommandFactory("testFactory.properties"));
        Scanner scanner = new Scanner(new StringReader("""
                PUSH 2
                PRINT
                PUSH 3
                PRINT
                """));

        CapturedOutput output = captureOutput(() -> calculator.run(scanner));

        assertEquals(
                "2.0" + System.lineSeparator() +
                        "3.0" + System.lineSeparator(),
                output.out()
        );
        assertEquals("", output.err());
    }

    @FunctionalInterface
    private interface ThrowingRunnable {
        void run() throws Exception;
    }

    private record CapturedOutput(String out, String err) {
    }

    private CapturedOutput captureOutput(ThrowingRunnable action) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayOutputStream err = new ByteArrayOutputStream();

        PrintStream originalOut = System.out;
        PrintStream originalErr = System.err;

        try {
            System.setOut(new PrintStream(out));
            System.setErr(new PrintStream(err));

            action.run();

            return new CapturedOutput(out.toString(), err.toString());
        } finally {
            System.setOut(originalOut);
            System.setErr(originalErr);
        }
    }

}
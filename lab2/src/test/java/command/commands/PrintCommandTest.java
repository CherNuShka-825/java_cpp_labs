package command.commands;

import baseTest.BaseTest;
import command.Context;
import command.commands.helpers.CommandAssertions;
import command.commands.helpers.CommandTestUtils;
import exceptions.CommandException;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrintCommandTest extends BaseTest {

    @Test
    void shouldPrintTopElement() throws CommandException {
        Context context = CommandTestUtils.contextWithStack(5.0);
        PrintCommand command = new PrintCommand();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(out));
            command.execute(context, CommandTestUtils.noArgs());
            assertEquals("5.0" + System.lineSeparator(), out.toString());
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void shouldNotRemoveElementFromStack() throws CommandException {
        Context context = CommandTestUtils.contextWithStack(7.0);
        PrintCommand command = new PrintCommand();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(out));
            command.execute(context, CommandTestUtils.noArgs());
            assertEquals(7.0, context.peekStack());
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void shouldThrowIfStackIsEmpty() {
        PrintCommand command = new PrintCommand();

        CommandAssertions.assertFailsOnEmptyStack(command);
    }

    @Test
    void shouldThrowIfUnexpectedArgsPassed() {
        Context context = CommandTestUtils.contextWithStack(1.0);
        PrintCommand command = new PrintCommand();

        CommandAssertions.assertFailsOnUnexpectedArgs(command, context);
    }
}
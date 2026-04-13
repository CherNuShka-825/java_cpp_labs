package command.commands;

import baseTest.BaseTest;
import command.Context;
import command.commands.helpers.CommandAssertions;
import exceptions.CommandException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PushCommandTest extends BaseTest {

    @Test
    void shouldPushNumber() throws CommandException {
        Context context = new Context();
        PushCommand command = new PushCommand();

        command.execute(context, new String[]{"5"});

        assertEquals(5.0, context.peekStack());
    }

    @Test
    void shouldPushNegativeAndDoubleNumber() throws CommandException {
        Context context = new Context();
        PushCommand command = new PushCommand();

        command.execute(context, new String[]{"-3.14"});

        assertEquals(-3.14, context.peekStack());
    }

    @Test
    void shouldPushVariableValue() throws CommandException {
        Context context = new Context();
        context.setVariables("x", 10.0);

        PushCommand command = new PushCommand();

        command.execute(context, new String[]{"x"});

        assertEquals(10.0, context.peekStack());
    }

    @Test
    void shouldThrowIfVariableNotDefined() {
        Context context = new Context();
        PushCommand command = new PushCommand();

        CommandAssertions.assertCommandException(command, context, new String[]{"x"});
    }

    @Test
    void shouldThrowIfNoArgs() {
        Context context = new Context();
        PushCommand command = new PushCommand();

        CommandAssertions.assertCommandException(command, context, new String[]{});
    }

    @Test
    void shouldThrowIfMoreThanOneArg() {
        Context context = new Context();
        PushCommand command = new PushCommand();

        CommandAssertions.assertCommandException(command, context, new String[]{"1", "2"});
    }

    @Test
    void shouldPushMultipleValuesInOrder() throws CommandException {
        Context context = new Context();
        PushCommand command = new PushCommand();

        command.execute(context, new String[]{"1"});
        command.execute(context, new String[]{"2"});

        assertEquals(2.0, context.popStack());
        assertEquals(1.0, context.popStack());
    }
}
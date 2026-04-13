package command.commands;

import baseTest.BaseTest;
import command.Context;
import command.commands.helpers.CommandAssertions;
import command.commands.helpers.CommandTestUtils;
import exceptions.CommandException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PopCommandTest extends BaseTest {

    @Test
    void shouldRemoveTopElement() throws CommandException {
        Context context = CommandTestUtils.contextWithStack(1.0, 2.0);
        PopCommand command = new PopCommand();

        command.execute(context, CommandTestUtils.noArgs());

        assertEquals(1.0, context.peekStack());
    }

    @Test
    void shouldRemoveOnlyTopElement() throws CommandException {
        Context context = CommandTestUtils.contextWithStack(5.0, 10.0, 20.0);
        PopCommand command = new PopCommand();

        command.execute(context, CommandTestUtils.noArgs());

        assertEquals(10.0, context.popStack());
        assertEquals(5.0, context.popStack());
    }

    @Test
    void shouldThrowIfStackIsEmpty() {
        PopCommand command = new PopCommand();

        CommandAssertions.assertFailsOnEmptyStack(command);
    }

    @Test
    void shouldThrowIfUnexpectedArgsPassed() {
        Context context = CommandTestUtils.contextWithStack(1.0);
        PopCommand command = new PopCommand();

        CommandAssertions.assertFailsOnUnexpectedArgs(command, context);
    }
}
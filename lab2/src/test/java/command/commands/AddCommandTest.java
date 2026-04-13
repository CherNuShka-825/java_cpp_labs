package command.commands;

import baseTest.BaseTest;
import command.Context;
import command.commands.helpers.CommandAssertions;
import command.commands.helpers.CommandTestUtils;
import exceptions.CommandException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddCommandTest extends BaseTest {

    @Test
    void shouldAddTwoTopElements() throws CommandException {
        Context context = CommandTestUtils.contextWithStack(2.0, 3.0);
        AddCommand command = new AddCommand();

        command.execute(context, CommandTestUtils.noArgs());

        assertEquals(5.0, context.peekStack());
    }

    @Test
    void shouldUseOnlyTwoTopElements() throws CommandException {
        Context context = CommandTestUtils.contextWithStack(1.0, 2.0, 3.0);
        AddCommand command = new AddCommand();

        command.execute(context, CommandTestUtils.noArgs());

        assertEquals(5.0, context.popStack());
        assertEquals(1.0, context.popStack());
    }

    @Test
    void shouldThrowIfStackIsEmpty() {
        AddCommand command = new AddCommand();

        CommandAssertions.assertFailsOnEmptyStack(command);
    }

    @Test
    void shouldThrowIfStackHasOnlyOneElement() {
        Context context = CommandTestUtils.contextWithStack(7.0);
        AddCommand command = new AddCommand();

        CommandAssertions.assertCommandException(command, context, CommandTestUtils.noArgs());
    }

    @Test
    void shouldThrowIfUnexpectedArgsPassed() {
        Context context = CommandTestUtils.contextWithStack(2.0, 3.0);
        AddCommand command = new AddCommand();

        CommandAssertions.assertFailsOnUnexpectedArgs(command, context);
    }

}
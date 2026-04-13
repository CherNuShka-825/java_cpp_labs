package command.commands;

import baseTest.BaseTest;
import command.Context;
import command.commands.helpers.CommandAssertions;
import command.commands.helpers.CommandTestUtils;
import exceptions.CommandException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SqrtCommandTest extends BaseTest {

    @Test
    void shouldCalculateSquareRoot() throws CommandException {
        Context context = CommandTestUtils.contextWithStack(16.0);
        SqrtCommand command = new SqrtCommand();

        command.execute(context, CommandTestUtils.noArgs());

        assertEquals(4.0, context.peekStack());
    }

    @Test
    void shouldUseOnlyTopElement() throws CommandException {
        Context context = CommandTestUtils.contextWithStack(2.0, 9.0);
        SqrtCommand command = new SqrtCommand();

        command.execute(context, CommandTestUtils.noArgs());

        assertEquals(3.0, context.popStack());
        assertEquals(2.0, context.popStack());
    }

    @Test
    void shouldThrowIfNumberIsNegative() {
        Context context = CommandTestUtils.contextWithStack(-4.0);
        SqrtCommand command = new SqrtCommand();

        CommandAssertions.assertCommandException(command, context, CommandTestUtils.noArgs());
    }

    @Test
    void shouldThrowIfStackIsEmpty() {
        SqrtCommand command = new SqrtCommand();

        CommandAssertions.assertFailsOnEmptyStack(command);
    }

    @Test
    void shouldThrowIfUnexpectedArgsPassed() {
        Context context = CommandTestUtils.contextWithStack(16.0);
        SqrtCommand command = new SqrtCommand();

        CommandAssertions.assertFailsOnUnexpectedArgs(command, context);
    }
}
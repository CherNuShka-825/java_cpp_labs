package command.commands;

import baseTest.BaseTest;
import command.Context;
import command.commands.helpers.CommandAssertions;
import command.commands.helpers.CommandTestUtils;
import exceptions.CommandException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MulCommandTest extends BaseTest {

    @Test
    void shouldMultiplyTwoTopElements() throws CommandException {
        Context context = CommandTestUtils.contextWithStack(4.0, 5.0);
        MulCommand command = new MulCommand();

        command.execute(context, CommandTestUtils.noArgs());

        assertEquals(20.0, context.peekStack());
    }

    @Test
    void shouldUseOnlyTwoTopElements() throws CommandException {
        Context context = CommandTestUtils.contextWithStack(2.0, 4.0, 5.0);
        MulCommand command = new MulCommand();

        command.execute(context, CommandTestUtils.noArgs());

        assertEquals(20.0, context.popStack());
        assertEquals(2.0, context.popStack());
    }

    @Test
    void shouldThrowIfStackIsEmpty() {
        MulCommand command = new MulCommand();

        CommandAssertions.assertFailsOnEmptyStack(command);
    }

    @Test
    void shouldThrowIfStackHasOnlyOneElement() {
        Context context = CommandTestUtils.contextWithStack(7.0);
        MulCommand command = new MulCommand();

        CommandAssertions.assertCommandException(command, context, CommandTestUtils.noArgs());
    }

    @Test
    void shouldThrowIfUnexpectedArgsPassed() {
        Context context = CommandTestUtils.contextWithStack(4.0, 5.0);
        MulCommand command = new MulCommand();

        CommandAssertions.assertFailsOnUnexpectedArgs(command, context);
    }

}
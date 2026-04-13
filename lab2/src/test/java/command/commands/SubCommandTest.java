package command.commands;

import baseTest.BaseTest;
import command.Context;
import command.commands.helpers.CommandAssertions;
import command.commands.helpers.CommandTestUtils;
import exceptions.CommandException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubCommandTest extends BaseTest {

    @Test
    void shouldSubtractTwoTopElements() throws CommandException {
        Context context = CommandTestUtils.contextWithStack(10.0, 3.0);
        SubCommand command = new SubCommand();

        command.execute(context, CommandTestUtils.noArgs());

        assertEquals(7.0, context.peekStack());
    }

    @Test
    void shouldUseOnlyTwoTopElements() throws CommandException {
        Context context = CommandTestUtils.contextWithStack(1.0, 10.0, 3.0);
        SubCommand command = new SubCommand();

        command.execute(context, CommandTestUtils.noArgs());

        assertEquals(7.0, context.popStack());
        assertEquals(1.0, context.popStack());
    }

    @Test
    void shouldRespectStackOrder() throws CommandException {
        Context context = CommandTestUtils.contextWithStack(5.0, 2.0);
        SubCommand command = new SubCommand();

        command.execute(context, CommandTestUtils.noArgs());

        assertEquals(3.0, context.peekStack());
    }

    @Test
    void shouldThrowIfStackIsEmpty() {
        SubCommand command = new SubCommand();

        CommandAssertions.assertFailsOnEmptyStack(command);
    }

    @Test
    void shouldThrowIfStackHasOnlyOneElement() {
        Context context = CommandTestUtils.contextWithStack(5.0);
        SubCommand command = new SubCommand();

        CommandAssertions.assertCommandException(command, context, CommandTestUtils.noArgs());
    }

    @Test
    void shouldThrowIfUnexpectedArgsPassed() {
        Context context = CommandTestUtils.contextWithStack(10.0, 3.0);
        SubCommand command = new SubCommand();

        CommandAssertions.assertFailsOnUnexpectedArgs(command, context);
    }

}
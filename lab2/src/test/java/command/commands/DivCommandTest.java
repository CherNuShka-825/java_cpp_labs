package command.commands;

import baseTest.BaseTest;
import command.Context;
import command.commands.helpers.CommandAssertions;
import command.commands.helpers.CommandTestUtils;
import exceptions.CommandException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DivCommandTest extends BaseTest {

    @Test
    void shouldDivideTwoTopElements() throws CommandException {
        Context context = CommandTestUtils.contextWithStack(10.0, 2.0);
        DivCommand command = new DivCommand();

        command.execute(context, CommandTestUtils.noArgs());

        assertEquals(5.0, context.peekStack());
    }

    @Test
    void shouldUseOnlyTwoTopElements() throws CommandException {
        Context context = CommandTestUtils.contextWithStack(1.0, 10.0, 2.0);
        DivCommand command = new DivCommand();

        command.execute(context, CommandTestUtils.noArgs());

        assertEquals(5.0, context.popStack());
        assertEquals(1.0, context.popStack());
    }

    @Test
    void shouldRespectStackOrder() throws CommandException {
        Context context = CommandTestUtils.contextWithStack(9.0, 3.0);
        DivCommand command = new DivCommand();

        command.execute(context, CommandTestUtils.noArgs());

        assertEquals(3.0, context.peekStack());
    }

    @Test
    void shouldThrowIfDivisionByZero() {
        Context context = CommandTestUtils.contextWithStack(10.0, 0.0);
        DivCommand command = new DivCommand();

        CommandAssertions.assertCommandException(command, context, CommandTestUtils.noArgs());
    }

    @Test
    void shouldThrowIfStackIsEmpty() {
        DivCommand command = new DivCommand();

        CommandAssertions.assertFailsOnEmptyStack(command);
    }

    @Test
    void shouldThrowIfStackHasOnlyOneElement() {
        Context context = CommandTestUtils.contextWithStack(5.0);
        DivCommand command = new DivCommand();

        CommandAssertions.assertCommandException(command, context, CommandTestUtils.noArgs());
    }

    @Test
    void shouldThrowIfUnexpectedArgsPassed() {
        Context context = CommandTestUtils.contextWithStack(10.0, 2.0);
        DivCommand command = new DivCommand();

        CommandAssertions.assertFailsOnUnexpectedArgs(command, context);
    }

}
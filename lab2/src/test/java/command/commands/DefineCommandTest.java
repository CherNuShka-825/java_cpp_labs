package command.commands;

import baseTest.BaseTest;
import command.Context;
import exceptions.CommandException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefineCommandTest extends BaseTest {

    @Test
    void shouldDefineVariable() throws CommandException {
        Context context = new Context();
        DefineCommand command = new DefineCommand();

        command.execute(context, new String[]{"x", "5"});

        assertEquals(5.0, context.getVariable("x"));
    }

    @Test
    void shouldOverwriteExistingVariable() throws CommandException {
        Context context = new Context();
        DefineCommand command = new DefineCommand();

        command.execute(context, new String[]{"x", "5"});
        command.execute(context, new String[]{"x", "10"});

        assertEquals(10.0, context.getVariable("x"));
    }

    @Test
    void shouldWorkWithNegativeAndDoubleValues() throws CommandException {
        Context context = new Context();
        DefineCommand command = new DefineCommand();

        command.execute(context, new String[]{"y", "-3.14"});

        assertEquals(-3.14, context.getVariable("y"));
    }

    @Test
    void shouldThrowIfArgsLessThanTwo() {
        Context context = new Context();
        DefineCommand command = new DefineCommand();

        assertThrows(CommandException.class,
                () -> command.execute(context, new String[]{"x"}));
    }

    @Test
    void shouldThrowIfArgsMoreThanTwo() {
        Context context = new Context();
        DefineCommand command = new DefineCommand();

        assertThrows(CommandException.class,
                () -> command.execute(context, new String[]{"x", "5", "extra"}));
    }

    @Test
    void shouldThrowIfValueIsNotNumber() {
        Context context = new Context();
        DefineCommand command = new DefineCommand();

        CommandException ex = assertThrows(CommandException.class,
                () -> command.execute(context, new String[]{"x", "abc"}));

        assertTrue(ex.getMessage().contains("value must be a number"));
    }

}
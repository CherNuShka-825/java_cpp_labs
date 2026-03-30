package command.commands.helpers;

import baseTest.BaseTest;
import command.Command;
import command.Context;
import exceptions.CommandException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class CommandAssertions extends BaseTest {

    private CommandAssertions() {
    }

    public static void assertCommandException(Command command, Context context, String[] args) {
        assertThrows(CommandException.class, () -> command.execute(context, args));
    }

    public static void assertFailsOnEmptyStack(Command command) {
        Context context = CommandTestUtils.emptyContext();
        assertThrows(CommandException.class, () -> command.execute(context, CommandTestUtils.noArgs()));
    }

    public static void assertFailsOnUnexpectedArgs(Command command, Context context) {
        assertThrows(CommandException.class, () -> command.execute(context, new String[]{"gugugaga"}));
    }

}
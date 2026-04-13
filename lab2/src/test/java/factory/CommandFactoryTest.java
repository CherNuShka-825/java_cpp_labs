package factory;

import baseTest.BaseTest;
import command.Command;
import command.commands.AddCommand;
import command.commands.PushCommand;
import exceptions.FactoryException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandFactoryTest extends BaseTest {

    @Test
    void shouldCreateAddCommand() throws FactoryException {
        CommandFactory factory = new CommandFactory("testFactory.properties");

        Command command = factory.getCommand("+");

        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    void shouldCreatePushCommand() throws FactoryException {
        CommandFactory factory = new CommandFactory("testFactory.properties");

        Command command = factory.getCommand("PUSH");

        assertInstanceOf(PushCommand.class, command);
    }

    @Test
    void shouldCreateNewInstanceEachTime() throws FactoryException {
        CommandFactory factory = new CommandFactory("testFactory.properties");

        Command first = factory.getCommand("+");
        Command second = factory.getCommand("+");

        assertNotSame(first, second);
    }

    @Test
    void shouldThrowIfConfigFileNotFound() {
        FactoryException ex = assertThrows(
                FactoryException.class,
                () -> new CommandFactory("noSuchFile.properties")
        );

        assertEquals("Config file not found: noSuchFile.properties", ex.getMessage());
    }

    @Test
    void shouldThrowIfCommandUnknown() throws FactoryException {
        CommandFactory factory = new CommandFactory("testFactory.properties");

        FactoryException ex = assertThrows(
                FactoryException.class,
                () -> factory.getCommand("UNKNOWN")
        );

        assertEquals("Unknown command: UNKNOWN", ex.getMessage());
    }

    @Test
    void shouldThrowIfCommandClassNotFound() throws FactoryException {
        CommandFactory factory = new CommandFactory("invalidClass.properties");

        FactoryException ex = assertThrows(
                FactoryException.class,
                () -> factory.getCommand("BAD")
        );

        assertEquals(
                "Class not found: no.such.ClassName",
                ex.getMessage()
        );
    }

    @Test
    void shouldThrowIfClassIsNotCommand() throws FactoryException {
        CommandFactory factory = new CommandFactory("notCommand.properties");

        FactoryException ex = assertThrows(
                FactoryException.class,
                () -> factory.getCommand("BAD")
        );

        assertEquals("Class is not a command.Command: java.lang.String", ex.getMessage());
    }
}
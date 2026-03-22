package factory;

import command.Command;
import exceptions.FactoryException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CommandFactory {

    private final Properties config = new Properties();

    public CommandFactory(String factory) throws FactoryException {
        try (InputStream is = CommandFactory.class.getResourceAsStream(factory)) {
            if (is == null) {
                throw new FactoryException("Config file not found: " + factory);
            }
            config.load(is);
        } catch (IOException e) {
            throw new FactoryException("Failed to load config", e);
        }
    }

    public Command getCommand(String commandName) throws FactoryException{
        String command = config.getProperty(commandName);

        if (command == null) {
            throw new FactoryException("Unknown command: " + commandName);
        }

        try {
            Class<?> aClass = Class.forName(command);
            // getDeclaredConstructor() - ищет конструктор
            // newInstance() - исполняет его
            Object obj = aClass.getDeclaredConstructor().newInstance();
            return (Command) obj;
        } catch (ClassNotFoundException e) {
            throw new FactoryException("command.Command class not found: " + command, e);
        } catch (ReflectiveOperationException e) {
            throw new FactoryException("Cannot create command: " + command, e);
        } catch (ClassCastException e) {
            throw new FactoryException("Class is not a command.Command: " + command, e);
        }
    }

}

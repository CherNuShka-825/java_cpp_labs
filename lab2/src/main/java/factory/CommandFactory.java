package factory;

import command.Command;
import exceptions.FactoryException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class CommandFactory {

    private static final Logger logger = Logger.getLogger(CommandFactory.class.getName());

    private final Properties config = new Properties();

    public CommandFactory(String factory) throws FactoryException {
        logger.info("Loading factory config: " + factory);

        try (InputStream is = CommandFactory.class.getClassLoader().getResourceAsStream(factory)) {
            if (is == null) {
                logger.severe("Config file not found: " + factory);
                throw new FactoryException("Config file not found: " + factory);
            }
            config.load(is);
            logger.info("Config loaded successfully");
            logger.fine("Loaded command: " + config);

        } catch (IOException e) {
            logger.severe("Failed to load config" + e.getMessage());
            throw new FactoryException("Failed to load config", e);
        }
    }

    public Command getCommand(String commandName) throws FactoryException{
        logger.info("Request command: " + commandName);
        String command = config.getProperty(commandName);

        if (command == null) {
            logger.warning("Unknown command: " + commandName);
            throw new FactoryException("Unknown command: " + commandName);
        }

        try {
            Class<?> aClass = Class.forName(command);
            // getDeclaredConstructor() - ищет конструктор
            // newInstance() - исполняет его
            Object obj = aClass.getDeclaredConstructor().newInstance();

            logger.info("Command instance created: " + commandName);

            return (Command) obj;

        } catch (ClassNotFoundException e) {
            logger.severe("Class not found: " + command);
            throw new FactoryException("Class not found: " + command, e);

        } catch (ReflectiveOperationException e) {
            logger.severe("Command class not found: " + command);
            throw new FactoryException("Cannot create command: " + command, e);

        } catch (ClassCastException e) {
            logger.severe("Class is not Command: " + command);
            throw new FactoryException("Class is not a command.Command: " + command, e);
        }
    }

}

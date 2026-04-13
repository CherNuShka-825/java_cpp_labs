package calculator;

import command.Command;
import command.Context;
import exceptions.CalcException;
import factory.CommandFactory;

import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Logger;

public class Calculator {

    private static final Logger logger = Logger.getLogger(Calculator.class.getName());

    private final Context context;
    private final CommandFactory factory;

    public Calculator(CommandFactory factory) {
        this.context = new Context();
        this.factory = factory;
        logger.info("Calculator created");
    }

    public void run(Scanner scanner) {
        logger.info("Calculator run started");

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            if (line.isEmpty()) {
                logger.info("Skipped empty line");
                continue;
            }

            if (line.startsWith("#")) {
                logger.info("Skipped comment line");
                continue;
            }

            logger.info("Read line: " + line);

            try {
                String[] parts = line.split("\\s+");
                String commandName = parts[0];
                String[] args = Arrays.copyOfRange(parts, 1, parts.length);

                logger.info("Executing command: " + commandName);
                logger.fine("Command argument: " + Arrays.toString(args));

                Command command = factory.getCommand(commandName);
                command.execute(context, args);

                logger.info("Command completed: " + commandName);

            } catch (CalcException e) {
                logger.warning("Command failed for line \"" + line + "\": " + e.getMessage());
                System.err.println("Error: " + e.getMessage());
            }
        }
        logger.info("Calculator run finished");
    }

}

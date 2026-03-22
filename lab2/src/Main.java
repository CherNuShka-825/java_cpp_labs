import calculator.Calculator;
import exceptions.FactoryException;
import factory.CommandFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        LogConfig.setup();

        logger.info("Application started");

        if (args.length > 1) {
            logger.warning("Invalid number of argument: " + args.length);
            System.err.print("Error: expected 0 or 1 argument, got " + args.length);
            return;
        }

        Scanner scanner;
        try {
            scanner = createScanner(args);
        } catch (FileNotFoundException e) {
            logger.severe("File not found: " + args[0]);
            System.err.println("File not found: " + args[0]);
            return;
        }

        try {
            logger.info("Initializing factory");
            CommandFactory factory = new CommandFactory("factoryConfig");

            logger.info("Creating calculator");
            Calculator calculator = new Calculator(factory);

            logger.info("Starting calculator");
            calculator.run(scanner);
        } catch (FactoryException e) {
            logger.severe("Failed to initialize calculator: " + e.getMessage());
            System.err.println("Failed to initialize calculator: " + e.getMessage());
        }
        logger.info("Application finished");
    }

    private static Scanner createScanner(String[] args) throws FileNotFoundException {
        if (args.length == 1) {
            logger.info("Using file input: " + args[0]);
            return new Scanner(new File(args[0]));
        }
        logger.info("Using standard input");
        return new Scanner(System.in);
    }

}
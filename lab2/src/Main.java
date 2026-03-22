import calculator.Calculator;
import exceptions.FactoryException;
import factory.CommandFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            LogConfig.setup();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (args.length > 1) {
            System.err.print("Error: expected 0 or 1 argument, got " + args.length);
            return;
        }

        Scanner scanner;
        try {
            scanner = createScanner(args);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + args[0]);
            return;
        }

        try {
            CommandFactory factory = new CommandFactory("factoryConfig");
            Calculator calculator = new Calculator(factory);
            calculator.run(scanner);
        } catch (FactoryException e) {
            System.err.println("Failed to initialize calculator: " + e.getMessage());
        }
    }

    private static Scanner createScanner(String[] args) throws FileNotFoundException {
        if (args.length == 1) {
            return new Scanner(new File(args[0]));
        }
        return new Scanner(System.in);
    }

}
package calculator;

import command.Command;
import command.Context;
import exceptions.CalcException;
import factory.CommandFactory;

import java.util.Arrays;
import java.util.Scanner;

public class Calculator {

    private final Context context;
    private final CommandFactory factory;

    public Calculator(CommandFactory factory) {
        this.context = new Context();
        this.factory = factory;
    }

    public void run(Scanner scanner) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }

            try {
                String[] parts = line.split("\\s+");
                String commandName = parts[0];
                String[] args = Arrays.copyOfRange(parts, 1, parts.length);

                Command command = factory.getCommand(commandName);
                command.execute(context, args);
            } catch (CalcException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

}

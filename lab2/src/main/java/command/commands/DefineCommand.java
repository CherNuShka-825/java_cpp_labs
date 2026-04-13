package command.commands;

import command.Command;
import command.Context;
import exceptions.CommandException;

import java.util.Deque;
import java.util.logging.Logger;

public class DefineCommand implements Command {

    private static final Logger logger = Logger.getLogger(DefineCommand.class.getName());

    @Override
    public void execute(Context context, String[] args) throws CommandException {
        if (args.length != 2) {
            logger.warning("DEFINE expected 2 args, got " + args.length);
            throw new CommandException("DEFINE expected 2 args, got " + args.length);
        }

        String name = args[0];

        double value;
        try {
            value = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            logger.warning("DEFINE value must be a number: " + args[1]);
            throw new CommandException("DEFINE value must be a number: " + args[1], e);
        }

        context.setVariables(name, value);
        logger.info("DEFINE: " + name + " = " + value);
    }

}

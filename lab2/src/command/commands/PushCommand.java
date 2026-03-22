package command.commands;

import command.Command;
import command.Context;
import exceptions.CommandException;

import java.util.logging.Logger;

public class PushCommand implements Command {

    private static final Logger logger = Logger.getLogger(PushCommand.class.getName());

    @Override
    public void execute(Context context, String[] args) throws CommandException {
        if (args.length != 1) {
            logger.warning("PUSH expected 1 args, got " + args.length);
            throw new CommandException("PUSH expected 1 args, got " + args.length);
        }

        double value;
        try {
            logger.fine("PUSH parsed number: " + args[0]);
            value = Double.parseDouble(args[0]);
        } catch (NumberFormatException e) {
            logger.fine("PUSH parsed variable: " + args[0]);
            value = context.getVariable(args[0]);
        }

        context.push(value);
        logger.info("PUSH: " + value);
    }

}

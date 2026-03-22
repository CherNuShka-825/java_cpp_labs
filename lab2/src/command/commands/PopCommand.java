package command.commands;

import command.Command;
import exceptions.CommandException;
import command.Context;

import java.util.logging.Logger;

public class PopCommand implements Command {

    private static final Logger logger = Logger.getLogger(PopCommand.class.getName());

    @Override
    public void execute(Context context, String[] args) throws CommandException {
        if (args.length != 0) {
            logger.warning("POP expected 0 args, got " + args.length);
            throw new CommandException("POP expected 0 args, got " + args.length);
        }

        double a = context.pop();
        logger.info("POP: " + a);
    }

}
